package uteq.student.project.carof.models;

public class PublicacionModel {

    String id,titulo,vehiculo;
    Long valor_diario;

    public PublicacionModel(String id, String titulo, String vehiculo, Long valor_diario) {
        this.id = id;
        this.titulo = titulo;
        this.vehiculo = vehiculo;
        this.valor_diario = valor_diario;
    }

    public PublicacionModel( ) {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(String vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Long getValor_diario() {
        return valor_diario;
    }

    public void setValor_diario(Long valor_diario) {
        this.valor_diario = valor_diario;
    }
}
