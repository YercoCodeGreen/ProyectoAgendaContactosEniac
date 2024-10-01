package com.example.proyectoagendacontactoseniac;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ViewFlipper;


import androidx.appcompat.app.AppCompatActivity;


public class MenuAct extends AppCompatActivity {

    private ViewFlipper vf;
    private int[] image = {R.drawable.a, R.drawable.b, R.drawable.c};

    public void flip_image(int i){
        ImageView view = new ImageView(this);
        view.setBackgroundResource(i);
        vf.addView(view);
        vf.setFlipInterval(2800);
        vf.setAutoStart(true);
        vf.setInAnimation(this, android.R.anim.slide_in_left);
        vf.setOutAnimation(this, android.R.anim.slide_out_right);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        vf = (ViewFlipper) findViewById(R.id.slider);

        for(int i=0; i<image.length;i++){
            flip_image(image[i]);
        }

    }
}