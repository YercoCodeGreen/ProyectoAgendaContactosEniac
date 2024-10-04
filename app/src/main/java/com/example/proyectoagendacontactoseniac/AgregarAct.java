package com.example.proyectoagendacontactoseniac;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;




public class AgregarAct extends AppCompatActivity {

    private EditText txtNombre, txtApellidos, txtTelefono, txtCorreo;
    private Button btnAgregar, btnCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);

        // Vincular los elementos de la interfaz con el código
        txtNombre = findViewById(R.id.txtNombre);
        txtApellidos = findViewById(R.id.txtApellidos);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtCorreo = findViewById(R.id.txtCorreo);
        btnAgregar = findViewById(R.id.btn_agregar);
        btnCancelar = findViewById(R.id.btn_cancelar);

        // Configurar el botón "Agregar"
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = txtNombre.getText().toString();
                String apellidos = txtApellidos.getText().toString();
                String telefono = txtTelefono.getText().toString();
                String correo = txtCorreo.getText().toString();

                if (nombre.isEmpty() || apellidos.isEmpty() || telefono.isEmpty() || correo.isEmpty()) {
                    Toast.makeText(AgregarAct.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    // Aquí puedes guardar los datos, por ejemplo en una base de datos o pasarlos a la actividad principal
                    // Ejemplo de Toast para mostrar los datos capturados
                    Toast.makeText(AgregarAct.this, "Contacto agregado exitosamente: " + nombre + " " + apellidos, Toast.LENGTH_SHORT).show();

                    // Regresar a la actividad anterior
                    finish();
                }
            }
        });

        // Configurar el botón "Cancelar"
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Regresar a la actividad anterior
                finish();
            }
        });
    }
}