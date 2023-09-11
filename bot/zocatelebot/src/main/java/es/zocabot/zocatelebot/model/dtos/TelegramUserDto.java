package es.zocabot.zocatelebot.model.dtos;

public class TelegramUserDto {

    private long telegramId;
    private String firstName;
    private String lastName;
    private String userName;

    public TelegramUserDto(long telegramId, String firstName, String lastName, String userName) {
        this.telegramId = telegramId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
    }

    public long getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(long telegramId) {
        this.telegramId = telegramId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
