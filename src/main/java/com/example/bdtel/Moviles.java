package com.example.bdtel;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Moviles {

    private final IntegerProperty id;
    private final StringProperty modelo;

    private final StringProperty marca;

    private final IntegerProperty almacenamiento;

    private final IntegerProperty ram;

    private final StringProperty sistemaOperativo;

    private final StringProperty cpu;

    private final IntegerProperty bateria;

    private final DoubleProperty precioSalida;

    private final DoubleProperty precio;

    private final StringProperty fecha;



    public Moviles(int id, String modelo, String marca, int almacenamiento, int ram, String sistemaOperativo, String cpu, int bateria, double precioSalida, double precio, String fecha) {
        this.id = new SimpleIntegerProperty(id);
        this.modelo = new SimpleStringProperty(modelo);
        this.marca = new SimpleStringProperty(marca);
        this.almacenamiento = new SimpleIntegerProperty(almacenamiento);
        this.ram = new SimpleIntegerProperty(ram);
        this.sistemaOperativo = new SimpleStringProperty(sistemaOperativo);
        this.cpu = new SimpleStringProperty(cpu);
        this.bateria = new SimpleIntegerProperty(bateria);
        this.precioSalida = new SimpleDoubleProperty(precioSalida);
        this.precio = new SimpleDoubleProperty(precio);
        this.fecha = new SimpleStringProperty(fecha);
    }

    @Override
    public String toString() {
        return "Moviles{" +
                "id=" + id +
                ", modelo=" + modelo +
                ", marca=" + marca +
                ", almacenamiento=" + almacenamiento +
                ", ram=" + ram +
                ", sistemaOperativo=" + sistemaOperativo +
                ", cpu=" + cpu +
                ", bateria=" + bateria +
                ", precioSalida=" + precioSalida +
                ", precio=" + precio +
                ", fecha=" + fecha +
                '}';
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getModelo() {
        return modelo.get();
    }

    public StringProperty modeloProperty() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo.set(modelo);
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

    public int getAlmacenamiento() {
        return almacenamiento.get();
    }

    public IntegerProperty almacenamientoProperty() {
        return almacenamiento;
    }

    public void setAlmacenamiento(int almacenamiento) {
        this.almacenamiento.set(almacenamiento);
    }

    public int getRam() {
        return ram.get();
    }

    public IntegerProperty ramProperty() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram.set(ram);
    }

    public String getSistemaOperativo() {
        return sistemaOperativo.get();
    }

    public StringProperty sistemaOperativoProperty() {
        return sistemaOperativo;
    }

    public void setSistemaOperativo(String sistemaOperativo) {
        this.sistemaOperativo.set(sistemaOperativo);
    }

    public String getCpu() {
        return cpu.get();
    }

    public StringProperty cpuProperty() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu.set(cpu);
    }

    public int getBateria() {
        return bateria.get();
    }

    public IntegerProperty bateriaProperty() {
        return bateria;
    }

    public void setBateria(int bateria) {
        this.bateria.set(bateria);
    }

    public double getPrecioSalida() {
        return precioSalida.get();
    }

    public DoubleProperty precioSalidaProperty() {
        return precioSalida;
    }

    public void setPrecioSalida(double precioSalida) {
        this.precioSalida.set(precioSalida);
    }

    public double getPrecio() {
        return precio.get();
    }

    public DoubleProperty precioProperty() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio.set(precio);
    }

    public String getFecha() {
        return fecha.get();
    }

    public StringProperty fechaProperty() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha.set(fecha);
    }


    private  ObjectProperty<LocalDate> date = new SimpleObjectProperty<>();
    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }


    public LocalDate getDate() {
        return dateProperty().get();
    }
    public void setDate(LocalDate date) {
        dateProperty().set(date);
    }


}