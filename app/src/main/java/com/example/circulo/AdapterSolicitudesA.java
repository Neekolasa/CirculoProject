package com.example.circulo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.circulo.Interfaces.InfoPagosRealizados;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdapterSolicitudesA extends RecyclerView.Adapter<AdapterSolicitudesA.ViewHolderDatos> implements View.OnClickListener, Filterable {



    public AdapterSolicitudesA(ArrayList<InfoHistorialSolicitudesH> listHistorial) {
        this.listHistorial = listHistorial;
        this.listAllHistorial=new ArrayList<>(listHistorial);
    }

    ArrayList<InfoHistorialSolicitudesH> listHistorial;
    ArrayList<InfoHistorialSolicitudesH> listAllHistorial;
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
    public AdapterSolicitudesA.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.solicitudhistoriala,null,false);
        view.setOnClickListener(this);
        return new AdapterSolicitudesA.ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSolicitudesA.ViewHolderDatos holder, int position) {
        holder.nombre.setText(listHistorial.get(position).getNombre()+" "+listHistorial.get(position).getApe1()+" "+listHistorial.get(position).getApe2());
        holder.fecha_insercion.setText(String.valueOf(listHistorial.get(position).getFechaInsercion()));
        holder.hora_captura.setText(listHistorial.get(position).getHora());
        holder.id.setText(String.valueOf(listHistorial.get(position).getNo_solicitud()));
        //holder.saldoActual.setText(listHistory.get(position).getSaldo_actual());
        SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date = parseador.parse(listHistorial.get(position).getFechaInsercion());
            holder.fechaInsercionParse.setText(formateador.format(date));
        } catch(Exception e) {
        }


    }

    @Override
    public int getItemCount() {
        return listHistorial.size();
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
            List<InfoHistorialSolicitudesH> filteredList=new ArrayList<>();

            if (constraint==null || constraint.length() == 0){
                filteredList.addAll(listAllHistorial);
            }
            else
            {
                String filterPattern=constraint.toString().toLowerCase().trim();

                for (InfoHistorialSolicitudesH item : listAllHistorial){
                    if (item.getNombre().toLowerCase().contains(filterPattern) || String.valueOf(item.getNo_solicitud()).toLowerCase().contains(filterPattern) || item.getApe1().toLowerCase().contains(filterPattern)){
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
            listHistorial.clear();
            listHistorial.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView nombre,fecha_insercion,hora_captura,id,fechaInsercionParse;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);

            id=itemView.findViewById(R.id.id_solicitud);
            hora_captura=itemView.findViewById(R.id.hora_captura);
            nombre=itemView.findViewById(R.id.nombre);
            fecha_insercion=itemView.findViewById(R.id.fecha_insercion);
            fechaInsercionParse=itemView.findViewById(R.id.fecha_insercionParse);
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


