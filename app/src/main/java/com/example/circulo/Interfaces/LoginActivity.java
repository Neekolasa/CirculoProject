package com.example.circulo.Interfaces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.circulo.Database.Database;
import com.example.circulo.Entitites.AbnCount;
import com.example.circulo.Entitites.Abono_moratorio;
import com.example.circulo.Entitites.Abonos;
import com.example.circulo.Entitites.Clientes;
import com.example.circulo.Entitites.Cobrador;
import com.example.circulo.Entitites.Credito;
import com.example.circulo.Entitites.TrabajadorLoggin;
import com.example.circulo.Entitites.Trabajadores;
import com.example.circulo.Entitites.Trabajadores_sistema;
import com.example.circulo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class LoginActivity extends AppCompatActivity{
    JSONArray ja;

    RelativeLayout rellay1,rellay2;
    EditText user,pass;

    Button btn_entrar;

    Handler handler=new Handler();
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            rellay1.setVisibility(View.VISIBLE);
            //rellay2.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.login_activity);
        user=findViewById(R.id.user);
        pass=findViewById(R.id.pass);
        SharedPreferences settings = getSharedPreferences(home.PREFS_NAME, 0);
        boolean hasLoggedIn = settings.getBoolean("hasLoggedIn", false);

        if(hasLoggedIn)
        {
            Intent intent=new Intent(getApplicationContext(), home.class);
            startActivity(intent);
            finish();
        }
        rellay1 = (RelativeLayout) findViewById(R.id.rellay1);
        //rellay2 = (RelativeLayout) findViewById(R.id.rellay2);

        handler.postDelayed(runnable,2000);
        btn_entrar=(Button) findViewById(R.id.btn_login);
        btn_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usr=user.getText().toString().toLowerCase();



                Cobrador cobrador=new Cobrador();
                cobrador.setUsername(usr);
                Database.getInstance(getApplicationContext()).cobradorDAO().deleteCobrador();
                Database.getInstance(getApplicationContext()).cobradorDAO().addCobrador(cobrador);
                //Toast.makeText(LoginActivity.this, usr, Toast.LENGTH_SHORT).show();
                /*RequestQueue queue = Volley.newRequestQueue(getApplicationContext());*/
                String url ="https://www.circulo.distic.com.mx/circulo/cont/login.php?pass="+pass.getText().toString().trim()+"&user="+usr.trim();
                //Toast.makeText(LoginActivity.this, url, Toast.LENGTH_SHORT).show();


                ConsultaPass(url);
                /*progress=new ProgressDialog();
                progress.setMessage("Cargando...");
                progress.show();*/
                //request= Volley.newRequestQueue(getApplicationContext());
                //IniciarSesion();
            }
        });


    }



    private void ConsultaPass(String URL) {
        StringRequest request=new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (/*response.contains("Success")*/true)
                {
                    Toast.makeText(LoginActivity.this, "Ha iniciado sesión correctamente", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getApplicationContext(), home.class);
                    String name = "Ting Ling";
                    //intent.putExtra("cobrador_username",user.getText().toString());
                    intent.putExtra("cobrador_username",name);
                    startActivity(intent);

                    Database.getInstance(getApplicationContext()).trabajadorLogginDAO().borrarTrabajadorLoggin();
                    Database.getInstance(getApplicationContext()).clientesDAO().borrarClientes();
                    Database.getInstance(getApplicationContext()).abono_moratorioDAO().borrarAbonoMoratorio();
                    Database.getInstance(getApplicationContext()).abonosDAO().borrarAbonos();
                    Database.getInstance(getApplicationContext()).trabajadoresDAO().borrarTrabajadores();
                    Database.getInstance(getApplicationContext()).trabajadores_sistemaDAO().borrarTrabajadoresSistema();
                    Database.getInstance(getApplicationContext()).creditoDAO().borrarCreditos();
                    Database.getInstance(getApplicationContext()).abncountDAO().deleteAbncount();
                    consultarTodosAbonos();
                    //Database.getInstance(getContext()).solicitudDAO().borrarSolicitud();

                    consultarClientes();
                    consultarCredito();
                    consultarTrabajadores();
                    consultarAbonos();
                    consultarTrabajadoresSistema();
                    eliminarCoincidentes();

                    TrabajadorLoggin trabajadorLoggin=new TrabajadorLoggin();
                    //trabajadorLoggin.setUsername(user.getText().toString().toLowerCase().trim());
                    trabajadorLoggin.setUsername(name);
                    //Toast.makeText(LoginActivity.this, user.getText().toString(), Toast.LENGTH_SHORT).show();
                    Database.getInstance(getApplicationContext()).trabajadorLogginDAO().addTrabajadorLoggin(trabajadorLoggin);



                    /**/

                    //consultarAbonosMoratorios();

                    Toast.makeText(getApplicationContext(), "Se ha actualizado la información del sistema", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(LoginActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (user.getText().toString()=="" || pass.getText().toString()=="" )
                Toast.makeText(LoginActivity.this, "Por favor inserte un valor correcto", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(LoginActivity.this, "Ha ocurrido un error inesperado "+error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(this).add(request);




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

                        Database.getInstance(getApplicationContext()).clientesDAO().addClientes(cliente);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(), "Ha ocurrido un error, compruebe su conexión a internet", Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        });

        Volley.newRequestQueue(getApplicationContext()).add(jsonArrayRequest);
    }
    private void consultarCredito(){
        String URL="https://circulo.distic.com.mx/circulo/cont/api_mobil/consultar_datos.php?datos=credito";
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject=null;
                Credito credito=new Credito();
                try {

                    for (int i=0; i<response.length();i++)
                    {
                        jsonObject=response.getJSONObject(i);
                        credito.setNo_credito(jsonObject.getString("no_credito"));
                        credito.setAbono(jsonObject.getString("abono"));
                        credito.setNo_cliente(jsonObject.getString("no_cliente"));
                        credito.setNo_cobros(jsonObject.getString("no_cobros"));
                        credito.setTipo_cobro(jsonObject.getString("tipo_cobro"));
                        credito.setMonto_credito(jsonObject.getString("monto_prestamo"));
                        Database.getInstance(getApplicationContext()).creditoDAO().addCredito(credito);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(), "Ha ocurrido un error, compruebe su conexión a internet "+error.getMessage(), Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        });

        Volley.newRequestQueue(getApplicationContext()).add(jsonArrayRequest);
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
                        Database.getInstance(getApplicationContext()).trabajadoresDAO().addTrabajadores(trabajadores);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(), "Ha ocurrido un error, compruebe su conexión a internet "+error.getMessage(), Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        });

        Volley.newRequestQueue(getApplicationContext()).add(jsonArrayRequest);
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
                        Database.getInstance(getApplicationContext()).abono_moratorioDAO().addAbono_moratorio(abono_moratorio);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(), "Ha ocurrido un error, compruebe su conexión a internet", Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        });

        Volley.newRequestQueue(getApplicationContext()).add(jsonArrayRequest);
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
                        Database.getInstance(getApplicationContext()).trabajadores_sistemaDAO().addTrabajadorSistema(trabajadores_sistema);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(), "Ha ocurrido un error, compruebe su conexión a internet "+error.getMessage(), Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        });

        Volley.newRequestQueue(getApplicationContext()).add(jsonArrayRequest);
    }
    private void consultarAbonos(){

        String trabajador=user.getText().toString().toLowerCase().trim();
        //Toast.makeText(getApplicationContext(), trabajador, Toast.LENGTH_SHORT).show();
        String URL="https://circulo.distic.com.mx/circulo/cont/api_mobil/consultar_datos.php?datos=abonos&trabajador="+trabajador;
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
                        Database.getInstance(getApplicationContext()).abonosDAO().addAbonos(abonos);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(), "Ha ocurrido un error, compruebe su conexión a internet "+error.getMessage(), Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        });

        Volley.newRequestQueue(getApplicationContext()).add(jsonArrayRequest);
    }
    private void eliminarCoincidentes() {
        List<Abonos> abonos=Database.getInstance(getApplicationContext()).abonosDAO().getAbonosCoincidentes();
        for (int i=0;i<abonos.size();i++)
        {
            Database.getInstance(getApplicationContext()).abonosDAO().borrarAbonoByID(Integer.parseInt(abonos.get(i).getNo_abono()));
        }
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
                        Database.getInstance(getApplicationContext()).abncountDAO().addAbncount(abonos);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Toast.makeText(getApplicationContext(), "Ha ocurrido un error, compruebe su conexión a internet "+error.getMessage(), Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        });

        Volley.newRequestQueue(getApplicationContext()).add(jsonArrayRequest);
    }




}

