package com.tomas.meteorites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView meteorListView = (ListView)findViewById(R.id.meteorList);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://data.nasa.gov/resource/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NASAService service = retrofit.create(NASAService.class);
        Call<List<Meteorite>> meteoriteCall = service.getMeteorites("y77d-th95.json");
        meteoriteCall.enqueue(new Callback<List<Meteorite>>() {
            @Override
            public void onResponse(Call<List<Meteorite>> call, Response<List<Meteorite>> response) {
                List<Meteorite> meteoriteList = response.body();
                Collections.sort(meteoriteList, new MeteoriteComparator());
                MeteoriteAdapter adapter = new MeteoriteAdapter(getApplicationContext(), R.layout.item, meteoriteList);
                meteorListView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Meteorite>> call, Throwable t) {
                int x = 5;
            }

        });
    }
}
