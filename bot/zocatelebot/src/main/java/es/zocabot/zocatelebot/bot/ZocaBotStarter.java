package es.zocabot.zocatelebot.bot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public class ZocaBotStarter implements CommandLineRunner {

    private final ZocaBot zocaBot;

    public ZocaBotStarter(ZocaBot zocaBot) {
        this.zocaBot = zocaBot;
    }

    @Override
    public void run(String... args) throws Exception {

        try{
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this.zocaBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
}
