package gngo.com.example.todolist.ui.main;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import gngo.com.example.todolist.R;

public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder> {
/*
    @NotNull*/
    public final static String TAG="ToDoListAdapter";

    public interface OnAdapterItemInteraction{
        void onItemSelected(Task task);
        void onItemLongClick(Task task);
}

    private final List<Task> mValues;
    final OnAdapterItemInteraction mListener;

    public TaskRecyclerViewAdapter(List<Task> items, OnAdapterItemInteraction listener){
        mValues = items;
        mListener = listener;
    }
    @NonNull
    @Override
    public TaskRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_linear, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.txtTitle.setText(mValues.get(position).getTitle());
        holder.txtDescription.setText(mValues.get(position).getDescription());
        holder.txtDueDate.setText(mValues.get(position).getDueDate());

        final Task task = mValues.get(position);

        holder.mView.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v){
                if (null != mListener){
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onItemLongClick(task);
                }
                return true;
            }
        });

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
        public final TextView txtTitle;
        public final TextView txtDescription;
        public final TextView txtDueDate;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            txtTitle = view.findViewById(R.id.task);
            txtDescription = view.findViewById(R.id.description);
            txtDueDate = view.findViewById(R.id.editTextDate);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + txtDescription.getText() + "'";
        }
    }

}
