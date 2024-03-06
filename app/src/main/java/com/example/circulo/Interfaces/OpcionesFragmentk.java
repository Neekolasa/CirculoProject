package com.example.circulo.Interfaces;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.circulo.BuildConfig;
import com.example.circulo.Database.Database;
import com.example.circulo.Entitites.AbnCount;
import com.example.circulo.Entitites.Abono_moratorio;
import com.example.circulo.Entitites.Abonos;
import com.example.circulo.Entitites.Clientes;
import com.example.circulo.Entitites.Credito;
import com.example.circulo.Entitites.Historial;
import com.example.circulo.Entitites.HistorialSolicitudes;
import com.example.circulo.Entitites.TrabajadorLoggin;
import com.example.circulo.Entitites.Trabajadores;
import com.example.circulo.Entitites.Trabajadores_sistema;
import com.example.circulo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class OpcionesFragmentk extends Fragment {
    Button exit;
    View vista;
    CardView descargar_datos,subir_datos,cache,acercade,ayuda;
    String no_abono,no_credito,no_cobrador,fecha_prox_pago,fecha_realizo_pago,hora,saldo_anterior,saldo_actual,abono_recibido,tipo_pago,estatus,estado,no_moratorio,cant_moratorio;
    String no_solicitud,nombre, ape_paterno, ape_materno,sexo,calle,num_ext,colonia,cp,telefono,celular,monto_prestamo,ingresos_mensuales,no_cliente_recomendado, email, num_int, localidad, municipio, comentarios,fecha,hora1,no_cobrador_soli; //FALTA SUBIR SOLICITUDES AL SERVER
    EditText consulta;
    String respuesta,fecha_disponibilidad;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        vista= inflater.inflate(R.layout.fragment_opciones, container, false);

        consulta=vista.findViewById(R.id.consulta);
        exit=vista.findViewById(R.id.exit);
        SharedPreferences settings = getActivity().getSharedPreferences("Loggin", 0);
        final SharedPreferences.Editor editor = settings.edit();
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),LoginActivity.class);
                getActivity().onBackPressed();
                startActivity(intent);
                editor.putBoolean("hasLoggedIn",false);
                editor.commit();
                Database.getInstance(getContext()).trabajadorLogginDAO().borrarTrabajadorLoggin();
            }
        });

        ayuda=vista.findViewById(R.id.ayuda);
        ayuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uriUrl = Uri.parse("https://circulo.distic.com.mx/circulo/tutoriales/tutorial.mp4");
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
            }
        });

        acercade=vista.findViewById(R.id.acercade);
        acercade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener=new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which)
                        {
                            case DialogInterface.BUTTON_POSITIVE:

                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                dialog.cancel();
                                break;
                        }
                    }
                };

                StringBuffer str=new StringBuffer();
                str.append("Sistema Integral de Gestión en Servicios Financieros (SIGSF)"+ "\n");
                str.append("\n");
                str.append("\n");
                str.append("Versión de la aplicación: v"+ BuildConfig.VERSION_NAME);

                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setTitle("Acerca de la aplicación").setMessage(str)
                        .setNegativeButton("Aceptar",dialogClickListener).show();
            }
        });


        subir_datos=vista.findViewById(R.id.subir);
        subir_datos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnline(getContext()))
                {

                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    dialog.cancel();
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    List<TrabajadorLoggin> trabajadorLoggins=Database.getInstance(getContext()).trabajadorLogginDAO().getTrabajadoreLoggin();
                                    String trabajador="";
                                    for (int i=0;i<trabajadorLoggins.size();i++)
                                    {
                                        trabajador=trabajadorLoggins.get(i).getUsername();
                                    }
                                    List<Trabajadores_sistema> trabajadores_sistemas=Database.getInstance(getContext()).trabajadores_sistemaDAO().getTrabajadorID(trabajador);
                                    try {
                                        List<HistorialSolicitudes> historialSolicitudes=Database.getInstance(getContext()).historialSolicitudesDAO().getHistorialSinSincronizar(trabajadores_sistemas.get(0).getNo_trabajador());
                                        if (historialSolicitudes.size()>0)
                                        {
                                            for (int i=0; i<historialSolicitudes.size();i++)
                                            {
                                                no_solicitud= String.valueOf(historialSolicitudes.get(i).getNo_solicitud());
                                                nombre=historialSolicitudes.get(i).getNombre();
                                                ape_paterno=historialSolicitudes.get(i).getApe1();
                                                ape_materno=historialSolicitudes.get(i).getApe2();
                                                sexo=historialSolicitudes.get(i).getSexo();
                                                calle=historialSolicitudes.get(i).getCalle();
                                                num_ext=historialSolicitudes.get(i).getNum_ext();
                                                colonia=historialSolicitudes.get(i).getColonia();
                                                cp=historialSolicitudes.get(i).getCp();
                                                telefono=historialSolicitudes.get(i).getTelefono();
                                                celular=historialSolicitudes.get(i).getCelular();
                                                monto_prestamo=historialSolicitudes.get(i).getMonto_prestamo();
                                                ingresos_mensuales=historialSolicitudes.get(i).getIngreso_mensual();
                                                no_cliente_recomendado=historialSolicitudes.get(i).getNo_cliente_recomendado();
                                                if (no_cliente_recomendado.equals("N/A") || no_cliente_recomendado.equals(""))
                                                {
                                                    no_cliente_recomendado="0";
                                                }
                                                fecha=historialSolicitudes.get(i).getFechaInsercion();
                                                hora1=historialSolicitudes.get(i).getHora();

                                                email=historialSolicitudes.get(i).getEmail();
                                                num_int=historialSolicitudes.get(i).getNum_int();
                                                localidad=historialSolicitudes.get(i).getLocalidad();
                                                municipio=historialSolicitudes.get(i).getMunicipio();
                                                comentarios=historialSolicitudes.get(i).getComentarios();
                                                no_cobrador_soli=historialSolicitudes.get(i).getNo_cobrador();

                                                String URL="https://circulo.distic.com.mx/circulo/cont/api_mobil/consultar_datos.php?datos=addSolicitud&no_solicitud="+no_solicitud+"&nombre="+nombre+"&ape_paterno="+ape_paterno+"&ape_materno="+ape_materno+
                                                        "&sexo="+sexo+"&calle="+calle+"&num_ext="+num_ext+"&colonia="+colonia+"&cp="+cp+"&telefono="+telefono+"&celular="+celular+"&monto_prestamo="+monto_prestamo+"&ingresos_mensuales="+ingresos_mensuales+
                                                        "&no_cliente_recomendado="+no_cliente_recomendado+"&email="+email+"&num_int="+num_int+"&localidad="+localidad+"&municipio="+municipio+"&comentarios="+comentarios+"&fecha="+fecha+"&hora="+hora1+"&no_cobrador="+no_cobrador_soli;
                                                addSolicitud(URL);
                                                Database.getInstance(getContext()).historialSolicitudesDAO().updateSincronizar(String.valueOf(historialSolicitudes.get(i).getNo_solicitud()));

                                                consulta.setText(URL);

                                            }
                                            //TimeUnit.SECONDS.sleep(2);
                                            Toast.makeText(getContext(), "Se ha subido la información de las solicitudes al servidor", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                    catch (Exception e)
                                    {
                                        Toast.makeText(getContext(), "Intente de nuevo", Toast.LENGTH_SHORT).show();
                                    }




                                    List<TrabajadorLoggin> trabajadorLogginss=Database.getInstance(getContext()).trabajadorLogginDAO().getTrabajadoreLoggin();
                                    List<Trabajadores_sistema> trabajadores_sistemass=Database.getInstance(getContext()).trabajadores_sistemaDAO().getTrabajadorID(trabajadorLogginss.get(0).getUsername());
                                    List<Historial> historials=Database.getInstance(getContext()).historialDAO().getHistorialNoSincronizado(trabajadores_sistemass.get(0).getNo_trabajador());
                                    if (historials.size()>0)
                                    {
                                        for (int i=0;i<historials.size();i++)
                                        {
                                            no_abono=historials.get(i).getNo_abono();
                                            no_cobrador=historials.get(i).getNo_cobrador();
                                            //no_credito,no_cobrador,fecha_prox_pago,fecha_realizo_pago,hora,saldo_anterior,saldo_actual,abono_recibido,tipo_pago,estatus,estado
                                            no_credito=historials.get(i).getNo_credito();
                                            fecha_prox_pago=historials.get(i).getFecha_prox_pago();
                                            fecha_realizo_pago=historials.get(i).getFecha_realizo_pago();
                                            hora=historials.get(i).getHora();

                                            saldo_anterior=String.valueOf(Math.rint(Double.parseDouble(historials.get(i).getSaldo_anterior())*100)/100);
                                            saldo_actual= String.valueOf(Math.rint(Double.parseDouble(historials.get(i).getSaldo_actual())*100)/100);
                                            abono_recibido=historials.get(i).getAbono_recibido();
                                            tipo_pago=historials.get(i).getTipo_pago();
                                            estatus=historials.get(i).getEstatus();
                                            estado=historials.get(i).getEstado();
                                            String URL="https://circulo.distic.com.mx/circulo/cont/api_mobil/consultar_datos.php?datos=updateAbonos&no_abono="+no_abono+"&no_credito="+no_credito+"&no_cobrador="+no_cobrador+"&fecha_prox_pago="+fecha_prox_pago+"&fecha_realizo_pago=" +
                                                    fecha_realizo_pago+"&hora="+hora+"&saldo_anterior="+saldo_anterior+"&saldo_actual="+saldo_actual+"&abono_recibido="+abono_recibido+"&tipo_pago="+tipo_pago+"&estatus="+estatus+"&estado="+estado;
                                            //Toast.makeText(getContext(), URL, Toast.LENGTH_SHORT).show();

                                            UpdateAbono(URL);




                                            String URL2="https://circulo.distic.com.mx/circulo/cont/api_mobil/consultar_datos.php?datos=addAbonos&no_abono="+no_abono+"&no_credito="+no_credito+"&no_cobrador="+no_cobrador+"&fecha_prox_pago="+fecha_prox_pago+"&saldo_anterior="+saldo_actual+"&abono_recibido="+abono_recibido+"&estado=0";
                                            if (Double.parseDouble(saldo_actual)<=0)
                                            {
                                                String URLALT="https://circulo.distic.com.mx/circulo/cont/api_mobil/consultar_datos.php?datos=addAbonos&no_abono="+no_abono+"&no_credito="+no_credito+"&no_cobrador="+no_cobrador+"&fecha_prox_pago="+fecha_prox_pago+"&saldo_anterior="+saldo_actual+"&abono_recibido="+abono_recibido+"&estado=0";
                                                addSigAbono(URLALT);
                                            }
                                            else
                                            {
                                                addSigAbono(URL2);
                                                //consulta.setText(URL2);
                                            }
                                            Database.getInstance(getContext()).historialDAO().sincronizarHistorial(no_abono);

                                           /* List<Historial> historialAlltimes=Database.getInstance(getContext()).historialDAO().getCantidadAbonos(no_abono);
                                            List<Credito> creditos=Database.getInstance(getContext()).creditoDAO().getNoCobros(no_credito);
                                            int creditos_num=0;
                                            String abonos_num="";
                                            for (int o=0;o<creditos.size();o++)
                                            {
                                                creditos_num= Integer.parseInt(creditos.get(o).getNo_cobros());
                                            }

                                            for (int j=0; j<historialAlltimes.size();j++)
                                            {
                                                abonos_num= historialAlltimes.get(j).getContador_abono();
                                            }

                                            if (abonos_num!=creditos_num || Double.parseDouble(saldo_actual)!=0)
                                            {

                                            }*/




                                        }
                                        List<TrabajadorLoggin> trabajadorLogginsx=Database.getInstance(getContext()).trabajadorLogginDAO().getTrabajadoreLoggin();
                                        List<Trabajadores_sistema> trabajadores_sistemasx=Database.getInstance(getContext()).trabajadores_sistemaDAO().getTrabajadorID(trabajadorLoggins.get(0).getUsername());
                                        List<Abono_moratorio> abono_moratorios=Database.getInstance(getContext()).abono_moratorioDAO().getAbonosMoratorios(trabajadores_sistemasx.get(0).getNo_trabajador());
                                        String no_abono,cantidad;
                                        for (int i=0;i<abono_moratorios.size();i++)
                                        {
                                            no_abono=abono_moratorios.get(i).getNo_abono();
                                            cantidad=abono_moratorios.get(i).getPago_interes();
                                            String URL="https://circulo.distic.com.mx/circulo/cont/api_mobil/consultar_datos.php?datos=addMoratorios&no_abono="+no_abono+"&pago_interes="+cantidad;
                                            addMoratorio(URL);

                                        }
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        Toast.makeText(getContext(), "Se ha subido la información de los abonos al servidor", Toast.LENGTH_LONG).show();
                                    }
                                    else
                                    {
                                        Toast.makeText(getContext(), "No hay más datos pendientes de subir", Toast.LENGTH_SHORT).show();
                                    }

                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("¿Está seguro que desea subir la información del sistema al servidor?").setTitle("¡Atención!")
                            .setPositiveButton("Cancelar", dialogClickListener)
                            .setNegativeButton("Aceptar", dialogClickListener).show();




                }
                else{
                    Toast.makeText(getContext(), "Debe tener una conexión estable a internet para realizar esta acción", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cache=vista.findViewById(R.id.cache);
        cache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuffer str=new StringBuffer();
                str.append("Al seleccionar está opción se eliminarán los datos contenidos en el sistema, incluyendo: ");
                str.append("\n");
                str.append("- Datos de los abonos"+"\n");
                str.append("- Datos de los impuestos moratorios"+"\n");
                str.append("- Datos de los créditos"+"\n");
                str.append("- Datos de las solicitudes"+"\n");
                str.append("- Datos del historial"+"\n");



                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                dialog.cancel();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:

                                //Database.getInstance(getContext()).clientesDAO().borrarClientes();
                                Database.getInstance(getContext()).abono_moratorioDAO().borrarAbonoMoratorio();
                                Database.getInstance(getContext()).abonosDAO().borrarAbonos();
                               // Database.getInstance(getContext()).trabajadoresDAO().borrarTrabajadores();
                                //Database.getInstance(getContext()).trabajadores_sistemaDAO().borrarTrabajadoresSistema();
                                Database.getInstance(getContext()).creditoDAO().borrarCreditos();
                                Database.getInstance(getContext()).historialDAO().borrarHistorial();
                                Database.getInstance(getContext()).solicitudDAO().borrarSolicitud();
                                Database.getInstance(getContext()).abncountDAO().deleteAbncount();
                                //Database.getInstance(getContext()).solicitudDAO().borrarSolicitud();
                                Database.getInstance(getContext()).historialSolicitudesDAO().borrarSolicitud();

                                 /*consultarClientes();
                                consultarCredito();
                                consultarTrabajadores();
                                consultarAbonos();
                                consultarTrabajadoresSistema();
                               */

                                //consultarAbonosMoratorios();

                                Toast.makeText(getContext(), "Se ha limpiado la memoria caché del sistema", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage(str).setTitle("¡Atención!")
                        .setPositiveButton("Cancelar", dialogClickListener)
                        .setNegativeButton("Aceptar", dialogClickListener).show();
            }
        });


        descargar_datos=vista.findViewById(R.id.descargar_datos);
        descargar_datos.setOnClickListener(new View.OnClickListener() {
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

                                Database.getInstance(getContext()).clientesDAO().borrarClientes();
                                Database.getInstance(getContext()).abono_moratorioDAO().borrarAbonoMoratorio();
                                Database.getInstance(getContext()).abonosDAO().borrarAbonos();
                                Database.getInstance(getContext()).trabajadoresDAO().borrarTrabajadores();
                                Database.getInstance(getContext()).trabajadores_sistemaDAO().borrarTrabajadoresSistema();
                                Database.getInstance(getContext()).creditoDAO().borrarCreditos();
                                Database.getInstance(getContext()).abncountDAO().deleteAbncount();
                                consultarTodosAbonos();
                                Database.getInstance(getContext()).solicitudDAO().borrarSolicitud();
                                //Database.getInstance(getContext()).historialSolicitudesDAO().borrarSolicitud();

                                eliminarCoincidentes();
                                consultarClientes();
                                consultarCredito();
                                consultarTrabajadores();
                                consultarAbonos();
                                consultarTrabajadoresSistema();
                                eliminarCoincidentes();


                                /**/

                                //consultarAbonosMoratorios();
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(getContext(), "Se ha actualizado la información", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Se obtendrá la información más actuál del servidor").setTitle("¡Atención!")
                        .setPositiveButton("Cancelar", dialogClickListener)
                        .setNegativeButton("Aceptar", dialogClickListener).show();






            }
        });

        return vista;
    }
    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }


    private void consultarClientes(){
        String URL="https://circulo.distic.com.mx/circulo/cont/api_mobil/consultar_datos.php?datos=clientes";
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject=null;
                Clientes cliente=new Clientes();
                try {

                    for (int i=0; i<response.length();i++)
                    {
                        jsonObject=response.getJSONObject(i);
                        cliente.setNo_cliente(jsonObject.getString("no_cliente"));
                        cliente.setNombre(jsonObject.getString("nombre"));
                        cliente.setApe1(jsonObject.getString("ape1"));
                        cliente.setApe2(jsonObject.getString("ape2"));
                        cliente.setCalle(jsonObject.getString("calle"));
                        cliente.setNum_ext(jsonObject.getString("num_ext"));
                        cliente.setNum_int(jsonObject.getString("num_int"));
                        cliente.setCp(jsonObject.getString("cp"));
                        cliente.setColonia(jsonObject.getString("colonia"));
                        cliente.setTelefono(jsonObject.getString("celular"));

                        Database.getInstance(getContext()).clientesDAO().addClientes(cliente);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Toast.makeText(getContext(), "Ha ocurrido un error, compruebe su conexión a internet", Toast.LENGTH_SHORT).show();
                //error.printStackTrace();

            }
        });

        Volley.newRequestQueue(getContext()).add(jsonArrayRequest);
    }
    private void consultarCredito(){
        String URL="https://circulo.distic.com.mx/circulo/cont/api_mobil/consultar_datos.php?datos=credito";
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject=null;
                Credito credito=new Credito();
                try {

                    for (int i=0; i<response.length();i++) {
                        jsonObject = response.getJSONObject(i);
                        credito.setNo_credito(jsonObject.getString("no_credito"));
                        credito.setAbono(jsonObject.getString("abono"));
                        credito.setNo_cliente(jsonObject.getString("no_cliente"));
                        credito.setNo_cobros(jsonObject.getString("no_cobros"));
                        credito.setTipo_cobro(jsonObject.getString("tipo_cobro"));
                        credito.setMonto_credito(jsonObject.getString("monto_prestamo"));
                        credito.setNo_colaborador(jsonObject.getString("no_colaborador"));
                        Database.getInstance(getContext()).creditoDAO().addCredito(credito);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getContext(), "Ha ocurrido un error, compruebe su conexión a internet "+error.getMessage(), Toast.LENGTH_SHORT).show();
               // Toast.makeText(getContext(), "No se han encontrado creditos", Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        });

        Volley.newRequestQueue(getContext()).add(jsonArrayRequest);
    }
    private void consultarTrabajadores(){
        String URL="https://circulo.distic.com.mx/circulo/cont/api_mobil/consultar_datos.php?datos=trabajadores";
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject=null;
                Trabajadores trabajadores=new Trabajadores();
                try {

                    for (int i=0; i<response.length();i++)
                    {
                        jsonObject=response.getJSONObject(i);
                        trabajadores.setNo_trabajador(jsonObject.getString("no_trabajador"));
                        trabajadores.setNombre(jsonObject.getString("nombre"));
                        Database.getInstance(getContext()).trabajadoresDAO().addTrabajadores(trabajadores);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Toast.makeText(getContext(), "Ha ocurrido un error, compruebe su conexión a internet "+error.getMessage(), Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        });

        Volley.newRequestQueue(getContext()).add(jsonArrayRequest);
    }
    private void consultarAbonosMoratorios(){
        String URL="https://circulo.distic.com.mx/circulo/cont/api_mobil/consultar_datos.php?datos=abono_moratorio";
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject=null;
                Abono_moratorio abono_moratorio=new Abono_moratorio();
                try {

                    for (int i=0; i<response.length();i++)
                    {
                        jsonObject=response.getJSONObject(i);
                        abono_moratorio.setNo_abono(jsonObject.getString("no_abono"));
                        abono_moratorio.setPago_interes(jsonObject.getString("pago_interes"));
                        Database.getInstance(getContext()).abono_moratorioDAO().addAbono_moratorio(abono_moratorio);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Ha ocurrido un error, compruebe su conexión a internet", Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        });

        Volley.newRequestQueue(getContext()).add(jsonArrayRequest);
    }
    private void consultarTrabajadoresSistema(){
        String URL="https://circulo.distic.com.mx/circulo/cont/api_mobil/consultar_datos.php?datos=trabajadores_sistema";
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject=null;
                Trabajadores_sistema trabajadores_sistema=new Trabajadores_sistema();
                try {

                    for (int i=0; i<response.length();i++)
                    {
                        jsonObject=response.getJSONObject(i);
                        trabajadores_sistema.setNo_trabajador(jsonObject.getString("no_trabajador"));
                        trabajadores_sistema.setUsuario_nombre(jsonObject.getString("usuario_nombre"));
                        Database.getInstance(getContext()).trabajadores_sistemaDAO().addTrabajadorSistema(trabajadores_sistema);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getContext(), "Ha ocurrido un error, compruebe su conexión a internet "+error.getMessage(), Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        });

        Volley.newRequestQueue(getContext()).add(jsonArrayRequest);
    }
    private void consultarAbonos(){
        try {
            List<TrabajadorLoggin> trabajadorLoggins=Database.getInstance(getContext()).trabajadorLogginDAO().getTrabajadoreLoggin();
            String trabajador=trabajadorLoggins.get(0).getUsername();

            //Toast.makeText(getContext(), trabajador, Toast.LENGTH_SHORT).show();
            String URL="https://circulo.distic.com.mx/circulo/cont/api_mobil/consultar_datos.php?datos=abonos&trabajador="+trabajador;
           // Toast.makeText(getContext(), URL, Toast.LENGTH_SHORT).show();

            JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    JSONObject jsonObject=null;
                    Abonos abonos=new Abonos();
                    try {

                        for (int i=0; i<response.length();i++)
                        {
                            jsonObject=response.getJSONObject(i);
                            abonos.setNo_abono(jsonObject.getString("no_abono"));
                            abonos.setFecha_prox_pago(jsonObject.getString("fecha_prox_pago"));
                            abonos.setSaldo_anterior(jsonObject.getString("saldo_anterior"));
                            abonos.setNo_credito(jsonObject.getString("no_credito"));
                            abonos.setEstado(jsonObject.getString("estado"));
                            Database.getInstance(getContext()).abonosDAO().addAbonos(abonos);

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try {
                        Toast.makeText(getContext(), "No se han encontrado nuevos abonos", Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e)
                    {

                    }
                    //error.printStackTrace();

                }
            });

            Volley.newRequestQueue(getContext()).add(jsonArrayRequest);

        }
        catch (Exception e)
        {}



    }
    private void consultarTodosAbonos(){
        String URL="https://circulo.distic.com.mx/circulo/cont/api_mobil/consultar_datos.php?datos=Allabonos";
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject=null;
                AbnCount abonos=new AbnCount();
                try {

                    for (int i=0; i<response.length();i++)
                    {
                        jsonObject=response.getJSONObject(i);
                        abonos.setNo_abono(jsonObject.getString("no_abono"));
                        abonos.setNo_credito(jsonObject.getString("no_credito"));
                       // abonos.setNum_abono(jsonObject.getString("num_abono"));
                        Database.getInstance(getContext()).abncountDAO().addAbncount(abonos);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getContext(), "Ha ocurrido un error, compruebe su conexión a internet "+error.getMessage(), Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        });

        Volley.newRequestQueue(getContext()).add(jsonArrayRequest);
    }
    private void UpdateAbono(String URL) {
        StringRequest request=new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(getActivity()).add(request);




    }
    private void addSigAbono(String URL) {
        StringRequest request=new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // Toast.makeText(getContext(), "Se ha subido la informacion al servidor", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(getContext()).add(request);




    }
    private void addMoratorio(String URL) {
        StringRequest request=new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Toast.makeText(getContext(), "Se ha subido la informacion al servidor", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(getContext()).add(request);
    }
    private void addSolicitud(String URL) {
        StringRequest request=new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Toast.makeText(getContext(), "Se ha subido la informacion al servidor", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(getContext()).add(request);

    }
    private void eliminarCoincidentes() {

        List<Abonos> abonos=Database.getInstance(getContext()).abonosDAO().getAbonosCoincidentes();
        for (int i=0; i<abonos.size(); i++)
        {

            Database.getInstance(getContext()).abonosDAO().borrarAbonoByID(Integer.parseInt(abonos.get(i).getNo_abono()));
        }
    }
}
