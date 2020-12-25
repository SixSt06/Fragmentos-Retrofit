package mx.com.softwell.fragmentos.core;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import mx.com.softwell.fragmentos.api.API;
import mx.com.softwell.fragmentos.api.apiservices.CategoriasService;
import mx.com.softwell.fragmentos.api.apiservices.OldSchoolService;
import mx.com.softwell.fragmentos.gui.Categorias;
import mx.com.softwell.fragmentos.gui.MainActivity;
import mx.com.softwell.fragmentos.gui.OldSchool;
import mx.com.softwell.fragmentos.model.Categoria;
import mx.com.softwell.fragmentos.model.Juego;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriasController {

    private static CategoriasController instance = null;
    private static MiscController miscController = MiscController.Instance();
    private boolean status = false;
    private String message = "";
    private String data = "";
    private List<Categoria> categorias;
    Type categoriasType = new TypeToken<List<Categoria>>() {
    }.getType();
    private static String TAG = "CategoriaController";

    private CategoriasController() {
    }

    public static CategoriasController Instance() {
        if (instance == null)
            instance = new CategoriasController();
        return instance;
    }

    public void getAll() {
        API.getApi().create(CategoriasService.class).getAll().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    status = jsonObject.getBoolean("status");
                    message = jsonObject.getString("message");
                    if (status) {
                        data = jsonObject.getJSONArray("data").toString();
                        categorias = new Gson().fromJson(data, categoriasType);
                        miscController.CloseWait();
                        Log.e(TAG, data);
                        Log.e(TAG, categorias.toString());
                        ((Categorias) MainActivity.GLOBALS.get("categoriasFragment")).actualizar(categorias);
                    } else {
                        miscController.CloseWait();
                        Log.e(TAG, message);
                    }

                } catch (JSONException e) {
                    miscController.CloseWait();
                    Log.e(TAG, e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }
}
