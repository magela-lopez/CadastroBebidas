package com.mobileexercicio.cadastrobebidas;

public class Bebida {


    private long id;
    private String nome;
    private double preco;
    private String codigo;
    private String tipo;

    public Bebida(long id, String nome, double preco, String codigo, String tipo) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.codigo = codigo;
        this.tipo = tipo;
    }


    public Bebida() {
        this.id = 0;
        this.nome = "";
        this.preco = 0.0D;
        this.codigo = "";
        this.tipo = "";
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return nome + "\n" + "R$" + preco + "\n"+tipo;
    }
}