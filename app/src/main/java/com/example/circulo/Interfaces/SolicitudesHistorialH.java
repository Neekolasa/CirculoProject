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
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.circulo.AdapterCliente;
import com.example.circulo.AdapterSolicitudesH;
import com.example.circulo.Database.Database;
import com.example.circulo.Entitites.Clientes;
import com.example.circulo.Entitites.Historial;
import com.example.circulo.Entitites.HistorialSolicitudes;
import com.example.circulo.Entitites.TrabajadorLoggin;
import com.example.circulo.Entitites.Trabajadores_sistema;
import com.example.circulo.InfoClientesRecomendados;
import com.example.circulo.InfoHistorialSolicitudesH;
import com.example.circulo.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SolicitudesHistorialH extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<InfoHistorialSolicitudesH> datos;
    EditText buscar,nombre1,ape_paterno1,ape_materno1,telefono,celular,calle,numero,colonia,cp,monto_credito,ingreso_mensual,recomendado,email,num_int,localidad,municipio,comentarios;
    Button volver,btn_modificar,btn_eliminar,btn_soli;
    Dialog myDialog,Dialog,dialogFragment;
    ImageButton im_close;
    TextView tv_nosolicitud,tv_nombre,tv_direccion,tv_telefono,tv_celular,tv_clienteRecomienda,tv_creditoSolicitado,tv_ingresoMensual,id_cliente,tv_email,tv_localidad,tv_municipio;
    Spinner sexo;

    RecyclerView recyclerView1;
    RecyclerView.LayoutManager layoutManager1;
    ArrayList<InfoClientesRecomendados> datos1;
    EditText buscar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitudes_historial_h);

        recyclerView=findViewById(R.id.historialhoy);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        datos=new ArrayList<>();
        llenarInformacion();
        final AdapterSolicitudesH adapter=new AdapterSolicitudesH(datos);
        recyclerView.setAdapter(adapter);



        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myDialog=new Dialog(SolicitudesHistorialH.this);
                myDialog.setContentView(R.layout.dialog_solicitud);
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();
                tv_nosolicitud=myDialog.findViewById(R.id.tv_nosolicitud);
                tv_nombre=myDialog.findViewById(R.id.tv_nombre);
                tv_direccion=myDialog.findViewById(R.id.tv_direccion);
                tv_telefono=myDialog.findViewById(R.id.tv_telefono);
                tv_clienteRecomienda=myDialog.findViewById(R.id.tv_clienteRecomienda);
                tv_creditoSolicitado=myDialog.findViewById(R.id.tv_creditoSolicitado);
                tv_ingresoMensual=myDialog.findViewById(R.id.tv_ingresoMensual);
                tv_celular=myDialog.findViewById(R.id.tv_celular);
                tv_email=myDialog.findViewById(R.id.tv_email);
                tv_localidad=myDialog.findViewById(R.id.tv_localidad);
                tv_municipio=myDialog.findViewById(R.id.tv_municipio);

                String nombre="",ape_paterno="",ape_materno="",calle1="",cp1="",num_ext1="",colonia1="",num_int1="",comentario="";
                List<HistorialSolicitudes> historialSolicitudes=Database.getInstance(getApplicationContext()).historialSolicitudesDAO().getSolicitud(String.valueOf(datos.get(recyclerView.getChildAdapterPosition(v)).getNo_solicitud()));
                for (int i=0; i<historialSolicitudes.size();i++)
                {
                    //tv_nosolicitud.setText(String.valueOf(historialSolicitudes.get(i).getNo_solicitud()));
                    tv_nosolicitud.setText(String.valueOf(datos.get(recyclerView.getChildAdapterPosition(v)).getNo_solicitud()));
                    //tv_nombre.setText(historialSolicitudes.get(i).getNombre()+" "+historialSolicitudes.get(i).getApe1()+" "+historialSolicitudes.get(i).getApe2());

                    nombre=datos.get(recyclerView.getChildAdapterPosition(v)).getNombre();
                    ape_materno=datos.get(recyclerView.getChildAdapterPosition(v)).getApe2();
                    ape_paterno=datos.get(recyclerView.getChildAdapterPosition(v)).getApe1();
                    calle1=datos.get(recyclerView.getChildAdapterPosition(v)).getCalle();
                    cp1=datos.get(recyclerView.getChildAdapterPosition(v)).getCp();
                    num_ext1=datos.get(recyclerView.getChildAdapterPosition(v)).getNum_ext();
                    colonia1=datos.get(recyclerView.getChildAdapterPosition(v)).getColonia();
                    num_int1=datos.get(recyclerView.getChildAdapterPosition(v)).getNum_int();
                    comentario=datos.get(recyclerView.getChildAdapterPosition(v)).getComentarios();
                    tv_nombre.setText(datos.get(recyclerView.getChildAdapterPosition(v)).getNombre()+" "+datos.get(recyclerView.getChildAdapterPosition(v)).getApe1()+" "+datos.get(recyclerView.getChildAdapterPosition(v)).getApe2());
                    tv_direccion.setText("C. "+datos.get(recyclerView.getChildAdapterPosition(v)).getCalle()+" #"+datos.get(recyclerView.getChildAdapterPosition(v)).getNum_ext()+" Col. "+datos.get(recyclerView.getChildAdapterPosition(v)).getColonia()+" \nC.P. "+
                            datos.get(recyclerView.getChildAdapterPosition(v)).getCp());
                    tv_telefono.setText(datos.get(recyclerView.getChildAdapterPosition(v)).getTelefono());
                    tv_celular.setText(datos.get(recyclerView.getChildAdapterPosition(v)).getCelular());
                    tv_email.setText(datos.get(recyclerView.getChildAdapterPosition(v)).getEmail());
                    tv_localidad.setText(datos.get(recyclerView.getChildAdapterPosition(v)).getLocalidad());
                    tv_municipio.setText(datos.get(recyclerView.getChildAdapterPosition(v)).getMunicipio());

                    //tv_clienteRecomienda.setText(datos.get(recyclerView.getChildAdapterPosition(v)).getNo_cliente_recomendado());

                    tv_creditoSolicitado.setText(datos.get(recyclerView.getChildAdapterPosition(v)).getMonto_prestamo());
                    tv_ingresoMensual.setText(datos.get(recyclerView.getChildAdapterPosition(v)).getIngreso_mensual());
                }
                final String no_cliente=datos.get(recyclerView.getChildAdapterPosition(v)).getNo_cliente_recomendado();
                List<Clientes> clientes=Database.getInstance(getApplicationContext()).clientesDAO().getClienteInfoByID(datos.get(recyclerView.getChildAdapterPosition(v)).getNo_cliente_recomendado());

                for (int i=0;i<clientes.size();i++)
                {
                   tv_clienteRecomienda.setText(clientes.get(i).getNombre()+" "+clientes.get(i).getApe1()+" "+clientes.get(i).getApe2());
                }
                /*Calendar now= Calendar.getInstance();
                final int year = now.get(Calendar.YEAR);
                final int month= now.get(Calendar.MONTH);
                final int day= now.get(Calendar.DAY_OF_MONTH);
                int minute=now.get(Calendar.MINUTE);
                int hour=now.get(Calendar.HOUR_OF_DAY);
                String curTime = String.format("%02d:%02d", hour, minute);

                int mes=month+1;
                String mos=String.valueOf(mes);

                int dia=month;
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
                        day;*/



                btn_modificar=myDialog.findViewById(R.id.btn_modificar);
                final String finalNombre = nombre;
                final String finalApe_materno = ape_materno;
                final String finalApe_paterno = ape_paterno;
                final String finalCalle = calle1;
                final String finalNum_ext = num_ext1;
                final String finalColonia = colonia1;
                final String finalCp = cp1;
                final String finalNum_int = num_int1;
                final String finalComentario = comentario;

                List<HistorialSolicitudes> historials= Database.getInstance(getApplicationContext()).historialSolicitudesDAO().getSincronizacion(tv_nosolicitud.getText().toString());
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
                        Dialog=new Dialog(SolicitudesHistorialH.this);
                        Dialog.setContentView(R.layout.dialog_solicitud_modificar);
                        Dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        Dialog.show();
                        nombre1=Dialog.findViewById(R.id.nombre);
                        ape_paterno1=Dialog.findViewById(R.id.ape_paterno);
                        ape_materno1=Dialog.findViewById(R.id.ape_materno);
                        telefono=Dialog.findViewById(R.id.telefono);
                        celular=Dialog.findViewById(R.id.celular);
                        calle=Dialog.findViewById(R.id.calle);
                        numero=Dialog.findViewById(R.id.numero);
                        colonia=Dialog.findViewById(R.id.colonia);
                        cp=Dialog.findViewById(R.id.cp);
                        monto_credito=Dialog.findViewById(R.id.monto_credito);
                        ingreso_mensual=Dialog.findViewById(R.id.ingreso_mensual);
                        recomendado=Dialog.findViewById(R.id.recomendado);
                        id_cliente=Dialog.findViewById(R.id.id_cliente);
                        btn_soli=Dialog.findViewById(R.id.btn_soli);
                        sexo=Dialog.findViewById(R.id.sexo);
                        email=Dialog.findViewById(R.id.email);
                        num_int=Dialog.findViewById(R.id.num_int);
                        localidad=Dialog.findViewById(R.id.localidad);
                        municipio=Dialog.findViewById(R.id.municipio);
                        comentarios=Dialog.findViewById(R.id.comentarios);

                        nombre1.setText(finalNombre);
                        ape_materno1.setText(finalApe_materno);
                        ape_paterno1.setText(finalApe_paterno);
                        telefono.setText(tv_telefono.getText().toString());
                        celular.setText(tv_celular.getText().toString());
                        calle.setText(finalCalle);
                        numero.setText(finalNum_ext);
                        colonia.setText(finalColonia);
                        cp.setText(finalCp);
                        monto_credito.setText(tv_creditoSolicitado.getText().toString());
                        ingreso_mensual.setText(tv_ingresoMensual.getText().toString());
                        id_cliente.setText(no_cliente);
                        email.setText(tv_email.getText().toString());
                        num_int.setText(finalNum_int);
                        localidad.setText(tv_localidad.getText().toString());
                        municipio.setText(tv_municipio.getText().toString());
                        comentarios.setText(finalComentario);


                        btn_soli.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (nombre1.getText().toString().equals("") || ape_paterno1.getText().toString().equals("") || celular.getText().toString().equals("") || calle.getText().toString().equals("") || cp.getText().toString().equals("") || monto_credito.getText().toString().equals("") ||
                                        ingreso_mensual.getText().toString().equals("") || numero.getText().toString().equals("") || localidad.getText().toString().equals("") || municipio.getText().toString().equals(""))
                                {
                                    Toast.makeText(SolicitudesHistorialH.this, "Asegurese de llenar todos los campos marcados con '*' para continuar", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Database.getInstance(getApplicationContext()).historialSolicitudesDAO().updateHistorial(nombre1.getText().toString(),ape_paterno1.getText().toString(),ape_materno1.getText().toString(), sexo.getSelectedItem().toString(), calle.getText().toString(),numero.getText().toString(),
                                            colonia.getText().toString(),cp.getText().toString(),telefono.getText().toString(),celular.getText().toString(),monto_credito.getText().toString(),ingreso_mensual.getText().toString(),id_cliente.getText().toString(),tv_nosolicitud.getText().toString(),email.getText().toString(),localidad.getText().toString(),municipio.getText().toString(),num_int.getText().toString(),comentarios.getText().toString());
                                    Dialog.dismiss();
                                    myDialog.dismiss();
                                    finish();
                                    startActivity(getIntent());
                                    Toast.makeText(SolicitudesHistorialH.this, "Se ha actualizado la solicitud", Toast.LENGTH_SHORT).show();
                                }




                            }
                        });

                        recomendado.setText(tv_clienteRecomienda.getText().toString());
                        recomendado.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogFragment=new Dialog(SolicitudesHistorialH.this);
                                dialogFragment.setContentView(R.layout.activity_dialog_cliente);
                                dialogFragment.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                dialogFragment.show();
                                recyclerView1=dialogFragment.findViewById(R.id.clientes);
                                recyclerView1.setHasFixedSize(true);
                                layoutManager1=new LinearLayoutManager(getApplicationContext());
                                recyclerView1.setLayoutManager(layoutManager1);
                                datos1=new ArrayList<>();

                                llenarDatos();
                                final AdapterCliente adapter1=new AdapterCliente(datos1);
                                recyclerView1.setAdapter(adapter1);

                                adapter1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // Toast.makeText(solicitudes.this, datos.get(recyclerView.getChildAdapterPosition(v)).getNombre(), Toast.LENGTH_SHORT).show();
                                        dialogFragment.dismiss();
                                        id_cliente.setText(datos1.get(recyclerView1.getChildAdapterPosition(v)).getNo_cliente());
                                        recomendado.setText(datos1.get(recyclerView1.getChildAdapterPosition(v)).getNombre()+" "+datos1.get(recyclerView.getChildAdapterPosition(v)).getApellido_paterno()+" "+datos1.get(recyclerView.getChildAdapterPosition(v)).getApellido_materno());
                                    }
                                });



                                buscar1=dialogFragment.findViewById(R.id.buscar);
                                buscar1.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {
                                        adapter1.getFilter().filter(s.toString());
                                    }

                                });
                            }
                        });
                        //calle.setText();

                        im_close=Dialog.findViewById(R.id.im_close);
                        im_close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Dialog.dismiss();
                            }
                        });
                    }
                });
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
                                        Database.getInstance(getApplicationContext()).historialSolicitudesDAO().borrarSolicitudByID(tv_nosolicitud.getText().toString());
                                        Database.getInstance(getApplicationContext()).solicitudDAO().borrarSolicitudByID(tv_nosolicitud.getText().toString());
                                        myDialog.dismiss();
                                        finish();
                                        startActivity(getIntent());

                                        Toast.makeText(getApplicationContext(), "Solicitud eliminada exitosamente", Toast.LENGTH_SHORT).show();
                                        break;
                                }
                            }
                        };

                        AlertDialog.Builder builder = new AlertDialog.Builder(SolicitudesHistorialH.this);
                        builder.setMessage("¿Está seguro que desea eliminar esta solicitud?").setTitle("¡Atención!")
                                .setPositiveButton("Cancelar", dialogClickListener)
                                .setNegativeButton("Aceptar", dialogClickListener).show();

                    }
                });
                //myDialog.dismiss();


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
    public void llenarDatos() {
        List<Clientes> clientes = Database.getInstance(getApplicationContext()).clientesDAO().getInfoClientes();
        for (int i = 0; i < clientes.size(); i++) {
            datos1.add(new InfoClientesRecomendados(clientes.get(i).getNombre(), clientes.get(i).getApe1(), clientes.get(i).getApe2(), clientes.get(i).getNo_cliente()));
        }
    }

    void llenarInformacion()
    {
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
        try {
            List<TrabajadorLoggin> trabajadorLoggins=Database.getInstance(getApplicationContext()).trabajadorLogginDAO().getTrabajadoreLoggin();
            List<Trabajadores_sistema> trabajadores_sistemas=Database.getInstance(getApplicationContext()).trabajadores_sistemaDAO().getTrabajadorID(trabajadorLoggins.get(0).getUsername());
            List<HistorialSolicitudes> solicitudesHistorialHS= Database.getInstance(getApplicationContext()).historialSolicitudesDAO().getHistorialSolicitudesHoy(stringBuffer,trabajadores_sistemas.get(0).getNo_trabajador());
            for (int i=0;i<solicitudesHistorialHS.size();i++)
            {
                datos.add(new InfoHistorialSolicitudesH(solicitudesHistorialHS.get(i).getNo_solicitud(),solicitudesHistorialHS.get(i).getNombre(),solicitudesHistorialHS.get(i).getApe1(),solicitudesHistorialHS.get(i).getApe2(),
                        solicitudesHistorialHS.get(i).getSexo(),solicitudesHistorialHS.get(i).getCalle(),solicitudesHistorialHS.get(i).getNum_ext(),solicitudesHistorialHS.get(i).getColonia(),solicitudesHistorialHS.get(i).getCp(),
                        solicitudesHistorialHS.get(i).getTelefono(),solicitudesHistorialHS.get(i).getCelular(),solicitudesHistorialHS.get(i).getMonto_prestamo(),solicitudesHistorialHS.get(i).getIngreso_mensual(),solicitudesHistorialHS.get(i).getNo_cliente_recomendado(),
                        solicitudesHistorialHS.get(i).getFechaInsercion(),solicitudesHistorialHS.get(i).getSincronizado(),solicitudesHistorialHS.get(i).getHora(),solicitudesHistorialHS.get(i).getNum_int(),solicitudesHistorialHS.get(i).getLocalidad(),solicitudesHistorialHS.get(i).getMunicipio(),solicitudesHistorialHS.get(i).getEmail(),solicitudesHistorialHS.get(i).getComentarios()));
            }
        }
        catch (Exception e)
        {

        }

    }
}
