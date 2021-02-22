package uteq.student.project.carof.models;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;

public class VehiculoModel {

    private String placa;
    private String transmision;
    private String marca;
    private String modelo;
    private String traccion;
    private String estado;
    private String combustible;
    private String url;
    private int anio;
    private int puerta;
    private int pasajero;
    private boolean aire_acondicionado;
    private boolean sunroof;
    private DocumentReference gpsModel;
    private DocumentReference duenioModel;
    private FieldValue created_at;
    private FieldValue updated_at;

    public VehiculoModel() {
    }

    public VehiculoModel(String placa, String transmision, String marca, String modelo, String traccion, String estado, String combustible, String url, int anio, int puerta, int pasajero, boolean aire_acondicionado, boolean sunroof, DocumentReference gpsModel, DocumentReference duenioModel) {
        this.placa = placa;
        this.transmision = transmision;
        this.marca = marca;
        this.modelo = modelo;
        this.traccion = traccion;
        this.estado = estado;
        this.combustible = combustible;
        this.url = url;
        this.anio = anio;
        this.puerta = puerta;
        this.pasajero = pasajero;
        this.aire_acondicionado = aire_acondicionado;
        this.sunroof = sunroof;
        this.gpsModel = gpsModel;
        this.duenioModel = duenioModel;
    }

    public VehiculoModel(String placa, String transmision, String marca, String modelo, String traccion, String estado, String combustible, String url, int anio, int puerta, int pasajero, boolean aire_acondicionado, boolean sunroof, DocumentReference gpsModel, DocumentReference duenioModel, FieldValue created_at, FieldValue updated_at) {
        this.placa = placa;
        this.transmision = transmision;
        this.marca = marca;
        this.modelo = modelo;
        this.traccion = traccion;
        this.estado = estado;
        this.combustible = combustible;
        this.url = url;
        this.anio = anio;
        this.puerta = puerta;
        this.pasajero = pasajero;
        this.aire_acondicionado = aire_acondicionado;
        this.sunroof = sunroof;
        this.gpsModel = gpsModel;
        this.duenioModel = duenioModel;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public FieldValue getCreated_at() {
        return created_at;
    }

    public void setCreated_at(FieldValue created_at) {
        this.created_at = created_at;
    }

    public FieldValue getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(FieldValue updated_at) {
        this.updated_at = updated_at;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getTransmision() {
        return transmision;
    }

    public void setTransmision(String transmision) {
        this.transmision = transmision;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getTraccion() {
        return traccion;
    }

    public void setTraccion(String traccion) {
        this.traccion = traccion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCombustible() {
        return combustible;
    }

    public void setCombustible(String combustible) {
        this.combustible = combustible;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getPuerta() {
        return puerta;
    }

    public void setPuerta(int puerta) {
        this.puerta = puerta;
    }

    public int getPasajero() {
        return pasajero;
    }

    public void setPasajero(int pasajero) {
        this.pasajero = pasajero;
    }

    public boolean isAire_acondicionado() {
        return aire_acondicionado;
    }

    public void setAire_acondicionado(boolean aire_acondicionado) {
        this.aire_acondicionado = aire_acondicionado;
    }

    public boolean isSunroof() {
        return sunroof;
    }

    public void setSunroof(boolean sunroof) {
        this.sunroof = sunroof;
    }

    public DocumentReference getGpsModel() {
        return gpsModel;
    }

    public void setGpsModel(DocumentReference gpsModel) {
        this.gpsModel = gpsModel;
    }

    public DocumentReference getDuenioModel() {
        return duenioModel;
    }

    public void setDuenioModel(DocumentReference duenioModel) {
        this.duenioModel = duenioModel;
    }
}
