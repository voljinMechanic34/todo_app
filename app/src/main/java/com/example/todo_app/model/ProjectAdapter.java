package com.example.todo_app.model;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo_app.R;

import java.util.List;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder> {
    List<Project> projectList;
   private OnItemClickListenerSave onItemClickListenerSave;
    RecyclerView mRecyclerView;

    public ProjectAdapter(List<Project> projectList, OnItemClickListenerSave onItemClickListenerSave) {
        this.projectList = projectList;
        this.onItemClickListenerSave = onItemClickListenerSave;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prjects_list_name,parent,false);
        mRecyclerView = (RecyclerView) parent;
        return new ViewHolder(view,onItemClickListenerSave);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Project project = projectList.get(position);

            holder.title.setText(project.getTitle());
    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        boolean isMarked = false;
        ImageView imageView;
        OnItemClickListenerSave onItemClickListenerSaveV;
        public ViewHolder(@NonNull View itemView, OnItemClickListenerSave onItemClickListenerSaveV) {
            super(itemView);
            this.onItemClickListenerSaveV = onItemClickListenerSaveV;
            itemView.setOnClickListener(this);
            title = itemView.findViewById(R.id.textview__list_project);
            imageView = itemView.findViewById(R.id.imageview_complete);
        }
        @Override
        public void onClick(View v) {

            Toast.makeText(itemView.getContext(),"select check is " + title.getText().toString(),Toast.LENGTH_SHORT).show();
            if (isMarked == false){

                imageView.setVisibility(View.VISIBLE);
                isMarked = true;






            }else{
                imageView.setVisibility(View.INVISIBLE);
                isMarked = false;
            }
            onItemClickListenerSaveV.onClickItem(getAdapterPosition());
        }
    }

    public interface  OnItemClickListenerSave{
        void onClickItem(int i);
    }

}
