package es.zocaminhoca.zocacontrol.backend.model.services;

import es.zocaminhoca.zocacontrol.backend.model.daos.ClientesDao;
import es.zocaminhoca.zocacontrol.backend.model.daos.UserDao;
import es.zocaminhoca.zocacontrol.backend.model.entities.Clientes;
import es.zocaminhoca.zocacontrol.backend.model.entities.User;
import es.zocaminhoca.zocacontrol.backend.model.exceptions.InstanceNotFoundException;
import es.zocaminhoca.zocacontrol.backend.model.exceptions.NotEnoughInformationException;
import es.zocaminhoca.zocacontrol.backend.model.exceptions.UserTelegramIdAlreadyExists;
import es.zocaminhoca.zocacontrol.backend.util.UserStatus;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Rollback
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ClientesDao clientesDao;

    private Clientes createClient() {
        Clientes client = new Clientes("000001", "test@test.com", "test", "test", null);
        clientesDao.save(client);
        return client;
    }

    @Test
    public void addTelegramUserTest() throws UserTelegramIdAlreadyExists {

        User user = userService.addTelegramUser(Long.valueOf(1), "testNAme", "testLastName",
                "testUserName");
        User foundUser = userDao.findByTelegramId(Long.valueOf(1));

        assertEquals(user, foundUser);
    }

    @Test
    public void addTelegramUserAlreadyExistsTest() throws UserTelegramIdAlreadyExists {

        User user = userService.addTelegramUser(Long.valueOf(1), "testNAme", "testLastName",
                "testUserName");


        assertThrows(UserTelegramIdAlreadyExists.class, () -> {
            User user2 = userService.addTelegramUser(Long.valueOf(1), "testNAme", "testLastName",
                    "testUserName");
        });
    }

    @Test
    public void findNewTelegramUsersTest() throws UserTelegramIdAlreadyExists {

        User user1 = userService.addTelegramUser(Long.valueOf(1), "testNAme", "testLastName",
                "testUserName");
        User user2 = userService.addTelegramUser(Long.valueOf(2), "testNAme", "testLastName",
                "testUserName");
        User user3 = userService.addTelegramUser(Long.valueOf(3), "testNAme", "testLastName",
                "testUserName");

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);

        List<User> foundUsers = userService.findNewTelegramUsers();

        for (int i = 0; i < users.size(); i++) {
            assertEquals(users.get(i), foundUsers.get(i));
        }
    }

    @Test
    public void updateUserTest() throws UserTelegramIdAlreadyExists,
            NotEnoughInformationException, InstanceNotFoundException {

        User user1 = userService.addTelegramUser(Long.valueOf(1), "testNAme", "testLastName",
                "testUserName");

        Clientes client = createClient();

        User updatedUser = userService.updateUser(user1.getId(), user1.getTelegramId(),
                Long.valueOf(1), client.getCodcliente(), "testName", "testLastName",
                "testUsername", "test@email.com");

        User foundUser = userDao.findByTelegramId(Long.valueOf(1));

        assertEquals(updatedUser, foundUser);
    }

    @Test
    public void updateUserNotFoundTest() throws UserTelegramIdAlreadyExists,
            NotEnoughInformationException, InstanceNotFoundException {

        assertThrows(InstanceNotFoundException.class, () -> {
            User updatedUser = userService.updateUser(Long.valueOf(2), Long.valueOf(2),
                    Long.valueOf(2), "000111", "testName", "testLastName",
                    "testUsername", "test@email.com");
        });

    }

    @Test
    public void updateUserNotEnoughInformationClientIdTest() throws UserTelegramIdAlreadyExists,
            NotEnoughInformationException, InstanceNotFoundException {

        assertThrows(NotEnoughInformationException.class, () -> {
            User updatedUser = userService.updateUser(Long.valueOf(2), Long.valueOf(2),
                    Long.valueOf(2), null, "testName", "testLastName",
                    "testUsername", "test@email.com");
        });

    }

    @Test
    public void updateUserNotEnoughInformationTelegramIdTest() throws UserTelegramIdAlreadyExists,
            NotEnoughInformationException, InstanceNotFoundException {

        assertThrows(NotEnoughInformationException.class, () -> {
            User updatedUser = userService.updateUser(Long.valueOf(2), null,
                    Long.valueOf(2), null, "testName", "testLastName",
                    "testUsername", "test@email.com");
        });

    }

    @Test
    public void updateUserTestNotEnoughInformationExceptionTest() throws UserTelegramIdAlreadyExists {
        User user1 = userService.addTelegramUser(Long.valueOf(1), "testNAme", "testLastName",
                "testUserName");

        Clientes client = createClient();

        assertThrows(NotEnoughInformationException.class, () -> {
            User updatedUser = userService.updateUser(user1.getId(), user1.getTelegramId(),
                    null, client.getCodcliente(), "testName", "testLastName",
                    "testUsername", "test@email.com");
        });
    }

    @Test
    public void updateUserTestInstanceNotFoundExceptionTest() throws UserTelegramIdAlreadyExists {
        User user1 = userService.addTelegramUser(Long.valueOf(1), "testNAme", "testLastName",
                "testUserName");

        assertThrows(InstanceNotFoundException.class, () -> {
            User updatedUser = userService.updateUser(user1.getId(), user1.getTelegramId(),
                    Long.valueOf(1), "000123", "testName", "testLastName",
                    "testUsername", "test@email.com");
        });
    }

    @Test
    public void findclientesByNameTest() {

        Clientes client = createClient();
        List<Clientes> foundClients = userService.findClientsByName("TES");

        assertEquals(client, foundClients.get(0));
    }

    @Test
    public void isUserConnectedTest() throws UserTelegramIdAlreadyExists,
            NotEnoughInformationException, InstanceNotFoundException {

        User user = userService.addTelegramUser(Long.valueOf(1), "testNAme", "testLastName",
                "testUserName");

        User user1 = userService.addTelegramUser(Long.valueOf(2), "testNAme", "testLastName",
                "testUserName");

        Clientes client = createClient();

        User updatedUser = userService.updateUser(user1.getId(), user1.getTelegramId(),
                Long.valueOf(1), client.getCodcliente(), "testName", "testLastName",
                "testUsername", "test@email.com");

        User foundUser = userDao.findByTelegramId(Long.valueOf(1));

        assertEquals(userService.isUserConnected(user.getTelegramId()), UserStatus.NOT_CONNECTED);
        assertEquals(userService.isUserConnected(user1.getTelegramId()), UserStatus.CONECTED);
        assertEquals(userService.isUserConnected(Long.valueOf(12)), UserStatus.NOT_CREATED);

    }
}
