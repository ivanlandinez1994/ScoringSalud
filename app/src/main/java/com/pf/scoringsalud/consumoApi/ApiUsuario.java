package com.pf.scoringsalud.consumoApi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pf.scoringsalud.GetUser;
import com.pf.scoringsalud.HomeActivity;
import com.pf.scoringsalud.RegisterActivity;
import com.pf.scoringsalud.User.User;
import com.pf.scoringsalud.interfaces.UserApi;


import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiUsuario {
    private static UserApi apiUsuario;
    private static String uriMongo = "http://192.168.0.26:8081/";
    final Retrofit retrofit = new Retrofit.Builder().baseUrl(uriMongo)
            .addConverterFactory(GsonConverterFactory.create()).build();

    final UserApi userApi = retrofit.create(UserApi.class);
    Response resp;

    /*public static UserApi getUserApi(){
        // Creamos un interceptor y le indicamos el log level a usar


        if (apiUsuario == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(uriMongo)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build()) // <-- usamos el log level
                    .build();
            apiUsuario = retrofit.create(UserApi.class);
        }

        return apiUsuario;
    }*/

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
                    Log.i("RESPONSE",response.body().string());
                    Log.i("ERROR Successful", Integer.toString(response.code()));
                } catch (Exception e) {
                    Log.i("Excepcion", "register exception"+e.getMessage());
                    //Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //Toast.makeText(context, "Error de conexion", Toast.LENGTH_SHORT).show();
                Log.i("Failure", t.toString());
            }
        });
    }

    public void getUsuarioApiAsincrono(final String mail, final Class activityDestino, final Context actualContext){
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
                goIntent(activityDestino, null, actualContext, null);
            }
        });
    }

    private void goIntent(Class activityDestino, Response response, Context context, String mail){
        Log.i("Inicio Go Intent: ", activityDestino.toString());
        Intent intent;
        if (response.body() instanceof User && (User)response.body() != null){
            intent = new Intent(context, activityDestino);
            intent.putExtra("usuario", (User)response.body());
            Log.i("NO NULO: ","user not null");
            context.startActivity(intent);
        }else if(response != null && response.code()==200){
            intent = new Intent(context, activityDestino);
            intent.putExtra("email", mail);
            Log.i("NO NULO: ","user not null");
            context.startActivity(intent);
        }else if(mail!=null){
            intent = new Intent(context, RegisterActivity.class);
            intent.putExtra("email", mail);
            Log.i("NULO: ","user null");
            context.startActivity(intent);
        }else{
            Log.i("context", context.toString());
            Toast.makeText(context, "Error de conexion intente nuevamente",Toast.LENGTH_LONG);
            Log.i("Disconnected: ","Return");
        }
    }

}
