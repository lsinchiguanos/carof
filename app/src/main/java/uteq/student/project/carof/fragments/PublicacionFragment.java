package uteq.student.project.carof.fragments;

import android.app.Activity;
import android.content.Context;
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

import com.google.android.gms.tasks.Task;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

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


    private String mParam1;
    private String mParam2;
    Bundle b = new Bundle();
    String id_vehiculo = "", id_duenio = "";
    private FirebaseFirestore firebaseFirestore;
    StorageReference storageRef;

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_publicacion, container, false);
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();

        try {
            id_vehiculo = this.getArguments().getString("id_vehiculo");
        } catch (Exception e) {
            id_vehiculo = "";
        }

        id_duenio = this.getArguments().getString("id_duenio");
        return view;
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

}