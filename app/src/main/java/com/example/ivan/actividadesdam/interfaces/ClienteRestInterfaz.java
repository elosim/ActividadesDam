package com.example.ivan.actividadesdam.interfaces;

import com.example.ivan.actividadesdam.pojo.Actividad;
import com.example.ivan.actividadesdam.pojo.Grupo;

import org.json.JSONObject;

import java.util.List;
import javax.security.auth.callback.Callback;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;


/**
 * Created by ivan on 3/3/2016.
 */
public interface ClienteRestInterfaz {

    @GET("restful/api/actividad/ivanvaldes")
    Call<List<Actividad>> getActividades();

    @GET("restful/api/grupo")
    Call<List<Grupo>> getGrupos();

    @POST("restful/api/actividad")
    Call<String> addActivity(@Body Actividad actividad);

    @PUT("restful/api/actividad")
    Call<String> putActivity(@Body Actividad actividad);

    @DELETE("restful/api/actividad/{id}")
    Call<String> delActivity(@Path("id") int id);

}
