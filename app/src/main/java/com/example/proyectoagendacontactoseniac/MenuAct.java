package com.example.proyectoagendacontactoseniac;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;

public class MenuAct extends AppCompatActivity {


    private ViewFlipper vf;
    private int[] image = {R.drawable.a, R.drawable.b, R.drawable.c};

    public void flip_image(int i)
    {
        ImageView view = new ImageView(this);
        view.setBackgroundResource(i);
        vf.addView(view);
        vf.setFlipInterval(2000);
        vf.setAutoStart(true);
        vf.setInAnimation(this, android.R.anim.slide_in_left);
        vf.setOutAnimation(this, android.R.anim.slide_out_right);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // SLIDE MENU

        vf = (ViewFlipper)findViewById(R.id.slider);

        for(int i=0; i<image.length;i++)
        {
            flip_image(image[i]);
        }



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
