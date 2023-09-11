package es.zocaminhoca.zocacontrol.backend.rest.dtos;

import es.zocaminhoca.zocacontrol.backend.model.entities.Clientes;
import es.zocaminhoca.zocacontrol.backend.model.entities.User;
import es.zocaminhoca.zocacontrol.backend.util.UserStatus;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserConversor {

    public final static UserDto toUserDto(User user) {

        String codclient;
        if (Objects.isNull(user.getClient())) {
            codclient = null;
        } else {
            codclient = user.getClient().getCodcliente();
        }

        return new UserDto(user.getId(), user.getTelegramId(), user.getWebId(),
                codclient, user.getFirstName(), user.getLastName(),
                user.getUserName(),
                user.getWebEmail());
    }

    public final static List<UserDto> toUserDtos(List<User> users) {

        return users.stream().map(u -> toUserDto(u)).collect(Collectors.toList());

    }

    public final static UserStatusDto toUserStatusDto(UserStatus userStatus) {

        if (userStatus == UserStatus.CONECTED) {
            return new UserStatusDto("CONNECTED");
        }

        if (userStatus == UserStatus.NOT_CONNECTED) {
            return new UserStatusDto("NOT_CONNECTED");
        }

        return new UserStatusDto("NOT_CREATED");

    }

}
