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

import java.util.ArrayList;
import java.util.List;

public class AdapterCliente extends RecyclerView.Adapter<com.example.circulo.AdapterCliente.ViewHolderDatos> implements View.OnClickListener, Filterable {



        public AdapterCliente(ArrayList<InfoClientesRecomendados> listClientes) {
            this.listClientes = listClientes;
            this.listAllClientes = new ArrayList<>(listClientes);
        }

        ArrayList<InfoClientesRecomendados> listClientes;
        ArrayList<InfoClientesRecomendados> listAllClientes;
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
        public AdapterCliente.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recomienda_cliente,null,false);
            view.setOnClickListener(this);
            return new AdapterCliente.ViewHolderDatos(view);
        }

        @Override
        public void onBindViewHolder(@NonNull com.example.circulo.AdapterCliente.ViewHolderDatos holder, int position) {
            holder.nombre.setText(listClientes.get(position).getNombre()+" "+listClientes.get(position).getApellido_paterno()+" "+listClientes.get(position).getApellido_materno());
            holder.id_cliente.setText(listClientes.get(position).getNo_cliente());
            //holder.saldoActual.setText(listHistory.get(position).getSaldo_actual());
        }

        @Override
        public int getItemCount() {
            return listClientes.size();
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
            List<InfoClientesRecomendados> filteredList=new ArrayList<>();

            if (constraint==null || constraint.length() == 0){
                filteredList.addAll(listAllClientes);
            }
            else
            {
                String filterPattern=constraint.toString().toLowerCase().trim();

                for (InfoClientesRecomendados item : listAllClientes){
                    if (item.getNombre().toLowerCase().contains(filterPattern) || item.getNo_cliente().toLowerCase().contains(filterPattern) || item.getApellido_paterno().toLowerCase().contains(filterPattern)){
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
            listClientes.clear();
            listClientes.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    public class ViewHolderDatos extends RecyclerView.ViewHolder {
            TextView nombre,id_cliente;

            public ViewHolderDatos(@NonNull View itemView) {
                super(itemView);

                nombre=itemView.findViewById(R.id.nombre);
                id_cliente=itemView.findViewById(R.id.id_cliente);
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


