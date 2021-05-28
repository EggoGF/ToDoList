package gngo.com.example.todolist.ui.main;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import gngo.com.example.todolist.R;

public class AnimalRecyclerViewAdapter extends RecyclerView.Adapter<AnimalRecyclerViewAdapter.ViewHolder> {
/*
    @NotNull*/
    public final static String TAG="ZooListAdapter";

    public interface OnAdapterItemInteraction{
        void onItemSelected(Task task);
}

    private final List<Task> mValues;
    final OnAdapterItemInteraction mListener;

    public AnimalRecyclerViewAdapter(List<Task> items, OnAdapterItemInteraction listener){
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

        final Task task = mValues.get(position);

        holder.mView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (null != mListener){
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onItemSelected(task);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void add(Task item){
    Log.d(TAG,"ADD" + item.toString());
    mValues.add(item);
    notifyItemInserted(mValues.size()-1);}

    public void remove(Task item){
        int position = mValues.indexOf(item);
        mValues.remove(position);
        notifyItemRemoved(position);
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        public final TextView txtName;
        public final TextView txtLocation;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            txtName = view.findViewById(R.id.task);
            txtLocation = view.findViewById(R.id.description);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + txtLocation.getText() + "'";
        }
    }

}
