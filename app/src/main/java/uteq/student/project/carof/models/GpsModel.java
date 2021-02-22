package uteq.student.project.carof.models;

import com.google.firebase.firestore.FieldValue;

public class GpsModel {
    private String alias;
    private String descripcion;
    private String estado;
    private FieldValue created_at;
    private FieldValue updated_at;

    public GpsModel() {
        estado = "ACTIVO";
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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
}
