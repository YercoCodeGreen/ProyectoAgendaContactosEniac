package com.example.proyectoagendacontactoseniac;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import models.Administrador;

public class MainActivity extends AppCompatActivity {

    private EditText tbUser;
    private EditText tbPass;
    private Button btnLogin;
    private Administrador admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar el administrador
        admin = new Administrador();

        // Asignar los IDs de los componentes
        tbUser = findViewById(R.id.tbUser);
        tbPass = findViewById(R.id.tbPass);
        btnLogin = findViewById(R.id.btnLogin);

        // Listener para el botón de login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = tbUser.getText().toString().trim();
                String password = tbPass.getText().toString().trim();

                // Validar que los campos no estén vacíos
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(MainActivity.this, "Ingrese el usuario", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(MainActivity.this, "Ingrese la contraseña", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Usar la clase Administrador para validar las credenciales
                if (admin.validarCredenciales(username, password)) {
                    Toast.makeText(MainActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                    // Aquí puedes redirigir a otra actividad
                } else {
                    Toast.makeText(MainActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
