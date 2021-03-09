package uteq.student.project.carof.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Vector;

import uteq.student.project.carof.R;
import uteq.student.project.carof.activities.SignUpActivity;
import uteq.student.project.carof.interfaces.IComunicacionFragments;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PublicacionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PublicacionFragment extends Fragment {

    IComunicacionFragments iComunicacionFragments;
    MenuFragment.OnFragmentInteractionListener onFragmentInteractionListener;
    Activity activity;

    TextInputLayout titulo, valor_diario, superaVelocidad, limiteChoques, limite, valorGeogra,vehiculoItem;
    MaterialCheckBox pagoanticipado, tanquefull;
    Button btnRegistroPubli;
    AutoCompleteTextView list_itemFrag;

    String id_duenio = "", id_publicacion = "";
    String id_vehiculo = "";

    Task<Void> query;
    private FirebaseFirestore firebaseFirestore;
    HashMap hashMap = new HashMap();

    Vector idVehiculo=new Vector();
    Vector items=new Vector();



    private String mParam1;
    private String mParam2;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public PublicacionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PublicacionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PublicacionFragment newInstance(String param1, String param2) {
        PublicacionFragment fragment = new PublicacionFragment();
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
        try {
            id_publicacion = this.getArguments().getString("id_publicacion");
            id_vehiculo = this.getArguments().getString("id_vehi");
            loadPublicacion();
        } catch (Exception e) {
            id_publicacion = "";
            id_vehiculo = "";
        }
        id_duenio = this.getArguments().getString("id_duenio");

        View view = inflater.inflate(R.layout.fragment_publicacion, container, false);

        titulo = view.findViewById(R.id.titulo);
        valor_diario = view.findViewById(R.id.valor_diario);
        superaVelocidad = view.findViewById(R.id.superaVelocidad);
        limiteChoques = view.findViewById(R.id.limiteChoques);
        limite = view.findViewById(R.id.limite);
        valorGeogra = view.findViewById(R.id.valorGeogra);
        vehiculoItem = view.findViewById(R.id.vehiculoItem);
        pagoanticipado = view.findViewById(R.id.pagoanticipado);
        tanquefull = view.findViewById(R.id.tanquefull);

        btnRegistroPubli = view.findViewById(R.id.btnRegistroPubli);
        list_itemFrag = view.findViewById(R.id.list_itemFrag);

        btnRegistroPubli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id_publicacion.equals("")) {
                    savePublicacion();
                } else {
                    updatePublicacion();
                }
            }
        });
        listAuto();

        return view;
    }


    void limpiar(){
        titulo.getEditText().setText("");
        valor_diario.getEditText().setText("");
        superaVelocidad.getEditText().setText("");
        limiteChoques.getEditText().setText("");
        limite.getEditText().setText("");
        valorGeogra.getEditText().setText("");
        vehiculoItem.getEditText().setText("");
    }

    void listAuto(){

        firebaseFirestore.collection("vehiculo").whereEqualTo("estado", "DISPONIBLE")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                items.add(document.getString("placa"));
                                idVehiculo.add(document.getId());
                            }
                            if(id_vehiculo.equals("")){
                            }else{
                                items.add("ACTUAL");
                                idVehiculo.add(id_vehiculo);

                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.item_select, items);
                            list_itemFrag.setAdapter(adapter);
                            if(id_vehiculo.equals("")){
                            }else{
                                //vehiculoItem.getEditText().setText("ACTUAL");
                            }
                        }
                    }
                });
    }

    void loadData() {

        hashMap.put("titulo", titulo.getEditText().getText().toString());
        hashMap.put("valor_diario", Integer.valueOf(valor_diario.getEditText().getText().toString()));
        hashMap.put("super_velocidad",Integer.valueOf(superaVelocidad.getEditText().getText().toString()));
        hashMap.put("limite_choques", Integer.valueOf(limiteChoques.getEditText().getText().toString()));
        hashMap.put("limite", Integer.valueOf(limite.getEditText().getText().toString()));
        hashMap.put("valor_geografico", Integer.valueOf(valorGeogra.getEditText().getText().toString()));
        hashMap.put("created_at", Timestamp.now());
        hashMap.put("vehiculo", id_vehiculo);
        hashMap.put("duenio", id_duenio);

        Boolean pago = false, tanque = false;
        if (pagoanticipado.isChecked())
            pago = true;
        if (tanquefull.isChecked())
            tanque = true;
        hashMap.put("pago_anticipado", pago);
        hashMap.put("tanque_full", tanque);
    }

    void loadIdVehiculo(){
        for(int i=0;i<idVehiculo.size(); i++){
            if(vehiculoItem.getEditText().getText().toString().equals(items.get(i).toString())){
                id_vehiculo = idVehiculo.get(i).toString();
            }
        }
    }

    void savePublicacion() {
        loadIdVehiculo();
        loadData();
        firebaseFirestore.collection("publicacion").document().set(hashMap).
                addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), "Datos Guardados", Toast.LENGTH_SHORT).show();
                        //iComunicacionFragments.vehiculo(id_duenio);
                    }
                });
        HashMap hashEstado = new HashMap();
        hashEstado.put("estado","OCUPADO");
        Toast.makeText(getContext(),id_vehiculo, Toast.LENGTH_SHORT).show();
        firebaseFirestore.collection("vehiculo").document(id_vehiculo).update(hashEstado).
                addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                });
        listAuto();
        limpiar();
    }

    void updatePublicacion() {
        loadIdVehiculo();
        loadData();
        query = firebaseFirestore.collection("publicacion").document(id_publicacion).update(
                hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getContext(), "Datos Actualizados", Toast.LENGTH_SHORT).show();
                //iComunicacionFragments.vehiculo(id_duenio);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
        limpiar();
        //iComunicacionFragments.publicacion(id_duenio);
    }

    void loadPublicacion() {

        firebaseFirestore.collection("publicacion").document(id_publicacion).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    titulo.getEditText().setText(documentSnapshot.getString("titulo"));
                    valor_diario.getEditText().setText(String.valueOf(documentSnapshot.getLong("valor_diario")));
                    superaVelocidad.getEditText().setText(String.valueOf(documentSnapshot.getLong("super_velocidad")));
                    limiteChoques.getEditText().setText(String.valueOf(documentSnapshot.getLong("limite_choques")));
                    limite.getEditText().setText(String.valueOf(documentSnapshot.getLong("limite")));
                    valorGeogra.getEditText().setText(String.valueOf(documentSnapshot.getLong("valor_geografico")));

                    if (documentSnapshot.getBoolean("pago_anticipado"))
                        pagoanticipado.setChecked(true);
                    if (documentSnapshot.getBoolean("tanque_full"))
                        tanquefull.setChecked(true);
                } else {
                    Toast.makeText(getContext(), "no", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            activity = (Activity) context;
            iComunicacionFragments = (IComunicacionFragments) activity;
        }
        if (context instanceof MenuFragment.OnFragmentInteractionListener) {
            onFragmentInteractionListener = (MenuFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}