package es.zocaminhoca.zocacontrol.backend.model.daos;

import es.zocaminhoca.zocacontrol.backend.model.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserDao extends CrudRepository<User, Long> {

    public User findByTelegramId(long telegramId);

    public User findByWebId(long webId);

    public User findByClientCodcliente(String clientId);

    public List<User> findByWebIdIsNullAndClientIsNull();

    public boolean existsByTelegramId(Long telegramId);
}