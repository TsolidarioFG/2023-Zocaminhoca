package es.zocaminhoca.zocacontrol.backend.rest.controllers;

import es.zocaminhoca.zocacontrol.backend.model.entities.Clientes;
import es.zocaminhoca.zocacontrol.backend.model.entities.User;
import es.zocaminhoca.zocacontrol.backend.model.exceptions.InstanceNotFoundException;
import es.zocaminhoca.zocacontrol.backend.model.exceptions.NotEnoughInformationException;
import es.zocaminhoca.zocacontrol.backend.model.exceptions.UserTelegramIdAlreadyExists;
import es.zocaminhoca.zocacontrol.backend.model.services.UserService;
import es.zocaminhoca.zocacontrol.backend.rest.dtos.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ExceptionHandler(InstanceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorsDto handleInstanceNotFoundExepiton(InstanceNotFoundException exception,
                                                    Locale locale) {

        String errorMessage =
                "No se encuentra el " + exception.getInstanceClass() + " con id " + exception.getKey();

        return new ErrorsDto(errorMessage, exception.getKey(), exception.getInstanceClass());
    }

    @ExceptionHandler(UserTelegramIdAlreadyExists.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorsDto handleUserTelegramIdAlreadyExists(UserTelegramIdAlreadyExists exception,
                                                       Locale locale) {

        String errorMessage =
                "El user con telegram id " + exception.getKey() + " ya existe ";

        return new ErrorsDto(errorMessage, exception.getKey(), User.class.getSimpleName());
    }

    @ExceptionHandler(NotEnoughInformationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorsDto handleNotEnoughInformation(NotEnoughInformationException exception,
                                                Locale locale) {
        String errorMessage =
                "Falta información para completar esta petición, comprueba los datos";

        return new ErrorsDto(errorMessage, null, null);
    }

    @Operation(
            summary = "Devuelve los nuevos usuarios creados por telegram sin conectar con los de " +
                    "la web"
    )
    @GetMapping("/new")
    public List<UserDto> getNewTelegramUsers() {

        return UserConversor.toUserDtos(userService.findNewTelegramUsers());

    }

    @Operation(
            summary = "Devuelve el estado de un usuario de telegram"
    )

    @GetMapping("/telegram/exists/{telegramId}")
    public UserStatusDto isUserConnected(@PathVariable Long telegramId) {

        return UserConversor.toUserStatusDto(userService.isUserConnected(telegramId));

    }

    @Operation(
            summary = "Crea un usuario nuevo de telegram con los datos de quien habló al bot"
    )
    @PostMapping("/telegram")
    public UserDto newTelegramUser(@RequestBody TelegramUserDto telegramUserDto) throws UserTelegramIdAlreadyExists {

        return UserConversor.toUserDto(userService.addTelegramUser(telegramUserDto.getTelegramId(),
                telegramUserDto.getFirstName(), telegramUserDto.getLastName(),
                telegramUserDto.getUserName()));
    }

    @Operation(
            summary = "Conecta un usuario creado por telegram con los datos de socia gestionados " +
                    "por la cooperativa"
    )
    @PostMapping("")
    public UserDto updateUser(@RequestBody UserDto userDto) throws NotEnoughInformationException,
            InstanceNotFoundException {

        return UserConversor.toUserDto(userService.updateUser(userDto.getId(),
                userDto.getTelegramId(), userDto.getWebId(), userDto.getClientId(),
                userDto.getFirstName(), userDto.getLastName(), userDto.getUserName(),
                userDto.getWebEmail()));

    }

    @Operation(
            summary = "Busca un cliente por nombre"
    )
    @GetMapping("/client")
    public List<ClientDto> findClientByName(@RequestParam(required = false) String queryName) {

        if (Objects.isNull(queryName)) {
            queryName = "";
        }

        List<Clientes> clients = userService.findClientsByName(queryName);

        return ClientConversor.toClientDtos(clients);

    }


}
