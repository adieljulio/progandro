package com.adiel.gogogicha;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by hp on 29/04/2018.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.FilmHolder>{
    private List<History> listHistory;
    private Context mContext;

    public HistoryAdapter(List<History> listHistory, Context mContext){
        this.listHistory = listHistory;
        this.mContext = mContext;
    }

    //mengkonversi layout item_film.xml ke bentuk Java
    @Override
    public FilmHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);
        return new FilmHolder(itemView);
    }

    //memasang atribut yang ada di layout item_film.xml
    @Override
    public void onBindViewHolder(FilmHolder holder, int position) {
        History history = listHistory.get(position);
        String timeinparse[] = history.getBoardingTime().split("_");
        holder.txvTime.setText(timeinparse[4]+":"+timeinparse[5]+":"+timeinparse[6]+" "+timeinparse[3]+"/"+timeinparse[2]+"/"+timeinparse[1]);
        holder.txvTitle.setText(history.getOrigin().split("_")[0].toUpperCase() + "->" + history.getDestination().split("_")[0].toUpperCase());

    }

    @Override
    public int getItemCount() {
        return listHistory.size();
    }

    public class FilmHolder extends RecyclerView.ViewHolder{
        private TextView txvTitle, txvTime;

        public FilmHolder(View itemView) {
            super(itemView);

            txvTitle = (TextView) itemView.findViewById(R.id.txvTitle);
            txvTime = (TextView) itemView.findViewById(R.id.txvTime);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadRideView(getLayoutPosition());
                }
            });
        }
    }

    public void loadRideView(int pos){
        Intent intent = new Intent(mContext, RideActivity.class);
        intent.putExtra("key",listHistory.get(pos).getTitle());
        mContext.startActivity(intent);
    }
}
