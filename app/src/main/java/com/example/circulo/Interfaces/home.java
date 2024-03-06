package com.example.circulo.Interfaces;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.circulo.Database.Database;
import com.example.circulo.Entitites.Trabajadores_sistema;
import com.example.circulo.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.List;

public class home extends AppCompatActivity {
    public static final String PREFS_NAME = "Loggin";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Database.getInstance(getApplicationContext()).historialDAO().borrarHistorial();
        SharedPreferences settings = getSharedPreferences(home.PREFS_NAME, 0); // 0 - for private mode
        SharedPreferences.Editor editor = settings.edit();
        //Set "hasLoggedIn" to true
        editor.putBoolean("hasLoggedIn", true);

        // Commit the edits!
        editor.commit();

        setContentView(R.layout.activity_home);
        String username="";
        username=getIntent().getStringExtra("cobrador_username");
       /* if (username!=null)
        {
            //Toast.makeText(this, username, Toast.LENGTH_SHORT).show();
            Bundle bundle=new Bundle();
            bundle.putString("username",username);
            OpcionesFragmentk fragobj = new OpcionesFragmentk();
            fragobj.setArguments(bundle);
        }
        else{

        }*/

        BottomNavigationView bottom = findViewById(R.id.nav_view);
        bottom.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, new HomeFragment()).commit();

    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment=null;

                    switch (item.getItemId())
                    {
                        case R.id.home:
                            selectedFragment=new HomeFragment();
                            break;
                        case R.id.abonos:
                            selectedFragment=new AbonosFragment();
                            break;
                        case R.id.options:
                            selectedFragment=new OpcionesFragmentk();
                            break;
                        case R.id.solicitud:
                            selectedFragment=new SolicitudFragment();
                            break;

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,
                            selectedFragment).commit();


                    return true;
                }
            };





}
