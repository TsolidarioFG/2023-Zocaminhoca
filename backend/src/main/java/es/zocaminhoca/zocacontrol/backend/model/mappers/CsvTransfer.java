package es.zocaminhoca.zocacontrol.backend.model.mappers;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class CsvTransfer {

    @CsvBindByName(column = "Fecha de la operación")
    @CsvBindByPosition(position = 0)
    private String operationDate;
    @CsvBindByName(column = "Fecha valor")
    @CsvBindByPosition(position = 1)
    private String valueDate;
    @CsvBindByName(column = "Concepto")
    @CsvBindByPosition(position = 2)
    private String concept;
    @CsvBindByName(column = "Importe")
    @CsvBindByPosition(position = 3)
    private String amount;
    @CsvBindByName(column = "Saldo")
    @CsvBindByPosition(position = 4)
    private String balance;
    @CsvBindByName(column = "Email")
    @CsvBindByPosition(position = 5)
    private String email;

    public CsvTransfer() {
    }

    public CsvTransfer(String operationDate, String valueDate, String concept, String amount,
                       String balance, String email) {
        this.operationDate = operationDate;
        this.valueDate = valueDate;
        this.concept = concept;
        this.amount = amount;
        this.balance = balance;
        this.email = email;
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
