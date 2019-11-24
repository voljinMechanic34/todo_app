package com.example.todo_app.api;

import com.example.todo_app.model.Post;
import com.example.todo_app.model.Todo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiTodo {

    @FormUrlEncoded
    @POST("posts/createTodo")
    Call<Todo> createTodo(@Field("id_project") int id,
                          @Field("text") String text,
                          @Field("isCompleted") boolean isCompleted);

    @FormUrlEncoded
    @PUT("posts/updateTodo")
    Call<Todo> updateTodo(@Field("id_todo") int id,
                          @Field("isCompleted") boolean isCompleted);

    @GET("posts/getParams")
    Call<Post> getAllTodo();
}
