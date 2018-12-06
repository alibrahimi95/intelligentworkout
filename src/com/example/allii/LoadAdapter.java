package com.example.allii;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.ali.R;



public class LoadAdapter extends RecyclerView.Adapter<LoadAdapter.ViewHolder>  {

    ArrayList<Jeux> list;

    public LoadAdapter(ArrayList<Jeux> list) {
        this.list = list;
    }
    private  void deleSaveFormList(int adapterPosition) {
        list.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.load_item,parent,false);
        LoadAdapter.ViewHolder holder=new LoadAdapter.ViewHolder(view,this);

        return  holder;
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(list.get(position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Jeux jeux;
        TextView load_nom,load_nbmove,load_temps;
        Context contaxt;
        LoadAdapter loadAdapter;
        public ViewHolder(View itemView, final LoadAdapter loadAdapter) {
            super(itemView);
             this.loadAdapter=loadAdapter;
            contaxt=itemView.getContext();
            load_nom=(TextView) itemView.findViewById(R.id.load_nom);
            load_nbmove=(TextView) itemView.findViewById(R.id.load_nbmove);
            load_temps=(TextView) itemView.findViewById(R.id.load_temps);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(view.getContext(),MainActivity.class);
                    intent.putExtra("toload",1);
                    intent.putExtra("nbmove",jeux.getNbmove());
                    intent.putExtra("nbtimer",jeux.getNbtimer());
                    intent.putExtra("level",jeux.getLevel());
                    intent.putExtra("carte",Helper.ArrayToString(jeux.getMatrice()));
                    view.getContext().startActivity(intent);
                }
            });
             itemView.setOnLongClickListener(new View.OnLongClickListener() {
                 @Override
                 public boolean onLongClick(View view) {
                     android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(contaxt);
                     alert.setTitle("Alert!!");
                     alert.setMessage("Voullez-vous supprimé la ligne?");
                     alert.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialog, int which) {
                             new JeuxDB(contaxt).delJeux(jeux.getId());
                             loadAdapter.deleSaveFormList(getPosition());
                             dialog.dismiss();
                         }
                     });
                     alert.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialog, int which) {
                             dialog.dismiss();
                         }
                     });
                     alert.show();
                     return true;
                 }
             });

        }

        public void bind(Jeux jeux) {
            this.jeux=jeux;
            load_nom.setText(jeux.getName());
            load_nbmove.setText(String.valueOf(jeux.getNbmove()));
            load_temps.setText(String.valueOf(jeux.getNbtimer()));
        }
    }




}
