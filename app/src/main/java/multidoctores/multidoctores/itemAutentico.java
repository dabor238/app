package multidoctores.multidoctores;

/**
 * Created by dabor238 on 3/4/16.
 */
public class itemAutentico {

    private String Nombre;
    private String IdUsuario;
    private String Correo;
    private boolean Entra;


    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        IdUsuario = idUsuario;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public boolean isEntra() {
        return Entra;
    }

    public void setEntra(boolean entra) {
        Entra = entra;
    }
}
