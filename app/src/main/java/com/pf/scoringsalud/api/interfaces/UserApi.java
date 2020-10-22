package com.pf.scoringsalud.api.interfaces;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.pf.scoringsalud.User.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserApi {
    @GET("usuario/obtenerUsuario")
    public Call<User> obtenerUsuario(@Query("mail") String mail);

    @POST("usuario/crearUsuario")
    public Call<ResponseBody> crearUsuario(@Body User user);
}
