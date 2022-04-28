package com.example.finnkinoharjt;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    ArrayList<String> movienamelist;
    ArrayList<String> movietimelist;
    ArrayList<String> movieplacelist;
    Context context;

    public RecyclerViewAdapter(Context ct, ArrayList<String> mnl, ArrayList<String> mtl, ArrayList<String> mpl){
        context=ct;
        movienamelist=mnl;
        movietimelist=mtl;
        movieplacelist=mpl;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.movierows,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nameofmovie.setText(movienamelist.get(position));
        holder.date.setText(movietimelist.get(position));
        holder.place.setText(movieplacelist.get(position));
    }

    @Override
    public int getItemCount() {
        return movienamelist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView date, nameofmovie, place;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.datetime);
            nameofmovie=itemView.findViewById(R.id.moviename);
            place=itemView.findViewById(R.id.place);
        }
    }
}
