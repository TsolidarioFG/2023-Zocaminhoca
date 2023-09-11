package es.zocabot.zocatelebot.bot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.logging.Logger;

import es.zocabot.zocatelebot.config.ZocaBotConfig;
import es.zocabot.zocatelebot.model.ZocaApiConsumer;
import es.zocabot.zocatelebot.model.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;

@Component
public class ZocaBot extends TelegramLongPollingBot {

    @Autowired
    private ZocaApiConsumer zocaApiConsumer;
    private final ZocaBotConfig zocaBotConfig;

    public ZocaBot(ZocaBotConfig zocaBotConfig) {
        this.zocaBotConfig = zocaBotConfig;
    }

    private static final Logger logger = Logger.getLogger(ZocaBot.class.getName());

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()) {

            final String messageTextReceived = update.getMessage().getText();
            final Long chatId = update.getMessage().getChatId();
            User user = update.getMessage().getFrom();
            final Long userId = user.getId();

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss dd-MM-yyyy");
            logger.info("["+ LocalDateTime.now().format(dateTimeFormatter)+"] - Recibido mensaje de " + user.getFirstName() + " con id " + user.getId().toString() + ": " + update.getMessage().getText());

            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setReplyMarkup(ZocaBotKeyboards.initialKeyboard());

            UserStatusDto userStatusDto = zocaApiConsumer.isUserConnected(userId);

            try {
                if (userStatusDto.getUserStatus().equals("CONNECTED")) {
                    switch (messageTextReceived.substring(0, 3)) {
                        case "01-":
                            OrderDto orderDto = zocaApiConsumer.getUserPedido(userId);
                            if (!Objects.isNull(orderDto)) {
                                message.setText(ZocaBotMessages.orderMessage(orderDto));
                            } else {
                                message.setText(ZocaBotMessages.orderNotFoundMessage());
                            }

                            break;
                        case "02-":
                            PathDto path = zocaApiConsumer.getLastUserBill(userId);
                            if (!Objects.isNull(path)) {
                                message.setText(ZocaBotMessages.lastBillMessage());
                                SendDocument sendDocument = new SendDocument();
                                InputFile billPdf = new InputFile(new File(path.getPath()));
                                sendDocument.setChatId(chatId);
                                sendDocument.setDocument(billPdf);
                                try {
                                    execute(sendDocument);
                                } catch (TelegramApiException e) {
                                    message.setText(ZocaBotMessages.operationProblems());
                                    e.printStackTrace();
                                }
                            } else {
                                message.setText(ZocaBotMessages.lastBillNotFoundMessage());
                            }
                            break;
                        case "03-":
                            ReceiptDto receipt = zocaApiConsumer.getLastUserUnpaidBill(userId);
                            if (!Objects.isNull(receipt)) {
                                message.setText(ZocaBotMessages.lastUnpaidBillMessage(receipt));
                            } else {
                                message.setText(ZocaBotMessages.lastUnpaidBillNotFoundMessage());
                            }
                            break;
                        default:
                            message.setText(ZocaBotMessages.unknownOperation());
                            break;
                    }

                } else if (userStatusDto.getUserStatus().equals("NOT_CONNECTED")) {
                    message.setText(ZocaBotMessages.userNotConnectedMessage(user.getFirstName()));
                } else {
                    TelegramUserDto telegramUserDto = new TelegramUserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getUserName());
                    zocaApiConsumer.newTelegramUser(telegramUserDto);
                    message.setText(ZocaBotMessages.newUserMessage(user.getFirstName()));
                }
            } catch (RuntimeException e) {
                message.setText(ZocaBotMessages.serverError());
            }
            try {
                logger.info("["+ LocalDateTime.now().format(dateTimeFormatter)+"] - Enviado mensaje a " + user.getFirstName() + " con id " + user.getId().toString() + ": " + message.getText());
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return zocaBotConfig.getName();
    }

    @Override
    public String getBotToken() {
        return zocaBotConfig.getToken();
    }
}
