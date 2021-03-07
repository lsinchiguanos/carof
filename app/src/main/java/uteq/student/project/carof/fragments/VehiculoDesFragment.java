package uteq.student.project.carof.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

import uteq.student.project.carof.MainActivity;
import uteq.student.project.carof.R;
import uteq.student.project.carof.interfaces.IComunicacionFragments;

public class VehiculoDesFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    IComunicacionFragments iComunicacionFragments;
    MenuFragment.OnFragmentInteractionListener onFragmentInteractionListener;
    Activity activity;


    private String mParam1;
    private String mParam2;
    Bundle b = new Bundle();
    String id_vehiculo = "",id_duenio="",img_url="";
    Task<Void> query;
    private FirebaseFirestore firebaseFirestore;
    HashMap hashMap = new HashMap();
    ImageView imgAuto;
    TextInputLayout placaTextInput, marcaTextInput, modeloTextInput, anioTextInput, transmisionTextInput, maletaTextInput, traccionTextInput;
    TextInputLayout puertaTextInput, pasajeroTextInput, gpsTextInput;
    MaterialCheckBox airCheck, sunroofCheck;
    Button btnRegistroVehiculo;
    StorageReference storageRef;

    public VehiculoDesFragment() {
        // Required empty public constructor
    }


    public static VehiculoDesFragment newInstance(String param1, String param2) {
        VehiculoDesFragment fragment = new VehiculoDesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();

        try {
            id_vehiculo = this.getArguments().getString("id_vehiculo");
            loadVehiculo();

        } catch (Exception e) {
            id_vehiculo = "";
        }

        id_duenio = this.getArguments().getString("id_duenio");

        View view = inflater.inflate(R.layout.fragment_vehiculo_des, container, false);

        placaTextInput = view.findViewById(R.id.placaTextInput);
        marcaTextInput = view.findViewById(R.id.marcaTextInput);
        modeloTextInput = view.findViewById(R.id.modeloTextInput);
        anioTextInput = view.findViewById(R.id.anioTextInput);
        transmisionTextInput = view.findViewById(R.id.transmisionTextInput);
        maletaTextInput = view.findViewById(R.id.maletaTextInput);
        traccionTextInput = view.findViewById(R.id.traccionTextInput);
        puertaTextInput = view.findViewById(R.id.puertaTextInput);
        pasajeroTextInput = view.findViewById(R.id.pasajeroTextInput);
        gpsTextInput = view.findViewById(R.id.gpsTextInput);

        imgAuto = (ImageView) view.findViewById(R.id.imgAuto);


        imgAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/");
                startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicacion"),10);
            }
        });



        airCheck = view.findViewById(R.id.airCheck);
        sunroofCheck = view.findViewById(R.id.sunroofCheck);

        btnRegistroVehiculo = view.findViewById(R.id.btnRegistroVehiculo);


        //Toast.makeText(getContext(), id_vehiculo, Toast.LENGTH_SHORT).show();

        btnRegistroVehiculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id_vehiculo.equals("")) {
                    saveVehiculo();
                } else {
                    updateVehiculo();
                }
            }
        });
        return view;
    }

    void saveVehiculo() {
        loadData();
        firebaseFirestore.collection("vehiculo").document().set(hashMap).
                addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), "Datos Guardados", Toast.LENGTH_SHORT).show();
                        iComunicacionFragments.vehiculo(id_duenio);
                    }
                });
    }
    void loadData(){
        hashMap.put("placa",placaTextInput.getEditText().getText().toString());
        hashMap.put("marca",marcaTextInput.getEditText().getText().toString());
        hashMap.put("modelo",modeloTextInput.getEditText().getText().toString());
        hashMap.put("anio",anioTextInput.getEditText().getText().toString());
        hashMap.put("duenio",id_duenio);
        hashMap.put("url",img_url);
        hashMap.put("transmision",transmisionTextInput.getEditText().getText().toString());
        hashMap.put("maleta", Integer.valueOf(maletaTextInput.getEditText().getText().toString()));
        hashMap.put("traccion",traccionTextInput.getEditText().getText().toString());
        hashMap.put("puertas", Integer.valueOf(puertaTextInput.getEditText().getText().toString()));
        hashMap.put("pasajero",Integer.valueOf(pasajeroTextInput.getEditText().getText().toString()));
        hashMap.put("gps",gpsTextInput.getEditText().getText().toString());
        hashMap.put("created_at", Timestamp.now());

        Boolean sun=false,air=false;
        if (airCheck.isChecked())
            air = true;
        if (sunroofCheck.isChecked())
            sun = true;
        hashMap.put("aire_acondicionado",air);
        hashMap.put("sunroof",sun);
    }
    void updateVehiculo(){
        loadData();
        query = firebaseFirestore.collection("vehiculo").document(id_vehiculo).update(
                hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getContext(), "Datos Actualizados", Toast.LENGTH_SHORT).show();
                iComunicacionFragments.vehiculo(id_duenio);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }


    void loadVehiculo(){

        firebaseFirestore.collection("vehiculo").document(id_vehiculo).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    placaTextInput.getEditText().setText(documentSnapshot.getString("placa"));
                    marcaTextInput.getEditText().setText(documentSnapshot.getString("marca"));
                    modeloTextInput.getEditText().setText(documentSnapshot.getString("modelo"));
                    anioTextInput.getEditText().setText(documentSnapshot.getString("anio"));
                    transmisionTextInput.getEditText().setText(documentSnapshot.getString("transmision"));
                    maletaTextInput.getEditText().setText(String.valueOf(documentSnapshot.getLong("maleta")));
                    traccionTextInput.getEditText().setText(documentSnapshot.getString("traccion"));
                    puertaTextInput.getEditText().setText(String.valueOf(documentSnapshot.getLong("puertas")));
                    pasajeroTextInput.getEditText().setText(String.valueOf(documentSnapshot.getLong("pasajero")));
                    gpsTextInput.getEditText().setText(documentSnapshot.getString("gps"));
                    if(documentSnapshot.getBoolean("aire_acondicionado"))
                        airCheck.setChecked(true);
                    if(documentSnapshot.getBoolean("sunroof"))
                        sunroofCheck.setChecked(true);

                }else {
                    Toast.makeText(getContext(), "no", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            activity= (Activity) context;
            iComunicacionFragments = (IComunicacionFragments) activity;
        }
        if (context instanceof MenuFragment.OnFragmentInteractionListener) {
            onFragmentInteractionListener = (MenuFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        //Toast.makeText(getActivity(), "SI", Toast.LENGTH_SHORT).show();
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(getActivity(), "SI", Toast.LENGTH_SHORT).show();
        if(resultCode == getActivity().RESULT_OK) {
            Uri patch = data.getData();
            imgAuto.setImageURI(patch);
            StorageReference filepath = storageRef.child("coches").child(patch.getLastPathSegment());
            filepath.putFile(patch).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    img_url =  taskSnapshot.getStorage().getDownloadUrl().getResult().toString();
                    Toast.makeText(getActivity(), "Imagen Guardada", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}