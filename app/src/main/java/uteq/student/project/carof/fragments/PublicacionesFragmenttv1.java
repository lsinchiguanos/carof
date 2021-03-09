package uteq.student.project.carof.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.firestore.SnapshotParser;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.firebase.ui.firestore.paging.LoadingState;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import uteq.student.project.carof.R;
import uteq.student.project.carof.interfaces.IComunicacionFragments;
import uteq.student.project.carof.models.PublicacionModel;
import uteq.student.project.carof.models.VehiculoModel;

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
        loadPublicaciones();
        return view;
    }

    void loadPublicaciones() {
        query = firebaseFirestore.collection("publicacion").whereEqualTo("duenio", id_duenio);
        config = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(7)
                .setPageSize(2)
                .build();

        FirestorePagingOptions<PublicacionModel> option = new FirestorePagingOptions.Builder<PublicacionModel>()
                .setLifecycleOwner(this)
                .setQuery(query, config, new SnapshotParser<PublicacionModel>() {
                    @NonNull
                    @Override
                    public PublicacionModel parseSnapshot(@NonNull DocumentSnapshot snapshot) {
                        PublicacionModel cardsModel = snapshot.toObject(PublicacionModel.class);
                        String item_id = snapshot.getId();
                        cardsModel.setId(item_id);
                        return cardsModel;
                    }
                }).build();

        adapter = new FirestorePagingAdapter<PublicacionModel, PublicacionesFragmenttv1.CardsViewHolder>(option) {
            @NonNull
            @Override
            public PublicacionesFragmenttv1.CardsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_fragment_publicacion, parent, false);
                return new PublicacionesFragmenttv1.CardsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull CardsViewHolder holder, int position, @NonNull PublicacionModel model) {
                holder.tituloPublicacion.setText(model.getTitulo());

                firebaseFirestore.collection("vehiculo").document(model.getVehiculo()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            holder.marca.setText(documentSnapshot.getString("marca"));
                            holder.anioC.setText(documentSnapshot.getString("anio"));
                            Glide.with(PublicacionesFragmenttv1.this).load(documentSnapshot.getString("url"))
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(holder.img2);
                        } else {
                        }
                    }
                });
                holder.costodiario.setText(String.valueOf(model.getValor_diario()));

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        iComunicacionFragments.editpublicacion(id_duenio, model.getId(),model.getVehiculo());
                    }
                });

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        dialogoAcceso(model.getId()).show();
                        return true;
                    }
                });
            }

            @Override
            protected void onLoadingStateChanged(@NonNull LoadingState state) {
                super.onLoadingStateChanged(state);
                switch (state) {
                    case LOADING_INITIAL:
                        Log.d("PAGING_LOG", "Loading Initial Date");
                        break;
                    case LOADING_MORE:
                        Log.d("PAGING_LOG", "Total Next Page");
                        break;
                    case FINISHED:
                        Log.d("PAGING_LOG", "All Date Loaded");
                        break;
                    case ERROR:
                        Log.d("PAGING_LOG", "Error Loading Data");
                        break;
                    case LOADED:
                        Log.d("PAGING_LOG", "Total Items Loaded:" + getItemCount());
                        break;
                }
            }
        };
        mFirestore_list.setHasFixedSize(true);
        mFirestore_list.setLayoutManager(new LinearLayoutManager(getContext()));
        mFirestore_list.setAdapter(adapter);

    }

    public AlertDialog dialogoAcceso(String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Eliminar Publicaion").
                setMessage("Desea eliminar la Publicacion ?").
                setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                firebaseFirestore.collection("publicaion").document(id).delete();
                loadPublicaciones();
            }
        });
        return builder.create();
    }

    private class CardsViewHolder extends RecyclerView.ViewHolder {

        private TextView tituloPublicacion, marca, anioC, costodiario;
        private ImageView img2;

        public CardsViewHolder(@NonNull View itemView) {
            super(itemView);
            tituloPublicacion = itemView.findViewById(R.id.tituloPublicacion);
            marca = itemView.findViewById(R.id.marca);
            anioC = itemView.findViewById(R.id.anioC);
            costodiario = itemView.findViewById(R.id.costodiario);
            img2 = itemView.findViewById(R.id.img1);
        }
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