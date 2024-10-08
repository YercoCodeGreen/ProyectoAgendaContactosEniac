package models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proyectoagendacontactoseniac.R;

import java.util.List;

public class ContactoAdapter extends ArrayAdapter<Contacto> {
    private Context context;
    private List<Contacto> contactos;

    public ContactoAdapter(Context context, List<Contacto> contactos) {
        super(context, R.layout.item_contacto, contactos);
        this.context = context;
        this.contactos = contactos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflar el layout del item si es nulo
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_contacto, parent, false);
        }

        // Obtener los elementos de la vista
        ImageView contactImage = convertView.findViewById(R.id.contactImage);
        TextView contactNombre = convertView.findViewById(R.id.contactNombre);
        TextView contactTelefono = convertView.findViewById(R.id.contactTelefono);
        TextView contactCorreo = convertView.findViewById(R.id.contactCorreo);

        //

        // Obtener el contacto actual
        Contacto contacto = contactos.get(position);

        // Configurar los valores del contacto
        contactNombre.setText(contacto.getNombre() + " " + contacto.getApellidos());
        contactTelefono.setText(contacto.getTelefono());
        contactCorreo.setText(contacto.getCorreo());

        // Si tienes imágenes personalizadas, configúralas aquí
        contactImage.setImageResource(R.drawable.ic_contact_image); // Ejemplo de imagen predeterminada

        return convertView;
    }
}
