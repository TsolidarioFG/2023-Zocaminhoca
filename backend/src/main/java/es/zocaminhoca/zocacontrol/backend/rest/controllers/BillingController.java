package es.zocaminhoca.zocacontrol.backend.rest.controllers;

import com.itextpdf.text.DocumentException;
import es.zocaminhoca.zocacontrol.backend.model.daos.ClientesDao;
import es.zocaminhoca.zocacontrol.backend.model.entities.Clientes;
import es.zocaminhoca.zocacontrol.backend.model.entities.Receipt;
import es.zocaminhoca.zocacontrol.backend.model.mappers.Transfer;
import es.zocaminhoca.zocacontrol.backend.model.exceptions.FileException;
import es.zocaminhoca.zocacontrol.backend.model.exceptions.InstanceNotFoundException;
import es.zocaminhoca.zocacontrol.backend.model.services.BillingService;
import es.zocaminhoca.zocacontrol.backend.rest.dtos.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@RestController
@RequestMapping("/bills")
@Tag(name = "Billing", description = "Endpoints referentes a recibos, facturación e ingresos")
public class BillingController {

    @Autowired
    private BillingService billingService;

    @Autowired
    private ClientesDao clientesDao;

    @ExceptionHandler(InstanceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorsDto handleInstanceNotFoundExcepiton(InstanceNotFoundException exception,
                                                     Locale locale) {

        String errorMessage =
                "No se encuentra el " + exception.getInstanceClass() + " con id " + exception.getKey();

        return new ErrorsDto(errorMessage, exception.getKey(), exception.getInstanceClass());
    }

    @ExceptionHandler(FileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorsDto handleFileNotFoundExcepiton(FileNotFoundException exception,
                                                 Locale locale) {

        String errorMessage =
                "No se encuentra el fichero especificado";

        return new ErrorsDto(errorMessage, null, null);
    }

    @ExceptionHandler(DocumentException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorsDto handleDocumentException(DocumentException exception,
                                             Locale locale) {

        String errorMessage =
                "Problemas internos procesando el documento especificado";

        return new ErrorsDto(errorMessage, null, null);
    }

    @ExceptionHandler(FileException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorsDto handleFileException(FileException exception, Locale locale) {
        String errorMessage = exception.getMessage();
        return new ErrorsDto(errorMessage, exception.getPath(), exception.getPath());
    }

    @Operation(
            summary = "Consulta el último recibo de un usuario de telegram"
    )
    @GetMapping("/user/{telegramId}")
    public ReceiptDto getLastUserReceipt(@PathVariable Long telegramId) throws InstanceNotFoundException {
        return BillReceiptConversor.toReceiptDto(billingService.findLastClientReceipt(telegramId));
    }

    @Operation(
            summary = "Consulta el último recibo que consta sin pagar, de un usuario de telegram"
    )
    @GetMapping("/user/{telegramId}/unpaid")
    public ReceiptDto getLastUserUnpaidReceipt(@PathVariable Long telegramId) throws InstanceNotFoundException {
        return BillReceiptConversor.toReceiptDto(billingService.getLastUnpaidUserReceipt(telegramId));
    }

    @Operation(
            summary = "Genera un pdf con el último recibo de un usuario de telegram y devuelve el" +
                    " path al archivo"
    )
    @GetMapping("/user/{telegramId}/pdf")
    public PathDto getLastUserReceiptPdfPath(@PathVariable Long telegramId) throws InstanceNotFoundException, DocumentException, FileNotFoundException {

        return new PathDto(billingService.getLastReceiptPdfPath(telegramId));

    }

    @Operation(
            summary = "Devuelva las transferencias cuadradas con los recibos almacenados"
    )
    @GetMapping("/transfers/{filename}")
    public List<TransferDto> getCheckedTransfers(@PathVariable String filename) throws FileException {

        List<Transfer> transfers = billingService.checkTransfers(filename);
        return TransferConversor.toTransferDtos(transfers);

    }

    @Operation(
            summary = "Escribe un archivo CSV con los ingresos y sus coincidencias con recibos"
    )
    @PostMapping("/transfers/{filename}")
    public PathDto writeCheckedTransfers(@RequestBody List<TransferDto> transfersDtos,
                                         @PathVariable String filename) throws FileException {
        List<Transfer> transfers = new ArrayList<>();

        for (TransferDto t : transfersDtos) {
            Clientes client = null;
            if (!Objects.isNull(t.getClientDto())) {
                client = clientesDao.findById(t.getClientDto().getCodclient()).get();
            }
            transfers.add(TransferConversor.toTransfer(t, client));
        }

        return new PathDto(billingService.writeCheckedTransfers(transfers, filename));
    }

    @Operation(
            summary = "Devuelve los recibos no pagados"
    )
    @GetMapping("/unpaid")
    public List<ReceiptDto> getUnpaidReceipts() {

        List<Receipt> receipts = billingService.getUnpaidReceipts();
        return BillReceiptConversor.toReceiptDtos(receipts);

    }

}
