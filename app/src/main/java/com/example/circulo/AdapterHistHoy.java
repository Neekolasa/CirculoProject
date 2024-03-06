package com.example.circulo;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.circulo.Database.Database;
import com.example.circulo.Entitites.Credito;
import com.example.circulo.Interfaces.InfoPagosRealizados;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AdapterHistHoy extends RecyclerView.Adapter<AdapterHistHoy.ViewHolderDatos> implements View.OnClickListener, Filterable {


    public AdapterHistHoy(ArrayList<InfoPagosRealizados> listHistory) {
        this.listHistory = listHistory;
        this.listAllHistory= new ArrayList<>(listHistory);

    }

    ArrayList<InfoPagosRealizados> listHistory;
    ArrayList<InfoPagosRealizados> listAllHistory;
    private View.OnClickListener listener;

    @Override
    public void onClick(View v) {
        if (listener!=null)
        {
            listener.onClick(v);
        }

    }

    @NonNull
    @Override
    public AdapterHistHoy.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_realizados_hoy_info,null,false);
        view.setOnClickListener(this);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHistHoy.ViewHolderDatos holder, int position) {
        holder.nombre.setText(listHistory.get(position).getNombre()+" "+listHistory.get(position).getApe1()+" "+listHistory.get(position).getApe2());
        holder.tv_horaPago.setText(listHistory.get(position).getHora());
        holder.tvIDCredito.setText(listHistory.get(position).getNo_credito());
        holder.saldoActual.setText(""+Math.rint(Double.parseDouble(listHistory.get(position).getSaldo_actual().replace("$",""))*100)/100);





    }

    @Override
    public int getItemCount() {
        return listHistory.size();
    }

    public void setOnClickListener(View.OnClickListener listener)
    {
        this.listener=listener;
    }

    @Override
    public Filter getFilter() {
        return filtroLista;
    }

    private Filter filtroLista=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<InfoPagosRealizados> filteredList=new ArrayList<>();

            if (constraint==null || constraint.length() == 0){
                filteredList.addAll(listAllHistory);
            }
            else
            {
                String filterPattern=constraint.toString().toLowerCase().trim();

                for (InfoPagosRealizados item : listAllHistory){
                    if (item.getNombre().toLowerCase().contains(filterPattern) || item.getNo_credito().toLowerCase().contains(filterPattern) || item.getApe1().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results=new FilterResults();
            results.values=filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listHistory.clear();
            listHistory.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView tvIDCredito,nombre,tv_horaPago,saldoActual;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            tvIDCredito=itemView.findViewById(R.id.id_credito);
            nombre=itemView.findViewById(R.id.nombre);
            tv_horaPago=itemView.findViewById(R.id.tv_horaPago);
            saldoActual=itemView.findViewById(R.id.saldo);
            /*cliente=itemView.findViewById(R.id.nombre);
            no_cliente=itemView.findViewById(R.id.no_cliente);
            id_credito=itemView.findViewById(R.id.id_credito);
            saldo=itemView.findViewById(R.id.saldo);
            pagar=itemView.findViewById(R.id.pagar_hoy);
            ape1=itemView.findViewById(R.id.ape1);
            ape2=itemView.findViewById(R.id.ape2);
            calle=itemView.findViewById(R.id.calle);
            colonia=itemView.findViewById(R.id.colonia);
            numero=itemView.findViewById(R.id.numero);
            telefono=itemView.findViewById(R.id.telefono);
            cp=itemView.findViewById(R.id.cp);
            num_int=itemView.findViewById(R.id.num_int);
            fechaPago=itemView.findViewById(R.id.tv_fechaPago);*/



        }

        /*public void asignarDatos(String datos) {
            cliente.setText(datos);
        }*/
    }

}
