package gngo.com.example.zoolist.ui.main;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.List;

import gngo.com.example.zoolist.R;

public class AnimalRecyclerViewAdapter extends RecyclerView.Adapter<AnimalRecyclerViewAdapter.ViewHolder> {
/*
    @NotNull*/
    public final static String TAG="ZooListAdapter";

    public interface OnAdapterItemInteraction{
        void onItemSelected(Animal animal);
}

    private final List<Animal> mValues;
    final OnAdapterItemInteraction mListener;

    public AnimalRecyclerViewAdapter(List<Animal> items, OnAdapterItemInteraction listener){
        mValues = items;
        mListener = listener;
    }
    @NonNull
    @Override
    public AnimalRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_linear, parent, false);
        return new ViewHolder(view);
        //should line 35 be parent?
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.txtName.setText(mValues.get(position).getName());
        holder.txtLocation.setText(mValues.get(position).getLocation());
        if(TextUtils.equals(mValues.get(position).getType(),Animal.MAMMAL))
            holder.imgSpecies.setImageResource(R.drawable.ic_lion);
        else if (TextUtils.equals(mValues.get(position).getType(),Animal.BIRD))
            holder.imgSpecies.setImageResource(R.drawable.ic_bird);
        else
            holder.imgSpecies.setImageResource(R.drawable.ic_lizard);
        final Animal animal = mValues.get(position);

        holder.mView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (null != mListener){
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onItemSelected(animal);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void add(Animal item){
    Log.d(TAG,"ADD" + item.toString());
    mValues.add(item);
    notifyItemInserted(mValues.size()-1);}

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView imgSpecies;
        public final TextView txtName;
        public final TextView txtLocation;
        public ViewHolder(View view){
            super(view);
            mView = view;
            txtName = view.findViewById(R.id.name);
            txtLocation = view.findViewById(R.id.location);
            imgSpecies = view.findViewById(R.id.icon);
        }

        @Override
        public String toString(){
            return super.toString()+ " '" + txtLocation.getText() + "'";
        }
    }
}
