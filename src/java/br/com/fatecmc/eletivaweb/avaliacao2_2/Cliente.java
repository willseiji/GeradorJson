package br.com.fatecmc.eletivaweb.avaliacao2_2;

import java.util.Date;

public class Cliente extends PessoaFisica {

    private String numeroCliente;
    @Formate(padrao="dd/MM/yyyy")
    private Date clienteDesde;
    private int testeInt;
    private double testeDouble;
    private char testeChar;
    private boolean testeBoolean;
    private String testeNull;
    private TesteLoop testeLoop;

    public TesteLoop getTesteLoop() {
        return testeLoop;
    }

    public void setTesteLoop(TesteLoop testeLoop) {
        this.testeLoop = testeLoop;
    }

    public String getTesteNull() {
        return testeNull;
    }

    public void setTesteNull(String testeNull) {
        this.testeNull = testeNull;
    }

    public int getTesteInt() {
        return testeInt;
    }

    public void setTesteInt(int testeInt) {
        this.testeInt = testeInt;
    }

    public double getTesteDouble() {
        return testeDouble;
    }

    public void setTesteDouble(double testeDouble) {
        this.testeDouble = testeDouble;
    }

    public char getTesteChar() {
        return testeChar;
    }

    public void setTesteChar(char testeChar) {
        this.testeChar = testeChar;
    }

    public boolean isTesteBoolean() {
        return testeBoolean;
    }

    public void setTesteBoolean(boolean testeBoolean) {
        this.testeBoolean = testeBoolean;
    }
    
    
    public String getNumeroCliente() {
        return numeroCliente;
    }

    public void setNumeroCliente(String numeroCliente) {
        this.numeroCliente = numeroCliente;
    }
    
    public Date getClienteDesde() {
        return clienteDesde;
    }

    public void setClienteDesde(Date clienteDesde) {
        this.clienteDesde = clienteDesde;
    }

}
