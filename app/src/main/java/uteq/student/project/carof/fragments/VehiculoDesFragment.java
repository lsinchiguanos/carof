package uteq.student.project.carof.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uteq.student.project.carof.R;

public class VehiculoDesFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private int anio;
    private String placa;
    private String marca;
    private String modelo;

    public VehiculoDesFragment() {
        // Required empty public constructor
    }

    public VehiculoDesFragment(String placa, String marca, String modelos, int anio) {
        this.anio = anio;
        this.modelo = modelos;
        this.placa = placa;
        this.marca = marca;
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vehiculo_des, container, false);
    }
}