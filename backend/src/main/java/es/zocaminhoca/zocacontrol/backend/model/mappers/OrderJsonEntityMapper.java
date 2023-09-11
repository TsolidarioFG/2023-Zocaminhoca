package es.zocaminhoca.zocacontrol.backend.model.mappers;

import es.zocaminhoca.zocacontrol.backend.model.daos.UserDao;
import es.zocaminhoca.zocacontrol.backend.model.entities.Order;
import es.zocaminhoca.zocacontrol.backend.model.entities.OrderLine;
import es.zocaminhoca.zocacontrol.backend.model.entities.User;
import es.zocaminhoca.zocacontrol.backend.util.WeekOfYearOperations;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class OrderJsonEntityMapper {

    @Autowired
    UserDao userDao;

    public static Order orderMap(WebOrder webOrder, User user) {

        Order order = new Order();
        BigDecimal totalPrice = BigDecimal.valueOf(0);
        List<OrderLine> orderLines = new ArrayList<OrderLine>();

        order.setEmail(webOrder.getUser_email());
        order.setName(webOrder.getBilling_first_name());
        order.setLastName(webOrder.getPlain_orders_USER_billing_last_name());
        order.setUser(user);
        order.setWebId(Long.parseLong(webOrder.getCustomer_user()));
        order.setDate(LocalDate.now());
        order.setWeekOfYear(WeekOfYearOperations.getWeekOfYear(order.getDate()));

        for (WebOrderLine webOrderLine : webOrder.getProducts()) {

            OrderLine orderLine = orderLineMap(webOrderLine);
            orderLines.add(orderLine);
            totalPrice = totalPrice.add(orderLine.getPrice());

        }

        order.setOrderLines(orderLines);
        order.setTotalPrice(totalPrice);

        return order;
    }

    private static OrderLine orderLineMap(WebOrderLine webOrderLine) {

        OrderLine orderLine = new OrderLine();

        orderLine.setProduct(webOrderLine.getName());
        orderLine.setPrice(BigDecimal.valueOf(webOrderLine.getItem_price()).setScale(2,
                RoundingMode.HALF_UP));
        orderLine.setUnits(webOrderLine.getQty());

        return orderLine;
    }

}
