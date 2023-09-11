package es.zocaminhoca.zocacontrol.backend.model.services;

import es.zocaminhoca.zocacontrol.backend.model.daos.ClientesDao;
import es.zocaminhoca.zocacontrol.backend.model.daos.OrderDao;
import es.zocaminhoca.zocacontrol.backend.model.daos.UserDao;
import es.zocaminhoca.zocacontrol.backend.model.entities.Clientes;
import es.zocaminhoca.zocacontrol.backend.model.entities.Order;
import es.zocaminhoca.zocacontrol.backend.model.entities.User;
import es.zocaminhoca.zocacontrol.backend.model.exceptions.FileException;
import es.zocaminhoca.zocacontrol.backend.model.exceptions.InstanceNotFoundException;
import es.zocaminhoca.zocacontrol.backend.model.exceptions.NotEnoughInformationException;
import es.zocaminhoca.zocacontrol.backend.model.exceptions.UserTelegramIdAlreadyExists;
import es.zocaminhoca.zocacontrol.backend.model.mappers.WebOrder;
import es.zocaminhoca.zocacontrol.backend.rest.dtos.OrderConversor;
import es.zocaminhoca.zocacontrol.backend.rest.dtos.OrderDto;
import es.zocaminhoca.zocacontrol.backend.rest.dtos.UserConversor;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Rollback
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ClientesDao clientesDao;

    @Autowired
    private OrderDao orderDao;

    private Clientes createClient() {
        Clientes client = new Clientes("000001", "test@test.com", "test", "test", null);
        clientesDao.save(client);
        return client;
    }

    @Test
    public void readOrdersTest() throws FileException {
        String filename = "pedidosTest.json";

        List<WebOrder> orders = orderService.readOrders(filename);
        assertTrue(orders.size() == 1);
        assertTrue(orders.get(0).getProducts().size() == 5);
        assertTrue(orders.get(0).getUser_email().equals("test@test.com"));
    }

    @Test
    public void readOrdersNonExistantFileTest() throws FileException {
        String filename = "nonExistantFile.json";

        assertThrows(FileException.class, () -> {
            List<WebOrder> orders = orderService.readOrders(filename);
        });
    }

    @Test
    public void saveOrderTest() throws FileException {
        String filename = "pedidosTest.json";

        List<WebOrder> webOrders = orderService.readOrders(filename);
        List<OrderDto> orderDtos = new ArrayList<>();
        User user = null;
        for (WebOrder o : webOrders) {
            user = userDao.findByWebId(Long.parseLong(o.getCustomer_user()));
            if (Objects.isNull(user)) {
                orderDtos.add(OrderConversor.webOrderToPedidoDto(o, null));
            } else {
                orderDtos.add(OrderConversor.webOrderToPedidoDto(o, UserConversor.toUserDto(user)));
            }
        }
        List<Order> orders = orderService.saveOrders(orderDtos);

        orderDao.findAll().forEach(order -> {
            assertEquals(order, orders.get(0));
        });

    }

    @Test
    public void getOrderTest() throws UserTelegramIdAlreadyExists, NotEnoughInformationException,
            InstanceNotFoundException, FileException {

        User user = userService.addTelegramUser(Long.valueOf(1), "testNAme", "testLastName",
                "testUserName");

        Clientes client = createClient();

        User updatedUser = userService.updateUser(user.getId(), user.getTelegramId(),
                Long.valueOf(1), client.getCodcliente(), "testName", "testLastName",
                "testUsername", "test@email.com");

        String filename = "pedidosTest.json";

        List<WebOrder> webOrders = orderService.readOrders(filename);
        List<OrderDto> orderDtos = new ArrayList<>();

        User user1 = null;
        for (WebOrder o : webOrders) {
            user1 = userDao.findByWebId(Long.parseLong(o.getCustomer_user()));
            if (Objects.isNull(user1)) {
                orderDtos.add(OrderConversor.webOrderToPedidoDto(o, null));
            } else {
                orderDtos.add(OrderConversor.webOrderToPedidoDto(o,
                        UserConversor.toUserDto(user1)));
            }
        }
        List<Order> orders = orderService.saveOrders(orderDtos);

        Order order = orderService.getOrder(user.getTelegramId());

        assertEquals(order, orders.get(0));
    }


    @Test
    public void getOrderNonExistantUserTest() throws UserTelegramIdAlreadyExists,
            NotEnoughInformationException,
            InstanceNotFoundException, FileException {

        assertThrows(InstanceNotFoundException.class, () -> {
            orderService.getOrder(Long.valueOf(1));
        });
    }

    @Test
    public void getOrderNonExistantOrderTest() throws UserTelegramIdAlreadyExists,
            NotEnoughInformationException,
            InstanceNotFoundException, FileException {
        User user = userService.addTelegramUser(Long.valueOf(1), "testNAme", "testLastName",
                "testUserName");

        Clientes client = createClient();

        User updatedUser = userService.updateUser(user.getId(), user.getTelegramId(),
                Long.valueOf(1), client.getCodcliente(), "testName", "testLastName",
                "testUsername", "test@email.com");

        assertThrows(InstanceNotFoundException.class, () -> {
            orderService.getOrder(Long.valueOf(1));
        });
    }


}
