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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.circulo.AdapterCliente;
import com.example.circulo.Database.Database;
import com.example.circulo.Entitites.Clientes;
import com.example.circulo.Entitites.Historial;
import com.example.circulo.Entitites.HistorialSolicitudes;
import com.example.circulo.Entitites.Solicitud;
import com.example.circulo.Entitites.TrabajadorLoggin;
import com.example.circulo.Entitites.Trabajadores_sistema;
import com.example.circulo.InfoClientesRecomendados;
import com.example.circulo.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class solicitudes extends AppCompatActivity {

    Button volver;
    Button btn_soli;
    Spinner spinner;
    EditText nombre,ape_paterno,ape_materno,telefono,celular,calle,numero,cp,monto_credito,ingreso_mensual,recomendado,colonia,email,num_int,localidad,municipio,comentarios;
    Dialog dialogFragment;
    TextView id_cliente;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<InfoClientesRecomendados> datos;
    EditText buscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitudes);

        btn_soli=findViewById(R.id.btn_soli);
        spinner = findViewById(R.id.sexo);

        //cobros_hechos=findViewById(R.id.cobros_hechos);
        nombre=findViewById(R.id.nombre);
        ape_paterno=findViewById(R.id.ape_paterno);
        ape_materno=findViewById(R.id.ape_materno);
        telefono=findViewById(R.id.telefono);
        celular=findViewById(R.id.celular);
        calle=findViewById(R.id.calle);
        cp=findViewById(R.id.cp);
        colonia=findViewById(R.id.colonia);
        monto_credito=findViewById(R.id.monto_credito);
        ingreso_mensual=findViewById(R.id.ingreso_mensual);
        recomendado=findViewById(R.id.recomendado);
        numero=findViewById(R.id.numero);
        id_cliente=findViewById(R.id.id_cliente);
        //email,num_int,localidad,municipio,comentarios
        email=findViewById(R.id.email);
        num_int=findViewById(R.id.num_int);
        localidad=findViewById(R.id.localidad);
        municipio=findViewById(R.id.municipio);
        comentarios=findViewById(R.id.comentarios);




        recomendado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFragment=new Dialog(solicitudes.this);
                dialogFragment.setContentView(R.layout.activity_dialog_cliente);
                dialogFragment.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogFragment.show();
                recyclerView=dialogFragment.findViewById(R.id.clientes);
                recyclerView.setHasFixedSize(true);
                layoutManager=new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
                datos=new ArrayList<>();

                llenarDatos();
                final AdapterCliente adapter=new AdapterCliente(datos);
                recyclerView.setAdapter(adapter);

                adapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       // Toast.makeText(solicitudes.this, datos.get(recyclerView.getChildAdapterPosition(v)).getNombre(), Toast.LENGTH_SHORT).show();
                        dialogFragment.dismiss();
                        id_cliente.setText(datos.get(recyclerView.getChildAdapterPosition(v)).getNo_cliente());
                        recomendado.setText(datos.get(recyclerView.getChildAdapterPosition(v)).getNombre()+" "+datos.get(recyclerView.getChildAdapterPosition(v)).getApellido_paterno()+" "+datos.get(recyclerView.getChildAdapterPosition(v)).getApellido_materno());
                    }
                });



                buscar=dialogFragment.findViewById(R.id.buscar);
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
                /*Intent intent=new Intent(getApplicationContext(), DialogCliente.class);
                startActivity(intent);*/


            }
        });

        volver=findViewById(R.id.volver);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_OK);
                finish();
            }
        });



        btn_soli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (monto_credito.getText().toString().equals("")){
                    Toast.makeText(solicitudes.this, "Debe ingresar un monto válido", Toast.LENGTH_SHORT).show();
                    monto_credito.setText("");
                }
                else if (monto_credito.getText().toString().equals("0"))
                {
                    Toast.makeText(solicitudes.this, "Debe ingresar un valor superior a 0", Toast.LENGTH_SHORT).show();
                }
                else if (Double.parseDouble(monto_credito.getText().toString())<2000)
                {
                    Toast.makeText(solicitudes.this, "El monto mínimo del crédito debe de ser 2000", Toast.LENGTH_SHORT).show();
                    monto_credito.setText("2000");
                }
                else{
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    dialog.cancel();
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:

                                    if (nombre.getText().toString().equals("") || ape_paterno.getText().toString().equals("") || celular.getText().toString().equals("") || calle.getText().toString().equals("") || cp.getText().toString().equals("") || monto_credito.getText().toString().equals("") ||
                                            ingreso_mensual.getText().toString().equals("") || numero.getText().toString().equals(""))
                                    {
                                        Toast.makeText(solicitudes.this, "Asegurese de llenar todos los campos marcados con '*' para continuar", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {

                                        Solicitud solicitud=new Solicitud();
                                        solicitud.setNombre(nombre.getText().toString());
                                        solicitud.setApe1(ape_paterno.getText().toString());
                                        solicitud.setApe2(ape_materno.getText().toString());
                                        solicitud.setTelefono(telefono.getText().toString());
                                        solicitud.setCelular(celular.getText().toString());
                                        solicitud.setCalle(calle.getText().toString());
                                        solicitud.setCp(cp.getText().toString());
                                        solicitud.setMonto_prestamo(monto_credito.getText().toString());
                                        solicitud.setIngreso_mensual(ingreso_mensual.getText().toString());
                                        solicitud.setNo_cliente_recomendado(recomendado.getText().toString());
                                        solicitud.setNum_ext(numero.getText().toString());
                                        solicitud.setSexo(spinner.getSelectedItem().toString());
                                        solicitud.setColonia(colonia.getText().toString());
                                        solicitud.setEstatus("Activo");

                                        HistorialSolicitudes historialSolicitudes=new HistorialSolicitudes();
                                        historialSolicitudes.setNombre(nombre.getText().toString());
                                        historialSolicitudes.setApe1(ape_paterno.getText().toString());
                                        historialSolicitudes.setApe2(ape_materno.getText().toString());
                                        historialSolicitudes.setTelefono(telefono.getText().toString());
                                        historialSolicitudes.setCelular(celular.getText().toString());
                                        historialSolicitudes.setCalle(calle.getText().toString());
                                        historialSolicitudes.setCp(cp.getText().toString());
                                        historialSolicitudes.setMonto_prestamo(monto_credito.getText().toString());
                                        historialSolicitudes.setIngreso_mensual(ingreso_mensual.getText().toString());
                                        historialSolicitudes.setNo_cliente_recomendado(id_cliente.getText().toString());
                                        historialSolicitudes.setNum_ext(numero.getText().toString());
                                        historialSolicitudes.setSexo(spinner.getSelectedItem().toString());
                                        historialSolicitudes.setColonia(colonia.getText().toString());
                                        historialSolicitudes.setSincronizado("no");
                                        //email,num_int,localidad,municipio,comentarios
                                        historialSolicitudes.setEmail(email.getText().toString());
                                        historialSolicitudes.setNum_int(num_int.getText().toString());
                                        historialSolicitudes.setLocalidad(localidad.getText().toString());
                                        historialSolicitudes.setMunicipio(municipio.getText().toString());
                                        historialSolicitudes.setComentarios(comentarios.getText().toString());
                                        List<TrabajadorLoggin> trabajadorLoggins=Database.getInstance(getApplicationContext()).trabajadorLogginDAO().getTrabajadoreLoggin();
                                        List<Trabajadores_sistema> trabajadores_sistemas=Database.getInstance(getApplicationContext()).trabajadores_sistemaDAO().getTrabajadorID(trabajadorLoggins.get(0).getUsername());
                                        historialSolicitudes.setNo_cobrador(trabajadores_sistemas.get(0).getNo_trabajador());

                                        Calendar now= Calendar.getInstance();
                                        final int year = now.get(Calendar.YEAR);
                                        final int month= now.get(Calendar.MONTH);
                                        final int day= now.get(Calendar.DAY_OF_MONTH);
                                        int minute=now.get(Calendar.MINUTE);
                                        int hour=now.get(Calendar.HOUR_OF_DAY);
                                        String curTime = String.format("%02d:%02d", hour, minute);

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
                                        historialSolicitudes.setFechaInsercion(stringBuffer);
                                        historialSolicitudes.setHora(curTime);

                                        Database.getInstance(getApplicationContext()).solicitudDAO().addSolicitud(solicitud);
                                        Database.getInstance(getApplicationContext()).historialSolicitudesDAO().addHistorial(historialSolicitudes);

                                        Toast.makeText(getApplicationContext(), "Información registrada correctamente", Toast.LENGTH_SHORT).show();
                                        nombre.setText("");
                                        ape_paterno.setText("");
                                        ape_materno.setText("");
                                        telefono.setText("");
                                        celular.setText("");
                                        calle.setText("");
                                        cp.setText("");
                                        monto_credito.setText("");
                                        ingreso_mensual.setText("");
                                        recomendado.setText("");
                                        numero.setText("");
                                        colonia.setText("");
                                        email.setText("");
                                        num_int.setText("");
                                        localidad.setText("");
                                        municipio.setText("");
                                        comentarios.setText("");
                                    }

                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(solicitudes.this);
                    builder.setMessage("Está a punto de añadir una nueva solicitud de crédito ¿Está seguro?").setPositiveButton("Cancelar", dialogClickListener)
                            .setNegativeButton("Aceptar", dialogClickListener).setTitle("¡Atención!").show();
                }




            }
        });
    }
    public void llenarDatos() {
        List<Clientes> clientes = Database.getInstance(getApplicationContext()).clientesDAO().getInfoClientes();
        for (int i = 0; i < clientes.size(); i++) {
            datos.add(new InfoClientesRecomendados(clientes.get(i).getNombre(), clientes.get(i).getApe1(), clientes.get(i).getApe2(), clientes.get(i).getNo_cliente()));
        }
    }
}
