package com.example.todo_app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.example.todo_app.api.ApiClient;
import com.example.todo_app.api.ApiTodo;
import com.example.todo_app.model.Project;
import com.example.todo_app.model.ProjectAdapter;
import com.example.todo_app.model.Todo;

import java.util.ArrayList;
import java.util.List;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateTodoActivity extends AppCompatActivity implements ProjectAdapter.OnItemClickListenerSave {
    EditText editText;
    Toolbar toolbar;
    private RecyclerView projectRecyclerView;
    int id_project = -1;
    List<Project> projectList;
    private String TAG = "CreateTodoActivity";
    private String text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initCaligraphy();

        toolbar = findViewById(R.id.toolbar);
        editText = findViewById(R.id.editext_todo);
        setSupportActionBar(toolbar);
        projectRecyclerView = findViewById(R.id.recycler_category);
        LinearLayoutManager recyclerLayoutManager = new LinearLayoutManager(this);
        projectRecyclerView.setLayoutManager(recyclerLayoutManager);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        projectList = extras.getParcelableArrayList("ArrayList");
        Log.d("CreateTodoActivity",projectList.get(0).getTitle());

        ProjectAdapter recyclerTodoAdapter = new ProjectAdapter(projectList,this);
        projectRecyclerView.setAdapter(recyclerTodoAdapter);
    }

    @Override
    public void onClickItem(int i) {
            id_project = projectList.get(i).getId();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
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

    public void imageview_back_click(View view) {
        onBackPressed();
    }

    public void savePost(View view) {
        text = editText.getText().toString();
        if (text.length()>0 && id_project != -1){
            postData(id_project,text);
            onBackPressed();
        } else{
            Toast.makeText(this,"Введите данные"
                    ,Toast.LENGTH_SHORT).show();
        }
        }

    void postData(int id_project, String text){
        ApiTodo service = ApiClient.getApiService();
        Call<Todo> call = service.createTodo(id_project,text,false);

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
