package com.example.todo_app;

import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo_app.model.Project;
import com.example.todo_app.model.Todo;

import java.util.List;


public class TodoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_Todo = 1;
    public static final int TYPE_Project = 2;
    private List<Object> listObject;
    OnItemClickListener onItemClickListener;

    public TodoAdapter(List<Object> listObject, OnItemClickListener onItemClickListener) {
        this.listObject = listObject;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = 0;
        RecyclerView.ViewHolder viewHolder;
        switch (viewType){
            case TYPE_Todo:
                layout = R.layout.item_layout;
                View todoView = LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
                viewHolder = new TodoViewHolder(todoView,onItemClickListener);
                break;
            case TYPE_Project:
                layout = R.layout.project_name;
                View projectView = LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
                viewHolder = new ProjectViewHolder(projectView);
                break;

                default:
                    viewHolder = null;
                    break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            int viewType =  holder.getItemViewType();
            switch (viewType){
                case TYPE_Todo:
                    Todo todo = (Todo)listObject.get(position);
                    ((TodoViewHolder)holder).text.setText(todo.getText());
                    if (todo.getIsCompleted()){
                        ((TodoViewHolder)holder).selectState.setChecked(true);
                        ((TodoViewHolder)holder).text.setPaintFlags(((TodoViewHolder)holder).text.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
                    } else {
                        ((TodoViewHolder)holder).selectState.setChecked(false);
                        ((TodoViewHolder)holder).text.setPaintFlags(((TodoViewHolder)holder).text.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                    }
                    break;
                case TYPE_Project:
                    Project project = (Project)listObject.get(position);
                    ((ProjectViewHolder)holder).title.setText(project.getTitle());
                    break;
            }


    }

    @Override
    public int getItemViewType(int position) {
        if (listObject.get(position) instanceof Todo){
            return TYPE_Todo;
        } else {
            return TYPE_Project;
        }
    }

    @Override
    public int getItemCount() {
        return listObject.size();
    }

    public class TodoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        OnItemClickListener onItemClickListener;
        public TextView text;
        public CheckBox selectState;
        public TodoViewHolder(@NonNull final View itemView, final OnItemClickListener onItemClickListener) {
            super(itemView);
            itemView.setOnClickListener(this);
            text = itemView.findViewById(R.id.text_todo_textView);
            selectState = itemView.findViewById(R.id.checkbox);
            this.onItemClickListener = onItemClickListener;


            selectState.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(),"select check is " + selectState.isChecked(),Toast.LENGTH_SHORT).show();
                    ((Todo)listObject.get(getAdapterPosition())).setIsCompleted(selectState.isChecked());
                    if (selectState.isChecked()){
                        text.setPaintFlags(text.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
                        onItemClickListener.onItemClick(getAdapterPosition(),true);
                    } else {
                        text.setPaintFlags(text.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                        onItemClickListener.onItemClick(getAdapterPosition(),false);
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {
            //TextView todo = (TextView) v.findViewById(R.id.text_todo_textView);
            Toast.makeText(itemView.getContext(),"select check is " + text.getText().toString(),Toast.LENGTH_SHORT).show();
            if (selectState.isChecked()){
                selectState.setChecked(false);
                ((Todo)listObject.get(getAdapterPosition())).setIsCompleted(false);
                text.setPaintFlags(text.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                onItemClickListener.onItemClick(getAdapterPosition(),false);
            } else {
                selectState.setChecked(true);
                ((Todo)listObject.get(getAdapterPosition())).setIsCompleted(true);
                text.setPaintFlags(text.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
                onItemClickListener.onItemClick(getAdapterPosition(),true);
            }


        }
    }

    public class ProjectViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public ProjectViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.textview_project);
        }
    }

    interface OnItemClickListener{
        void onItemClick(int i,boolean isCompleted);
    }
}
