package org.example.pokemon;

class Habilidad {
    private String nombre;
    private String descripcion;

    public Habilidad(String nombre, String descripcion) {
        this.descripcion = descripcion;
        this.nombre = nombre;
    }
    public String getNombre() {return nombre;}
    public String getDescripcion() {return descripcion;}
}
