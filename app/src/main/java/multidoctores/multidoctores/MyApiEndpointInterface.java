package multidoctores.multidoctores;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by dabor238 on 1/20/16.
 */
public interface MyApiEndpointInterface {



    @GET("/api/Usuarios/fichaMedica?Mail=dabor238@gmail.com")
    Call<Usuario> getUser();

    @GET("/api/usuarios/historial")
    Call<ItemChat[]> getChatList(@Query("idUser") String idUser);
   // Call<ChatL> getChatList(@Query("idUser") int UserID);

    @GET("/api/usuarios/chatsHistory")
    Call<itemHistoria[]> getChatHistoria(@Query("chatId") String chatId);
    // Call<ChatL> getChatList(@Query("idUser") int UserID);

    @GET("/api/usuarios/autentico")
    Call<itemAutentico> getAutenticar(@Query("usuario") String usuario,@Query("clave") String clave);

    @GET("/api/Usuarios/arreglo")
    Call<Usuarios> getVarios();

    @GET("/api/Usuarios/chatActivo")
    Call<itemActivo> getActivo(@Query("idUser") String idUser);

    @POST("/api/usuarios/newUserApp")
    Call<Boolean> createUser(@Query("nameNew") String username,@Query("passNew") String pass,@Query("emailNew") String email);

    @POST("/api/usuarios/recuperando")
    Call<Boolean> RecuperarUser(@Query("mail") String mail);

    @POST("/api/Usuarios/asignarUsuario")
    Call<Boolean> AsignarUser(@Query("usuario") String usuario);


}
