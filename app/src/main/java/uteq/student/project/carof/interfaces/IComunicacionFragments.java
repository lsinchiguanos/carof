package uteq.student.project.carof.interfaces;

import android.content.Intent;

public interface IComunicacionFragments {

    void vehiculo(String id);

    void addvehiculo(String id);

    void editvehiculo(String id_vehiculo, String id_duenio);


    void contratos();

    void monitoreo();

    void historial();

    void publicacion();

    void informacion();

    void dispositivoGps();

    void signUp();

}