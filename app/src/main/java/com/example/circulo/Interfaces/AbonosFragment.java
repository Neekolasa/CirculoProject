package com.example.circulo.Interfaces;

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
import androidx.fragment.app.FragmentTransaction;


import com.example.circulo.Database.Database;
import com.example.circulo.Entitites.Historial;
import com.example.circulo.Entitites.TrabajadorLoggin;
import com.example.circulo.Entitites.Trabajadores_sistema;
import com.example.circulo.R;

import java.util.Calendar;
import java.util.List;

public class AbonosFragment extends Fragment {
    View vista;
    TextView tv_cobrados;
    CardView cobros,cobro_comp,cobros_hechos;
    Button exit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        vista=inflater.inflate(R.layout.fragment_abonos, container, false);
        //getActivity().getSupportFragmentManager().beginTransaction().replace(this.getId(), new AbonosFragment()).commit();


        cobros=vista.findViewById(R.id.cobros);
        cobro_comp=vista.findViewById(R.id.cobro_comp);
        exit=vista.findViewById(R.id.exit);
        tv_cobrados=vista.findViewById(R.id.tv_cobrados);
        cobros_hechos=vista.findViewById(R.id.cobros_hechos);
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
            List<TrabajadorLoggin> trabajadorLoggins=Database.getInstance(getContext()).trabajadorLogginDAO().getTrabajadoreLoggin();
            List<Trabajadores_sistema> trabajadores_sistemas=Database.getInstance(getContext()).trabajadores_sistemaDAO().getTrabajadorID(trabajadorLoggins.get(0).getUsername());
            List<Historial> historials= Database.getInstance(getContext()).historialDAO().getAbonosRecibidos(stringBuffer,trabajadores_sistemas.get(0).getNo_trabajador());
            Double total=0.0;
            for (int i=0; i<historials.size(); i++)
            {
                total=Double.parseDouble(historials.get(i).getAbono_recibido().replace("$",""))+total;
            }
            total=(Math.rint(total*100)/100);
            tv_cobrados.setText("$"+total);
        }
        catch (Exception e)
        {

        }




        //Toast.makeText(getContext(), new Cobrador().getUsername(), Toast.LENGTH_SHORT).show();
        /*List<Clientes> cl= Database.getInstance(getContext()).clientesDAO().getInfoCreditoClientes();
        List<Credito> cr= Database.getInstance(getContext()).creditoDAO().getClientesCredito();
        List<Abonos> ab= Database.getInstance(getContext()).abonosDAO().getAbonosHoyInfo();*/


        //Toast.makeText(getContext(), ab.get(1).getSaldo_anterior()+" "+ab.get(1).getNo_abono(), Toast.LENGTH_LONG).show();

        SharedPreferences settings = getActivity().getSharedPreferences("Loggin", 0);
        final SharedPreferences.Editor editor = settings.edit();
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getContext(), LoginActivity.class);
                getActivity().onBackPressed();
                startActivity(intent);
                editor.putBoolean("hasLoggedIn",false);
                editor.commit();

                Database.getInstance(getContext()).trabajadorLogginDAO().borrarTrabajadorLoggin();

            }
        });

        cobros_hechos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),RealizadosHoy.class);
                startActivityForResult(intent,1);
            }
        });

        cobros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(menu.this, "Seleccionaste cobros", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getContext(), com.example.circulo.Interfaces.cobros.class);
                startActivityForResult(intent,2);
            }
        });

        cobro_comp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "Seleccionaste cobros completados", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getContext(),HistorialAlltime.class);
                startActivityForResult(intent,3);
            }
        });


        return vista;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case 1: // Do your stuff here...
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, new AbonosFragment()).commit();
                break;
            case 2: // Do your other stuff here...
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, new AbonosFragment()).commit();
                break;
            case 3:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, new AbonosFragment()).commit();
                break;
        }

        /*if (resultCode == Activity.RESULT_OK) {


        }*/
    }

}
