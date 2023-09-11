package es.zocaminhoca.zocacontrol.backend.model.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.zocaminhoca.zocacontrol.backend.model.daos.OrderDao;
import es.zocaminhoca.zocacontrol.backend.model.daos.UserDao;
import es.zocaminhoca.zocacontrol.backend.model.entities.Order;
import es.zocaminhoca.zocacontrol.backend.model.entities.User;
import es.zocaminhoca.zocacontrol.backend.model.exceptions.FileException;
import es.zocaminhoca.zocacontrol.backend.model.exceptions.InstanceNotFoundException;
import es.zocaminhoca.zocacontrol.backend.model.mappers.WebOrder;
import es.zocaminhoca.zocacontrol.backend.rest.dtos.OrderConversor;
import es.zocaminhoca.zocacontrol.backend.rest.dtos.OrderDto;
import es.zocaminhoca.zocacontrol.backend.util.PedidosPathConfiguration;
import es.zocaminhoca.zocacontrol.backend.util.WeekOfYearOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private PedidosPathConfiguration pedidosPathConfiguration;
    @Autowired
    private UserDao userDao;

    public List<WebOrder> readOrders(String filename) throws FileException {

        String path = pedidosPathConfiguration.getFolderPath() + filename;
        ObjectMapper mapper = new ObjectMapper();

        try {
            List<WebOrder> webOrderList = Arrays.asList(mapper.readValue(Paths.get(path).toFile()
                    , WebOrder[].class));

            return webOrderList;

        } catch (IOException e) {
            e.printStackTrace();
            throw new FileException(path, "Error leyendo el archivo" + path);
        }
    }

    public Order getOrder(long telegramId) throws InstanceNotFoundException {

        User user = userDao.findByTelegramId(telegramId);
        if (Objects.isNull(user)) {
            throw new InstanceNotFoundException("TelegramUser", String.valueOf(telegramId));
        }

        Order order = orderDao.findTopByUserIdOrderByDateDesc(user.getId());
        if (Objects.isNull(order) || order.getDate().isBefore(LocalDate.now().minusDays(7))) {
            throw new InstanceNotFoundException("Order", "latestPedido");
        }

        return order;
    }

    public List<Order> saveOrders(List<OrderDto> orderDtos) {
        List<Order> orders = new ArrayList<>();
        for (OrderDto p : orderDtos) {
            Order order = new Order();
            order.setEmail(p.getEmail());
            order.setName(p.getName());
            order.setLastName(p.getLastName());
            if (!Objects.isNull(p.getUserDto())) {
                order.setUser(userDao.findById(p.getUserDto().getId()).get());
            }
            order.setWebId(p.getWebId());
            order.setDate(LocalDate.now());
            order.setWeekOfYear(WeekOfYearOperations.getWeekOfYear(order.getDate()));
            order.setOrderLines(OrderConversor.orderLineDtosToOrderLines(p.getOrderLines()));
            order.setTotalPrice(p.getTotalPrice());

            orderDao.save(order);
            orders.add(order);
        }

        return orders;

    }

    public List<Order> getWeekOrders() {

        LocalDate monday = WeekOfYearOperations.getFirstDayOfWeek(LocalDate.now());
        LocalDate sunday = WeekOfYearOperations.getLastDayOfWeek(LocalDate.now());

        List<Order> orders = orderDao.findByDateAfterAndDateBefore(monday, sunday);

        return orders;
    }


}
