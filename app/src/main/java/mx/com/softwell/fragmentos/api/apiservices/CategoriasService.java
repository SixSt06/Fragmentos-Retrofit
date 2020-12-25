package mx.com.softwell.fragmentos.api.apiservices;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CategoriasService {
    @GET("categories")
    Call<JsonObject> getAll();

    @GET("categories/{id}")
    Call<JsonObject> getByID(@Path("id") int id);
}
