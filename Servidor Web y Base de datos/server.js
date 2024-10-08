const express = require('express');
const mysql = require('mysql2');
const bodyParser = require('body-parser');
const cors = require('cors');

const app = express();
app.use(bodyParser.json());
app.use(cors());

const db = mysql.createConnection({
  host: 'localhost',
  user: 'root',
  password: 'admin',
  database: 'agendaContactos'
});

db.connect((err) => {
  if (err) throw err;
  console.log('Conectado a la base de datos MySQL');
});

// Rutas Agregar para contactos
app.post('/contacto', (req, res) => {
    const { nombre, apellidos, telefono, correo } = req.body;
    
    // Verifica si los datos están llegando correctamente
    console.log(nombre, apellidos, telefono, correo);
    
    const sql = `INSERT INTO contactos (nombre, apellidos, telefono, correo) VALUES (?, ?, ?, ?)`;
    
    db.query(sql, [nombre, apellidos, telefono, correo], (err, result) => {
        if (err) {
            console.error(err);
            res.status(500).send('Error al agregar contacto');
        } else {
            res.send('Contacto agregado exitosamente');
        }
    });
});

// ruta para listar contactos

app.get('/api/contactos', (req, res) => {
    console.log('Estoy ingresando desde android');
    const query = 'SELECT * FROM contactos';

    db.query(query, (err, results) => {  // Cambié connection.query por db.query
        if (err) {
            console.error('Error al obtener contactos:', err);
            return res.status(500).json({ error: 'Error al obtener los contactos' });
        }

     
        console.log("Resultados de la consulta:", results);
        res.json(results);
    });
});

// ruta para editar contacto 

app.put('/contacto/:id', (req, res) => {
    const { id } = req.params;
    const { nombre, apellidos, telefono, correo } = req.body;
  
    console.log(nombre, apellidos, telefono, correo);
  
    const sql = `UPDATE contactos SET nombre = ?, apellidos = ?, telefono = ?, correo = ? WHERE id = ?`;
    
    db.query(sql, [nombre, apellidos, telefono, correo, id], (err, result) => {
      if (err) throw err;
      res.send('Contacto actualizado de forma correcta');
    });
  });


// ruta para eliminar contacto

app.delete('/contacto/:id', (req, res) => {
  const { id } = req.params;
  const sql = `DELETE FROM contactos WHERE id = ?`;
  db.query(sql, [id], (err, result) => {
    if (err) throw err;
    res.send('Contacto eliminado');
  });
});

app.listen(3000, () => {
  console.log('Servidor corriendo en el puerto 3000');
});
