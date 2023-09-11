package es.zocaminhoca.zocacontrol.backend.model.services;

import com.itextpdf.text.DocumentException;
import es.zocaminhoca.zocacontrol.backend.model.daos.*;
import es.zocaminhoca.zocacontrol.backend.model.entities.*;
import es.zocaminhoca.zocacontrol.backend.model.exceptions.FileException;
import es.zocaminhoca.zocacontrol.backend.model.exceptions.InstanceNotFoundException;
import es.zocaminhoca.zocacontrol.backend.model.mappers.Transfer;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Rollback
public class BillingServiceTest {

    @Autowired
    private BillingService billingService;

    @Autowired
    private ClientesDao clientesDao;

    @Autowired
    private ReceiptDao receiptDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private BillLineDao billLineDao;

    @Autowired
    private BillDao billDao;

    private String transfersFileName = "movimientosTest.csv";
    private String transferFileNameOutput = "transfersOutput.csv";

    private Clientes createClient() {
        Clientes client = new Clientes("000001", "test@test.com", "test", "test", null);
        clientesDao.save(client);
        return client;
    }

    private Receipt createReceipt(Clientes client, Double value, Bill bill) {
        Receipt receipt = new Receipt();
        receipt.setId(Long.valueOf(1));
        receipt.setClient(client);
        receipt.setClientName(client.getNombre());
        receipt.setState("Emitido");
        receipt.setDate(LocalDate.now().minusDays(10));
        receipt.setPrice(BigDecimal.valueOf(value));
        receipt.setBill(bill);
        receiptDao.save(receipt);
        return receipt;
    }

    private User createUser(Clientes client) {
        User user = new User();
        user.setUserName("testUserName");
        user.setWebEmail("test@email.com");
        user.setLastName("testLastName");
        user.setFirstName("testFirstName");
        user.setClient(client);
        user.setTelegramId(Long.valueOf(1));
        userDao.save(user);
        return user;
    }

    private Bill createBill(Clientes client) {
        List<BillLine> billLines = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            BillLine billLine = new BillLine((i + 1), BigDecimal.valueOf(123),
                    BigDecimal.valueOf(10), BigDecimal.valueOf(10),
                    "testItem" + String.valueOf(i + 1));
            billLineDao.save(billLine);
            billLines.add(billLine);
        }
        Bill bill = new Bill(Long.valueOf(1), client, BigDecimal.valueOf(100), LocalTime.now(),
                LocalDate.now()
                , "codpagoTest", billLines);
        billDao.save(bill);
        return bill;
    }

    @Test
    public void checkTransfersTest() throws FileException {

        Clientes client = createClient();
        Receipt receipt = createReceipt(client, 110.5, null);

        List<Transfer> transfers = billingService.checkTransfers(transfersFileName);
        List<Transfer> checkedTransfers =
                transfers.stream().filter(t -> t.isChecked()).collect(Collectors.toList());

        assertTrue(transfers.size() == 3);
        assertTrue(checkedTransfers.size() == 1);

    }

    @Test
    public void checkTransfersBadDateTest() throws FileException {

        Clientes client = createClient();
        Receipt receipt = createReceipt(client, 12.5, null);

        List<Transfer> transfers = billingService.checkTransfers(transfersFileName);
        List<Transfer> checkedTransfers =
                transfers.stream().filter(t -> t.isChecked()).collect(Collectors.toList());

        assertTrue(checkedTransfers.size() == 0);

    }

    @Test
    public void writeCheckedTransfersTest() throws FileException {

        String projectDir = System.getProperty("user.dir");

        String relativeDirectoryPath = "src/test/resources/examples/";

        List<Transfer> transfers = billingService.checkTransfers(transfersFileName);
        billingService.writeCheckedTransfers(transfers, transferFileNameOutput);

        File file = new File(projectDir, relativeDirectoryPath + transferFileNameOutput);

        assertTrue(file.exists());

        if (!file.delete()) {
            System.out.println("Failed to delete file: " + file.getAbsolutePath());
        }
    }

    @Test
    public void writeCheckedTransfersTestNonExistantTransfersFile() throws FileException {

        String projectDir = System.getProperty("user.dir");

        String relativeDirectoryPath = "src/test/resources/examples/";

        assertThrows(FileException.class, () -> {
            List<Transfer> transfers = billingService.checkTransfers("nonexistantFile");
        });
    }

    @Test
    public void findLastClientReceiptTest() throws InstanceNotFoundException {

        Clientes client = createClient();
        Receipt receipt = createReceipt(client, 110.5, null);
        User user = createUser(client);

        Receipt lastReceipt = billingService.findLastClientReceipt(Long.valueOf(1));
        assertEquals(receipt.getId(), lastReceipt.getId());
    }

    @Test
    public void findLastClientReceiptTestNonExistantUser() throws InstanceNotFoundException {

        assertThrows(InstanceNotFoundException.class, () -> {
            billingService.findLastClientReceipt(Long.valueOf(2));
        });
    }

    @Test
    public void findLastClientReceiptTestNotConnected() {
        User user = createUser(null);
        assertThrows(InstanceNotFoundException.class, () -> {
            billingService.findLastClientReceipt(Long.valueOf(1));
        });
    }

    @Test
    public void findLastClientReceiptTestNoReceiptFound() {
        Clientes client = createClient();
        User user = createUser(client);
        assertThrows(InstanceNotFoundException.class, () -> {
            billingService.findLastClientReceipt(Long.valueOf(1));
        });
    }

    @Test
    public void getLastUnpaidUserReceiptTest() throws InstanceNotFoundException {

        Clientes client = createClient();
        Receipt receipt = createReceipt(client, 110.5, null);
        User user = createUser(client);

        Receipt lastReceipt = billingService.getLastUnpaidUserReceipt(Long.valueOf(1));
        assertEquals(receipt.getId(), lastReceipt.getId());
    }

    @Test
    public void getLastUnpaidUserReceiptUserNotFoundTest() throws InstanceNotFoundException {

        Clientes client = createClient();
        Receipt receipt = createReceipt(client, 110.5, null);
        User user = createUser(client);

        assertThrows(InstanceNotFoundException.class, () -> {
                    Receipt lastReceipt = billingService.getLastUnpaidUserReceipt(Long.valueOf(2));
                }
        );
    }

    @Test
    public void getLastUnpaidUserReceiptClientNotFoundTest() throws InstanceNotFoundException {

        Clientes client = createClient();
        Receipt receipt = createReceipt(client, 110.5, null);
        User user = createUser(null);

        assertThrows(InstanceNotFoundException.class, () -> {
                    Receipt lastReceipt = billingService.getLastUnpaidUserReceipt(Long.valueOf(1));
                }
        );
    }

    @Test
    public void getLastReceiptPdfPathTest() throws InstanceNotFoundException, DocumentException,
            FileNotFoundException {

        String projectDir = System.getProperty("user.dir");

        String relativeDirectoryPath = "src/test/resources/examples/";

        Clientes client = createClient();
        Bill bill = createBill(client);
        Receipt receipt = createReceipt(client, 110.5, null);
        receipt.setBill(bill);
        receiptDao.save(receipt);
        User user = createUser(client);

        String path = billingService.getLastReceiptPdfPath(Long.valueOf(1));

        File file = new File(path);

        assertTrue(file.exists());

        file.delete();
    }

    @Test
    public void getLastReceiptPdfPathReceiptNotFoundTest() throws InstanceNotFoundException,
            DocumentException,
            FileNotFoundException {

        String projectDir = System.getProperty("user.dir");

        String relativeDirectoryPath = "src/test/resources/examples/";

        Clientes client = createClient();
        User user = createUser(client);

        assertThrows(InstanceNotFoundException.class, () -> {
            String path = billingService.getLastReceiptPdfPath(Long.valueOf(1));

        });
    }

    @Test
    public void getLastReceiptPdfPathUsertNotFoundTest() throws InstanceNotFoundException,
            DocumentException,
            FileNotFoundException {

        String projectDir = System.getProperty("user.dir");

        String relativeDirectoryPath = "src/test/resources/examples/";

        assertThrows(InstanceNotFoundException.class, () -> {
            String path = billingService.getLastReceiptPdfPath(Long.valueOf(2));

        });
    }

    @Test
    public void getLastReceiptPdfPathClienttNotFoundTest() throws InstanceNotFoundException,
            DocumentException,
            FileNotFoundException {

        String projectDir = System.getProperty("user.dir");

        String relativeDirectoryPath = "src/test/resources/examples/";

        User user = createUser(null);

        assertThrows(InstanceNotFoundException.class, () -> {
            String path = billingService.getLastReceiptPdfPath(Long.valueOf(1));

        });
    }


}
