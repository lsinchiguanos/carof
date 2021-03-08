package uteq.student.project.carof.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import uteq.student.project.carof.R;
import uteq.student.project.carof.interfaces.IComunicacionFragments;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PublicacionesFragmenttv1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PublicacionesFragmenttv1 extends Fragment {

    Activity activity;
    private RecyclerView mFirestore_list;
    private FirebaseFirestore firebaseFirestore;
    private FirestorePagingAdapter adapter;
    Query query;
    PagedList.Config config;
    String id_duenio;
    Bundle b = new Bundle();
    FloatingActionButton btnAdd;
    IComunicacionFragments iComunicacionFragments;
    MenuFragment.OnFragmentInteractionListener onFragmentInteractionListener;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PublicacionesFragmenttv1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PublicacionesFragmenttv1.
     */
    // TODO: Rename and change types and number of parameters
    public static PublicacionesFragmenttv1 newInstance(String param1, String param2) {
        PublicacionesFragmenttv1 fragment = new PublicacionesFragmenttv1();
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
        View view = inflater.inflate(R.layout.fragment_publicaciones_fragmenttv1, container, false);
        firebaseFirestore = FirebaseFirestore.getInstance();
        mFirestore_list = view.findViewById(R.id.firestore_listP);
        btnAdd = view.findViewById(R.id.fbAddPublicacion);
        id_duenio = this.getArguments().getString("id_duenio");
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                iComunicacionFragments.addpublicacion(id_duenio);
            }
        });
        return view;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
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