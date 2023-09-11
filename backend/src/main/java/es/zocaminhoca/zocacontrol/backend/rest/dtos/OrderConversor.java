package es.zocaminhoca.zocacontrol.backend.rest.dtos;

import es.zocaminhoca.zocacontrol.backend.model.entities.OrderLine;
import es.zocaminhoca.zocacontrol.backend.model.entities.Order;
import es.zocaminhoca.zocacontrol.backend.model.mappers.WebOrder;
import es.zocaminhoca.zocacontrol.backend.model.mappers.WebOrderLine;
import es.zocaminhoca.zocacontrol.backend.util.WeekOfYearOperations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class OrderConversor {

    public final static OrderLineTelegramDto toOrderLineTelegramDto(OrderLine orderLine) {

        return new OrderLineTelegramDto(orderLine.getProduct(), orderLine.getUnits());

    }

    public final static List<OrderLineTelegramDto> toOrderLineTelegramDtos(List<OrderLine> orderLines) {

        return orderLines.stream().map(o -> toOrderLineTelegramDto(o)).collect(Collectors.toList());

    }

    public final static OrderLineDto toOrderLineDto(OrderLine orderLine) {
        return new OrderLineDto(orderLine.getId(), orderLine.getProduct(), orderLine.getUnits(),
                orderLine.getPrice());
    }

    public final static List<OrderLineDto> toOrderLineDtos(List<OrderLine> orderLines) {

        return orderLines.stream().map(o -> toOrderLineDto(o)).collect(Collectors.toList());

    }

    public final static OrderDto toPedidoDto(Order order) {

        UserDto userDto = null;
        if (!Objects.isNull(order.getUser())) {
            userDto = UserConversor.toUserDto(order.getUser());
        }
        List<OrderLineDto> orderLineDtos = toOrderLineDtos(order.getOrderLines());

        return new OrderDto(order.getId(), order.getEmail(), userDto, order.getName(),
                order.getLastName(), order.getWebId(), orderLineDtos,
                order.getTotalPrice());

    }

    public final static List<OrderDto> toOrdersDtos(List<Order> orders) {
        return orders.stream().map(p -> toPedidoDto(p)).collect(Collectors.toList());
    }

    public final static OrderTelegramDto toPedidoTelegramDto(Order order) {

        LocalDate mondayOfWeek = WeekOfYearOperations.getFirstDayOfWeek(order.getDate());
        LocalDate sundayOfWeek = WeekOfYearOperations.getLastDayOfWeek(order.getDate());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/YY");


        return new OrderTelegramDto(mondayOfWeek.format(dateTimeFormatter),
                sundayOfWeek.format(dateTimeFormatter), order.getTotalPrice().toString(),
                toOrderLineTelegramDtos(order.getOrderLines()));

    }

    public final static OrderLineDto webOrderLineToOrderLineDto(WebOrderLine webOrderLine) {
        OrderLineDto orderLineDto = new OrderLineDto();

        orderLineDto.setItem(webOrderLine.getName());
        orderLineDto.setUnitPrice(BigDecimal.valueOf(webOrderLine.getItem_price()).setScale(2,
                RoundingMode.HALF_UP));
        orderLineDto.setUnits(webOrderLine.getQty());

        return orderLineDto;
    }

    public final static List<OrderLineDto> webOrderLinesToOrderLineDtos(List<WebOrderLine> webOrderLines) {
        return webOrderLines.stream().map(o -> webOrderLineToOrderLineDto(o)).collect(Collectors.toList());
    }

    public final static OrderDto webOrderToPedidoDto(WebOrder webOrder, UserDto userDto) {
        List<OrderLineDto> orderLineDtos = webOrderLinesToOrderLineDtos(webOrder.getProducts());
        BigDecimal totalPrice = BigDecimal.valueOf(0);

        for (OrderLineDto orderLineDto : orderLineDtos) {
            totalPrice =
                    totalPrice.add(orderLineDto.getUnitPrice().multiply(orderLineDto.getUnits()));
        }
        return new OrderDto(null, webOrder.getUser_email(), userDto,
                webOrder.getBilling_first_name(),
                webOrder.getPlain_orders_USER_billing_last_name(),
                Long.parseLong(webOrder.getCustomer_user()), orderLineDtos, totalPrice);
    }

    public final static OrderLine orderLineDtoToOrderLine(OrderLineDto orderLineDto) {

        return new OrderLine(orderLineDto.getItem(), orderLineDto.getUnits(),
                orderLineDto.getUnitPrice());

    }

    public final static List<OrderLine> orderLineDtosToOrderLines(List<OrderLineDto> orderLineDtos) {
        return orderLineDtos.stream().map(o -> orderLineDtoToOrderLine(o)).collect(Collectors.toList());
    }
}
