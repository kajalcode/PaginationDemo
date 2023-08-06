package com.example.pagination0310;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView rv;
    ArrayList<Result> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = findViewById(R.id.rv);
        arrayList = new ArrayList<>();
        APIInterface apiInterface = APPClient.getclient().create(APIInterface.class);
        Call<List<Result>> call = apiInterface.getdata();
        call.enqueue(new Callback<List<Result>>() {
            @Override
            public void onResponse(Call<List<Result>> call, Response<List<Result>> response) {
                for (int i = 0; i < 50; i++) {
                    //response.body().getId();
                    Result result = new Result();
                    result.setId(response.body().get(i).getId());
                    result.setAlbumId(response.body().get(i).getAlbumId());
                    result.setTitle(response.body().get(i).getTitle());
                    result.setUrl(response.body().get(i).getUrl());
                    result.setThumbnailUrl(response.body().get(i).getThumbnailUrl());
                    arrayList.add(result);
                }

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                rv.setLayoutManager(linearLayoutManager);
                RvAdapter rvAdapter = new RvAdapter(MainActivity.this, arrayList);
                rv.setAdapter(rvAdapter);
            }

            @Override
            public void onFailure(Call<List<Result>> call, Throwable t) {
                Log.e("ERROR", String.valueOf(t));

            }


        });
    }
}