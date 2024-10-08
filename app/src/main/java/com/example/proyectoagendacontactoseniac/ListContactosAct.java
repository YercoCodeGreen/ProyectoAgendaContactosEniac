package com.example.proyectoagendacontactoseniac;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import models.Contacto;
import models.ContactoAdapter;

public class ListContactosAct extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Contacto> listaContactos;
    private ContactoAdapter adapter;
    private Button btnAtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_contactos);
        btnAtras = findViewById(R.id.btn_atras);
        listView = findViewById(R.id.listViewContactos);
        listaContactos = new ArrayList<>();

        // Crear un ContactoAdapter que se vincula con el ListView
        adapter = new ContactoAdapter(this, listaContactos);
        listView.setAdapter(adapter);

        // Obtener los contactos del servidor en un hilo de fondo
        new Thread(() -> {
            try {
                // URL de tu servidor
                URL url = new URL("http://192.168.56.1:3000/api/contactos");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                // Parsear la respuesta JSON
                JSONArray contactosArray = new JSONArray(response.toString());

                // Verificar la cantidad de contactos recibidos
                Log.d("Contactos", "Número de contactos recibidos: " + contactosArray.length());

                for (int i = 0; i < contactosArray.length(); i++) {
                    JSONObject contactoJson = contactosArray.getJSONObject(i);

                    // Crear un nuevo Contacto usando los datos del JSON
                    Contacto contacto = new Contacto(
                            contactoJson.getString("nombre"),
                            contactoJson.getString("apellidos"),
                            contactoJson.getString("telefono"),
                            contactoJson.getString("correo")
                    );

                    // Añadir el contacto a la lista
                    listaContactos.add(contacto);
                }

                // Actualizar la UI en el hilo principal
                runOnUiThread(() -> {
                    adapter.notifyDataSetChanged(); // Refrescar el ListView con los nuevos datos
                });

                // Cerrar conexiones
                reader.close();
                in.close();
                urlConnection.disconnect();

            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(ListContactosAct.this, "Error al obtener los contactos", Toast.LENGTH_SHORT).show());
            }
        }).start();

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Regresar a la actividad anterior
                finish();
            }
        });
    }
}
