package modelo;

import util.Validador;

// Entidad Cliente: valida sus datos en el constructor y en los setters.
public class Cliente {

    private final String dni;   // identificador
    private String nombre;
    private String correo;

    public Cliente(String dni, String nombre, String correo) {
        Validador.validarDni(dni);
        Validador.validarTexto(nombre, "nombre");
        Validador.validarEmail(correo);
        this.dni = dni;
        this.nombre = nombre;
        this.correo = correo;
    }

    public String getDni() { return dni; }
    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }

    public void setNombre(String nombre) {
        Validador.validarTexto(nombre, "nombre");
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        Validador.validarEmail(correo);
        this.correo = correo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente otro)) return false;
        return dni.equals(otro.dni);
    }

    @Override
    public int hashCode() { return dni.hashCode(); }

    @Override
    public String toString() {
        return "Cliente{dni='" + dni + "', nombre='" + nombre + "', correo='" + correo + "'}";
    }
}
