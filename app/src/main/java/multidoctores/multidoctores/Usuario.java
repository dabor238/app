package multidoctores.multidoctores;

/**
 * Created by dabor238 on 1/20/16.
 */
public class Usuario {

    private String Nombre;
    private String Telefono;
    private String Alergias;
    private String Enfermedades;
    private String Dia;
    private String Mes;
    private String Ano;
    private boolean Sexo;



    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }


    public String getAlergias() {
        return Alergias;
    }

    public void setAlergias(String alergias) {
        Alergias = alergias;
    }

    public String getEnfermedades() {
        return Enfermedades;
    }

    public void setEnfermedades(String enfermedades) {
        Enfermedades = enfermedades;
    }

    public boolean isSexo() {
        return Sexo;
    }

    public void setSexo(boolean sexo) {
        Sexo = sexo;
    }






    public String getAno() {
        return Ano;
    }

    public void setAno(String ano) {
        Ano = ano;
    }

    public String getMes() {
        return Mes;
    }

    public void setMes(String mes) {
        Mes = mes;
    }

    public String getDia() {
        return Dia;
    }

    public void setDia(String dia) {
        Dia = dia;
    }
}



