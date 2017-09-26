package valcolra.viiascreen.com.proyecto_viiascreen.Posteando;

/**
 * Created by Vic on 25/09/2017.
 */
public class Post2 {
    // Atributos
    private String Punto;
    private String fecha;
    private String usuario;
    private String imagen;


    public Post2() {
    }

    public Post2(String punto, String fecha, String usuario, String imagen) {
        Punto = punto;
        this.fecha = fecha;
        this.usuario = usuario;
        this.imagen = imagen;
    }

    public String getPunto() {
        return Punto;
    }

    public void setPunto(String punto) {
        Punto = punto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
