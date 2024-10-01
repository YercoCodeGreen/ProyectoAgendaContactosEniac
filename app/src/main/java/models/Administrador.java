package models;

public class Administrador {
    private String username;
    private String password;

    // Constructor
    public Administrador() {
        this.username = "aiep";
        this.password = "2024";
    }

    public Administrador(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Métodos getter para el nombre de usuario y contraseña


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Método para validar las credenciales
    public boolean validarCredenciales(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
}
