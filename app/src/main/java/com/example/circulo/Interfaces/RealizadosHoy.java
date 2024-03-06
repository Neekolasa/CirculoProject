package com.example.circulo.Interfaces;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.circulo.AdapterHistHoy;
import com.example.circulo.Database.Database;
import com.example.circulo.Entitites.AbnCount;
import com.example.circulo.Entitites.Abono_moratorio;
import com.example.circulo.Entitites.Abonos;
import com.example.circulo.Entitites.Credito;
import com.example.circulo.Entitites.Historial;
import com.example.circulo.Entitites.TrabajadorLoggin;
import com.example.circulo.Entitites.Trabajadores_sistema;
import com.example.circulo.InfoHistorialAlltime;
import com.example.circulo.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RealizadosHoy extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<InfoPagosRealizados> datos;
    Button volver,btn_modificar,btn_modificacion,btn_eliminar;
    EditText buscar,et_pagoRecibido,et_Hora;
    Dialog myDialog,Dialog;
    Spinner tipo_pago;
    TextView tv_nombre,tv_direccion,tv_telefono,tv_saldoActual,tv_abonoRecibido,tv_moratorio,tv_no_cliente,tv_noCredito,abono_inicial,abono_final,tv_saldoActualM,tv_abonoRecibidoM,tv_moratorioM;
    CheckBox abono_moratorioo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realizados_hoy);

        recyclerView=findViewById(R.id.historialhoy);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        datos=new ArrayList<>();


        List<Abonos> abonos=Database.getInstance(getApplicationContext()).abonosDAO().getAbonosCoincidentes();
        for (int i=0; i<abonos.size(); i++)
        {
            Database.getInstance(getApplicationContext()).abonosDAO().borrarAbonoByID(Integer.parseInt(abonos.get(i).getNo_abono()));
        }

        volver=findViewById(R.id.volver);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_OK);
                finish();
            }
        });

        llenarDatos();

        final AdapterHistHoy adapter=new AdapterHistHoy(datos);
        recyclerView.setAdapter(adapter);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(RealizadosHoy.this, datos.get(recyclerView.getChildAdapterPosition(v)).getNombre()+" "+datos.get(recyclerView.getChildAdapterPosition(v)).getApe1(), Toast.LENGTH_SHORT).show();

                myDialog=new Dialog(RealizadosHoy.this);
                myDialog.setContentView(R.layout.opc_popup);
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();

                tv_nombre=myDialog.findViewById(R.id.tv_nombre);
                tv_direccion=myDialog.findViewById(R.id.tv_direccion);
                tv_telefono=myDialog.findViewById(R.id.tv_telefono);
                tv_saldoActual=myDialog.findViewById(R.id.tv_saldoActual);
                tv_abonoRecibido=myDialog.findViewById(R.id.tv_abonoRecibido);
                tv_moratorio=myDialog.findViewById(R.id.tv_moratorio);
                tv_no_cliente=myDialog.findViewById(R.id.tv_no_cliente);
                tv_noCredito=myDialog.findViewById(R.id.tv_noCredito);
                abono_inicial=myDialog.findViewById(R.id.abono_inicial);
                abono_final=myDialog.findViewById(R.id.abono_final);
                final String fechaRealiza=datos.get(recyclerView.getChildAdapterPosition(v)).getFecha_realizo_pago();
                tv_nombre.setText(datos.get(recyclerView.getChildAdapterPosition(v)).getNombre()+" "+datos.get(recyclerView.getChildAdapterPosition(v)).getApe1()+" "+datos.get(recyclerView.getChildAdapterPosition(v)).getApe2());
                tv_direccion.setText("C. "+datos.get(recyclerView.getChildAdapterPosition(v)).getCalle()+" #"+datos.get(recyclerView.getChildAdapterPosition(v)).getNum_ext()+" Col. "+datos.get(recyclerView.getChildAdapterPosition(v)).getColonia()+" \nC.P. "+
                        datos.get(recyclerView.getChildAdapterPosition(v)).getCp());
                tv_telefono.setText(datos.get(recyclerView.getChildAdapterPosition(v)).getTelefono());
                //(Math.rint(Double.parseDouble(datos.get(recyclerView.getChildAdapterPosition(v)).getSaldo_actual().replace("$",""))*100)/100)
                tv_saldoActual.setText("$"+Math.rint(Double.parseDouble(datos.get(recyclerView.getChildAdapterPosition(v)).getSaldo_actual().replace("$",""))*100)/100);
                tv_abonoRecibido.setText("$"+datos.get(recyclerView.getChildAdapterPosition(v)).getAbono_recibido());
                tv_no_cliente.setText(datos.get(recyclerView.getChildAdapterPosition(v)).getNo_cliente());
                tv_noCredito.setText(datos.get(recyclerView.getChildAdapterPosition(v)).getNo_credito());



                final String noAbono=datos.get(recyclerView.getChildAdapterPosition(v)).getNo_abono();
                //Toast.makeText(RealizadosHoy.this, noAbono + "Numero de abono", Toast.LENGTH_SHORT).show();

                final String saldoAnterior=datos.get(recyclerView.getChildAdapterPosition(v)).getSaldo_anterior();

                List<TrabajadorLoggin> trabajadorLoggins=Database.getInstance(getApplicationContext()).trabajadorLogginDAO().getTrabajadoreLoggin();
                List<Trabajadores_sistema> trabajadores_sistemas=Database.getInstance(getApplicationContext()).trabajadores_sistemaDAO().getTrabajadorID(trabajadorLoggins.get(0).getUsername());
                final List<Abono_moratorio>[] abono_moratorio = new List[]{Database.getInstance(getApplicationContext()).abono_moratorioDAO().getAbonoMoratorio(datos.get(recyclerView.getChildAdapterPosition(v)).getNo_abono(),trabajadores_sistemas.get(0).getNo_trabajador())};
                String abonomorat="";
                for (int i = 0; i< abono_moratorio[0].size(); i++)
                {
                    abonomorat= abono_moratorio[0].get(i).getPago_interes();
                }
                if (abonomorat.equals(""))
                {
                    tv_moratorio.setText("N/A");
                }
                else {
                    tv_moratorio.setText("$"+abonomorat);
                }


                //List<AbnCount> abonos=Database.getInstance(getApplicationContext()).abncountDAO().getAbonosByCredito(datos.get(recyclerView.getChildAdapterPosition(v)).getNo_credito());
                List<Historial> abonos=Database.getInstance(getApplicationContext()).historialDAO().getCantidad(datos.get(recyclerView.getChildAdapterPosition(v)).getNo_abono());
                //Toast.makeText(cobros.this, datos.get(recyclerView.getChildAdapterPosition(v)).getNo_credito(), Toast.LENGTH_SHORT).show();
                int num_abonos=0;
                for (int i=0; i<abonos.size(); i++)
                {
                    num_abonos=Integer.parseInt(abonos.get(i).getContador_abono());
                }
                SpannableString contenido = new SpannableString(String.valueOf(num_abonos));
                contenido.setSpan(new UnderlineSpan(), 0, contenido.length(), 0);
                abono_inicial.setText(contenido);




                List<Credito> creditos=Database.getInstance(getApplicationContext()).creditoDAO().getNoCobros(datos.get(recyclerView.getChildAdapterPosition(v)).getNo_credito());
                String num_cobros= "";
                for (int i=0; i<creditos.size();i++)
                {
                    num_cobros=creditos.get(i).getNo_cobros();
                }
                SpannableString content = new SpannableString(num_cobros);
                content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                abono_final.setText(content);

                btn_eliminar=myDialog.findViewById(R.id.btn_eliminar);
                btn_eliminar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case DialogInterface.BUTTON_POSITIVE:
                                        dialog.cancel();
                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        List<Historial> historials=Database.getInstance(getApplicationContext()).historialDAO().getFechaAnterior(noAbono);

                                        String fechaAnterior="";
                                        for (int i=0;i<historials.size();i++)
                                        {
                                            fechaAnterior=historials.get(i).getFecha_anterior_pago();
                                        }

                                        Abonos abonos1=new Abonos();
                                        abonos1.setNo_abono(noAbono);
                                        abonos1.setFecha_prox_pago(fechaAnterior);
                                        abonos1.setSaldo_anterior(saldoAnterior);
                                        abonos1.setNo_credito(tv_noCredito.getText().toString());
                                        abonos1.setEstado("0");
                                        Database.getInstance(getApplicationContext()).abonosDAO().addAbonos(abonos1);
                                        Database.getInstance(getApplicationContext()).historialDAO().borrarHistorialElemento(noAbono);
                                        finish();
                                        startActivity(getIntent());
                                        Toast.makeText(RealizadosHoy.this, "Se ha eliminado el abono satisfactoriamente", Toast.LENGTH_SHORT).show();
                                        break;
                                }
                            }
                        };

                        AlertDialog.Builder builder = new AlertDialog.Builder(RealizadosHoy.this);
                        builder.setMessage("¿Está seguro que desea eliminar este abono?").setTitle("¡Atención!")
                                .setPositiveButton("Cancelar", dialogClickListener)
                                .setNegativeButton("Aceptar", dialogClickListener).show();
                    }
                });

                btn_modificar=myDialog.findViewById(R.id.btn_modificar);
                List<Historial> historials= Database.getInstance(getApplicationContext()).historialDAO().getSincronizacion(noAbono);
                for (int i=0;i<historials.size();i++)
                {
                    if (historials.get(i).getSincronizado().equals("si"))
                    {
                        btn_modificar.setVisibility(View.GONE);
                    }
                }
                btn_modificar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Dialog=new Dialog(RealizadosHoy.this);
                        Dialog.setContentView(R.layout.modify_abono);
                        Dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        Dialog.show();
                        //Toast.makeText(RealizadosHoy.this, noAbono+" Numero de abono modificar", Toast.LENGTH_SHORT).show();
                        tv_saldoActualM=Dialog.findViewById(R.id.tv_saldoActualM);
                        et_Hora=Dialog.findViewById(R.id.et_Hora);
                        tv_abonoRecibidoM=Dialog.findViewById(R.id.tv_abonoRecibidoM);
                        tv_moratorioM=Dialog.findViewById(R.id.tv_moratorioM);
                        abono_moratorioo=Dialog.findViewById(R.id.abono_moratorio);
                        tv_saldoActualM.setText(tv_saldoActual.getText().toString());
                        tv_abonoRecibidoM.setText(tv_abonoRecibido.getText().toString());
                        tv_moratorioM.setText(tv_moratorio.getText().toString());

                        try {
                            if (Double.parseDouble(tv_moratorioM.getText().toString().replace("$",""))>0)
                            {
                                abono_moratorioo.setChecked(true);
                            }
                        }
                        catch (Exception e)
                        {
                            abono_moratorioo.setChecked(false);
                        }

                        Calendar now= Calendar.getInstance();
                        int minute=now.get(Calendar.MINUTE);
                        int hour=now.get(Calendar.HOUR_OF_DAY);
                        String curTime = String.format("%02d:%02d", hour, minute);
                        et_Hora.setText(curTime);

                        tipo_pago=Dialog.findViewById(R.id.tipo_pago);


                        abono_moratorioo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                if (abono_moratorioo.isChecked())
                                {

                                    String fechaPago="";
                                    List<Historial> historials=Database.getInstance(getApplicationContext()).historialDAO().getFechaAnterior(noAbono);
                                    for (int i = 0; i<historials.size(); i++)
                                    {
                                        fechaPago=historials.get(i).getFecha_anterior_pago();
                                        //Toast.makeText(RealizadosHoy.this, , Toast.LENGTH_SHORT).show();
                                    }

                                    List<Credito> monto=Database.getInstance(getApplicationContext()).creditoDAO().getMontoPrestamo(noAbono);
                                    String montos="";
                                    for (int i = 0; i<monto.size(); i++)
                                    {
                                        montos=monto.get(i).getMonto_credito();
                                        //Toast.makeText(RealizadosHoy.this, , Toast.LENGTH_SHORT).show();
                                    }
                                    String numero_abono=noAbono;
                                    DateFormat formatter;
                                    Date ant, desp;
                                    formatter = new SimpleDateFormat("yyyy-MM-dd");
                                    try {
                                        ant = formatter.parse(fechaPago);
                                        desp = formatter.parse(fechaRealiza);
                                        //Toast.makeText(getApplicationContext(), "Fecha anterior "+ant.getTime(), Toast.LENGTH_LONG).show();
                                       // Toast.makeText(getApplicationContext(), "Fecha posterior "+desp.getTime(), Toast.LENGTH_LONG).show();

                                        long diff = desp.getTime() - ant.getTime();
                                        float days = (diff / (1000 * 60 * 60 * 24));
                                        //Toast.makeText(getApplicationContext(),days+"",Toast.LENGTH_LONG).show();
                                        Double res = ((Double.parseDouble(montos) * 5) / 1000) * days;
                                       // Toast.makeText(RealizadosHoy.this, montos+" Monto", Toast.LENGTH_SHORT).show();

                                        if (res<0)
                                        {
                                            Toast.makeText(RealizadosHoy.this, "El impuesto moratorio no aplica a este abono", Toast.LENGTH_SHORT).show();
                                            abono_moratorioo.setChecked(false);
                                        }
                                        else{
                                            tv_moratorioM.setText("$"+res.toString());
                                        }

                                    }
                                    catch (Exception e){

                                    }
                                }
                                else
                                {
                                    abono_moratorioo.setChecked(false);
                                    tv_moratorioM.setText("N/A");
                                }


                            }
                        });



                        et_pagoRecibido=Dialog.findViewById(R.id.et_pagoRecibido);
                        et_pagoRecibido.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                String recibido=et_pagoRecibido.getText().toString().replace(" ","").trim();
                                if (recibido.equals("")){
                                    recibido="0";
                                    recibido=recibido.replace(" ","");
                                }
                                if (tv_moratorioM.getText().toString().equals("$"))
                                {
                                    tv_moratorioM.setText("$0");
                                }
                                else
                                {
                                    tv_abonoRecibidoM.setText("$"+et_pagoRecibido.getText().toString());
                                }



                                try {
                                    if (Double.valueOf(recibido) > Double.parseDouble(saldoAnterior))
                                    {
                                        Toast.makeText(RealizadosHoy.this, "No puede abonar un valor superior al crédito solicitado", Toast.LENGTH_SHORT).show();
                                        et_pagoRecibido.setText(saldoAnterior.replace("$",""));
                                    }
                                    else
                                    {
                                        Double total=Double.parseDouble(saldoAnterior)-Double.parseDouble(recibido);
                                        tv_saldoActualM.setText("$"+total.toString());
                                    }
                                }
                                catch (Exception e)
                                {
                                    Toast.makeText(RealizadosHoy.this, "Ingrese solo un valor númerico sin signos o espacios", Toast.LENGTH_SHORT).show();
                                    et_pagoRecibido.setText("");
                                }


                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });

                        btn_modificacion=Dialog.findViewById(R.id.btn_modificacion);
                        btn_modificacion.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (et_pagoRecibido.getText().toString().equals("") || et_pagoRecibido.getText().toString().equals("0"))
                                {
                                    Toast.makeText(RealizadosHoy.this, "Debe ingresar una cantidad correcta en el abono", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    if (abono_moratorioo.isChecked())
                                    {

                                        Database.getInstance(getApplicationContext()).abono_moratorioDAO().AbonoMoratorioUpdate(tv_moratorioM.getText().toString().replace("$",""),noAbono);
                                    }
                                    else
                                    {
                                        Database.getInstance(getApplicationContext()).historialDAO().HistorialUpdate(et_pagoRecibido.getText().toString().replace("$",""),tv_saldoActualM.getText().toString().replace("$",""),
                                                tipo_pago.getSelectedItem().toString(),noAbono,et_Hora.getText().toString());

                                        Database.getInstance(getApplicationContext()).historialDAO().noSincronizarHistorial(noAbono);
                                    }


                                    finish();
                                    startActivity(getIntent());
                                    Toast.makeText(RealizadosHoy.this, "Se ha actualizado el abono satisfactoriamente", Toast.LENGTH_SHORT).show();


                                }
                            }
                        });


                    }
                });


            }
        });

        buscar=findViewById(R.id.buscar);
        buscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.getFilter().filter(s.toString());
            }


        });
    }



    public void llenarDatos(){

        Calendar cal = Calendar.getInstance();
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
       // Toast.makeText(this, cal.get(Calendar.YEAR)+"-"+mess+"-"+diaa, Toast.LENGTH_SHORT).show();
        String fecha=cal.get(Calendar.YEAR)+"-"+mess+"-"+diaa;
        try {
            List<TrabajadorLoggin> trabajadorLoggins=Database.getInstance(getApplicationContext()).trabajadorLogginDAO().getTrabajadoreLoggin();
            List<Trabajadores_sistema> trabajadores_sistemas=Database.getInstance(getApplicationContext()).trabajadores_sistemaDAO().getTrabajadorID(trabajadorLoggins.get(0).getUsername());
            List<Historial> historials= Database.getInstance(getApplicationContext()).historialDAO().getHistorialHoy(fecha,trabajadores_sistemas.get(0).getNo_trabajador());
            //List<Historial> historials=Database.getInstance(getApplicationContext()).historialDAO().getAllHistorial();

            for (int i=0;i<historials.size(); i++)
            {
                datos.add(new InfoPagosRealizados(historials.get(i).getNo_abono(),historials.get(i).getNo_credito(),historials.get(i).getNo_cobrador(),historials.get(i).getFecha_prox_pago(),historials.get(i).getFecha_realizo_pago(),historials.get(i).getHora(),historials.get(i).getSaldo_anterior(),historials.get(i).getSaldo_actual(),historials.get(i).getAbono_recibido(),historials.get(i).getTipo_pago(),historials.get(i).getEstatus(),historials.get(i).getEstado(),historials.get(i).getSincronizado(),historials.get(i).getNo_cliente(),historials.get(i).getNombre(),historials.get(i).getApe1(),
                        historials.get(i).getApe2(),historials.get(i).getCalle(),historials.get(i).getNum_ext(),historials.get(i).getNum_int(),historials.get(i).getCp(),historials.get(i).getColonia(),historials.get(i).getTelefono()));
            }
        }
        catch (Exception e)
        {

        }



    }
}
