package es.zocaminhoca.zocacontrol.backend.model.services;

import es.zocaminhoca.zocacontrol.backend.model.daos.ClientesDao;
import es.zocaminhoca.zocacontrol.backend.model.daos.UserDao;
import es.zocaminhoca.zocacontrol.backend.model.entities.Clientes;
import es.zocaminhoca.zocacontrol.backend.model.entities.User;
import es.zocaminhoca.zocacontrol.backend.model.exceptions.InstanceNotFoundException;
import es.zocaminhoca.zocacontrol.backend.model.exceptions.NotEnoughInformationException;
import es.zocaminhoca.zocacontrol.backend.model.exceptions.UserTelegramIdAlreadyExists;
import es.zocaminhoca.zocacontrol.backend.util.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ClientesDao clientesDao;

    public User addTelegramUser(long telegramId, String firstName, String lastName,
                                String userName) throws UserTelegramIdAlreadyExists {

        User user = userDao.findByTelegramId(telegramId);
        if (!Objects.isNull(user)) {
            throw new UserTelegramIdAlreadyExists(String.valueOf(telegramId));
        }

        user = new User();
        user.setTelegramId(telegramId);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUserName(userName);

        userDao.save(user);
        return user;

    }

    public List<User> findNewTelegramUsers() {

        List<User> newUsers = userDao.findByWebIdIsNullAndClientIsNull();
        return newUsers;

    }

    public User updateUser(Long userId, Long telegramId, Long webId, String clientId,
                           String firstName, String lastName, String userName, String webEmail) throws InstanceNotFoundException,
            NotEnoughInformationException {

        if (Objects.isNull(userId) || Objects.isNull(telegramId) || Objects.isNull(webId) || Objects.isNull(clientId) || Objects.isNull(webEmail)) {
            throw new NotEnoughInformationException();
        }

        Optional<User> userOpt = userDao.findById(userId);
        if (userOpt.isEmpty()) {
            throw new InstanceNotFoundException(User.class.getSimpleName(), String.valueOf(userId));
        }

        Optional<Clientes> clientOpt = clientesDao.findById(clientId);
        if (clientOpt.isEmpty()) {
            throw new InstanceNotFoundException(Clientes.class.getSimpleName(), clientId);
        }

        User user = new User(userId, telegramId, webId, clientOpt.get(), firstName, lastName,
                userName, webEmail);

        userDao.save(user);

        return user;

    }

    public List<Clientes> findClientsByName(String queryName) {

        List<Clientes> clients = clientesDao.findByName(queryName);

        return clients;

    }

    public UserStatus isUserConnected(Long telegramId) {

        User user = userDao.findByTelegramId(telegramId);
        if (Objects.isNull(user)) {
            return UserStatus.NOT_CREATED;
        }

        if (Objects.isNull(user.getClient())) {
            return UserStatus.NOT_CONNECTED;
        }

        return UserStatus.CONECTED;

    }


}
