package uteq.student.project.carof.models;

import com.google.firebase.Timestamp;

public class LogModel {
    private String estado_log;
    private double latitud_log;
    private double longitud_log;
    private double velocidad_log;
    private boolean bloqueado_log;
    private Timestamp fecha_log;

    public LogModel() {
    }

    public LogModel(String estado_log, double latitud_log, double longitud_log, double velocidad_log, boolean bloqueado_log, Timestamp fecha_log) {
        this.estado_log = estado_log;
        this.latitud_log = latitud_log;
        this.longitud_log = longitud_log;
        this.velocidad_log = velocidad_log;
        this.bloqueado_log = bloqueado_log;
        this.fecha_log = fecha_log;
    }

    public String getEstado_log() {
        return estado_log;
    }

    public void setEstado_log(String estado_log) {
        this.estado_log = estado_log;
    }

    public double getLatitud_log() {
        return latitud_log;
    }

    public void setLatitud_log(double latitud_log) {
        this.latitud_log = latitud_log;
    }

    public double getLongitud_log() {
        return longitud_log;
    }

    public void setLongitud_log(double longitud_log) {
        this.longitud_log = longitud_log;
    }

    public double getVelocidad_log() {
        return velocidad_log;
    }

    public void setVelocidad_log(double velocidad_log) {
        this.velocidad_log = velocidad_log;
    }

    public boolean isBloqueado_log() {
        return bloqueado_log;
    }

    public void setBloqueado_log(boolean bloqueado_log) {
        this.bloqueado_log = bloqueado_log;
    }

    public Timestamp getFecha_log() {
        return fecha_log;
    }

    public void setFecha_log(Timestamp fecha_log) {
        this.fecha_log = fecha_log;
    }
}
