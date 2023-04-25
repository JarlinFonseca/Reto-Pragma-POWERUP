package com.pragma.powerup.domain.model;

public class SmsMessageModel {

    private String numero;
    private String mensaje;

    public SmsMessageModel() {
    }

    public SmsMessageModel(String numero, String mensaje) {
        this.numero = numero;
        this.mensaje = mensaje;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
