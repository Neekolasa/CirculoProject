package com.example.circulo;

public class informacion {

    private String nombre;
    private String ape1;
    private String ape2;
    private String no_credito;
    private String abono;
    private String pagar_hoy;
    private String id;
    private String saldoActual;
    private String calle;
    private String num_ext;
    private String num_int;
    private String cp;
    private String colonia;
    private String telefono;
    private String fecha_prox_pago;
    private String no_abono;


    public informacion(String nombre, String ape1, String ape2, String no_credito, String abono, String pagar_hoy, String id, String saldoActual, String calle, String num_ext, String num_int, String cp, String colonia, String telefono, String fecha_prox_pago, String no_abono) {
        this.nombre = nombre;
        this.ape1 = ape1;
        this.ape2 = ape2;
        this.no_credito = no_credito;
        this.abono = abono;
        this.pagar_hoy = pagar_hoy;
        this.id = id;
        this.saldoActual = saldoActual;
        this.calle = calle;
        this.num_ext = num_ext;
        this.num_int = num_int;
        this.cp = cp;
        this.colonia = colonia;
        this.telefono = telefono;
        this.fecha_prox_pago = fecha_prox_pago;
        this.no_abono = no_abono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApe1() {
        return ape1;
    }

    public void setApe1(String ape1) {
        this.ape1 = ape1;
    }

    public String getApe2() {
        return ape2;
    }

    public void setApe2(String ape2) {
        this.ape2 = ape2;
    }

    public String getNo_credito() {
        return no_credito;
    }

    public void setNo_credito(String no_credito) {
        this.no_credito = no_credito;
    }

    public String getAbono() {
        return abono;
    }

    public void setAbono(String abono) {
        this.abono = abono;
    }

    public String getPagar_hoy() {
        return pagar_hoy;
    }

    public void setPagar_hoy(String pagar_hoy) {
        this.pagar_hoy = pagar_hoy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(String saldoActual) {
        this.saldoActual = saldoActual;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNum_ext() {
        return num_ext;
    }

    public void setNum_ext(String num_ext) {
        this.num_ext = num_ext;
    }

    public String getNum_int() {
        return num_int;
    }

    public void setNum_int(String num_int) {
        this.num_int = num_int;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFecha_prox_pago() {
        return fecha_prox_pago;
    }

    public void setFecha_prox_pago(String fecha_prox_pago) {
        this.fecha_prox_pago = fecha_prox_pago;
    }

    public String getNo_abono() {
        return no_abono;
    }

    public void setNo_abono(String no_abono) {
        this.no_abono = no_abono;
    }
}
