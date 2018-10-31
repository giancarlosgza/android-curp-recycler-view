package com.example.giancarlos.evidencia_2.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import com.example.giancarlos.evidencia_2.R;
import com.example.giancarlos.evidencia_2.model.Curp;

import java.util.ArrayList;

public class CurpRecyclerAdapter extends RecyclerView.Adapter<CurpRecyclerAdapter.CurpViewHolder>  {

    private ArrayList<Curp> listCurp;
    public  ImageView overflow;
    private Context mContext;
    private ArrayList<Curp> mFilteredList;


    public CurpRecyclerAdapter(ArrayList<Curp> listCurp, Context mContext) {
        this.listCurp = listCurp;
        this.mContext = mContext;
        this.mFilteredList = listCurp;
    }

    public class CurpViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView textViewlastName1;
        public AppCompatTextView textViewlastName2;
        public AppCompatTextView textViewname;
        public AppCompatTextView textViewgender;
        public AppCompatTextView textViewdate;
        public AppCompatTextView textViewstate;
        public  ImageView overflow;

        public CurpViewHolder(View view) {
            super(view);
            textViewlastName1 = (AppCompatTextView) view.findViewById(R.id.lastName1);
            textViewlastName2 = (AppCompatTextView) view.findViewById(R.id.lastName2);
            textViewname = (AppCompatTextView) view.findViewById(R.id.name);
            textViewgender = (AppCompatTextView) view.findViewById(R.id.gender);
            textViewdate = (AppCompatTextView) view.findViewById(R.id.date);
            textViewstate = (AppCompatTextView) view.findViewById(R.id.state);
        }
    }

    @Override
    public CurpViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_item, parent, false);

        return new CurpViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CurpViewHolder holder, int position) {
        holder.textViewlastName1.setText(listCurp.get(position).getprimerApellido());
        holder.textViewlastName2.setText(listCurp.get(position).getsegundoApellido());
        holder.textViewname.setText(listCurp.get(position).getnombre());
        holder.textViewdate.setText(listCurp.get(position).getnacimiento());
        holder.textViewgender.setText(listCurp.get(position).getsexo());
        holder.textViewstate.setText(listCurp.get(position).getentidadFederativa());
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

}