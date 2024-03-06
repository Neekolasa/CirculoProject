package com.example.circulo.Interfaces;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.circulo.AdapterHistorialAbonos;
import com.example.circulo.Database.Database;
import com.example.circulo.Entitites.Abono_moratorio;
import com.example.circulo.Entitites.Abonos;
import com.example.circulo.Entitites.Credito;
import com.example.circulo.Entitites.Historial;
import com.example.circulo.Entitites.TrabajadorLoggin;
import com.example.circulo.Entitites.Trabajadores_sistema;
import com.example.circulo.InfoHistorialAlltime;
import com.example.circulo.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HistorialAlltime extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<InfoHistorialAlltime> datos;
    Button volver;
    EditText buscar;
    Dialog myDialog,Dialog;
    TextView primer_abono,tv_nombre,tv_direccion,tv_telefono,tv_saldoActual,tv_abonoRecibido,tv_moratorio,tv_no_cliente,tv_noCredito,abono_inicial,abono_final,tv_fechaPagoRealizado,tv_horaRealizo,tv_saldoActualM,tv_abonoRecibidoM,tv_moratorioM;
    Spinner tipo_pago;
    EditText et_pagoRecibido;
    Button btn_eliminar, btn_modificacion,btn_modificar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_alltime);
       // setSupportActionBar(toolbar);
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

        final AdapterHistorialAbonos adapter=new AdapterHistorialAbonos(datos);
        recyclerView.setAdapter(adapter);


        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(HistorialAlltime.this, datos.get(recyclerView.getChildAdapterPosition(v)).getNombre()+" "+datos.get(recyclerView.getChildAdapterPosition(v)).getApe1(), Toast.LENGTH_SHORT).show();

                myDialog=new Dialog(HistorialAlltime.this);
                myDialog.setContentView(R.layout.opc_popup2);
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
                tv_horaRealizo=myDialog.findViewById(R.id.tv_horaPagoRealizado);
                tv_fechaPagoRealizado=myDialog.findViewById(R.id.tv_fechaPagoRealizado);
                primer_abono=myDialog.findViewById(R.id.abono_inicial);
                //primer_abono.setText(datos.get(recyclerView.getChildAdapterPosition(v)).getContador_abono());

                SpannableString contenido = new SpannableString(String.valueOf(datos.get(recyclerView.getChildAdapterPosition(v)).getContador_abono()));
                contenido.setSpan(new UnderlineSpan(), 0, contenido.length(), 0);
                primer_abono.setText(contenido);

                tv_nombre.setText(datos.get(recyclerView.getChildAdapterPosition(v)).getNombre()+" "+datos.get(recyclerView.getChildAdapterPosition(v)).getApe1()+" "+datos.get(recyclerView.getChildAdapterPosition(v)).getApe2());
                tv_direccion.setText("C. "+datos.get(recyclerView.getChildAdapterPosition(v)).getCalle()+" #"+datos.get(recyclerView.getChildAdapterPosition(v)).getNum_ext()+" Col. "+datos.get(recyclerView.getChildAdapterPosition(v)).getColonia()+" \nC.P. "+
                        datos.get(recyclerView.getChildAdapterPosition(v)).getCp());
                tv_telefono.setText(datos.get(recyclerView.getChildAdapterPosition(v)).getTelefono());
                tv_saldoActual.setText("$"+ Math.rint(Double.parseDouble(datos.get(recyclerView.getChildAdapterPosition(v)).getSaldo_actual().replace("$",""))*100)/100);
                tv_abonoRecibido.setText("$"+datos.get(recyclerView.getChildAdapterPosition(v)).getAbono_recibido());
                tv_no_cliente.setText(datos.get(recyclerView.getChildAdapterPosition(v)).getNo_cliente());

                List<Historial> data=Database.getInstance(getApplicationContext()).historialDAO().getDatos(datos.get(recyclerView.getChildAdapterPosition(v)).getNo_abono());
                for (int z=0; z<data.size();z++)
                {
                    tv_noCredito.setText(data.get(z).getNo_credito());
                    //Toast.makeText(HistorialAlltime.this, data.get(z).getNo_credito()+" "+data.get(z).getNombre(), Toast.LENGTH_SHORT).show();
                }
                //tv_noCredito.setText(datos.get(recyclerView.getChildAdapterPosition(v)).getNo_credito());

                SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date date = parseador.parse(datos.get(recyclerView.getChildAdapterPosition(v)).getFecha_realizo_pago());
                    tv_fechaPagoRealizado.setText(formateador.format(date));
                } catch(Exception e) {
                }

                tv_horaRealizo.setText(datos.get(recyclerView.getChildAdapterPosition(v)).getHora());


                final String noAbono=datos.get(recyclerView.getChildAdapterPosition(v)).getNo_abono();

                final String saldoAnterior=datos.get(recyclerView.getChildAdapterPosition(v)).getSaldo_anterior();


                List<TrabajadorLoggin> trabajadorLoggins=Database.getInstance(getApplicationContext()).trabajadorLogginDAO().getTrabajadoreLoggin();
                List<Trabajadores_sistema> trabajadores_sistemas=Database.getInstance(getApplicationContext()).trabajadores_sistemaDAO().getTrabajadorID(trabajadorLoggins.get(0).getUsername());
                List<Abono_moratorio> abono_moratorio=Database.getInstance(getApplicationContext()).abono_moratorioDAO().getAbonoMoratorio(datos.get(recyclerView.getChildAdapterPosition(v)).getNo_abono(),trabajadores_sistemas.get(0).getNo_trabajador());
                String abonomorat="";
                for (int i=0;i<abono_moratorio.size();i++)
                {
                    abonomorat=abono_moratorio.get(i).getPago_interes();
                }
                if (abonomorat.equals(""))
                {
                    tv_moratorio.setText("N/A");
                }
                else {
                    tv_moratorio.setText("$"+abonomorat);
                }




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

                                        List<Historial> data=Database.getInstance(getApplicationContext()).historialDAO().getDatos(noAbono);
                                        String saldoAnterior="";
                                        String noCredito="";
                                        for (int i=0;i<data.size();i++)
                                        {
                                            saldoAnterior=data.get(i).getSaldo_anterior();
                                            noCredito=data.get(i).getNo_credito();
                                        }


                                        Abonos abonos1=new Abonos();
                                        abonos1.setNo_abono(noAbono);
                                        abonos1.setFecha_prox_pago(fechaAnterior);
                                        abonos1.setSaldo_anterior(saldoAnterior);
                                        abonos1.setNo_credito(noCredito);
                                        abonos1.setEstado("0");
                                        Database.getInstance(getApplicationContext()).abonosDAO().addAbonos(abonos1);
                                        Database.getInstance(getApplicationContext()).historialDAO().borrarHistorialElemento(noAbono);
                                        finish();
                                        startActivity(getIntent());
                                        Toast.makeText(HistorialAlltime.this, "Se ha eliminado el abono satisfactoriamente", Toast.LENGTH_SHORT).show();
                                        //Toast.makeText(HistorialAlltime.this, "", Toast.LENGTH_LONG).show();
                                        break;
                                }
                            }
                        };

                        AlertDialog.Builder builder = new AlertDialog.Builder(HistorialAlltime.this);
                        builder.setMessage("¿Está seguro que desea eliminar este abono?").setTitle("¡Atención!")
                                .setPositiveButton("Cancelar", dialogClickListener)
                                .setNegativeButton("Aceptar", dialogClickListener).show();
                    }
                });

                btn_modificar=myDialog.findViewById(R.id.btn_modificar);
                btn_modificar.setVisibility(View.GONE);
                btn_modificar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Dialog=new Dialog(HistorialAlltime.this);
                        Dialog.setContentView(R.layout.modify_abono);
                        Dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        Dialog.show();

                        tv_saldoActualM=Dialog.findViewById(R.id.tv_saldoActualM);
                        tv_abonoRecibidoM=Dialog.findViewById(R.id.tv_abonoRecibidoM);
                        tv_moratorioM=Dialog.findViewById(R.id.tv_moratorioM);

                        tv_saldoActualM.setText(tv_saldoActual.getText().toString());
                        tv_abonoRecibidoM.setText(tv_abonoRecibido.getText().toString());
                        tv_moratorioM.setText(tv_moratorio.getText().toString());

                        tipo_pago=Dialog.findViewById(R.id.tipo_pago);

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
                                        Toast.makeText(HistorialAlltime.this, "No puede abonar un valor superior al crédito solicitado", Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(HistorialAlltime.this, "Ingrese solo un valor númerico sin signos o espacios", Toast.LENGTH_SHORT).show();
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
                                if (et_pagoRecibido.getText().toString().equals(""))
                                {
                                    Toast.makeText(HistorialAlltime.this, "Debe ingresar una cantidad correcta en el abono", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                   // Database.getInstance(getApplicationContext()).historialDAO().HistorialUpdate(et_pagoRecibido.getText().toString().replace("$",""),tv_saldoActualM.getText().toString().replace("$",""),
                                           // tipo_pago.getSelectedItem().toString(),noAbono,);
                                    Database.getInstance(getApplicationContext()).abono_moratorioDAO().AbonoMoratorioUpdate(tv_moratorioM.getText().toString().replace("$",""),noAbono);
                                    finish();
                                    startActivity(getIntent());
                                    Toast.makeText(HistorialAlltime.this, "Se ha actualizado el abono satisfactoriamente", Toast.LENGTH_SHORT).show();


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
                //Toast.makeText(HistorialAlltime.this, s.toString(), Toast.LENGTH_SHORT).show();
            }

           /* private void filter(String text){
                ArrayList<InfoHistorialAlltime> filtro=new ArrayList<>();

                for (InfoHistorialAlltime item : datos)
                {
                    if (item.getNombre().toLowerCase().contains(text.toLowerCase())){
                        filtro.add(item);
                    }
                    else{}
                }

                adapter.filterList(filtro);

            }*/
        });
    }

    public void llenarDatos()
    {

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
        //List<Historial> historials= Database.getInstance(getApplicationContext()).historialDAO().getHistorialHoy("2020-04-27");
        try {
            List<TrabajadorLoggin> trabajadorLoggins=Database.getInstance(getApplicationContext()).trabajadorLogginDAO().getTrabajadoreLoggin();
            List<Trabajadores_sistema> trabajadores_sistemas=Database.getInstance(getApplicationContext()).trabajadores_sistemaDAO().getTrabajadorID(trabajadorLoggins.get(0).getUsername());
            List<Historial> historials=Database.getInstance(getApplicationContext()).historialDAO().getAllHistorial(trabajadores_sistemas.get(0).getNo_trabajador());

            for (int i=0;i<historials.size(); i++)
            {
                datos.add(new InfoHistorialAlltime(historials.get(i).getNo_abono(),historials.get(i).getNo_credito(),historials.get(i).getNo_cobrador(),historials.get(i).getFecha_prox_pago(),historials.get(i).getFecha_realizo_pago(),historials.get(i).getHora(),historials.get(i).getSaldo_anterior(),historials.get(i).getSaldo_actual(),historials.get(i).getAbono_recibido(),historials.get(i).getTipo_pago(),historials.get(i).getEstatus(),historials.get(i).getEstado(),historials.get(i).getSincronizado(),historials.get(i).getNo_cliente(),historials.get(i).getNombre(),historials.get(i).getApe1(),
                        historials.get(i).getApe2(),historials.get(i).getCalle(),historials.get(i).getNum_ext(),historials.get(i).getNum_int(),historials.get(i).getCp(),historials.get(i).getColonia(),historials.get(i).getTelefono(),historials.get(i).getContador_abono()));
            }
        }
        catch (Exception e)
        {

        }


    }

}
