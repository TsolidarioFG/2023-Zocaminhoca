package es.zocabot.zocatelebot.model.dtos;

public class UserDto {

    private Long id;
    private Long telegramId;
    private Long webId;
    private String clientId;
    private String firstName;
    private String lastName;
    private String userName;
    private String webEmail;

    public UserDto() {
    }

    public UserDto(Long id, Long telegramId, Long webId, String clientId, String firstName,
                   String lastName, String userName, String webEmail) {
        this.id = id;
        this.telegramId = telegramId;
        this.webId = webId;
        this.clientId = clientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.webEmail = webEmail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(Long telegramId) {
        this.telegramId = telegramId;
    }

    public Long getWebId() {
        return webId;
    }

    public void setWebId(Long webId) {
        this.webId = webId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
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

    public String getWebEmail() {
        return webEmail;
    }

    public void setWebEmail(String webEmail) {
        this.webEmail = webEmail;
    }
}
