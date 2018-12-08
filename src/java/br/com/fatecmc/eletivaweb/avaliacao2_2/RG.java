package br.com.fatecmc.eletivaweb.avaliacao2_2;

public class RG {

    private String numero;
    private String orgaoExpeditor;
    private Estado estadoExpeditor;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getOrgaoExpeditor() {
        return orgaoExpeditor;
    }

    public void setOrgaoExpeditor(String orgaoExpeditor) {
        this.orgaoExpeditor = orgaoExpeditor;
    }

    public Estado getEstadoExpeditor() {
        return estadoExpeditor;
    }

    public void setEstadoExpeditor(Estado estadoExpeditor) {
        this.estadoExpeditor = estadoExpeditor;
    }

}
