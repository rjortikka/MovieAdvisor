package com.example.movieadvisor;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.O)
public class RecyclerViewAdapter2 extends RecyclerView.Adapter<RecyclerViewAdapter2.MyViewHolder> {

    ArrayList<String> listmoviename;
    ArrayList<String> listmovietime;
    ArrayList<String> listmovieplace;
    Context context;
    TheatreInfo ti=TheatreInfo.getInstance();

    public RecyclerViewAdapter2(Context ct, ArrayList<String> lmn, ArrayList<String> lmt, ArrayList<String> lmp){
        context=ct;
        listmoviename=lmn;
        listmovietime=lmt;
        listmovieplace=lmp;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.movieinforows,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nameofthemovie.setText((CharSequence) listmoviename.get(position));
        holder.timeanddate.setText(listmovietime.get(position));
        holder.placeofthemovie.setText(listmovieplace.get(position));
    }

    @Override
    public int getItemCount() {
        return listmoviename.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView timeanddate, nameofthemovie, placeofthemovie;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            timeanddate=itemView.findViewById(R.id.timedateforrate);
            nameofthemovie=itemView.findViewById(R.id.nameforrate);
            placeofthemovie=itemView.findViewById(R.id.theatreforrate);

            Spinner ratemovies = itemView.findViewById(R.id.spinnerforrate2);
            ArrayAdapter<Object> adapter3 =new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, ti.ratelist);
            adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ratemovies.setAdapter(adapter3);
        }
    }
}

