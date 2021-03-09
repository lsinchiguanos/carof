package uteq.student.project.carof.models;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;

public class VehiculoModel {

    private String id_vehiculo;
    private String placa;
    private String marca;
    private String modelo;
    private String url;
    private String anio;
    private Timestamp fecha;
    private String vehiculoCustom;

    public VehiculoModel(String id_vehiculo, String placa, String marca, String modelo, String url, String anio) {
        this.id_vehiculo = id_vehiculo;
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.url = url;
        this.anio = anio;
    }
    public VehiculoModel() {

    }

    public VehiculoModel(String id_vehiculo, String placa) {
        this.id_vehiculo = id_vehiculo;
        this.placa = placa;
    }

    public String getId_vehiculo() {
        return id_vehiculo;
    }

    public void setId_vehiculo(String id_vehiculo) {
        this.id_vehiculo = id_vehiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        this.vehiculoCustom = this.placa;
        return vehiculoCustom;
    }
}
