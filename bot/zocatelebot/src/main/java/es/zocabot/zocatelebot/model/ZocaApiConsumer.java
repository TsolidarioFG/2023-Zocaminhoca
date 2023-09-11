package es.zocabot.zocatelebot.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.zocabot.zocatelebot.config.ModelConnectionConfiguration;
import es.zocabot.zocatelebot.model.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Service
public class ZocaApiConsumer {

    @Autowired
    private ModelConnectionConfiguration modelConnectionConfiguration;

    private RestTemplate buildRestTemplate() {

        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        RestTemplate restTemplate = restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(10))
                .setReadTimeout(Duration.ofSeconds(10))
                .build();
        return restTemplate;

    }

    public OrderDto getUserPedido(Long telegramId) {

        String uri = modelConnectionConfiguration.getUri() + "/pedidos/user/" + telegramId;
        RestTemplate restTemplate = buildRestTemplate();
        try {
            OrderDto orderDto = restTemplate.getForObject(uri, OrderDto.class);
            return orderDto;

        } catch (HttpClientErrorException e) {
            if(e.getStatusCode().is5xxServerError()) {
                throw new RuntimeException();
            }
            return null;
        }

    }

    public UserStatusDto isUserConnected(Long telegramId) {

        String uri = modelConnectionConfiguration.getUri() + "/user/telegram/exists/" + telegramId;

        RestTemplate restTemplate = buildRestTemplate();
        UserStatusDto isUserConnected = restTemplate.getForObject(uri, UserStatusDto.class);

        return isUserConnected;

    }

    public UserDto newTelegramUser(TelegramUserDto telegramUserDto) {

        String uri = modelConnectionConfiguration.getUri() + "/user/telegram";
        RestTemplate restTemplate = buildRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonStr = objectMapper.writeValueAsString(telegramUserDto);
            HttpEntity<String> request = new HttpEntity<String>(jsonStr, headers);
            UserDto userDto = restTemplate.postForObject(uri, request, UserDto.class);
            return userDto;

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public PathDto getLastUserBill(Long telegramId) {

        String uri = modelConnectionConfiguration.getUri() + "/bills/user/" + telegramId.toString() + "/pdf";
        RestTemplate restTemplate = buildRestTemplate();

        try {
            PathDto pdfPath = restTemplate.getForObject(uri, PathDto.class);
            return pdfPath;
        } catch (HttpClientErrorException e) {
            if(e.getStatusCode().is5xxServerError()) {
                throw new RuntimeException();
            }
            return null;
        }

    }

    public ReceiptDto getLastUserUnpaidBill(Long telegramId) {

        String uri = modelConnectionConfiguration.getUri() + "/bills/user/" + telegramId.toString() + "/unpaid";
        RestTemplate restTemplate = buildRestTemplate();

        try {
            ReceiptDto receiptDto = restTemplate.getForObject(uri, ReceiptDto.class);
            return receiptDto;
        } catch (HttpClientErrorException e) {
            if(e.getStatusCode().is5xxServerError()) {
                throw new RuntimeException();
            }
            return null;
        }
    }
}
