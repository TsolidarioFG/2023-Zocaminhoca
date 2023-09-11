package es.zocaminhoca.zocacontrol.backend.model.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import es.zocaminhoca.zocacontrol.backend.model.daos.ReceiptDao;
import es.zocaminhoca.zocacontrol.backend.model.daos.UserDao;
import es.zocaminhoca.zocacontrol.backend.model.entities.*;
import es.zocaminhoca.zocacontrol.backend.model.exceptions.FileException;
import es.zocaminhoca.zocacontrol.backend.model.exceptions.InstanceNotFoundException;
import es.zocaminhoca.zocacontrol.backend.model.mappers.Transfer;
import es.zocaminhoca.zocacontrol.backend.model.mappers.TransferCsvEntityMapper;
import es.zocaminhoca.zocacontrol.backend.model.mappers.CsvTransfer;
import es.zocaminhoca.zocacontrol.backend.util.TransfersPathConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;


@Service
public class BillingService {

    @Autowired
    private ReceiptDao receiptDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private TransfersPathConfiguration transfersPathConfiguration;


    private PdfPCell getReceiptCell(int idx, String text, Font font) {

        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(Rectangle.NO_BORDER);
        if ((idx % 2) == 0) {
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        }
        return cell;
    }

    private String writeReceiptPdf(String path, Receipt receipt) throws FileNotFoundException,
            DocumentException {

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss");
        String receiptPdfPath =
                path + receipt.getClientName().replace(" ", "") + "-" + receipt.getDate().format(dateFormatter) + "-" + receipt.getId().toString();
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(receiptPdfPath));

        document.open();
        Font headerFont = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        String billHeader = "ZOCAMIÑOCA SOC. COOP. GALEGA";
        Paragraph headerParagraph = new Paragraph(billHeader, headerFont);

        Font dataFont = FontFactory.getFont(FontFactory.COURIER, 11, BaseColor.BLACK);
        String billData = "Nº Tiquet:\t" + receipt.getId().toString() +
                "\nData:\t" + receipt.getDate().format(dateFormatter) +
                "\nHora:\t" + receipt.getBill().getTime().format(timeFormatter) +
                "\n\n";
        Paragraph dataParagraph = new Paragraph(billData, dataFont);

        PdfPTable billTable = new PdfPTable(4);

        Stream.of("DESCRICION", "UD", "P.U.", "TOTAL")
                .forEach(tableHeader -> {
                            PdfPCell header = new PdfPCell();
                            header.setBackgroundColor(BaseColor.GREEN);
                            header.setBorder(Rectangle.NO_BORDER);
                            Phrase phrase = new Phrase(tableHeader);
                            phrase.setFont(dataFont);
                            header.setPhrase(phrase);
                            billTable.addCell(header);
                        }
                );

        List<BillLine> billLines = receipt.getBill().getBillLines();

        for (int i = 0; i < billLines.size(); i++) {
            BillLine b = billLines.get(i);

            billTable.addCell(getReceiptCell(i, b.getDescription(), dataFont));
            billTable.addCell(getReceiptCell(i, b.getQuantity().toString(), dataFont));
            billTable.addCell(getReceiptCell(i, b.getUnityPrice().toString(), dataFont));
            billTable.addCell(getReceiptCell(i, b.getTotalPrice().toString(), dataFont));

        }

        String totalTicket = "Total tiguet: \t" + receipt.getPrice().toString();
        Paragraph totalTicketParagraph = new Paragraph(totalTicket, dataFont);

        String footer = "*** I.V.E. ENGADIDO *** \n" +
                "GRAZAS POR PARTICIPAR NO PROXECTO";
        Paragraph footerTicketParagraph = new Paragraph(footer, dataFont);


        document.add(headerParagraph);
        document.add(dataParagraph);
        document.add(billTable);
        document.add(totalTicketParagraph);
        document.add(footerTicketParagraph);

        document.close();

        return receiptPdfPath;
    }

    private LocalDate getEarliestDate(List<Transfer> transfers) {
        LocalDate date = LocalDate.now();

        for (Transfer t : transfers) {
            if (t.getOperationDate().isBefore(date)) {
                date = t.getOperationDate();
            }
        }

        return date;

    }

    private List<Transfer> readBankExtract(String path) throws FileException {
        try {

            List<Transfer> transfers = new ArrayList<Transfer>();
            CSVReader reader = new CSVReader(new FileReader(path));
            HeaderColumnNameMappingStrategy<CsvTransfer> mappingStrategy =
                    new HeaderColumnNameMappingStrategy<>();
            mappingStrategy.setType(CsvTransfer.class);
            CsvToBean<CsvTransfer> csvToBean = new CsvToBeanBuilder<CsvTransfer>(reader)
                    .withType(CsvTransfer.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withIgnoreQuotations(true)
                    .withIgnoreEmptyLine(true)
                    .withMappingStrategy(mappingStrategy)
                    .build();

            Iterator<CsvTransfer> transferDtoIterator = csvToBean.iterator();

            while (transferDtoIterator.hasNext()) {
                Transfer transfer = TransferCsvEntityMapper.transferMap(transferDtoIterator.next());
                transfers.add(transfer);
            }

            return transfers;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new FileException(path, "FileNotFoundException");
        }
    }

    private List<Transfer> filterTransferList(List<Transfer> transferList) {

        List<Transfer> filteredTransferList = new ArrayList<Transfer>();

        filteredTransferList =
                transferList.stream().filter(transfer -> transfer.getAmount().compareTo(BigDecimal.ZERO) > 0).toList();
        
        return filteredTransferList;
    }

    public List<Transfer> checkTransfers(String filename) throws FileException {

        String path = transfersPathConfiguration.getFolderPath() + filename;

        List<Transfer> transfers = readBankExtract(path);
        transfers = new ArrayList<>(filterTransferList(transfers));
        LocalDate dateFrom = getEarliestDate(transfers).minusMonths(3);
        transfers.sort(Comparator.comparing(Transfer::getAmount));

        List<Receipt> receipts = receiptDao.findByStateAndDateGreaterThanEqualOrderByPriceAsc(
                "Emitido", dateFrom);
        //receipts.sort(Comparator.comparing(Receipt::getPrice));

        int transferPointer = 0;
        int receiptPointer = 0;

        while (transferPointer < transfers.size() && receiptPointer < receipts.size()) {
            Transfer transfer = transfers.get(transferPointer);
            Receipt receipt = receipts.get(receiptPointer);
            if (transfer.getAmount().compareTo(receipt.getPrice()) < 0) {
                transferPointer++;
            } else if (receipt.getPrice().compareTo(transfer.getAmount()) < 0) {
                receiptPointer++;
            } else if ((receipt.getPrice().compareTo(transfer.getAmount()) == 0) && (receipt.getDate().isEqual(transfer.getOperationDate()) || receipt.getDate().isBefore(transfer.getOperationDate()))) {
                transfer.setChecked(true);
                transfer.setClient(receipt.getClient());
                receiptPointer++;
                transferPointer++;
            } else {
                receiptPointer++;
            }
        }
        return transfers;
    }

    public String writeCheckedTransfers(List<Transfer> transferList, String filename) throws FileException {

        String path = transfersPathConfiguration.getFolderPath() + filename;

        try {


            CsvTransfer headers = new CsvTransfer("Fecha de la operación", "Fecha valor",
                    "Concepto", "Importe", "Saldo", "Email");
            List<CsvTransfer> checkedTransferList =
                    TransferCsvEntityMapper.transfersToCsvTransfers(transferList);
            checkedTransferList.add(0, headers);

            ColumnPositionMappingStrategy<CsvTransfer> mappingStrategy =
                    new ColumnPositionMappingStrategy<CsvTransfer>();
            mappingStrategy.setType(CsvTransfer.class);

            CSVWriter writer = new CSVWriter(new FileWriter(path));
            StatefulBeanToCsv<CsvTransfer> beanToCsv =
                    new StatefulBeanToCsvBuilder<CsvTransfer>(writer)
                            .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                            .withMappingStrategy(mappingStrategy)
                            .withApplyQuotesToAll(true)
                            .build();

            beanToCsv.write(checkedTransferList);
            writer.close();

            return path;
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileException(path, "IOException");
        } catch (CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
            throw new FileException(path, "CsvRequiredFieldEmpty");
        } catch (CsvDataTypeMismatchException e) {
            e.printStackTrace();
            throw new FileException(path, "CsvDataTypeMismatch");
        }
    }

    public Receipt findLastClientReceipt(Long telegramId) throws InstanceNotFoundException {

        User user = userDao.findByTelegramId(telegramId);

        if (Objects.isNull(user)) {
            throw new InstanceNotFoundException(User.class.getSimpleName(), telegramId.toString());
        }

        if (Objects.isNull(user.getClient())) {
            throw new InstanceNotFoundException(Clientes.class.getSimpleName(),
                    telegramId.toString());
        }

        Receipt receipt =
                receiptDao.findTopByClientCodclienteOrderByDateDesc(user.getClient().getCodcliente());

        if (Objects.isNull(receipt)) {
            throw new InstanceNotFoundException(Receipt.class.getSimpleName(), "");
        }

        return receipt;
    }

    public Receipt getLastUnpaidUserReceipt(Long telegramId) throws InstanceNotFoundException {


        User user = userDao.findByTelegramId(telegramId);

        if (Objects.isNull(user)) {
            throw new InstanceNotFoundException(User.class.getSimpleName(), telegramId.toString());
        }

        if (Objects.isNull(user.getClient())) {
            throw new InstanceNotFoundException(Clientes.class.getSimpleName(),
                    telegramId.toString());
        }

        Receipt receipt =
                receiptDao.findTopByClientCodclienteAndStateOrderByDateDesc(user.getClient().getCodcliente(), "Emitido");

        if (Objects.isNull(receipt)) {
            throw new InstanceNotFoundException(Receipt.class.getSimpleName(), "Emitido");
        }

        return receipt;

    }

    public String getLastReceiptPdfPath(Long telegramId) throws InstanceNotFoundException,
            DocumentException, FileNotFoundException {

        User user = userDao.findByTelegramId(telegramId);
        if (Objects.isNull(user)) {
            throw new InstanceNotFoundException(User.class.getSimpleName(), telegramId.toString());
        }

        Clientes client = user.getClient();
        if (Objects.isNull(client)) {
            throw new InstanceNotFoundException(Clientes.class.getSimpleName(),
                    telegramId.toString());
        }

        Receipt receipt =
                receiptDao.findTopByClientCodclienteOrderByDateDesc(client.getCodcliente());
        if (Objects.isNull(receipt)) {
            throw new InstanceNotFoundException(Receipt.class.getSimpleName(),
                    telegramId.toString());
        }

        String receiptPdfPath = writeReceiptPdf(transfersPathConfiguration.getFolderPath(),
                receipt);

        return receiptPdfPath;

    }

    public List<Receipt> getUnpaidReceipts() {
        List<Receipt> receipts = receiptDao.findByState("Emitido");
        return receipts;
    }
}