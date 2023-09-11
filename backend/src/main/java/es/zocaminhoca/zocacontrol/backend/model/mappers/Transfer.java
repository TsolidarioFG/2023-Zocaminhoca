package es.zocaminhoca.zocacontrol.backend.model.mappers;

import ch.qos.logback.core.net.server.Client;
import es.zocaminhoca.zocacontrol.backend.model.entities.Clientes;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transfer {

    private LocalDate operationDate;
    private LocalDate valueDate;
    private String concept;
    private BigDecimal amount;
    private BigDecimal balance;
    private Clientes client;
    private boolean checked;

    public Transfer() {
    }

    public Transfer(LocalDate operationDate, LocalDate valueDate, String concept, BigDecimal amount,
                    BigDecimal balance) {
        this.operationDate = operationDate;
        this.valueDate = valueDate;
        this.concept = concept;
        this.amount = amount;
        this.balance = balance;
    }

    public Transfer(LocalDate operationDate, LocalDate valueDate, String concept, BigDecimal amount,
                    BigDecimal balance, Clientes client) {
        this.operationDate = operationDate;
        this.valueDate = valueDate;
        this.concept = concept;
        this.amount = amount;
        this.balance = balance;
        this.client = client;
    }

    public LocalDate getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(LocalDate operationDate) {
        this.operationDate = operationDate;
    }

    public LocalDate getValueDate() {
        return valueDate;
    }

    public void setValueDate(LocalDate valueDate) {
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

    public Clientes getClient() {
        return client;
    }

    public void setClient(Clientes client) {
        this.client = client;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }


}
