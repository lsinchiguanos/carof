package uteq.student.project.carof.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import uteq.student.project.carof.R;
import uteq.student.project.carof.adapter.VehiculoAdapter;
import uteq.student.project.carof.models.VehiculoModel;

public class VehiculosFragment extends Fragment {

    private FirebaseFirestore db;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView recview;
    private View view;
    private VehiculoAdapter vehiculoAdapter;

    public VehiculosFragment() {
    }

    public static VehiculosFragment newInstance(String param1, String param2) {
        VehiculosFragment fragment = new VehiculosFragment();
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
        view = inflater.inflate(R.layout.fragment_vehiculos, container, false);

        recview = view.findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(getContext()));
        db = FirebaseFirestore.getInstance();
        Query query = db.collection("vehiculo");
        FirestoreRecyclerOptions<VehiculoModel> vehiculoModelFirestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<VehiculoModel>()
                .setQuery(query, VehiculoModel.class)
                .build();

        vehiculoAdapter = new VehiculoAdapter(vehiculoModelFirestoreRecyclerOptions);
        recview.setAdapter(vehiculoAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        vehiculoAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        vehiculoAdapter.stopListening();
    }
}