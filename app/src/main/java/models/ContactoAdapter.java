package models;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.proyectoagendacontactoseniac.EditContactoAct;
import com.example.proyectoagendacontactoseniac.R;

import java.util.ArrayList;

public class ContactoAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Contacto> listaContactos;

    public ContactoAdapter(Context context, ArrayList<Contacto> listaContactos) {
        this.context = context;
        this.listaContactos = listaContactos;
    }

    @Override
    public int getCount() {
        return listaContactos.size();
    }

    @Override
    public Object getItem(int position) {
        return listaContactos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_contacto, parent, false);
        }

        TextView nombreContacto = convertView.findViewById(R.id.nombreContacto);
        Button btnEditar = convertView.findViewById(R.id.btnEditar);

        // Obtener el contacto actual
        Contacto contacto = listaContactos.get(position);

        // Establecer el nombre del contacto en el TextView
        nombreContacto.setText(contacto.getNombre());

        // Configurar el botón "Editar"
        btnEditar.setOnClickListener(v -> {
            // Iniciar la actividad de edición, pasando los datos del contacto
            Intent intent = new Intent(context, EditContactoAct.class);
            intent.putExtra("nombre", contacto.getNombre());
            intent.putExtra("apellidos", contacto.getApellidos());
            intent.putExtra("telefono", contacto.getTelefono());
            intent.putExtra("correo", contacto.getCorreo());
            context.startActivity(intent);
        });

        return convertView;
    }
}
