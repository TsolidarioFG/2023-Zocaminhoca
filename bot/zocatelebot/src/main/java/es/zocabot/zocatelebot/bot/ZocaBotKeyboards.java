package es.zocabot.zocatelebot.bot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public abstract class ZocaBotKeyboards {

    public static ReplyKeyboardMarkup initialKeyboard() {
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardList = new ArrayList<KeyboardRow>();

        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add("01- lista de la compra");
        keyboardList.add(keyboardRow);

        keyboardRow = new KeyboardRow();
        keyboardRow.add("02- última factura");
        keyboardList.add(keyboardRow);

        keyboardRow = new KeyboardRow();
        keyboardRow.add("03- ultima factura sin pagar");
        keyboardList.add(keyboardRow);

        keyboard.setKeyboard(keyboardList);
        keyboard.setResizeKeyboard(true);

        return keyboard;
    }

}
