package apiInterface;

import model.Symptom;
import model.SymptomList;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface getSymptomsService {
    @GET("symptoms")
    Call<ArrayList<Symptom>> getSymptomData(@Query("token") String TOKEN ,  @Query("language") String language, @Query("format") String format );

}
