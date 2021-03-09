package uteq.student.project.carof.interfaces;

public interface IComunicacionFragments {

    void vehiculo(String id);

    void addvehiculo(String id);

    void editvehiculo(String id_vehiculo, String id_duenio);

    void contratos();

    void monitoreo();

    void publicacion(String id);

    void addpublicacion(String id);

    void editpublicacion(String id,String id_publi, String id_vehi);

    void signUp();

}