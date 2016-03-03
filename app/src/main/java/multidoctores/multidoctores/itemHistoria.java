package multidoctores.multidoctores;

/**
 * Created by dabor238 on 3/3/16.
 */
public class itemHistoria {

    private String Mensaje;
    private boolean Escribe;
    private String IdDetalleChat;


    public String getMensaje() {
        return Mensaje;
    }

    public void setMensaje(String mensaje) {
        Mensaje = mensaje;
    }

    public boolean isEscribe() {
        return Escribe;
    }

    public void setEscribe(boolean escribe) {
        Escribe = escribe;
    }

    public String getIdDetalleChat() {
        return IdDetalleChat;
    }

    public void setIdDetalleChat(String idDetalleChat) {
        IdDetalleChat = idDetalleChat;
    }
}
