package es.zocaminhoca.zocacontrol.backend.rest.dtos;

import es.zocaminhoca.zocacontrol.backend.model.entities.Clientes;

import java.util.List;
import java.util.stream.Collectors;

public class ClientConversor {

    public final static ClientDto toClientDto(Clientes client) {

        return new ClientDto(client.getCodcliente(), client.getEmail(), client.getNombre(),
                client.getNombrecomercial());

    }

    public final static List<ClientDto> toClientDtos(List<Clientes> clients) {

        return clients.stream().map(c -> toClientDto(c)).collect(Collectors.toList());
    }
}
