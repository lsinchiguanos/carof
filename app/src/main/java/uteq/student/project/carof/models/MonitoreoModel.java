package uteq.student.project.carof.models;

public class MonitoreoModel {

    private String estado;
    private double latitud;
    private double longitud;
    private int velocidad;
    private boolean bloqueado;

    public MonitoreoModel() {
    }

    public MonitoreoModel(String estado, double latitud, double longitud, int velocidad, boolean bloqueado) {
        this.estado = estado;
        this.latitud = latitud;
        this.longitud = longitud;
        this.velocidad = velocidad;
        this.bloqueado = bloqueado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }
}
