package multidoctores.multidoctores;

import com.squareup.okhttp.RequestBody;



import retrofit.Call;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;

/**
 * Created by dabor238 on 1/20/16.
 */
public interface MyApiEndpointInterface {



    @GET("/api/Usuarios/fichaMedica")
    Call<Usuario> getFicha(@Query("Mail") String Mail);

    @GET("/api/Usuarios/tienePlan")
    Call<Plan> getPlan(@Query("mail") String email);

    @GET("/api/usuarios/doctorbio")
    Call<Bio> getBio(@Query("idchat") String chatId);

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

    @POST("/api/usuarios/ingresoFicha")
    Call<Boolean> updateFicha(@Query("usuario") String usuario,@Query("alergia") String alergia,@Query("enfermedad") String enfermedad,@Query("dia") String dia,@Query("mes") String mes,@Query("anio") String anio,@Query("genero") String genero,@Query("celular") String celular,@Query("pais") String pais);


    @POST("/api/usuarios/recuperando")
    Call<Boolean> RecuperarUser(@Query("mail") String mail);

    @POST("/api/Usuarios/asignarUsuario")
    Call<Boolean> AsignarUser(@Query("usuario") String usuario);



    @FormUrlEncoded
    @POST("/api/Usuarios/complex")
    Call<Boolean> test(@Field("status") String status, @Field("usuario") String idUsuario, @Field("fileName") String fileName,@Field("chatId") String chatId);


}
