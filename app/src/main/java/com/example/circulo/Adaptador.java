package com.example.circulo;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolderDatos> implements View.OnClickListener, Filterable {

    public Adaptador(ArrayList<informacion> listDatos) {
        this.listDatos = listDatos;
        this.listAllDatos=new ArrayList<>(listDatos);
    }

    ArrayList<informacion> listDatos;
    ArrayList<informacion> listAllDatos;
    private View.OnClickListener listener;

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.informacion,null,false);
        view.setOnClickListener(this);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        holder.cliente.setText(listDatos.get(position).getNombre()+" "+listDatos.get(position).getApe1()+" "+listDatos.get(position).getApe2());
        holder.id_credito.setText(listDatos.get(position).getNo_credito());
        holder.saldo.setText(listDatos.get(position).getSaldoActual());
        holder.pagar.setText(listDatos.get(position).getPagar_hoy());
        holder.no_cliente.setText(listDatos.get(position).getId());
        holder.ape1.setText(listDatos.get(position).getApe1());
        holder.ape2.setText(listDatos.get(position).getApe2());
        holder.calle.setText(listDatos.get(position).getCalle());
        holder.colonia.setText(listDatos.get(position).getColonia());
        holder.numero.setText(listDatos.get(position).getNum_ext());
        holder.telefono.setText(listDatos.get(position).getTelefono());
        holder.cp.setText(listDatos.get(position).getCp());
        holder.num_int.setText(listDatos.get(position).getCp());
        holder.fechaPago.setText(listDatos.get(position).getFecha_prox_pago());

        SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date = parseador.parse(listDatos.get(position).getFecha_prox_pago());
            holder.fechaPagoFormat.setText(formateador.format(date));
        } catch(Exception e) {
        }


        final DateFormat formatter;
        Date fechaIni,fechaRec;
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Calendar cal = Calendar.getInstance();
            String year=String.valueOf(cal.get(Calendar.YEAR));
            String mess=String.valueOf(cal.get(Calendar.MONTH)+1);
            String diaa=String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
            if (diaa.equals("1") || diaa.equals("2") || diaa.equals("3") || diaa.equals("4") || diaa.equals("5") || diaa.equals("6") || diaa.equals("7") || diaa.equals("8") || diaa.equals("9"))
            {
                diaa="0"+diaa;
            }
            if (mess.equals("1") || mess.equals("2") || mess.equals("3") || mess.equals("4") || mess.equals("5") || mess.equals("6") || mess.equals("7") || mess.equals("8") || mess.equals("9"))
            {
                mess="0"+mess;
            }

            String fecha=year+"-"+mess+"-"+diaa;
            fechaRec = formatter.parse(listDatos.get(position).getFecha_prox_pago());
            fechaIni = formatter.parse(fecha);



           // Log.i("Fecha",year+"-"+mess+"-"+diaa);

            if(fechaRec.after(fechaIni)){
               // Log.i("Date", "Estoy despues de la fecha de pago");
                holder.cobros.setBackgroundColor(Color.parseColor("#FFB4FDA4"));
                holder.cobros.setRadius(8);
                holder.atraso.setVisibility(View.INVISIBLE);
                holder.tvAtraso.setVisibility(View.INVISIBLE);


            }
            else if(fechaRec.before(fechaIni)){
                //holder.cobros.setCardBackgroundColor(Color.parseColor("#FDA4A4"));
                holder.cobros.setBackgroundColor(Color.parseColor("#FDA4A4"));
               // Log.i("Date", "Estoy antes de la fecha de pago");
                long diff = fechaIni.getTime() - fechaRec.getTime();
                float days = (diff / (1000 * 60 * 60 * 24));
                int dai=(int) days;
                holder.cobros.setRadius(8);
                holder.atraso.setText(dai+"");
                holder.atraso.setVisibility(View.VISIBLE);
                holder.tvAtraso.setVisibility(View.VISIBLE);
            }
            else if(fechaRec.equals(fechaIni)){
                holder.cobros.setBackgroundColor(Color.parseColor("#FFFDFDA4"));

               // Log.i("Date", "Estoy igual que la fecha de pago");FFFDFDA4
                holder.cobros.setRadius(8);
                holder.atraso.setVisibility(View.INVISIBLE);
                holder.tvAtraso.setVisibility(View.INVISIBLE);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return listDatos.size();
    }

    public void setOnClickListener(View.OnClickListener listener)
    {
        this.listener=listener;
    }

    @Override
    public void onClick(View v) {
        if (listener!=null)
        {
            listener.onClick(v);
        }

    }

    @Override
    public Filter getFilter() {
        return filtroLista;
    }

    private Filter filtroLista=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<informacion> filteredList=new ArrayList<>();

            if (constraint==null || constraint.length() == 0){
                filteredList.addAll(listAllDatos);
            }
            else
            {
                String filterPattern=constraint.toString().toLowerCase().trim();

                for (informacion item : listAllDatos){
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
            listDatos.clear();
            listDatos.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView cliente,no_cliente,id_credito,saldo,pagar,ape1,ape2,calle,colonia,numero,telefono,cp,num_int,fechaPago,fechaPagoFormat,tvAtraso,atraso;
        CardView cobros;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);

            cliente=itemView.findViewById(R.id.nombre);
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
            fechaPago=itemView.findViewById(R.id.tv_fechaPago);
            fechaPagoFormat=itemView.findViewById(R.id.fecha_pagoFormat);
            cobros=itemView.findViewById(R.id.cobros);
            tvAtraso=itemView.findViewById(R.id.tv_diasAtraso);
            atraso=itemView.findViewById(R.id.atraso);


        }

        /*public void asignarDatos(String datos) {
            cliente.setText(datos);
        }*/
    }

}
