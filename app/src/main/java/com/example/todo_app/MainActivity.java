package com.example.todo_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import com.example.todo_app.api.ApiClient;
import com.example.todo_app.api.ApiTodo;
import com.example.todo_app.model.Post;
import com.example.todo_app.model.Project;
import com.example.todo_app.model.Todo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements TodoAdapter.OnItemClickListener{
    Toolbar toolbar;
    ArrayList<Project> listProject;
    ArrayList<Object> objectList;
    private static final String TAG = "MainActivity";
    String url = "https://test-app-aaaq.herokuapp.com/posts/getParams";
    private RecyclerView todoRecyclerView;
    private FloatingActionButton fab;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);

        initCaligraphy();
        todoRecyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager recyclerLayoutManager = new LinearLayoutManager(this);
        todoRecyclerView.setLayoutManager(recyclerLayoutManager);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),CreateTodoActivity.class);
                intent.putParcelableArrayListExtra("ArrayList",listProject);

                startActivity(intent);
            }
        });


        firstLoadData();
        //postData();
       // putData();
    }

    void postData(){
        ApiTodo service = ApiClient.getApiService();
        Call<Todo> call = service.createTodo(2,"12345",true);

            call.enqueue(new Callback<Todo>() {
                @Override
                public void onResponse(Call<Todo> call, Response<Todo> response) {
                    Log.e(TAG,"true");
                }

                @Override
                public void onFailure(Call<Todo> call, Throwable t) {
                    Log.e(TAG,"failure");
                }
            });

    }

    void firstLoadData(){
        ApiTodo service = ApiClient.getApiService();
        Call<Post> call = service.getAllTodo();
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Log.e(TAG,"s");
                Log.e(TAG,response.body().getTodos().get(0).getText().toString());
                Log.e(TAG,response.body().getProjects().get(0).getTitle());
                Log.e(TAG,"1"+response.body().getTodos().get(0).getIsCompleted());
                Log.e(TAG,response.body().getTodos().get(0).getId().toString());
                List<Todo> listTodo = response.body().getTodos();
                listProject = (ArrayList<Project>)response.body().getProjects();

                objectList = (ArrayList)getSortedList(listProject,listTodo);
                Log.e(TAG,"size:"+objectList.size());

                TodoAdapter recyclerTodoAdapter = new TodoAdapter(objectList, MainActivity.this);
                todoRecyclerView.setAdapter(recyclerTodoAdapter);

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.e(TAG,"e");
            }
        });
    }

    List<Object> getSortedList(List<Project> listProject, List<Todo> listTodo){
        List<Object> objectList = new ArrayList<>();
                for (int i=0;i<listProject.size();i++){
                    objectList.add(listProject.get(i));

                    for (int j = 0;j<listTodo.size();j++){
                        if (listProject.get(i).getId().intValue() == listTodo.get(j).getIdProject().intValue()){
                            objectList.add(listTodo.get(j));
                        }
                    }
                }
                return objectList;
    }

    void initCaligraphy(){
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/OpenSans-Light.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
    }



    @Override
    public void onItemClick(int i, boolean isCompleted) {
            int id = ((Todo)objectList.get(i)).getId();
            putData(id,isCompleted);

    }
    void putData(int id, boolean isCompleted){
        ApiTodo service = ApiClient.getApiService();
        Call<Todo> call = service.updateTodo(id,isCompleted);

        call.enqueue(new Callback<Todo>() {
            @Override
            public void onResponse(Call<Todo> call, Response<Todo> response) {
                Log.e(TAG,"true");
            }

            @Override
            public void onFailure(Call<Todo> call, Throwable t) {
                Log.e(TAG,"failure");
            }
        });

    }
}
