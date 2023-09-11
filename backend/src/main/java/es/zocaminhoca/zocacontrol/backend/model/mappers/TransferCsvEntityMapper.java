package es.zocaminhoca.zocacontrol.backend.model.mappers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class TransferCsvEntityMapper {

    public static Transfer transferMap(CsvTransfer csvTransfer) {

        String dateFormat = "d/M/yyyy";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        LocalDate operationDate = LocalDate.parse(csvTransfer.getOperationDate(), formatter);
        LocalDate valueDate = LocalDate.parse(csvTransfer.getValueDate(), formatter);
        BigDecimal amount = new BigDecimal(csvTransfer.getAmount().replace(",", "."));
        BigDecimal balance = new BigDecimal(csvTransfer.getBalance().replace(",", "."));

        Transfer transfer = new Transfer(operationDate, valueDate, csvTransfer.getConcept(),
                amount, balance);

        return transfer;
    }

    public static CsvTransfer transferToCsvTransfer(Transfer transfer) {

        CsvTransfer csvTransfer = new CsvTransfer();
        String dateFormat = "d/M/yyyy";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        csvTransfer.setOperationDate(transfer.getOperationDate().format(formatter));
        csvTransfer.setConcept(transfer.getConcept());
        csvTransfer.setValueDate(transfer.getValueDate().format(formatter));
        csvTransfer.setAmount(transfer.getAmount().toString());
        csvTransfer.setBalance(transfer.getBalance().toString());
        if (transfer.isChecked() && (!Objects.isNull(transfer.getClient()))) {
            csvTransfer.setEmail(transfer.getClient().getEmail());
        }
        return csvTransfer;
    }

    public static List<CsvTransfer> transfersToCsvTransfers(List<Transfer> transferList) {

        List<CsvTransfer> csvTransferList = new ArrayList<CsvTransfer>();

        for (Transfer transfer : transferList) {
            csvTransferList.add(transferToCsvTransfer(transfer));
        }
        return csvTransferList;
    }
}
