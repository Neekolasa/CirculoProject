package com.example.circulo.Interfaces;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.circulo.Database.Database;
import com.example.circulo.R;

public class HomeFragment extends Fragment {
    View vista;
    Button exit,web;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        vista=inflater.inflate(R.layout.fragment_inicio, container, false);
        exit=vista.findViewById(R.id.exit);
        web=vista.findViewById(R.id.web);

        /*Clientes cliente=new Clientes();
        cliente.setNo_cliente(1);
        cliente.setNombre("Juan");
        cliente.setApe1("Pablo");
        cliente.setApe2("Segundo");
        cliente.setCalle("20 de Noviembre");
        cliente.setNum_ext("225");
        cliente.setNum_int("");
        cliente.setCp("34188");
        cliente.setColonia("Constitucion");
        cliente.setTelefono("6188136371");

        Database.getInstance(getContext()).clientesDAO().addClientes(cliente);
        List<Clientes> cl=Database.getInstance(getContext()).clientesDAO().getInfoClienteByID(1);
        String name="";
        for (Clientes clt : cl)
        {
            int id=clt.getNo_cliente();
            name=clt.getNombre();
        }
        Toast.makeText(getContext(), "Se ha insertado el dato "+name, Toast.LENGTH_LONG).show();*/

        /*Clientes cl=new Clientes();
        cl.setNo_cliente(2);
        Database.getInstance(getContext()).clientesDAO().deleteClientes(cl);*/

        SharedPreferences settings = getActivity().getSharedPreferences("Loggin", 0);
        final SharedPreferences.Editor editor = settings.edit();
        //Set "hasLoggedIn" to true

        // Commit the edits!
        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uriUrl = Uri.parse("https://circulo.distic.com.mx/circulo/login");
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
            }
        });
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

        return vista;
    }


}
