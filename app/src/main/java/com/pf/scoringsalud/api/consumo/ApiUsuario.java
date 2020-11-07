package com.pf.scoringsalud.api.consumo;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

//---------Autentication---------//
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pf.scoringsalud.activity.RegisterActivity;
import com.pf.scoringsalud.user.Domain.User;
import com.pf.scoringsalud.api.Config;
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

public class ApiUsuario {
    final Retrofit retrofit = new Retrofit.Builder().baseUrl(Config.URL_API)
            .addConverterFactory(GsonConverterFactory.create()).build();

    final UserApi userApi = retrofit.create(UserApi.class);

    public void registrarUsuario(User user,  final Class activityDestino, final Context actualContext){
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
                        FirebaseUser userFirebase;
                        userFirebase = FirebaseAuth.getInstance().getCurrentUser();
                        goIntent(activityDestino,response,actualContext,userFirebase.getEmail());
                    }
                    Log.i("Response body:",response.body().string());
                    Log.i("Response code: ", Integer.toString(response.code()));
                } catch (Exception e) {
                    Log.i("Excepcion", "register exception"+e.getMessage());
                    //Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                String error = "Error de conexion, intente nuevamente";
                Toast.makeText(actualContext, error, Toast.LENGTH_SHORT).show();
                Log.i("Failure", t.toString());
            }
        });
    }

    public void getUsuario(final String mail, final Class activityDestino, final Context actualContext){
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
                        Log.i("Usuario",response.body().getNombre());
                        Log.i("Successful",retrofit.baseUrl().toString()+response.code());
                        Log.i("Failure", response.body().toString());
                    }else{
                        Log.i("Error parseo response: ",retrofit.baseUrl().toString()+response.code());
                    }
                    goIntent(activityDestino, response, actualContext, mail);
                }catch(Exception e){
                    Log.i("Exception catch onResponse: ", e.getMessage());
                    Log.i("Exception catch onResponse: ", activityDestino.toString());
                    goIntent(activityDestino, null, actualContext, mail);
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i("Failure (OnFailure): ",t.toString());
                String error = "Error de conexion, intente nuevamente";
                Toast.makeText(actualContext, error, Toast.LENGTH_SHORT).show();
                goIntent(activityDestino, null, actualContext, null);
            }
        });
    }

    private void goIntent(Class activityDestino, Response response, Context context, String mail){
        Log.i("Inicio Go Intent: ", activityDestino.toString());
        Intent intent;
        try {
            if (response.body() instanceof User && (User) response.body() != null) {
                String nombre;
                nombre = ((User) response.body()).getNombre();
                Toast.makeText(context, "Bienvenid@: "+nombre, Toast.LENGTH_SHORT).show();
                intent = new Intent(context, activityDestino);
                intent.putExtra("usuario", (User) response.body());
                Log.i("NO NULO: ", "user not null");
                context.startActivity(intent);
            } else if (response != null && response.code() == 200) {
                intent = new Intent(context, activityDestino);
                intent.putExtra("email", mail);
                Log.i("NO NULO: ", "user not null");
                context.startActivity(intent);
            } else if (mail != null) {
                intent = new Intent(context, RegisterActivity.class);
                intent.putExtra("email", mail);
                Log.i("NULO: ", "user null");
                context.startActivity(intent);
            } else {
                Log.i("context", context.toString());
                Log.i("Disconnected: ", "Return");
            }
        }catch(NullPointerException e){
            Log.i("Conection Error: ", e.toString());
        }
    }

}
