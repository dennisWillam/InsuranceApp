package com.example.insuranceapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.insuranceapp.R;
import com.example.insuranceapp.dialog.DialogRembuseUser;
import com.example.insuranceapp.fragment.FragmentRembuse;
import com.example.insuranceapp.model.ModelRembuse;

import java.util.List;

public class AdapterRembuse extends RecyclerView.Adapter<AdapterRembuse.MyViewHolder> {
    private List<ModelRembuse> mlist;
    private Class<FragmentRembuse> activity;

    public AdapterRembuse(List<ModelRembuse> mlist, Class<FragmentRembuse> activity) {
        this.mlist = mlist;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdapterRembuse.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewItem = inflater.inflate(R.layout.layout_list_rembuse, parent, false);
        return new AdapterRembuse.MyViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRembuse.MyViewHolder holder, int position) {
        final  ModelRembuse dataRembuse = mlist.get(position);
        holder.tvJenis.setText(dataRembuse.getNama());
        holder.tvTanggal.setText(dataRembuse.getTanggal());
        holder.tvBayar.setText("Rp. "+dataRembuse.getTotalBayar());
        holder.tvStatus.setText(dataRembuse.getStatus());
        holder.tvDokter.setText(dataRembuse.getNamaDokter());
        holder.tvTempat.setText(dataRembuse.getTempat());
        holder.cardRembuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = new FragmentManager() {
                    @Nullable
                    @Override
                    public Fragment findFragmentByTag(@Nullable String tag) {
                        return super.findFragmentByTag("RembuseFragment");
                    }
                };
                DialogRembuseUser dialog = new DialogRembuseUser(
                        dataRembuse.getUrl()
                );
                dialog.show(manager, "form");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvJenis,tvTanggal,tvBayar,tvStatus,tvDokter,tvTempat;
        CardView cardRembuse;
        public MyViewHolder(View viewItem) {
            super(viewItem);
            tvJenis = viewItem.findViewById(R.id.tvJenisKlaim);
            tvTanggal = viewItem.findViewById(R.id.tvTanggalKlaim);
            tvBayar = viewItem.findViewById(R.id.tvTotalKlaim);
            tvStatus = viewItem.findViewById(R.id.tvStatusKlaim);
            tvDokter = viewItem.findViewById(R.id.tvDokterKlaim);
            tvTempat = viewItem.findViewById(R.id.tvTempatKlaim);
            cardRembuse = viewItem.findViewById(R.id.cardKlaim);

        }
    }
}
