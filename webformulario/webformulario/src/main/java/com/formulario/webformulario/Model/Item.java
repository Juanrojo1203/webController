package com.formulario.webformulario.Model;

public class Item {
    private String carro;     // Nombre del carro
    private double cantidad;  // Cantidad de carros
    private double precio;    // Precio unitario del carro

    // Constructor que inicializa los valores
    public Item(String carro, double cantidad, double precio) {
        this.carro = carro;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    // Getter para el nombre del carro
    public String getCarro() {
        return carro;
    }

    // Setter para el nombre del carro
    public void setCarro(String carro) {
        this.carro = carro;
    }

    // Getter para la cantidad de carros
    public double getCantidad() {
        return cantidad;
    }

    // Setter para la cantidad de carros
    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    // Getter para el precio unitario del carro
    public double getPrecio() {
        return precio;
    }

    // Setter para el precio unitario del carro
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    // Método para calcular el precio total (cantidad * precio unitario)
    public double getPrecioTotal() {
        return cantidad * precio; // Calcula el precio total según cantidad y precio
    }

    // Método que devuelve una representación legible del objeto Item
    @Override
    public String toString() {
        return "Item{" +
                "carro='" + carro + '\'' +
                ", cantidad=" + cantidad +
                ", precio=" + precio +
                ", precioTotal=" + getPrecioTotal() +
                '}';
    }
}
