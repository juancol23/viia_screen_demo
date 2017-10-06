package valcolra.viiascreen.com.proyecto_viiascreen.model;

/**
 * Created by Vic on 25/09/2017.
 */
public class ModelIncidente {
    // Atributos
    private String imagen;
    private String Punto;
    private String fecha;
    private String usuario;
    private String estado;
    private String servicio;
    private String observacion;
    private String Id;

    public ModelIncidente() {
    }

    public ModelIncidente(String punto, String fecha, String usuario, String imagen) {
        Punto = punto;
        this.fecha = fecha;
        this.usuario = usuario;
        this.imagen = imagen;
    }
    /*Constructor para el adapter*/
    public ModelIncidente(String imagen, String punto, String fecha, String usuario, String estado, String  Id) {
        this.imagen = imagen;
        Punto = punto;
        this.fecha = fecha;
        this.usuario = usuario;
        this.estado = estado;
        this.Id = Id;
    }

    public ModelIncidente(String imagen, String punto, String fecha, String usuario, String estado, String servicio, String observacion) {
        this.imagen = imagen;
        this.Punto = punto;
        this.fecha = fecha;
        this.usuario = usuario;
        this.estado = estado;
        this.servicio = servicio;
        this.observacion = observacion;
    }
    /*Encapsulamiento GET and SET*/
    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
