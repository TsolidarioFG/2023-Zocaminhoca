package es.zocaminhoca.zocacontrol.backend.model.daos;

import es.zocaminhoca.zocacontrol.backend.model.entities.Order;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface OrderDao extends CrudRepository<Order, Long> {

    public Order findTopByUserIdOrderByDateDesc(long userId);

    public List<Order> findByDateAfterAndDateBefore(LocalDate monday, LocalDate sunday);
}
