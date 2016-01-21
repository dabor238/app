package multidoctores.multidoctores;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by dabor238 on 1/20/16.
 */
public interface MyApiEndpointInterface {



    @GET("/api/Usuarios/fichaMedica?Mail=dabor238@gmail.com")
    Call<Usuario> getUser();

    @GET("/api/Usuarios/arreglo")
    Call<Usuarios> getVarios();



}
