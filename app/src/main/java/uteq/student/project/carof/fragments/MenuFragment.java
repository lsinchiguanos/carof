package uteq.student.project.carof.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uteq.student.project.carof.R;
import uteq.student.project.carof.interfaces.IComunicacionFragments;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment {

    View view;
    Activity activity;
    CardView cardViewVehiculo, cardViewContrato, cardViewHistorial, cardViewPublicaciones, cardViewInformacion, cardViewMonitoreo, cardViewGps, cardViewSignUp;
    IComunicacionFragments iComunicacionFragments;
    OnFragmentInteractionListener onFragmentInteractionListener;
    Bundle b = new Bundle();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
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
        view = inflater.inflate(R.layout.fragment_menu, container, false);

        String ar = this.getArguments().getString("id_duenio");
        String a = ar;
        cardViewVehiculo = view.findViewById(R.id.vehiculoCarView);
        cardViewVehiculo.setOnClickListener(v -> iComunicacionFragments.vehiculo(this.getArguments().getString("id_duenio")));

        cardViewContrato = view.findViewById(R.id.contratosCardView);
        cardViewContrato.setOnClickListener(v -> iComunicacionFragments.contratos());
        
        cardViewMonitoreo = view.findViewById(R.id.monitoreoCardView);
        cardViewMonitoreo.setOnClickListener(v -> iComunicacionFragments.monitoreo());

        cardViewPublicaciones = view.findViewById(R.id.cardPublicaciones);
        cardViewPublicaciones.setOnClickListener(v -> iComunicacionFragments.publicacion());

        cardViewSignUp = view.findViewById(R.id.cardSignUp);
        cardViewSignUp.setOnClickListener(v -> iComunicacionFragments.signUp());

        return view;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            activity= (Activity) context;
            iComunicacionFragments= (IComunicacionFragments) activity;
        }
        if (context instanceof OnFragmentInteractionListener) {
            onFragmentInteractionListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

}