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

import com.example.circulo.AdapterSolicitudesA;
import com.example.circulo.AdapterSolicitudesH;
import com.example.circulo.Database.Database;
import com.example.circulo.Entitites.Clientes;
import com.example.circulo.Entitites.Historial;
import com.example.circulo.Entitites.HistorialSolicitudes;
import com.example.circulo.Entitites.TrabajadorLoggin;
import com.example.circulo.Entitites.Trabajadores_sistema;
import com.example.circulo.InfoHistorialSolicitudesH;
import com.example.circulo.R;

import java.util.ArrayList;
import java.util.List;

public class SolicitudesHistorialA extends AppCompatActivity {
    Button volver;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<InfoHistorialSolicitudesH> datos;
    EditText buscar,nombre1,ape_paterno1,ape_materno1,telefono,celular,calle,numero,colonia,cp,monto_credito,ingreso_mensual,recomendado;
    Button btn_modificar,btn_eliminar,btn_soli;
    Dialog myDialog,Dialog,dialogFragment;
    ImageButton im_close;
    TextView tv_nosolicitud,tv_nombre,tv_direccion,tv_telefono,tv_celular,tv_clienteRecomienda,tv_creditoSolicitado,tv_ingresoMensual,id_cliente,tv_email,tv_localidad,tv_municipio;
    Spinner sexo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitudes_historial_a);
        recyclerView=findViewById(R.id.historialhoy);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        datos=new ArrayList<>();
        llenarInformacion();
        final AdapterSolicitudesA adapter=new AdapterSolicitudesA(datos);
        recyclerView.setAdapter(adapter);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog=new Dialog(SolicitudesHistorialA.this);
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
                btn_modificar=myDialog.findViewById(R.id.btn_modificar);
                btn_modificar.setVisibility(View.GONE);
                tv_email=myDialog.findViewById(R.id.tv_email);
                tv_localidad=myDialog.findViewById(R.id.tv_localidad);
                tv_municipio=myDialog.findViewById(R.id.tv_municipio);

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

                        AlertDialog.Builder builder = new AlertDialog.Builder(SolicitudesHistorialA.this);
                        builder.setMessage("¿Está seguro que desea eliminar esta solicitud?").setTitle("¡Atención!")
                                .setPositiveButton("Cancelar", dialogClickListener)
                                .setNegativeButton("Aceptar", dialogClickListener).show();

                    }
                });

                List<HistorialSolicitudes> historialSolicitudes=Database.getInstance(getApplicationContext()).historialSolicitudesDAO().getSolicitud(String.valueOf(datos.get(recyclerView.getChildAdapterPosition(v)).getNo_solicitud()));
                for (int i=0; i<historialSolicitudes.size();i++)
                {
                    //tv_nosolicitud.setText(String.valueOf(historialSolicitudes.get(i).getNo_solicitud()));
                    tv_nosolicitud.setText(String.valueOf(datos.get(recyclerView.getChildAdapterPosition(v)).getNo_solicitud()));
                    //tv_nombre.setText(historialSolicitudes.get(i).getNombre()+" "+historialSolicitudes.get(i).getApe1()+" "+historialSolicitudes.get(i).getApe2());

                    tv_nombre.setText(datos.get(recyclerView.getChildAdapterPosition(v)).getNombre()+" "+datos.get(recyclerView.getChildAdapterPosition(v)).getApe1()+" "+datos.get(recyclerView.getChildAdapterPosition(v)).getApe2());
                    tv_direccion.setText("C. "+datos.get(recyclerView.getChildAdapterPosition(v)).getCalle()+" #"+datos.get(recyclerView.getChildAdapterPosition(v)).getNum_ext()+" Col. "+datos.get(recyclerView.getChildAdapterPosition(v)).getColonia()+" \nC.P. "+
                            datos.get(recyclerView.getChildAdapterPosition(v)).getCp());
                    tv_telefono.setText(datos.get(recyclerView.getChildAdapterPosition(v)).getTelefono());
                    tv_celular.setText(datos.get(recyclerView.getChildAdapterPosition(v)).getCelular());

                    //tv_clienteRecomienda.setText(datos.get(recyclerView.getChildAdapterPosition(v)).getNo_cliente_recomendado());

                    tv_creditoSolicitado.setText(datos.get(recyclerView.getChildAdapterPosition(v)).getMonto_prestamo());
                    tv_ingresoMensual.setText(datos.get(recyclerView.getChildAdapterPosition(v)).getIngreso_mensual());
                    tv_email.setText(datos.get(recyclerView.getChildAdapterPosition(v)).getEmail());
                    tv_localidad.setText(datos.get(recyclerView.getChildAdapterPosition(v)).getLocalidad());
                    tv_municipio.setText(datos.get(recyclerView.getChildAdapterPosition(v)).getMunicipio());
                }
                final String no_cliente=datos.get(recyclerView.getChildAdapterPosition(v)).getNo_cliente_recomendado();
                List<Clientes> clientes=Database.getInstance(getApplicationContext()).clientesDAO().getClienteInfoByID(datos.get(recyclerView.getChildAdapterPosition(v)).getNo_cliente_recomendado());

                for (int i=0;i<clientes.size();i++)
                {
                    tv_clienteRecomienda.setText(clientes.get(i).getNombre()+" "+clientes.get(i).getApe1()+" "+clientes.get(i).getApe2());
                }
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
        volver=findViewById(R.id.volver);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
    }

    void llenarInformacion() {
        try {
            List<TrabajadorLoggin> trabajadorLoggins=Database.getInstance(getApplicationContext()).trabajadorLogginDAO().getTrabajadoreLoggin();
            List<Trabajadores_sistema> trabajadores_sistemas=Database.getInstance(getApplicationContext()).trabajadores_sistemaDAO().getTrabajadorID(trabajadorLoggins.get(0).getUsername());
            List<HistorialSolicitudes> solicitudesHistorialHS = Database.getInstance(getApplicationContext()).historialSolicitudesDAO().getAllHistorial(trabajadores_sistemas.get(0).getNo_trabajador());
            for (int i = 0; i < solicitudesHistorialHS.size(); i++) {
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
