package util;

import java.io.Serializable;

public class Requisicao implements Serializable {
    private double Id_Requisicao;
    private Object valor, valor2, valor3;

    public Requisicao(double id_Requisicao) {
        Id_Requisicao = id_Requisicao;
    }

    public Requisicao(double id_Requisicao, Object valor) {
        Id_Requisicao = id_Requisicao;
        this.valor = valor;
    }

    public Requisicao(double id_Requisicao, Object valor, Object valor2) {
        Id_Requisicao = id_Requisicao;
        this.valor = valor;
        this.valor2 = valor2;
    }

    public Requisicao(double id_Requisicao, Object valor, Object valor2, Object valor3) {
        Id_Requisicao = id_Requisicao;
        this.valor = valor;
        this.valor2 = valor2;
        this.valor3 = valor3;
    }

    public double getId_Requisicao() {
        return Id_Requisicao;
    }

    public Object getValor() {
        return valor;
    }

    public Object getValor2() {
        return valor2;
    }

    public Object getValor3() {
        return valor3;
    }
}