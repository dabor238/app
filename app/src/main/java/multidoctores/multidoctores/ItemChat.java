package multidoctores.multidoctores;

import java.util.Date;

/**
 * Created by dabor238 on 3/1/16.
 */
public class ItemChat {
    private String IdChat;
    private String Fecha;
    private String Titulo;
    private String Doctor;
    private String Apellido;
    private String Foto;
    private String Bio;



    public String getIdChat() {
        return IdChat;
    }

    public void setIdChat(String idChat) {
        IdChat = idChat;
    }


    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getDoctor() {
        return Doctor;
    }

    public void setDoctor(String doctor) {
        Doctor = doctor;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public String getFoto() {
        return Foto;
    }

    public void setFoto(String foto) {
        Foto = foto;
    }

    public String getBio() {
        return Bio;
    }

    public void setBio(String bio) {
        Bio = bio;
    }
}
