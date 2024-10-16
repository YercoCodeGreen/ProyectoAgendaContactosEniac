package com.example.proyectoagendacontactoseniac;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONObject;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class EditContactoAct extends AppCompatActivity {

    private EditText editNombre, editApellidos, editTelefono, editCorreo;
    private Button btnGuardar;
    private int contactoId; // ID del contacto a editar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contacto);

        // Obtener los extras del Intent
        contactoId = getIntent().getIntExtra("id", 2);



        String nombre = getIntent().getStringExtra("nombre");
        String apellidos = getIntent().getStringExtra("apellidos");
        String telefono = getIntent().getStringExtra("telefono");
        String correo = getIntent().getStringExtra("correo");

        // Enlazar los EditText y el botón
        editNombre = findViewById(R.id.editNombre);
        editApellidos = findViewById(R.id.editApellidos);
        editTelefono = findViewById(R.id.editTelefono);
        editCorreo = findViewById(R.id.editCorreo);
        btnGuardar = findViewById(R.id.btnGuardar);

        // Establecer los valores recibidos en los EditText
        editNombre.setText(nombre);
        editApellidos.setText(apellidos);
        editTelefono.setText(telefono);
        editCorreo.setText(correo);

        // Configurar el botón "Guardar" para actualizar el contacto en la base de datos
        btnGuardar.setOnClickListener(v -> {
            // Obtener los valores modificados
            String nuevoNombre = editNombre.getText().toString();
            String nuevosApellidos = editApellidos.getText().toString();
            String nuevoTelefono = editTelefono.getText().toString();
            String nuevoCorreo = editCorreo.getText().toString();

            // Validar que los campos no estén vacíos (opcional)
            if (nuevoNombre.isEmpty() || nuevosApellidos.isEmpty() || nuevoTelefono.isEmpty() || nuevoCorreo.isEmpty()) {
                Toast.makeText(EditContactoAct.this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }

            // Ejecutar la actualización en un hilo separado
            new Thread(() -> {
                try {
                    // URL del servidor para actualizar el contacto
                    URL url = new URL("http://10.110.46.112:3000/contacto/" + contactoId);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("PUT");
                    conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    conn.setDoOutput(true);

                    // Crear un objeto JSON con los datos del contacto
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("nombre", nuevoNombre);
                    jsonParam.put("apellidos", nuevosApellidos);
                    jsonParam.put("telefono", nuevoTelefono);
                    jsonParam.put("correo", nuevoCorreo);

                    // Enviar los datos en el cuerpo de la solicitud
                    OutputStream os = conn.getOutputStream();
                    os.write(jsonParam.toString().getBytes("UTF-8"));
                    os.close();

                    // Comprobar la respuesta del servidor
                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        runOnUiThread(() -> {
                            Toast.makeText(EditContactoAct.this, "Contacto actualizado correctamente", Toast.LENGTH_SHORT).show();
                            finish(); // Terminar la actividad y volver a la lista
                        });
                    } else {
                        runOnUiThread(() -> {
                            Toast.makeText(EditContactoAct.this, "Error al actualizar el contacto", Toast.LENGTH_SHORT).show();
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(() -> {
                        Toast.makeText(EditContactoAct.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                }
            }).start();
        });
    }
}
