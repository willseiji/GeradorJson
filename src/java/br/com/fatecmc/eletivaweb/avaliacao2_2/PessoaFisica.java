package br.com.fatecmc.eletivaweb.avaliacao2_2;

public class PessoaFisica {

    private String nome;
    //@IgnoreJeyzon
    private RG[] rg;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public RG[] getRg() {
        return rg;
    }

    public void setRg(RG[] rg) {
        this.rg = rg;
    }
    
}
