package es.zocaminhoca.zocacontrol.backend.rest.dtos;

import es.zocaminhoca.zocacontrol.backend.model.entities.Clientes;
import es.zocaminhoca.zocacontrol.backend.model.mappers.Transfer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TransferConversor {

    public static TransferDto toTransferDto(Transfer transfer) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String operationDateString = transfer.getOperationDate().format(dateFormatter);
        String valueDateString = transfer.getValueDate().format(dateFormatter);
        ClientDto client = null;
        if (!Objects.isNull(transfer.getClient())) {
            client = ClientConversor.toClientDto(transfer.getClient());
        }
        return new TransferDto(operationDateString, valueDateString, transfer.getConcept(),
                transfer.getAmount(), transfer.getBalance(), client, transfer.isChecked());

    }

    public static Transfer toTransfer(TransferDto transferDto, Clientes client) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate operationDate = LocalDate.parse(transferDto.getOperationDate(),
                dateTimeFormatter);
        LocalDate valueDate = LocalDate.parse(transferDto.getValueDate(), dateTimeFormatter);

        return new Transfer(operationDate, valueDate, transferDto.getConcept(),
                transferDto.getAmount(), transferDto.getBalance(), client);

    }

    public static List<TransferDto> toTransferDtos(List<Transfer> transfers) {
        return transfers.stream().map(t -> toTransferDto(t)).collect(Collectors.toList());
    }
}
