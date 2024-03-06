package com.example.circulo;

import android.icu.text.IDNA;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.circulo.Interfaces.InfoPagosRealizados;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class AdapterHistorialAbonos extends RecyclerView.Adapter<AdapterHistorialAbonos.ViewHolderDatos> implements View.OnClickListener, Filterable {
    ArrayList<InfoHistorialAlltime> listHistory;
    ArrayList<InfoHistorialAlltime> listAllHistory;

    public AdapterHistorialAbonos(ArrayList<InfoHistorialAlltime> listHistory) {
        this.listHistory = listHistory;
        listAllHistory=new ArrayList<>(listHistory);
    }


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
    public AdapterHistorialAbonos.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.historialcompleto,null,false);
        view.setOnClickListener(this);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHistorialAbonos.ViewHolderDatos holder, int position) {
        holder.nombre.setText(listHistory.get(position).getNombre()+" "+listHistory.get(position).getApe1()+" "+listHistory.get(position).getApe2());
        holder.tv_horaPago.setText(listHistory.get(position).getHora());
        holder.tvIDCredito.setText(listHistory.get(position).getNo_credito());

        holder.saldoActual.setText(Math.rint(Double.parseDouble(listHistory.get(position).getSaldo_actual().replace("$",""))*100)/100+"");
        holder.tv_fechaPago.setText(listHistory.get(position).getFecha_realizo_pago());


        SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date = parseador.parse(listHistory.get(position).getFecha_realizo_pago());
            holder.fechaPagoFormat.setText(formateador.format(date));
        } catch(Exception e) {
        }


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
            List<InfoHistorialAlltime> filteredList=new ArrayList<>();

            if (constraint==null || constraint.length() == 0){
                filteredList.addAll(listAllHistory);
            }
            else
            {
                String filterPattern=constraint.toString().toLowerCase().trim();

                for (InfoHistorialAlltime item : listAllHistory){
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
        TextView tvIDCredito,nombre,tv_horaPago,saldoActual,tv_fechaPago,fechaPagoFormat;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            tvIDCredito=itemView.findViewById(R.id.id_credito);
            nombre=itemView.findViewById(R.id.nombre);
            tv_horaPago=itemView.findViewById(R.id.tv_horaPago);
            saldoActual=itemView.findViewById(R.id.saldo);
            tv_fechaPago=itemView.findViewById(R.id.tv_fechaPago);
            fechaPagoFormat=itemView.findViewById(R.id.fecha_PagoFormat);

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

    }

}
