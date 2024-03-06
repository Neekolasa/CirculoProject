package com.example.circulo.Interfaces;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.sqlite.db.SimpleSQLiteQuery;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.circulo.Adaptador;
import com.example.circulo.DAO.AbncountDAO;
import com.example.circulo.Database.Database;
import com.example.circulo.Entitites.AbnCount;
import com.example.circulo.Entitites.Abono_moratorio;
import com.example.circulo.Entitites.Abonos;
import com.example.circulo.Entitites.Clientes;
import com.example.circulo.Entitites.Cobrador;
import com.example.circulo.Entitites.Credito;
import com.example.circulo.Entitites.Historial;
import com.example.circulo.Entitites.TrabajadorLoggin;
import com.example.circulo.Entitites.Trabajadores_sistema;
import com.example.circulo.R;
import com.example.circulo.informacion;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class cobros extends AppCompatActivity {
    Button volver;

    TextView noCredito,noCliente,pagarHoy,saldoAnterior,telefono,direccion,nombre,noAbono,tv_noAbono,tv_nombre,tv_fechaPago,tv_saldoAnterior,tv_saldoActual,abono_inicial,abono_final,
            tv_pagoEsperado,primer_abono,ultimo_abono,tv_moratorio,monto_credito,tv_fechaPagoPreview;
    EditText buscar,et_fechaRealizaPago,et_Hora,et_pagoRecibido,et_fechaProxPago,et_moratorio;
    CheckBox abono_moratorio;
    Spinner tipo_pago;
    Button btn_abono,btn_cobrar,actualizar;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<informacion> datos;
    Dialog myDialog,Dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cobros);
        actualizar=findViewById(R.id.actualizar);
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                startActivity(getIntent());

            }
        });

        if(ActivityCompat.checkSelfPermission(cobros.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(cobros.this,Manifest
                .permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(cobros.this,new String[]{
                    Manifest.permission.SEND_SMS,
            },1000);
        }
        else {

        };





        List<Abonos> abonos=Database.getInstance(getApplicationContext()).abonosDAO().getAbonosCoincidentes();
        for (int i=0; i<abonos.size(); i++)
        {
            Database.getInstance(getApplicationContext()).abonosDAO().borrarAbonoByID(Integer.parseInt(abonos.get(i).getNo_abono()));
        }

        final String[] fecha = {""};
        recyclerView=findViewById(R.id.vistas);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        datos=new ArrayList<>();
        llenarInformacion();
        final Adaptador adapter=new Adaptador(datos);
        recyclerView.setAdapter(adapter);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog=new Dialog(cobros.this);
                myDialog.setContentView(R.layout.popup);
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();

                btn_abono=myDialog.findViewById(R.id.btn_abono);
                noCredito=myDialog.findViewById(R.id.tv_noCredito);
                noCliente=myDialog.findViewById(R.id.tv_no_cliente);
                noAbono=myDialog.findViewById(R.id.tv_noAbono);
                pagarHoy=myDialog.findViewById(R.id.tv_aPagarHoy);
                saldoAnterior=myDialog.findViewById(R.id.tv_saldoAnterior);
                telefono=myDialog.findViewById(R.id.tv_telefono);
                direccion=myDialog.findViewById(R.id.tv_direccion);
                nombre=myDialog.findViewById(R.id.tv_nombre);
                monto_credito=myDialog.findViewById(R.id.monto_credito);
                fecha[0] =datos.get(recyclerView.getChildAdapterPosition(v)).getFecha_prox_pago();


                abono_inicial=myDialog.findViewById(R.id.abono_inicial);
                abono_final=myDialog.findViewById(R.id.abono_final);


                List<AbnCount> abonos=Database.getInstance(getApplicationContext()).abncountDAO().getAbonosByCredito(datos.get(recyclerView.getChildAdapterPosition(v)).getNo_credito());
                //Toast.makeText(cobros.this, datos.get(recyclerView.getChildAdapterPosition(v)).getNo_credito(), Toast.LENGTH_SHORT).show();
                int num_abonos=0;
                for (int i=0; i<abonos.size(); i++)
                {
                    num_abonos++;
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



                nombre.setText(datos.get(recyclerView.getChildAdapterPosition(v)).getNombre()+" "+datos.get(recyclerView.getChildAdapterPosition(v)).getApe1()+" "+datos.get(recyclerView.getChildAdapterPosition(v)).getApe2());
                direccion.setText("C. "+datos.get(recyclerView.getChildAdapterPosition(v)).getCalle()+" #"+datos.get(recyclerView.getChildAdapterPosition(v)).getNum_ext()+" Col. "+datos.get(recyclerView.getChildAdapterPosition(v)).getColonia()+" \nC.P. "+
                        datos.get(recyclerView.getChildAdapterPosition(v)).getCp());
                telefono.setText(datos.get(recyclerView.getChildAdapterPosition(v)).getTelefono());
                saldoAnterior.setText("$"+datos.get(recyclerView.getChildAdapterPosition(v)).getSaldoActual());
                pagarHoy.setText("$"+datos.get(recyclerView.getChildAdapterPosition(v)).getPagar_hoy());
                noCredito.setText(datos.get(recyclerView.getChildAdapterPosition(v)).getNo_credito());
                noCliente.setText(datos.get(recyclerView.getChildAdapterPosition(v)).getId());
                noAbono.setText(datos.get(recyclerView.getChildAdapterPosition(v)).getNo_abono());


                btn_abono.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Dialog=new Dialog(cobros.this);
                        Dialog.setContentView(R.layout.popup_abono);
                        Dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        Dialog.show();

                        et_fechaRealizaPago=Dialog.findViewById(R.id.et_fechaRealizaPago);
                        et_Hora=Dialog.findViewById(R.id.et_Hora);
                        et_pagoRecibido=Dialog.findViewById(R.id.et_pagoRecibido);


                        btn_cobrar=Dialog.findViewById(R.id.btn_cobrar);
                        tv_noAbono=Dialog.findViewById(R.id.tv_noAbono);
                        tv_nombre=Dialog.findViewById(R.id.tv_nombre);
                        tv_fechaPago=Dialog.findViewById(R.id.tv_fechaPago);
                        tv_saldoAnterior=Dialog.findViewById(R.id.tv_saldoAnterior);
                        tv_saldoActual=Dialog.findViewById(R.id.tv_saldoActual);
                        tv_pagoEsperado=Dialog.findViewById(R.id.tv_pagoEsperado);
                        abono_moratorio=Dialog.findViewById(R.id.abono_moratorio);
                        et_fechaProxPago=Dialog.findViewById(R.id.et_fechaProxPago);
                        tipo_pago=Dialog.findViewById(R.id.tipo_pago);
                        primer_abono=Dialog.findViewById(R.id.primer_abono);
                        ultimo_abono=Dialog.findViewById(R.id.ultimo_abono);
                        tv_moratorio=Dialog.findViewById(R.id.tv_moratorio);
                        et_moratorio=Dialog.findViewById(R.id.et_moratorio);
                        tv_fechaPagoPreview=Dialog.findViewById(R.id.tv_fechaPagoPreview);


                        tv_noAbono.setText(noAbono.getText());
                        tv_nombre.setText(nombre.getText());

                        //****************************************************************************
                        //****************************************************************************
                        SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");
                        SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
                        try {
                            Date date = parseador.parse(fecha[0]);
                            tv_fechaPagoPreview.setText(formateador.format(date));
                        } catch(Exception e) {
                        }
                        tv_fechaPago.setText(fecha[0]);


                        tv_pagoEsperado.setText(pagarHoy.getText());
                        tv_saldoAnterior.setText(saldoAnterior.getText());
                        tv_saldoActual.setText(saldoAnterior.getText());

                        et_pagoRecibido.setText(tv_pagoEsperado.getText().toString().replace("$",""));
                        Double totalG;
                        totalG=Double.parseDouble(tv_saldoAnterior.getText().toString().replace("$",""))-Double.parseDouble(tv_pagoEsperado.getText().toString().replace("$",""));
                        tv_saldoActual.setText("$"+totalG);
                        abono_moratorio.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (abono_moratorio.isChecked()) {
                                    et_moratorio.setVisibility(View.VISIBLE);
                                    tv_moratorio.setVisibility(View.VISIBLE);
                                    DateFormat formatter;
                                    Date ant, desp;
                                    formatter = new SimpleDateFormat("yyyy-MM-dd");
                                    try {
                                        ant = formatter.parse(tv_fechaPago.getText().toString());
                                        desp = formatter.parse(et_fechaRealizaPago.getText().toString());
                                        long diff = desp.getTime() - ant.getTime();
                                        float days = (diff / (1000 * 60 * 60 * 24));
                                        List<Credito> creditoMonto=Database.getInstance(getApplicationContext()).creditoDAO().getMontoPrestamoFromAbn(tv_noAbono.getText().toString());
                                        Double res = ((Double.parseDouble(creditoMonto.get(0).getMonto_credito()) * 5) / 1000) * days;
                                        et_moratorio.setText("$"+res.toString());
                                    }
                                    catch (Exception e){

                                    }
                                }
                                else if (!abono_moratorio.isChecked())
                                {
                                    et_moratorio.setVisibility(View.GONE);
                                    tv_moratorio.setVisibility(View.GONE);
                                    et_moratorio.setText("");
                                }
                            }
                        });
                        //////////////////////
                        final List<AbnCount> abonos=Database.getInstance(getApplicationContext()).abncountDAO().getAbonosByCredito(noCredito.getText().toString());
                        int num_abonos=0;
                        for (int i=0; i<abonos.size(); i++)
                        {
                            num_abonos++;
                        }
                        //List<Historial> count= Database.getInstance(getApplicationContext()).historialDAO().getCantidadAbonos();
                        /*String num_abonos="";
                        String queryString=new String();
                        queryString="SELECT row_number() over(Order by no_abono) AS num FROM abncount WHERE no_credito="+noCredito.getText().toString();
                        SimpleSQLiteQuery query = new SimpleSQLiteQuery(queryString);
                        List<AbnCount> result = Database.getInstance(getApplicationContext()).abncountDAO().getNum(query);
                        for (int i=0;i<result.size();i++){
                            num_abonos=result.get(i).getNo_credito();
                            Toast.makeText(cobros.this, result.get(i).getNo_credito(), Toast.LENGTH_SHORT).show();
                        }*/

                        SpannableString contenido = new SpannableString(String.valueOf(num_abonos));
                        contenido.setSpan(new UnderlineSpan(), 0, contenido.length(), 0);
                        primer_abono.setText(contenido);

                        List<Credito> creditos=Database.getInstance(getApplicationContext()).creditoDAO().getNoCobros(noCredito.getText().toString());
                        String num_cobros= "";
                        for (int i=0; i<creditos.size();i++)
                        {
                            num_cobros=creditos.get(i).getNo_cobros();
                        }
                        SpannableString content = new SpannableString(num_cobros);
                        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                        ultimo_abono.setText(content);

                        Calendar now= Calendar.getInstance();
                        final int year = now.get(Calendar.YEAR);
                        final int month= now.get(Calendar.MONTH);
                        final int day= now.get(Calendar.DAY_OF_MONTH);
                        int minute=now.get(Calendar.MINUTE);
                        int hour=now.get(Calendar.HOUR_OF_DAY);
                        String curTime = String.format("%02d:%02d", hour, minute);
                        et_Hora.setText(curTime);

                        int mes=month+1;
                        String mos=String.valueOf(mes);

                        int dia=day;
                        String dai=String.valueOf(dia);
                        if (dai.equals("1") || dai.equals("2") || dai.equals("3") || dai.equals("4") || dai.equals("5") || dai.equals("6") || dai.equals("7") || dai.equals("8") || dai.equals("9"))
                        {
                            dai="0"+dai;
                        }

                        if (mos.equals("1") || mos.equals("2") || mos.equals("3") || mos.equals("4") || mos.equals("5") || mos.equals("6") || mos.equals("7") || mos.equals("8") || mos.equals("9"))
                        {
                            mos="0"+mos;
                        }


                        String stringBuffer = year +
                                "-" +
                                mos +
                                "-" +
                                dai;
                        et_fechaRealizaPago.setText(stringBuffer);

                        final DateFormat formatter;
                        Date fechaIni,fechaRec;
                        formatter = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            fechaIni = formatter.parse(tv_fechaPago.getText().toString());
                            fechaRec = formatter.parse(et_fechaRealizaPago.getText().toString());


                                    /*Toast.makeText(cobros.this, String.valueOf(fechaIni), Toast.LENGTH_LONG).show();
                                    Toast.makeText(cobros.this, String.valueOf(fechaRec), Toast.LENGTH_SHORT).show();*/

                            if(fechaRec.after(fechaIni)){
                                //Toast.makeText(cobros.this, "Fecha rec está despues de fecha ini", Toast.LENGTH_SHORT).show();
                                abono_moratorio.setChecked(true);
                                et_moratorio.setVisibility(View.VISIBLE);
                                tv_moratorio.setVisibility(View.VISIBLE);
                                DateFormat formater;
                                Date ant, desp;
                                formater = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    ant = formater.parse(tv_fechaPago.getText().toString());
                                    desp = formater.parse(et_fechaRealizaPago.getText().toString());
                                    long diff = desp.getTime() - ant.getTime();
                                    float days = (diff / (1000 * 60 * 60 * 24));

                                    List<Credito> momonto=Database.getInstance(getApplicationContext()).creditoDAO().getMontoPrestamoFromAbn(tv_noAbono.getText().toString());
                                    String monto="";
                                    for (int i=0;i<momonto.size();i++)
                                    {
                                        monto=momonto.get(i).getMonto_credito();
                                    }

                                    Double res = ((Double.parseDouble(monto) * 5) / 1000) * days;
                                    et_moratorio.setText("$"+res.toString());
                                }
                                catch (Exception e){

                                }


                            }
                            else if(fechaRec.before(fechaIni)){
                                abono_moratorio.setChecked(false);
                               // abono_moratorio.setFocusable(false);
                                abono_moratorio.setVisibility(View.GONE);


                            }
                            else if(fechaRec.equals(fechaIni)){
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        final Date fechaProx;
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            fechaProx=format.parse(tv_fechaPago.getText().toString());
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(fechaProx);

                            List<Credito> creditos1=Database.getInstance(getApplicationContext()).creditoDAO().getTipoCobro(tv_noAbono.getText().toString());

                            String tipo_pago="";
                            for (int i=0; i<creditos1.size();i++)
                            {
                                tipo_pago=creditos1.get(i).getTipo_cobro();
                            }
                            if (tipo_pago.equals("Semanal"))
                            {
                                cal.add(Calendar.DATE,7);
                            }
                            else if (tipo_pago.equals("Quincenal"))
                            {
                                cal.add(Calendar.DATE,14);
                            }
                            else{
                                cal.add(Calendar.DATE,7);
                            }

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

                            String fechaSig= cal.get(Calendar.YEAR)+"-"+mess+"-"+diaa;
                            et_fechaProxPago.setText(fechaSig);


                            // add 20 days to the calendar
                           // Toast.makeText(cobros.this, fechaSig.toString(), Toast.LENGTH_LONG).show();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        et_fechaProxPago.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DatePickerDialog.OnDateSetListener onDateSetListener=new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                        int mes=month+1;
                                        String mos=String.valueOf(mes);

                                        int dia=dayOfMonth;
                                        String day=String.valueOf(dia);
                                        if (day.equals("1") || day.equals("2") || day.equals("3") || day.equals("4") || day.equals("5") || day.equals("6") || day.equals("7") || day.equals("8") || day.equals("9"))
                                        {
                                            day="0"+day;
                                        }

                                        if (mos.equals("1") || mos.equals("2") || mos.equals("3") || mos.equals("4") || mos.equals("5") || mos.equals("6") || mos.equals("7") || mos.equals("8") || mos.equals("9"))
                                        {
                                            mos="0"+mos;
                                        }


                                        String stringBuffer = year +
                                                "-" +
                                                mos +
                                                "-" +
                                                day;
                                        et_fechaProxPago.setText(stringBuffer);

                                       /* Calendar now=Calendar.getInstance();
                                        int minute=now.get(Calendar.MINUTE);
                                        int hour=now.get(Calendar.HOUR_OF_DAY);
                                        String curTime = String.format("%02d:%02d", hour, minute);
                                        et_Hora.setText(curTime);*/
                                    }
                                };

                                Calendar now= Calendar.getInstance();
                                SimpleDateFormat parseador = new SimpleDateFormat("dd-MM-yyyy");
                                SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    Date date = formateador.parse(et_fechaProxPago.getText().toString());
                                    now.setTime(date);
                                } catch(Exception e) {
                                }
                                int year = now.get(Calendar.YEAR);
                                int month= now.get(Calendar.MONTH);
                                int day= now.get(Calendar.DAY_OF_MONTH);

                                DatePickerDialog datePickerDialog=new DatePickerDialog(cobros.this, onDateSetListener, year, month, day);
                                datePickerDialog.setTitle("Seleccione una fecha");
                                datePickerDialog.show();
                            }
                        });
                        et_fechaRealizaPago.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DatePickerDialog.OnDateSetListener onDateSetListener=new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                       int mes=month+1;
                                       String mos=String.valueOf(mes);

                                       int dia=dayOfMonth;
                                       String day=String.valueOf(dia);
                                        if (day.equals("1") || day.equals("2") || day.equals("3") || day.equals("4") || day.equals("5") || day.equals("6") || day.equals("7") || day.equals("8") || day.equals("9"))
                                        {
                                            day="0"+day;
                                        }

                                        if (mos.equals("1") || mos.equals("2") || mos.equals("3") || mos.equals("4") || mos.equals("5") || mos.equals("6") || mos.equals("7") || mos.equals("8") || mos.equals("9"))
                                        {
                                            mos="0"+mos;
                                        }


                                        String stringBuffer = year +
                                                "-" +
                                                mos +
                                                "-" +
                                                day;
                                        et_fechaRealizaPago.setText(stringBuffer);

                                        Calendar now=Calendar.getInstance();
                                        int minute=now.get(Calendar.MINUTE);
                                        int hour=now.get(Calendar.HOUR_OF_DAY);
                                        String curTime = String.format("%02d:%02d", hour, minute);
                                        et_Hora.setText(curTime);
                                    }
                                };

                                Calendar now= Calendar.getInstance();
                                int year = now.get(Calendar.YEAR);
                                int month= now.get(Calendar.MONTH);
                                int day= now.get(Calendar.DAY_OF_MONTH);



                                DatePickerDialog datePickerDialog=new DatePickerDialog(cobros.this, onDateSetListener, year, month, day);
                                datePickerDialog.setTitle("Seleccione una fecha");
                                datePickerDialog.show();
                            }
                        });

                        et_fechaRealizaPago.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                                String fechaIn=tv_fechaPago.getText().toString();
                                String fechaRe=et_fechaRealizaPago.getText().toString();
                                /*Toast.makeText(cobros.this, et_fechaRealizaPago.getText().toString(), Toast.LENGTH_SHORT).show();

                                Toast.makeText(cobros.this, dat, Toast.LENGTH_LONG).show();*/



                                DateFormat formatter;
                                Date fechaIni,fechaRec;
                                formatter = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    fechaIni = formatter.parse(fechaIn);
                                    fechaRec = formatter.parse(fechaRe);


                                    /*Toast.makeText(cobros.this, String.valueOf(fechaIni), Toast.LENGTH_LONG).show();
                                    Toast.makeText(cobros.this, String.valueOf(fechaRec), Toast.LENGTH_SHORT).show();*/

                                    if(fechaRec.after(fechaIni)){
                                        //Toast.makeText(cobros.this, "Fecha rec está despues de fecha ini", Toast.LENGTH_SHORT).show();
                                        abono_moratorio.setChecked(true);

                                            et_moratorio.setVisibility(View.VISIBLE);
                                            tv_moratorio.setVisibility(View.VISIBLE);
                                            Date ant, desp;
                                            formatter = new SimpleDateFormat("yyyy-MM-dd");
                                            try {
                                                ant = formatter.parse(tv_fechaPago.getText().toString());
                                                desp = formatter.parse(et_fechaRealizaPago.getText().toString());
                                                long diff = desp.getTime() - ant.getTime();
                                                float days = (diff / (1000 * 60 * 60 * 24));

                                                //*******************************************************************************************************

                                                List<Credito> momonto=Database.getInstance(getApplicationContext()).creditoDAO().getMontoPrestamoFromAbn(tv_noAbono.getText().toString());
                                                String monto="";
                                                for (int i=0;i<momonto.size();i++)
                                                {
                                                    monto=momonto.get(i).getMonto_credito();
                                                }

                                                Double res = ((Double.parseDouble(monto) * 5) / 1000) * days;
                                                et_moratorio.setText("$"+res.toString());
                                            }
                                            catch (Exception e){

                                            }



                                    }
                                    else if(fechaRec.before(fechaIni)){
                                        /*Toast.makeText(cobros.this, "No puede ingresar una fecha anterior a la fecha de pago", Toast.LENGTH_SHORT).show();
                                        et_Hora.setText("");
                                        et_fechaRealizaPago.setText("");*/
                                        abono_moratorio.setChecked(false);
                                        abono_moratorio.setVisibility(View.GONE);

                                    }
                                    else if(fechaRec.equals(fechaIni)){
                                       //Toast.makeText(cobros.this, "Son iguales", Toast.LENGTH_SHORT).show();
                                        abono_moratorio.setChecked(false);

                                        et_moratorio.setVisibility(View.GONE);
                                        tv_moratorio.setVisibility(View.GONE);
                                        et_moratorio.setText("");
                                    }

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }


                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });

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



                                String actual=tv_saldoAnterior.getText().toString().replace("$","");
                                try {
                                    if (Double.valueOf(recibido) > Double.parseDouble(tv_saldoAnterior.getText().toString().trim().replace("$","")) )
                                    {
                                        Toast.makeText(cobros.this, "No puede abonar un valor superior al crédito solicitado", Toast.LENGTH_SHORT).show();
                                        et_pagoRecibido.setText(tv_saldoAnterior.getText().toString().replace("$",""));
                                    }
                                    else
                                    {
                                        Double total=Double.parseDouble(actual)-Double.parseDouble(recibido);
                                        tv_saldoActual.setText("$"+total.toString());
                                    }
                                }
                                catch (Exception e)
                                {
                                    Toast.makeText(cobros.this, "Ingrese solo un valor númerico sin signos o espacios", Toast.LENGTH_SHORT).show();
                                    et_pagoRecibido.setText("");
                                }


                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });

                        if (Double.parseDouble(et_pagoRecibido.getText().toString())>Double.parseDouble(tv_saldoAnterior.getText().toString().replace("$","")))
                        {
                            et_pagoRecibido.setText(tv_saldoAnterior.getText().toString().replace("$",""));
                            Toast.makeText(cobros.this, "El abono no puede ser superior al saldo", Toast.LENGTH_SHORT).show();
                        }

                        btn_cobrar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(final View v) {
                                //Toast.makeText(getApplicationContext(), "NAISU NAISU", Toast.LENGTH_SHORT).show();
                                if (et_pagoRecibido.getText().toString().trim().equals("") || et_pagoRecibido.getText().toString().trim().equals("0.0") || et_pagoRecibido.getText().toString().trim().equals("0"))
                                {
                                    Toast.makeText(cobros.this, "Debe ingresar una cantidad correcta en el abono", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    List<TrabajadorLoggin> trabajadorLoggins=Database.getInstance(getApplicationContext()).trabajadorLogginDAO().getTrabajadoreLoggin();
                                    String cobradr = "";
                                    for (int i = 0; i < trabajadorLoggins.size(); i++) {
                                        cobradr = trabajadorLoggins.get(i).getUsername();
                                    }
                                    List<Trabajadores_sistema> trabajadores_sistemas = Database.getInstance(getApplicationContext()).trabajadores_sistemaDAO().getTrabajadorID(cobradr);

                                    String id_cobrador = "";
                                    for (int i = 0; i < trabajadores_sistemas.size(); i++)
                                    {
                                        id_cobrador=trabajadores_sistemas.get(i).getNo_trabajador();
                                    }
                                    /*List<Cobrador> cobrador = Database.getInstance(getApplicationContext()).cobradorDAO().getCobrador();*/





                                /*Toast.makeText(cobros.this, id_cobrador+" ID", Toast.LENGTH_LONG).show();

                                Toast.makeText(cobros.this, noCredito.getText().toString(), Toast.LENGTH_SHORT).show();*/

                                    final Abonos abonos=new Abonos();
                                    abonos.setNo_abono(tv_noAbono.getText().toString());
                                    abonos.setNo_credito(noCredito.getText().toString());
                                    abonos.setNo_cobrador(id_cobrador);
                                    abonos.setFecha_prox_pago(et_fechaProxPago.getText().toString());
                                    abonos.setFecha_realizo_pago(et_fechaRealizaPago.getText().toString());
                                    abonos.setHora(et_Hora.getText().toString());
                                    abonos.setSaldo_anterior(tv_saldoAnterior.getText().toString().replace("$",""));

                                    abonos.setSaldo_actual(String.valueOf(Math.rint(Double.parseDouble(tv_saldoActual.getText().toString().replace("$",""))*100)/100));
                                    abonos.setAbono_recibido(et_pagoRecibido.getText().toString().replace("$",""));
                                    abonos.setTipo_pago(tipo_pago.getSelectedItem().toString());
                                    abonos.setEstatus("Preaprobado");
                                    abonos.setEstado("1");
                                    abonos.setSincronizado("no");

                                    final Historial historial=new Historial();
                                     ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                    historial.setNo_abono(tv_noAbono.getText().toString());
                                    historial.setNo_credito(noCredito.getText().toString());


                                    historial.setNo_cobrador(id_cobrador);

                                    historial.setFecha_prox_pago(et_fechaProxPago.getText().toString());
                                    historial.setFecha_realizo_pago(et_fechaRealizaPago.getText().toString());
                                    historial.setHora(et_Hora.getText().toString());
                                    historial.setSaldo_anterior(tv_saldoAnterior.getText().toString().replace("$",""));
                                    historial.setSaldo_actual(tv_saldoActual.getText().toString().replace("$",""));
                                    historial.setAbono_recibido(et_pagoRecibido.getText().toString().replace("$",""));
                                    historial.setTipo_pago(tipo_pago.getSelectedItem().toString());
                                    historial.setFecha_anterior_pago(tv_fechaPago.getText().toString());
                                    historial.setContador_abono(primer_abono.getText().toString());
                                   // Toast.makeText(cobros.this, tv_fechaPago.getText().toString(), Toast.LENGTH_SHORT).show();
                                    historial.setEstatus("Preaprobado");
                                    historial.setEstado("1");
                                    historial.setSincronizado("no");
                                    historial.setNo_cliente(noCliente.getText().toString());
                                    //Toast.makeText(cobros.this, noCliente.getText().toString(), Toast.LENGTH_LONG).show();
                                    List<Clientes> clientes=Database.getInstance(getApplicationContext()).clientesDAO().getClienteInfoByID(noCliente.getText().toString());
                                    StringBuffer strong=new StringBuffer();
                                    //CAMBIAR CONSULTA SQL ALGO ANDA MAL
                                    for (int i=0;i<clientes.size();i++)
                                    {
                                        strong.append(clientes.get(i).getNombre()+"\n");
                                        strong.append(clientes.get(i).getApe1()+"\n");
                                        strong.append(clientes.get(i).getApe2()+"\n");
                                        strong.append(clientes.get(i).getCalle()+"\n");
                                        strong.append(clientes.get(i).getNum_ext()+"\n");
                                        strong.append(clientes.get(i).getNum_int()+"\n");
                                        strong.append(clientes.get(i).getCp()+"\n");
                                        strong.append(clientes.get(i).getColonia()+"\n");
                                        strong.append(clientes.get(i).getTelefono()+"\n");
                                        historial.setNombre(clientes.get(i).getNombre());
                                        historial.setApe1(clientes.get(i).getApe1());
                                        historial.setApe2(clientes.get(i).getApe2());
                                        historial.setCalle(clientes.get(i).getCalle());
                                        historial.setNum_ext(clientes.get(i).getNum_ext());
                                        historial.setNum_int(clientes.get(i).getNum_int());
                                        historial.setCp(clientes.get(i).getCp());
                                        historial.setColonia(clientes.get(i).getColonia());
                                        historial.setTelefono(clientes.get(i).getTelefono());
                                    }
                                    //Toast.makeText(cobros.this, strong, Toast.LENGTH_LONG).show();

                                    /**/



                                    DialogInterface.OnClickListener dialogClickListener=new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which)
                                            {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    dialog.cancel();
                                                    break;

                                                case DialogInterface.BUTTON_NEGATIVE:

                                                    if (abono_moratorio.isChecked())
                                                    {

                                                        DateFormat formatter;
                                                        Date ant,desp;
                                                        formatter = new SimpleDateFormat("yyyy-MM-dd");
                                                        try {
                                                            ant = formatter.parse(tv_fechaPago.getText().toString());
                                                            desp = formatter.parse(et_fechaRealizaPago.getText().toString());
                                                            long diff = desp.getTime() - ant.getTime();
                                                            float days = (diff / (1000*60*60*24));

                                                            List<Credito> momonto=Database.getInstance(getApplicationContext()).creditoDAO().getMontoPrestamoFromAbn(tv_noAbono.getText().toString());
                                                            //Toast.makeText(cobros.this, "abono: "+tv_noAbono.getText().toString(), Toast.LENGTH_SHORT).show();
                                                            String monto="";
                                                            for (int i=0;i<momonto.size();i++)
                                                            {
                                                                monto=momonto.get(i).getMonto_credito();
                                                            }

                                                            Double res = ((Double.parseDouble(monto) * 5) / 1000) * days;
                                                           // Toast.makeText(cobros.this, "Tamaño impuesto: "+res, Toast.LENGTH_SHORT).show();
                                                            Abono_moratorio abono_moratorio1=new Abono_moratorio();
                                                            abono_moratorio1.setNo_abono(tv_noAbono.getText().toString());
                                                            abono_moratorio1.setPago_interes(res.toString());
                                                            List<TrabajadorLoggin> trabajadorLoggins=Database.getInstance(getApplicationContext()).trabajadorLogginDAO().getTrabajadoreLoggin();
                                                            List<Trabajadores_sistema> trabajadores_sistemas=Database.getInstance(getApplicationContext()).trabajadores_sistemaDAO().getTrabajadorID(trabajadorLoggins.get(0).getUsername());
                                                            abono_moratorio1.setNo_cobrador(trabajadores_sistemas.get(0).getNo_trabajador());

                                                            if (res<0)
                                                            {
                                                                abono_moratorio.setChecked(false);
                                                            }

                                                            /*-------------------------------------------------------------------------------------------------------*/
                                                            if (res!=0.0)
                                                            {
                                                                try {
                                                                    Database.getInstance(getApplicationContext()).abono_moratorioDAO().addAbono_moratorio(abono_moratorio1);
                                                                }
                                                                catch (Exception e)
                                                                {

                                                                }

                                                            }
                                                            else
                                                            {

                                                            }

                                                            //Toast.makeText(cobros.this, res+"", Toast.LENGTH_SHORT).show();
                                                            //Toast.makeText(cobros.this, "Aqui hay "+days+" dias de diferencia", Toast.LENGTH_SHORT).show();

                                                        } catch (ParseException e) {
                                                            e.printStackTrace();
                                                        }


                                                        //Toast.makeText(cobros.this, "YES", Toast.LENGTH_SHORT).show();


                                                    }

                                                    //Database.getInstance(getApplicationContext()).abonosDAO().updateAbonos(abonos);
                                                    /*-------------------------------------------------------------------------------------------------------*/
                                                   Database.getInstance(getApplicationContext()).historialDAO().addHistorial(historial);
                                                    Database.getInstance(getApplicationContext()).abonosDAO().borrarAbonoByID(Integer.parseInt(tv_noAbono.getText().toString()));
                                                    datos.remove(v);
                                                    finish();
                                                    startActivity(getIntent());
                                                    Toast.makeText(cobros.this, "Se ha completado el abono satisfactoriamente", Toast.LENGTH_SHORT).show();
                                                    StringBuffer mensaje=new StringBuffer();
                                                    mensaje.append("Resumen de su abono "+nombre.getText().toString()+ "\n");
                                                    mensaje.append("Saldo anterior: "+tv_saldoAnterior.getText().toString()+ "\n");
                                                   // mensaje.append("Abono recibido: "+ "\n");
                                                    if (abono_moratorio.isChecked())
                                                    {
                                                        DateFormat formatter;
                                                        Date ant,desp;
                                                        formatter = new SimpleDateFormat("yyyy-MM-dd");

                                                        try {
                                                            ant = formatter.parse(tv_fechaPago.getText().toString());
                                                            desp = formatter.parse(et_fechaRealizaPago.getText().toString());
                                                            long diff = desp.getTime() - ant.getTime();
                                                            float days = (diff / (1000 * 60 * 60 * 24));

                                                            //*************************************************************************************************************************************
                                                            List<Credito> momonto=Database.getInstance(getApplicationContext()).creditoDAO().getMontoPrestamoFromAbn(tv_noAbono.getText().toString());
                                                            String monto="";
                                                            for (int i=0;i<momonto.size();i++)
                                                            {
                                                                monto=momonto.get(i).getMonto_credito();
                                                            }

                                                            Double res = ((Double.parseDouble(monto) * 5) / 1000) * days;

                                                            Double total=res+Double.parseDouble(et_pagoRecibido.getText().toString());
                                                            mensaje.append("Abono recibido: $"+Double.parseDouble(et_pagoRecibido.getText().toString())+ "\n");
                                                            mensaje.append("Impuesto moratorio: $"+res+ "\n");
                                                            mensaje.append("Saldo actual: "+tv_saldoActual.getText().toString()+ "\n");

                                                            //mensaje.append("\n");
                                                           // mensaje.append("Total a pagar: $"+total+ "\n");

                                                        }
                                                        catch (Exception e)
                                                        {

                                                        }
                                                    }
                                                    else
                                                    {

                                                        mensaje.append("Abono recibido: $"+et_pagoRecibido.getText().toString()+ "\n");;
                                                        mensaje.append("Saldo actual: $"+(Math.rint(Double.parseDouble(tv_saldoActual.getText().toString().replace("$",""))*100)/100)+ "\n");
                                                        //mensaje.append("Total a pagar: $"+et_pagoRecibido.getText().toString()+ "\n");
                                                        //mensaje.append("\n");
                                                    }
                                                    SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");
                                                    SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
                                                    try {
                                                        Date date = parseador.parse(et_fechaProxPago.getText().toString());
                                                        String fechaFormateada=formateador.format(date);
                                                        if((Math.rint(Double.parseDouble(tv_saldoActual.getText().toString().replace("$",""))*100)/100)>0){
                                                            mensaje.append("Fecha sig. abono: "+fechaFormateada.toString().toLowerCase().trim());
                                                        }
                                                        else
                                                        {

                                                        }
                                                        if (!telefono.getText().toString().equals(""))
                                                        {

                                                            enviarSMS(telefono.getText().toString(),mensaje.toString());
                                                        }
                                                        else {
                                                            Toast.makeText(cobros.this, "No se ha encontrado un celular registrado del cliente, no se enviará el mensaje con su resumen de pago", Toast.LENGTH_LONG).show();
                                                        }
                                                        //
                                                    } catch(Exception e) {
                                                    }

                                                    //Limite de 160 caracteres
                                                    break;
                                            }
                                        }
                                    };
                                    StringBuffer str=new StringBuffer();

                                    String totalPagar="";
                                    str.append("Saldo anterior: "+tv_saldoAnterior.getText().toString()+ "\n");



                                    if (abono_moratorio.isChecked())
                                    {
                                       // tv_moratorio.setVisibility(View.VISIBLE);
                                        //et_moratorio.setVisibility(View.VISIBLE);
                                        DateFormat formatter;
                                        Date ant,desp;
                                        formatter = new SimpleDateFormat("yyyy-MM-dd");

                                        try {
                                            ant = formatter.parse(tv_fechaPago.getText().toString());
                                            desp = formatter.parse(et_fechaRealizaPago.getText().toString());
                                            long diff = desp.getTime() - ant.getTime();
                                            float days = (diff / (1000 * 60 * 60 * 24));

                                            //*************************************************************************************************************************************
                                            List<Credito> momonto=Database.getInstance(getApplicationContext()).creditoDAO().getMontoPrestamoFromAbn(tv_noAbono.getText().toString());
                                            String monto="";
                                            for (int i=0;i<momonto.size();i++)
                                            {
                                                monto=momonto.get(i).getMonto_credito();
                                            }

                                            Double res = ((Double.parseDouble(monto) * 5) / 1000) * days;

                                            Double total=res+Double.parseDouble(et_pagoRecibido.getText().toString());
                                            str.append("Abono recibido: $"+Double.parseDouble(et_pagoRecibido.getText().toString())+ "\n");
                                            str.append("Impuesto moratorio: $"+res+ "\n");
                                            str.append("Saldo actual: "+tv_saldoActual.getText().toString()+ "\n");

                                            str.append("\n");
                                            str.append("Total a pagar: $"+total+ "\n");

                                        }
                                        catch (Exception e)
                                        {

                                        }
                                    }
                                    else
                                    {

                                        str.append("Abono recibido: $"+et_pagoRecibido.getText().toString()+ "\n");;
                                        str.append("Saldo actual: $"+(Math.rint(Double.parseDouble(tv_saldoActual.getText().toString().replace("$",""))*100)/100)+ "\n");
                                        str.append("Total a pagar: $"+et_pagoRecibido.getText().toString()+ "\n");
                                        str.append("\n");
                                    }
                                    SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");
                                    SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
                                    try {
                                        Date date = parseador.parse(et_fechaProxPago.getText().toString());
                                        String fechaFormateada=formateador.format(date);
                                        str.append("Siguiente abono programado para el día: "+fechaFormateada);
                                    } catch(Exception e) {
                                    }


                                    AlertDialog.Builder builder=new AlertDialog.Builder(cobros.this);
                                    builder.setTitle("Resumen de abono").setMessage(str)
                                            .setPositiveButton("Cancelar",dialogClickListener)
                                            .setNegativeButton("Aceptar",dialogClickListener).show();





                                    /*try{
                                        SmsManager smgr = SmsManager.getDefault();
                                        smgr.sendTextMessage("+526181720129",null,"Holaaaaa",null,null);
                                        Toast.makeText(getApplicationContext(), "SMS Sent Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                    catch (Exception e){
                                        Toast.makeText(getApplicationContext(), "SMS Failed to Send, Please try again", Toast.LENGTH_SHORT).show();
                                    }*/


                                }




                            }
                        });


                        //startActivity(intent);
                    }
                });

               /* Toast.makeText(cobros.this, "Nombre: "+datos.get(recyclerView.getChildAdapterPosition(v)).getNombre()
                        +"Saldo: "+datos.get(recyclerView.getChildAdapterPosition(v)).getSaldoActual()+" ID: "+datos.get(recyclerView.getChildAdapterPosition(v)).getId(), Toast.LENGTH_SHORT).show();*/

            }
        });
        volver=findViewById(R.id.volver);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_OK);
                finish();
                //print=0;
               // Intent intent=new Intent(getApplicationContext(),AbonosFragment.class);
                //startActivity(intent);

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

    private void llenarInformacion() {
        List<Clientes> cl= Database.getInstance(getApplicationContext()).clientesDAO().getInfoCreditoClientes();
        List<Credito> cr= Database.getInstance(getApplicationContext()).creditoDAO().getClientesCredito();

        List<Abonos> ab= Database.getInstance(getApplicationContext()).abonosDAO().getAbonosHoyInfo();

        ///**********************************************************************************************************
        // *********************************************************************************************************/
        List<Abonos> abonos=Database.getInstance(getApplicationContext()).abonosDAO().getAbonosCoincidentes();
        for (int i=0; i<abonos.size(); i++)
        {
            Database.getInstance(getApplicationContext()).abonosDAO().borrarAbonoByID(Integer.parseInt(abonos.get(i).getNo_abono()));
        }
       // datos.clear();
        for (int i=0; i<cr.size(); i++)
        {
            datos.add(new informacion(cl.get(i).getNombre(),cl.get(i).getApe1(),cl.get(i).getApe2(),cr.get(i).getNo_credito(),cr.get(i).getAbono(),cr.get(i).getAbono(),
                    cl.get(i).getNo_cliente(),ab.get(i).getSaldo_anterior(),cl.get(i).getCalle(),cl.get(i).getNum_ext(),cl.get(i).getNum_int(),cl.get(i).getCp(),
                    cl.get(i).getColonia(),cl.get(i).getTelefono(),ab.get(i).getFecha_prox_pago(),ab.get(i).getNo_abono()));
        }


        /*for (Clientes clt : cl)
        {
            String id=clt.getNo_cliente();
            name=clt.getNombre() ;
            credito=clt.getNo_cliente();
        }*/
        /*Toast.makeText(getApplicationContext(), cl.get(0).getNo_cliente()+" "+cl.get(0).getNombre()+" "+cl.get(0).getApe1()+" "+cr.get(0).getNo_credito()+" "+cr.get(0).getAbono()+" "+ab.get(0).getFecha_prox_pago()+" "+ab.get(0).getNo_abono(), Toast.LENGTH_LONG).show();

        datos.add(new informacion("fsdfds","fsdfds","fdsfsd","fdsfsdfsd","fdsfsdfsd","fdsfds",
                "fdsf","fdsfsd","fsdfsd","fdsfds","fdsfsd"
        , "dsffsd","fsdfsd","fdsfsd","fsdfsd","fdsfsdfs"));
        datos.add(new informacion("Jose Rodrigo Chavéz","600","496","7894","450"));
        datos.add(new informacion("Mario Hugo Lopez Ramirez","100","487","4965","500"));
        datos.add(new informacion("Selene Delgado","800","184","5000","799"));*/
    }

    private void enviarSMS(String numero,String mensaje){
        try {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage("+52"+numero,null,mensaje,null,null);
            Toast.makeText(getApplicationContext(), "Se ha enviado el resumen de pago al cliente", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), "No se ha enviado el mensaje al cliente", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


}
