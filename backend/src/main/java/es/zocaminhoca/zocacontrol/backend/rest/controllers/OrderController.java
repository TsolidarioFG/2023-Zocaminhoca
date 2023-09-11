package es.zocaminhoca.zocacontrol.backend.rest.controllers;

import es.zocaminhoca.zocacontrol.backend.model.daos.UserDao;
import es.zocaminhoca.zocacontrol.backend.model.entities.User;
import es.zocaminhoca.zocacontrol.backend.model.exceptions.FileException;
import es.zocaminhoca.zocacontrol.backend.model.exceptions.InstanceNotFoundException;
import es.zocaminhoca.zocacontrol.backend.model.mappers.WebOrder;
import es.zocaminhoca.zocacontrol.backend.model.services.OrderService;
import es.zocaminhoca.zocacontrol.backend.rest.dtos.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@RestController
@RequestMapping("/pedidos")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserDao userDao;

    @ExceptionHandler(InstanceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorsDto handleInstanceNotFoundExepiton(InstanceNotFoundException exception,
                                                    Locale locale) {

        String errorMessage =
                "No se encuentra el " + exception.getInstanceClass() + " con id " + exception.getKey();

        return new ErrorsDto(errorMessage, exception.getKey(), exception.getInstanceClass());
    }

    @ExceptionHandler(FileException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorsDto handleFileException(FileException exception, Locale locale) {
        String errorMessage = exception.getMessage();
        return new ErrorsDto(errorMessage, exception.getPath(), exception.getPath());
    }

    @Operation(
            summary = "Devuelve el último pedido realizado por una socia según el usuario de " +
                    "telegram"
    )
    @GetMapping("/user/{telegramId}")
    public OrderTelegramDto getUserOrders(@PathVariable long telegramId) throws InstanceNotFoundException {

        return OrderConversor.toPedidoTelegramDto(orderService.getOrder(telegramId));

    }

    @Operation(
            summary = "Devuelve los pedidos guardados de la semana actual"
    )
    @GetMapping("/week")
    public List<OrderDto> getWeekOrders() {
        return OrderConversor.toOrdersDtos(orderService.getWeekOrders());
    }

    @Operation(
            summary = "Lee del fichero especificado los pedidos de la semana y los devuelve"
    )
    @GetMapping("/{filename}")
    public List<OrderDto> readOrders(@PathVariable String filename) throws FileException {

        List<WebOrder> webOrders = orderService.readOrders(filename);
        List<OrderDto> orderDtos = new ArrayList<OrderDto>();

        for (WebOrder o : webOrders) {
            User user = userDao.findByWebId(Long.parseLong(o.getCustomer_user()));
            if (Objects.isNull(user)) {
                orderDtos.add(OrderConversor.webOrderToPedidoDto(o, null));
            } else {
                orderDtos.add(OrderConversor.webOrderToPedidoDto(o, UserConversor.toUserDto(user)));
            }
        }

        return orderDtos;
    }

    @Operation(
            summary = "Guarda los pedidos"
    )
    @PostMapping("/save")
    public List<OrderDto> saveOrders(@RequestBody List<OrderDto> orderDtos) {

        return OrderConversor.toOrdersDtos(orderService.saveOrders(orderDtos));

    }


}
