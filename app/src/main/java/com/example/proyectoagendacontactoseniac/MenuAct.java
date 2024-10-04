package com.example.proyectoagendacontactoseniac;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MenuAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button btnAgregarContacto = findViewById(R.id.btn_agregarcontacto);
        btnAgregarContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un Intent para ir a AgregarAct
                Intent intent = new Intent(MenuAct.this, AgregarAct.class);
                startActivity(intent);
            }
        });
    }
}
