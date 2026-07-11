package modelo;

// STUB temporal - lo reemplaza Persona 1
public class Cliente {

    private final String dni;   // identificador
    private String nombre;
    private String correo;

    public Cliente(String dni, String nombre, String correo) {
        this.dni = dni;
        this.nombre = nombre;
        this.correo = correo;
    }

    public String getDni() { return dni; }
    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCorreo(String correo) { this.correo = correo; }
}
