package com.tomas.meteorites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    MapView mapView;
    private GoogleMap googleMap;
    MeteoriteAdapter adapter;
    private ListView meteorListView;
    List<Meteorite> meteoriteList;
    private Realm realm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        meteorListView = (ListView)findViewById(R.id.meteorList);

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Realm.init(this);
        realm = Realm.getDefaultInstance();
        RealmResults<Meteorite> results = realm.where(Meteorite.class).findAll();
        if (results.size() == 0){
            UpdateData();

        }
        else {
            meteoriteList = results;
            adapter = new MeteoriteAdapter(getApplicationContext(), R.layout.item, meteoriteList);
            meteorListView.setAdapter(adapter);
            TextView meteorCount = (TextView)findViewById(R.id.meteorNumber);
            meteorCount.setText("Meteorites since 2011: " + meteoriteList.size());
        }



        meteorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Meteorite meteorite = adapter.getItem(position);
                mapView.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap mMap) {
                        googleMap = mMap;

                        // For dropping a marker at a point on the Map

                        LatLng position = new LatLng(Float.parseFloat(meteorite.reclat), Float.parseFloat(meteorite.reclong));
                        CameraPosition cameraPosition = new CameraPosition.Builder().target(position).zoom(12).build();
                        googleMap.addMarker(new MarkerOptions().position(position)
                                .title(meteorite.name));
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    }
                });
            }
        });

    }

    public void UpdateData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://data.nasa.gov/resource/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NASAService service = retrofit.create(NASAService.class);
        Call<List<Meteorite>> meteoriteCall = service.getMeteorites("y77d-th95.json");
        meteoriteCall.enqueue(new Callback<List<Meteorite>>() {
            @Override
            public void onResponse(Call<List<Meteorite>> call, Response<List<Meteorite>> response) {
                meteoriteList = new ArrayList<Meteorite>();

                for (final Meteorite meteorite : response.body()) {
                    if (meteorite.year != null && Integer.parseInt(meteorite.year.substring(0, 4)) >= 2011) {
                        meteoriteList.add(meteorite);
                        mapView.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(GoogleMap mMap) {
                                googleMap = mMap;

                            }
                        });
                    }
                }
                Collections.sort(meteoriteList, new MeteoriteComparator());
                realm.beginTransaction();
                realm.copyToRealm(meteoriteList);
                realm.commitTransaction();
                adapter = new MeteoriteAdapter(getApplicationContext(), R.layout.item, meteoriteList);
                meteorListView.setAdapter(adapter);
                TextView meteorCount = (TextView)findViewById(R.id.meteorNumber);
                meteorCount.setText("Meteorites since 2011: " + meteoriteList.size());
            }

            @Override
            public void onFailure(Call<List<Meteorite>> call, Throwable t) {
                int x = 5;
            }
        });
    }
}
