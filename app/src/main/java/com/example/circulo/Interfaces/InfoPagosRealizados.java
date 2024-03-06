package com.example.circulo.Interfaces;

public class InfoPagosRealizados {

    private String no_abono;

    private String no_credito;

    private String no_cobrador;

    private String fecha_prox_pago;

    private String fecha_realizo_pago;

    private String hora;

    private String saldo_anterior;

    private String saldo_actual;

    private String abono_recibido;

    private String tipo_pago;

    private String estatus;

    private String estado;

    private String sincronizado;

    private String no_cliente;

    private String nombre;

    private String ape1;

    private String ape2;

    private String calle;

    private String num_ext;

    private String num_int;

    private String cp;

    private String colonia;

    private String telefono;


    public InfoPagosRealizados(String no_abono, String no_credito, String no_cobrador, String fecha_prox_pago, String fecha_realizo_pago, String hora, String saldo_anterior, String saldo_actual, String abono_recibido, String tipo_pago, String estatus, String estado, String sincronizado, String no_cliente, String nombre, String ape1, String ape2, String calle, String num_ext, String num_int, String cp, String colonia, String telefono) {


        this.no_abono = no_abono;
        this.no_credito = no_credito;
        this.no_cobrador = no_cobrador;
        this.fecha_prox_pago = fecha_prox_pago;
        this.fecha_realizo_pago = fecha_realizo_pago;
        this.hora = hora;
        this.saldo_anterior = saldo_anterior;
        this.saldo_actual = saldo_actual;
        this.abono_recibido = abono_recibido;
        this.tipo_pago = tipo_pago;
        this.estatus = estatus;
        this.estado = estado;
        this.sincronizado = sincronizado;
        this.no_cliente = no_cliente;
        this.nombre = nombre;
        this.ape1 = ape1;
        this.ape2 = ape2;
        this.calle = calle;
        this.num_ext = num_ext;
        this.num_int = num_int;
        this.cp = cp;
        this.colonia = colonia;
        this.telefono = telefono;
    }

    public String getNo_abono() {
        return no_abono;
    }

    public void setNo_abono(String no_abono) {
        this.no_abono = no_abono;
    }

    public String getNo_credito() {
        return no_credito;
    }

    public void setNo_credito(String no_credito) {
        this.no_credito = no_credito;
    }

    public String getNo_cobrador() {
        return no_cobrador;
    }

    public void setNo_cobrador(String no_cobrador) {
        this.no_cobrador = no_cobrador;
    }

    public String getFecha_prox_pago() {
        return fecha_prox_pago;
    }

    public void setFecha_prox_pago(String fecha_prox_pago) {
        this.fecha_prox_pago = fecha_prox_pago;
    }

    public String getFecha_realizo_pago() {
        return fecha_realizo_pago;
    }

    public void setFecha_realizo_pago(String fecha_realizo_pago) {
        this.fecha_realizo_pago = fecha_realizo_pago;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getSaldo_anterior() {
        return saldo_anterior;
    }

    public void setSaldo_anterior(String saldo_anterior) {
        this.saldo_anterior = saldo_anterior;
    }

    public String getSaldo_actual() {
        return saldo_actual;
    }

    public void setSaldo_actual(String saldo_actual) {
        this.saldo_actual = saldo_actual;
    }

    public String getAbono_recibido() {
        return abono_recibido;
    }

    public void setAbono_recibido(String abono_recibido) {
        this.abono_recibido = abono_recibido;
    }

    public String getTipo_pago() {
        return tipo_pago;
    }

    public void setTipo_pago(String tipo_pago) {
        this.tipo_pago = tipo_pago;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getSincronizado() {
        return sincronizado;
    }

    public void setSincronizado(String sincronizado) {
        this.sincronizado = sincronizado;
    }

    public String getNo_cliente() {
        return no_cliente;
    }

    public void setNo_cliente(String no_cliente) {
        this.no_cliente = no_cliente;
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

}
