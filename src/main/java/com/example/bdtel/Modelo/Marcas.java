package com.example.bdtel.Modelo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Marcas {

    private StringProperty marca;

    public Marcas(String marca) {
        this.marca = new SimpleStringProperty(marca);
    }


    public String getMarca() {
        return marca.get();
    }

    public StringProperty marcaProperty() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca.set(marca);
    }

    @Override
    public String toString() {
        return "Marcas{" +
                "marca=" + marca +
                '}';
    }
}
