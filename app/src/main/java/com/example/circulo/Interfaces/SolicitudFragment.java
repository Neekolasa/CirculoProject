package com.example.circulo.Interfaces;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;


import com.example.circulo.Database.Database;
import com.example.circulo.Entitites.Historial;
import com.example.circulo.Entitites.HistorialSolicitudes;
import com.example.circulo.Entitites.TrabajadorLoggin;
import com.example.circulo.Entitites.Trabajadores_sistema;
import com.example.circulo.R;

import java.util.Calendar;
import java.util.List;

public class SolicitudFragment extends Fragment{
    Button exit;
    View vista;
    CardView cv_solicitudes,cobros_hechos,cobro_comp;
    TextView tv_solicitudes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        vista=inflater.inflate(R.layout.fragment_solicitud, container, false);
        tv_solicitudes=vista.findViewById(R.id.tv_solicitudes);


        cv_solicitudes=vista.findViewById(R.id.cv_solicitudes);

        tv_solicitudes.setText(ActualizarSolicitud()+"");

        cv_solicitudes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getContext(),solicitudes.class);
                intent.putExtra("Name","Value");
                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, new SolicitudFragment()).commit();
                startActivityForResult(intent,1);

            }

        });
        cobros_hechos=vista.findViewById(R.id.cobros_hechos);
        cobros_hechos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), SolicitudesHistorialH.class);
                intent.putExtra("Name","Value");
                startActivityForResult(intent,2);
            }
        });


        cobro_comp=vista.findViewById(R.id.cobro_comp);
        cobro_comp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),SolicitudesHistorialA.class);
                startActivityForResult(intent,3);
            }
        });

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



            }
        });
        return vista;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case 1: // Do your stuff here...
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, new SolicitudFragment()).commit();
                break;
            case 2: // Do your other stuff here...
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, new SolicitudFragment()).commit();
                break;
            case 3:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, new SolicitudFragment()).commit();
                break;
        }

        /*if (resultCode == Activity.RESULT_OK) {


        }*/
    }

    public int ActualizarSolicitud(){

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
        int num=0;

        try {
            List<TrabajadorLoggin> trabajadorLoggins=Database.getInstance(getContext()).trabajadorLogginDAO().getTrabajadoreLoggin();
            List<Trabajadores_sistema> trabajadores_sistemas=Database.getInstance(getContext()).trabajadores_sistemaDAO().getTrabajadorID(trabajadorLoggins.get(0).getUsername());
            List<HistorialSolicitudes> solicituds= Database.getInstance(getContext()).historialSolicitudesDAO().getHistorialNum(stringBuffer,trabajadores_sistemas.get(0).getNo_trabajador());

            for (int i=0;i<solicituds.size();i++)
            {
                num++;
            }
        }
        catch (Exception e)
        {
            //Toast.makeText(getContext(), "Intente de nuevo", Toast.LENGTH_SHORT).show();
        }


        return num;

    }

}
