package com.example.circulo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.circulo.Database.Database;
import com.example.circulo.Entitites.Clientes;

import java.util.ArrayList;
import java.util.List;

public class DialogCliente extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<InfoClientesRecomendados> datos;
    // this method create view for your Dialog
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_cliente);
        //inflate layout with recycler view
        recyclerView=findViewById(R.id.clientes);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        datos=new ArrayList<>();

        llenarDatos();
        final AdapterCliente adapter=new AdapterCliente(datos);
        recyclerView.setAdapter(adapter);


        //get your recycler view and populate it.
    }
    public void llenarDatos() {
        List<Clientes> clientes = Database.getInstance(getApplicationContext()).clientesDAO().getInfoClientes();
        for (int i = 0; i < clientes.size(); i++) {
            datos.add(new InfoClientesRecomendados(clientes.get(i).getNombre(), clientes.get(i).getApe1(), clientes.get(i).getApe2(), clientes.get(i).getNo_cliente()));
        }
    }

}
