package es.zocaminhoca.zocacontrol.backend.rest.dtos;

public class ClientDto {

    private String codclient;
    private String email;
    private String name;
    private String comercialname;

    public ClientDto() {
    }

    public ClientDto(String codclient, String email, String name, String comercialname) {
        this.codclient = codclient;
        this.email = email;
        this.name = name;
        this.comercialname = comercialname;
    }

    public String getCodclient() {
        return codclient;
    }

    public void setCodclient(String codclient) {
        this.codclient = codclient;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComercialname() {
        return comercialname;
    }

    public void setComercialname(String comercialname) {
        this.comercialname = comercialname;
    }
}
