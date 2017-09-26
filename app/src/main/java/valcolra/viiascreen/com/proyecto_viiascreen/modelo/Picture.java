package valcolra.viiascreen.com.proyecto_viiascreen.modelo;



/**
 * Created by Vic on 25/09/2017.
 */

public class Picture {
    private String numIncidente;
    private String fechaIncidente;
    private String statuIncidente;
    private String userIncidente;
    private String puntoIncidente;

    public Picture(String numIncidente, String fechaIncidente, String statuIncidente, String userIncidente, String puntoIncidente) {
        this.numIncidente = numIncidente;
        this.fechaIncidente = fechaIncidente;
        this.statuIncidente = statuIncidente;
        this.userIncidente = userIncidente;
        this.puntoIncidente = puntoIncidente;
    }

    public String getNumIncidente() {
        return numIncidente;
    }

    public void setNumIncidente(String numIncidente) {
        this.numIncidente = numIncidente;
    }

    public String getFechaIncidente() {
        return fechaIncidente;
    }

    public void setFechaIncidente(String fechaIncidente) {
        this.fechaIncidente = fechaIncidente;
    }

    public String getStatuIncidente() {
        return statuIncidente;
    }

    public void setStatuIncidente(String statuIncidente) {
        this.statuIncidente = statuIncidente;
    }

    public String getUserIncidente() {
        return userIncidente;
    }

    public void setUserIncidente(String userIncidente) {
        this.userIncidente = userIncidente;
    }

    public String getPuntoIncidente() {
        return puntoIncidente;
    }

    public void setPuntoIncidente(String puntoIncidente) {
        this.puntoIncidente = puntoIncidente;
    }
}
