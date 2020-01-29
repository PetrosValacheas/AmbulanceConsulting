package apiInterface;

import java.util.ArrayList;
import java.util.Map;


import model.Isuue;
import model.diagnosis;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface getDiagnosisService {


    @GET("diagnosis")
    Call<ArrayList<diagnosis>>  getDiagnosis(@Query("token") String TOKEN , @Query("language") String language,
                                             @Query("symptoms") String symptoms, @Query("gender") String gender, @Query("year_of_birth") String year);

}
