package com.example.allii;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.ali.R;

/**
 * Created by Ali    DEC/2018.
 */

/// class pour remplir le receclerview

public class BestPlayerAdapter extends RecyclerView.Adapter<BestPlayerAdapter.ViewHolder> {
    ArrayList<Score> list;

    public BestPlayerAdapter(ArrayList<Score> list) {// constructeur meme nom que la class ( istancier) afficter des données a ma class
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.score_item,parent,false);
        BestPlayerAdapter.ViewHolder holder=new BestPlayerAdapter.ViewHolder(view);// instancier
        return holder;
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
        TextView score_nom,score_nbmove,score_temps,score_score;
        public ViewHolder(View itemView) {
            super(itemView);
            score_nom=(TextView) itemView.findViewById(R.id.score_nom);
            score_nbmove=(TextView) itemView.findViewById(R.id.score_nbmove);
            score_temps=(TextView) itemView.findViewById(R.id.score_temps);
            score_score=(TextView) itemView.findViewById(R.id.score_score);
        }

        public void bind(Score score) {
            score_nom.setText(score.getName());
            score_nbmove.setText(String.valueOf(score.getNbdeplacement()));
            score_temps.setText(String.valueOf(score.getTemps()));
            score_score.setText(String.valueOf(score.getNbsecdep()));
        }
    }
}
