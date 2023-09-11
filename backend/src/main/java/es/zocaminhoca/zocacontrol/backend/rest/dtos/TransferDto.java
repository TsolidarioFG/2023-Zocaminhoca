package es.zocaminhoca.zocacontrol.backend.rest.dtos;

import java.math.BigDecimal;

public class TransferDto {

    private String operationDate;
    private String valueDate;
    private String concept;
    private BigDecimal amount;
    private BigDecimal balance;
    private ClientDto clientDto;
    private boolean checked;

    public TransferDto() {
    }

    public TransferDto(String operationDate, String valueDate, String concept, BigDecimal amount,
                       BigDecimal balance, ClientDto clientDto, boolean checked) {
        this.operationDate = operationDate;
        this.valueDate = valueDate;
        this.concept = concept;
        this.amount = amount;
        this.balance = balance;
        this.clientDto = clientDto;
        this.checked = checked;
    }

    public String getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(String operationDate) {
        this.operationDate = operationDate;
    }

    public String getValueDate() {
        return valueDate;
    }

    public void setValueDate(String valueDate) {
        this.valueDate = valueDate;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public ClientDto getClientDto() {
        return clientDto;
    }

    public void setClientDto(ClientDto clientDto) {
        this.clientDto = clientDto;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
