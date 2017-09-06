package com.tomas.meteorites;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Tomas on 6. 9. 2017.
 */

public interface NASAService {
    @GET("{id}")
    Call<List<Meteorite>> getMeteorites(@Path("id") String id);
}
