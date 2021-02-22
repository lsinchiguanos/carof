package uteq.student.project.carof.models;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

public class DuenioModel {

    private String cedula;
    private String tipo_identificacion;
    private String apellido_materno;
    private String apellido_paterno;
    private String nombres;
    private String email;
    private String fecha_nacimiento;
    private String user_name;
    private String password;
    private String telefono;

    private String estado;
    private FieldValue created_at;
    private FieldValue updated_at;

    public DuenioModel() {
        estado = "ACTIVO";
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getTipo_identificacion() {
        return tipo_identificacion;
    }

    public void setTipo_identificacion(String tipo_identificacion) {
        this.tipo_identificacion = tipo_identificacion;
    }

    public String getApellido_materno() {
        return apellido_materno;
    }

    public void setApellido_materno(String apellido_materno) {
        this.apellido_materno = apellido_materno;
    }

    public String getApellido_paterno() {
        return apellido_paterno;
    }

    public void setApellido_paterno(String apellido_paterno) {
        this.apellido_paterno = apellido_paterno;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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
