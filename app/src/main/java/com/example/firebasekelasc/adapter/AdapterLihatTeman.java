package com.example.firebasekelasc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasekelasc.R;
import com.example.firebasekelasc.database.Teman;

import java.util.ArrayList;

public class AdapterLihatTeman extends RecyclerView.Adapter<AdapterLihatTeman.ViewHolder> {
    //variabel
    private ArrayList<Teman> daftarTeman;
    private Context context; //agar kita mudah menentukan ada di activity mana

    //construktor

    public AdapterLihatTeman(ArrayList<Teman> daftarTeman, Context context) {
        this.daftarTeman = daftarTeman;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_teman, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //apa yg mau kita isikan ke item_teman
        String nama = daftarTeman.get(position).getNama();
        holder.tvNama.setText(nama);

        holder.tvNama.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //fungsi untuk update dan delete
                return true;
            }
        });

        holder.tvNama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //menampilkan detail data
            }
        });
    }

    @Override
    public int getItemCount() {

        return daftarTeman.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama;

        ViewHolder(View v)
        {
            super(v);
            tvNama = (TextView) v.findViewById(R.id.tv_nama);

        }
    }
}
