package com.example.proyectoagendacontactoseniac;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

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
                String nombre = txtNombre.getText().toString().trim();
                String apellidos = txtApellidos.getText().toString().trim();
                String telefono = txtTelefono.getText().toString().trim();
                String correo = txtCorreo.getText().toString().trim();

                if (nombre.isEmpty() || apellidos.isEmpty() || telefono.isEmpty()) {
                    Toast.makeText(AgregarAct.this, "Por favor, completa todos los campos obligatorios", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.PHONE.matcher(telefono).matches()) {
                    Toast.makeText(AgregarAct.this, "Número de teléfono no válido", Toast.LENGTH_SHORT).show();
                } else if (!correo.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                    Toast.makeText(AgregarAct.this, "Correo electrónico no válido", Toast.LENGTH_SHORT).show();
                } else {
                    new AgregarContactoTask().execute(nombre, apellidos, telefono, correo);
                }
            }
        });

        // Configurar el botón "Cancelar"
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private class AgregarContactoTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String nombre = params[0];
            String apellidos = params[1];
            String telefono = params[2];
            String correo = params[3];
            String response = "";

            try {
                String urlString = "http://127.0.0.1/AgendaContactos/Registrar.php"; // Cambia localhost por tu dirección IP
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);

                String postData = "nombre=" + URLEncoder.encode(nombre, "UTF-8") +
                        "&apellidos=" + URLEncoder.encode(apellidos, "UTF-8") +
                        "&telefono=" + URLEncoder.encode(telefono, "UTF-8") +
                        "&correo=" + URLEncoder.encode(correo, "UTF-8");

                OutputStream os = conn.getOutputStream();
                os.write(postData.getBytes());
                os.flush();
                os.close();

                response = conn.getResponseMessage(); // Obtener la respuesta del servidor
                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Toast.makeText(AgregarAct.this, "Contacto agregado: " + result, Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
