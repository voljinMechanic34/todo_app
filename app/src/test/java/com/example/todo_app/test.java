package com.example.todo_app;

import com.example.todo_app.api.ApiTodo;
import com.example.todo_app.model.Todo;

import org.junit.Test;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static junit.framework.TestCase.assertTrue;

public class test {
    @Test
    public void testPostForm() throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://test-app-aaaq.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiTodo service = retrofit.create(ApiTodo.class);

        Call<Todo> call = service.updateBook(1, "Thinking in Java", true);

        Response<Todo> response = call.execute();
        assertTrue(response.isSuccessful());

    }
}
