package util;

import java.io.Serializable;

public class Requisicao implements Serializable {
    private int id_Requisicao;
    private Object valor, valor2, valor3;

    public Requisicao(int id_Requisicao) {
        this.id_Requisicao = id_Requisicao;
    }

    public Requisicao(int id_Requisicao, Object valor) {
        this.id_Requisicao = id_Requisicao;
        this.valor = valor;
    }

    public Requisicao(int id_Requisicao, Object valor, Object valor2) {
        this.id_Requisicao = id_Requisicao;
        this.valor = valor;
        this.valor2 = valor2;
    }

    public Requisicao(int id_Requisicao, Object valor, Object valor2, Object valor3) {
        this.id_Requisicao = id_Requisicao;
        this.valor = valor;
        this.valor2 = valor2;
        this.valor3 = valor3;
    }

    public int getId_Requisicao() {
        return this.id_Requisicao;
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