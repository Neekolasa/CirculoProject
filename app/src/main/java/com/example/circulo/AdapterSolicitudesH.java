package com.example.circulo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdapterSolicitudesH extends RecyclerView.Adapter<com.example.circulo.AdapterSolicitudesH.ViewHolderDatos> implements View.OnClickListener, Filterable {



    public AdapterSolicitudesH(ArrayList<InfoHistorialSolicitudesH> listHistorial) {
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
    public AdapterSolicitudesH.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.solicitudhistorialh,null,false);
        view.setOnClickListener(this);
        return new AdapterSolicitudesH.ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.circulo.AdapterSolicitudesH.ViewHolderDatos holder, int position) {
        holder.nombre.setText(listHistorial.get(position).getNombre()+" "+listHistorial.get(position).getApe1()+" "+listHistorial.get(position).getApe2());
        holder.id_solicitud.setText(String.valueOf(listHistorial.get(position).getNo_solicitud()));
        holder.tv_horacaptura.setText(listHistorial.get(position).getHora());
        //holder.saldoActual.setText(listHistory.get(position).getSaldo_actual());
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
        TextView nombre,id_solicitud,tv_horacaptura;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);

            tv_horacaptura=itemView.findViewById(R.id.hora_captura);
            nombre=itemView.findViewById(R.id.nombre);
            id_solicitud=itemView.findViewById(R.id.id_solicitud);
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


