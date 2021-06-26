package com.example.firebasekelasc.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasekelasc.MainActivity;
import com.example.firebasekelasc.R;
import com.example.firebasekelasc.TemanEdit;
import com.example.firebasekelasc.database.Teman;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterLihatTeman extends RecyclerView.Adapter<AdapterLihatTeman.ViewHolder> {
    //variabel
    private ArrayList<Teman> daftarTeman;
    private Context context; //agar kita mudah menentukan ada di activity mana

    private DatabaseReference databaseReference;
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
        String kode, telpon, nama;

        nama = daftarTeman.get(position).getNama();
        kode = daftarTeman.get(position).getKode();
        telpon = daftarTeman.get(position).getTelpon();

        holder.tvNama.setText(nama);

        holder.tvNama.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //fungsi untuk update dan delete
                PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                popupMenu.inflate(R.menu.menuteman);

                //buat event
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId())
                        {
                            case R.id.mnEdit:
                                //kode untuk mengirim value dari kode, nama, telpon ke activity TemanEdit
                                Bundle bundle = new Bundle();
                                bundle.putString("kunci1", kode);
                                bundle.putString("kunci2", nama);
                                bundle.putString("kunci3", telpon);

                                Intent intent = new Intent(v.getContext(), TemanEdit.class);
                                intent.putExtras(bundle);
                                v.getContext().startActivity(intent);
                                break;

                            case R.id.mnHapus:
                                AlertDialog.Builder alertDlg = new AlertDialog.Builder(v.getContext());
                                alertDlg.setTitle("Yakin data " + nama + " akan dihapus?");
                                alertDlg.setMessage("Tekan 'Ya' untuk menghapus").setCancelable(false)
                                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                DeleteData(kode);
                                                Toast.makeText(v.getContext(), "Data "+ nama + "berhasil dihapus", Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(v.getContext(), MainActivity.class);
                                                v.getContext().startActivity(intent);
                                            }
                                        })
                                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });
                                AlertDialog aDlg = alertDlg.create();
                                aDlg.show();
                                break;

                        }
                        return false;
                    }
                });
                popupMenu.show();
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

            databaseReference = FirebaseDatabase.getInstance().getReference();


        }
    }

    public void DeleteData(String kode){
        if (databaseReference != null)
        {
            databaseReference.child("Teman").child(kode).removeValue();
        }
    }
}
