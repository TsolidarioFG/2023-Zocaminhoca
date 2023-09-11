package es.zocaminhoca.zocacontrol.backend.rest.dtos;

import es.zocaminhoca.zocacontrol.backend.model.entities.Bill;
import es.zocaminhoca.zocacontrol.backend.model.entities.BillLine;
import es.zocaminhoca.zocacontrol.backend.model.entities.Receipt;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class BillReceiptConversor {

    public final static BillLineDto toBillLineDto(BillLine billLine) {

        return new BillLineDto(billLine.getDescription(), billLine.getUnityPrice(),
                billLine.getTotalPrice(), billLine.getQuantity());
    }

    public final static List<BillLineDto> toBillLinesDtos(List<BillLine> billLines) {

        return billLines.stream().map(bl -> toBillLineDto(bl)).collect(Collectors.toList());

    }

    public final static BillDto toBillDto(Bill bill) {

        List<BillLineDto> billLinesDtos = toBillLinesDtos(bill.getBillLines());
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedTime = bill.getTime().format(timeFormatter);
        String formattedDate = bill.getDate().format(dateFormatter);

        return new BillDto(bill.getId(), formattedTime, formattedDate, billLinesDtos);

    }

    public final static ReceiptDto toReceiptDto(Receipt receipt) {

        BillDto billDto = toBillDto(receipt.getBill());

        return new ReceiptDto(receipt.getId(), receipt.getClientName(), billDto,
                receipt.getState(), receipt.getPrice());

    }

    public final static List<ReceiptDto> toReceiptDtos(List<Receipt> receipts) {
        return receipts.stream().map(r -> toReceiptDto(r)).collect(Collectors.toList());
    }
}
