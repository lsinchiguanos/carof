package uteq.student.project.carof.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import uteq.student.project.carof.R;
import uteq.student.project.carof.fragments.VehiculoDesFragment;
import uteq.student.project.carof.models.VehiculoModel;

public class VehiculoAdapter extends FirestoreRecyclerAdapter<VehiculoModel, VehiculoAdapter.myviewholder> {

    public VehiculoAdapter(@NonNull FirestoreRecyclerOptions<VehiculoModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull VehiculoModel model) {
        holder.placa.setText(model.getPlaca());
        holder.marca.setText(model.getMarca());
        holder.modelo.setText(model.getModelo());
        holder.anio.setText(model.getAnio());
        holder.imageView.setOnClickListener((view) ->{
            AppCompatActivity activity = (AppCompatActivity)view.getContext();
            //activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,new VehiculoDesFragment(model.getPlaca(),model.getMarca(),model.getModelo(),model.getAnio())).addToBackStack(null).commit();
        });
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_fragment,parent,false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView placa, marca, modelo, anio;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img1);
            placa = itemView.findViewById(R.id.placa);
            marca = itemView.findViewById(R.id.marca);
            modelo = itemView.findViewById(R.id.modelo);
            anio = itemView.findViewById(R.id.anio);
        }
    }

}
