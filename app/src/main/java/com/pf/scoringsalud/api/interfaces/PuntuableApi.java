package com.pf.scoringsalud.api.interfaces;

import com.pf.scoringsalud.api.infraestructura.PuntuableEndPoint;
import com.pf.scoringsalud.puntuable.Puntuable;
import com.pf.scoringsalud.user.Domain.User;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface PuntuableApi {
    /*@GET("puntuable/obtenerPuntuable")
    public Call<Puntuable> obtenerPuntuable(@Query("id") String id);*/

    @POST("puntuable/crearPuntuable")
    public Call<ResponseBody> crearPuntuable(@Body PuntuableEndPoint puntuable);

    @PUT("puntuable/actualizarPuntuable")
    public Call<ResponseBody> actualizarPuntuable(@Query("mail") String mail,
                                               @Query("tipo") String tipo,
                                               @Query("unidades") int cantidad);

    @GET("puntuable/obtenerPuntosDia")
    public Call<ResponseBody> obtenerPuntosDia(@Query("mail") String mail);

    @GET("puntuable/obtenerReportes")
    public Call<ArrayList<ResponseBody>> obtenerReportes(@Query("mail") String mail,
                                                      @Query("tipo") String tipo);
}
