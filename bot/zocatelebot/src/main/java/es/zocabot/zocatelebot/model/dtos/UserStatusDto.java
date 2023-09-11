package es.zocabot.zocatelebot.model.dtos;

public class UserStatusDto {

    private String userStatus;

    public UserStatusDto() {
    }

    public UserStatusDto(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }
}
