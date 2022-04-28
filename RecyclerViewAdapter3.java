package com.example.finnkinoharjt;



import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.O)
public class RecyclerViewAdapter3 extends RecyclerView.Adapter<RecyclerViewAdapter3.MyViewHolder> {

    ArrayList<String> movielistname;

    Context context;

    public RecyclerViewAdapter3(Context ct, ArrayList<String> mln){
        context=ct;
        movielistname=mln;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ratemovierows,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.moviesname.setText(movielistname.get(position));
    }

    @Override
    public int getItemCount() {
        return movielistname.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView moviesname;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            moviesname=itemView.findViewById(R.id.listname);

        }
    }
}
