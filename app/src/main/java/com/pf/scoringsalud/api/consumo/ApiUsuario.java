package com.pf.scoringsalud.api.consumo;
import android.util.Log;

//---------Autentication---------//
import com.pf.scoringsalud.activity.RegisterActivity;
import com.pf.scoringsalud.api.infraestructura.StringValueCallback;
import com.pf.scoringsalud.api.infraestructura.UserValueCallback;
import com.pf.scoringsalud.user.Domain.User;
import com.pf.scoringsalud.api.interfaces.UserApi;

//---------Conection---------//
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.pf.scoringsalud.BuildConfig.URL_API;

public class ApiUsuario {
    final Retrofit retrofit = new Retrofit.Builder().baseUrl(URL_API)
            .addConverterFactory(GsonConverterFactory.create()).build();

    final UserApi userApi = retrofit.create(UserApi.class);

    public void registrarUsuario(User user, final StringValueCallback callback){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Call<ResponseBody> call=userApi.crearUsuario(user);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful()) {
                        callback.onSuccess(response.body().toString());
                    }else{
                        callback.onFailure();
                    }
                    Log.i("Response body:",response.body().string());
                } catch (Exception e) {
                    Log.i("Excepcion", "register exception"+e.getMessage());
                    //Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("Failure", t.toString());
            }
        });
    }

    public void getUsuario(final String mail, final UserValueCallback callback){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Asociamos el interceptor a las peticiones
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Call<User> call=userApi.obtenerUsuario(mail);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                try{
                    if(response.isSuccessful()){
                        callback.onSuccess(response.body());
                    }else{
                        callback.onFailure();
                        Log.i("Error parseo response: ",retrofit.baseUrl().toString()+response.code());
                    }
                }catch(Exception e){
                    Log.i("Exception catch onResponse: ", e.getMessage());
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i("Failure (OnFailure): ",t.toString());
            }
        });
    }

}
