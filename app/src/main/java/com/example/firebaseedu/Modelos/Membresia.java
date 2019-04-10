package com.example.firebaseedu.Modelos;

public class Membresia {
    Integer id;
    String tipo;
    String Descripcion;
    Integer Precio;
    String imagen;
    String imagen_detalles;
    String detalles;

    public String getImagen_detalles() {
        return imagen_detalles;
    }

    public void setImagen_detalles(String imagen_detalles) {
        this.imagen_detalles = imagen_detalles;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public Integer getPrecio() {
        return Precio;
    }

    public void setPrecio(Integer precio) {
        Precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
