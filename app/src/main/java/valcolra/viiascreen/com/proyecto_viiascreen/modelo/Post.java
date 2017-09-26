package valcolra.viiascreen.com.proyecto_viiascreen.modelo;

/**
 * Created by Vic on 25/09/2017.
 */

public class Post {

    private String codpunto;
    private String puntos;
    private String fecha;
    private String usuario;
    private String status;
    private String urlImagen;


    public Post(String codpunto, String puntos, String fecha, String usuario, String status, String urlImagen) {
        this.codpunto = codpunto;
        this.puntos = puntos;
        this.fecha = fecha;
        this.usuario = usuario;
        this.status = status;
        this.urlImagen = urlImagen;
    }

    public Post() {

    }

    public String getCodpunto() {
        return codpunto;
    }

    public void setCodpunto(String codpunto) {
        this.codpunto = codpunto;
    }

    public String getPuntos() {
        return puntos;
    }

    public void setPuntos(String puntos) {
        this.puntos = puntos;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }
}
