package uteq.student.project.carof.fragments;


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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.firestore.SnapshotParser;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.firebase.ui.firestore.paging.LoadingState;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import uteq.student.project.carof.R;
import uteq.student.project.carof.models.VehiculoModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VehiculosFragmentv1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VehiculosFragmentv1 extends Fragment {

    private RecyclerView mFirestore_list;
    private FirebaseFirestore firebaseFirestore;
    private FirestorePagingAdapter adapter;
    Query query;
    PagedList.Config config;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public VehiculosFragmentv1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VehiculosFragmentv1.
     */
    // TODO: Rename and change types and number of parameters
    public static VehiculosFragmentv1 newInstance(String param1, String param2) {
        VehiculosFragmentv1 fragment = new VehiculosFragmentv1();
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
        View view = inflater.inflate(R.layout.fragment_vehiculos_fragmentv1, container, false);

        firebaseFirestore = FirebaseFirestore.getInstance();
        mFirestore_list = view.findViewById(R.id.firestore_list);

        //Query
        query = firebaseFirestore.collection("vehiculo");

        // Search how like in sql
        // orderBy("name").startAt("Scar")
         config = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(7)
                .setPageSize(2)
                .build();

        FirestorePagingOptions<VehiculoModel> option = new FirestorePagingOptions.Builder<VehiculoModel>()
                .setLifecycleOwner(this)
                .setQuery(query, config, new SnapshotParser<VehiculoModel>() {
                    @NonNull
                    @Override
                    public VehiculoModel parseSnapshot(@NonNull DocumentSnapshot snapshot) {
                        VehiculoModel cardsModel = snapshot.toObject(VehiculoModel.class);
                        String item_id = snapshot.getId();
                        //cardsModel.setItem_id(item_id);
                        return cardsModel;
                    }
                }).build();

        adapter = new FirestorePagingAdapter<VehiculoModel, CardsViewHolder>(option) {
            @NonNull
            @Override
            public CardsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_fragment,parent,false);
                return new CardsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull CardsViewHolder holder, int position, @NonNull VehiculoModel model) {
                holder.placa.setText(model.getPlaca());
                holder.marca.setText(model.getMarca());
                holder.modelo.setText(model.getModelo());
                holder.anio.setText(model.getAnio());

                Glide.with(VehiculosFragmentv1.this).load(model.getUrl())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.imgUrl);
                //holder.imgUrl.setText(model.getImage());
            }
            @Override
            protected void onLoadingStateChanged(@NonNull LoadingState state) {
                super.onLoadingStateChanged(state);
                switch (state){
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
                        Log.d("PAGING_LOG", "Total Items Loaded:"+ getItemCount());
                        break;
                }
            }
        };

        mFirestore_list.setHasFixedSize(true);
        mFirestore_list.setLayoutManager(new LinearLayoutManager(getContext()));
        mFirestore_list.setAdapter(adapter);

        return view;
    }

    private class CardsViewHolder extends RecyclerView.ViewHolder{

        private TextView placa,marca,modelo,anio;
        private ImageView imgUrl;
        public CardsViewHolder(@NonNull View itemView) {
            super(itemView);
            placa = itemView.findViewById(R.id.placa);
            marca = itemView.findViewById(R.id.marca);
            modelo = itemView.findViewById(R.id.modelo);
            anio = itemView.findViewById(R.id.anio);
            imgUrl = itemView.findViewById(R.id.img1);
        }
    }

}