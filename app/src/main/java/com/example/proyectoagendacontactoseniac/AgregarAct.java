package com.example.proyectoagendacontactoseniac;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AgregarAct extends AppCompatActivity {

    private EditText txtNombre, txtApellidos, txtTelefono, txtCorreo;
    private Button btnAgregar, btnCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);

        // Permitir operaciones de red en el hilo principal (esto es solo para pruebas, evita hacer esto en producción)
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

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
                    return;
                }

                // Crear el objeto JSON para enviar
                JSONObject jsonData = new JSONObject();
                try {
                    jsonData.put("nombre", nombre);
                    jsonData.put("apellidos", apellidos);
                    jsonData.put("telefono", telefono);
                    jsonData.put("correo", correo);

                    // Definir la URL de tu servidor
                    URL url = new URL("http://10.110.47.20:3000/contacto");  // Si usas el emulador de Android
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    urlConnection.setDoOutput(true);

                    // Enviar los datos
                    OutputStream os = new BufferedOutputStream(urlConnection.getOutputStream());
                    os.write(jsonData.toString().getBytes("UTF-8"));
                    os.flush();
                    os.close();

                    // Leer la respuesta del servidor
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    String line;
                    StringBuilder response = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    // Manejar la respuesta
                    String result = response.toString();
                    Toast.makeText(AgregarAct.this, result, Toast.LENGTH_SHORT).show();

                    // Cerrar conexión
                    reader.close();
                    in.close();
                    urlConnection.disconnect();

                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
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
