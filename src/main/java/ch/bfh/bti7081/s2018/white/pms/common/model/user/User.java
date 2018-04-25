package ch.bfh.bti7081.s2018.white.pms.common.model.user;

import ch.bfh.bti7081.s2018.white.pms.common.model.PmsType;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.App;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.request.Request;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class User extends PmsType {

    private String name;

    private String surname;

    private LocalDate dateOfBirth;

    private String email;

    private String passwordHash;

    private Role role;

    private List<App> appList;

    private List<User> userList;

    private List<Request> openRequestList;

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<App> getAppList() {
        return appList;
    }

    public void setAppList(List<App> appList) {
        this.appList = appList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<Request> getOpenRequestList() {
        return openRequestList;
    }

    public void setOpenRequestList(List<Request> openRequestList) {
        this.openRequestList = openRequestList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) &&
                Objects.equals(surname, user.surname) &&
                Objects.equals(dateOfBirth, user.dateOfBirth) &&
                Objects.equals(email, user.email) &&
                Objects.equals(passwordHash, user.passwordHash) &&
                Objects.equals(role, user.role) &&
                Objects.equals(appList, user.appList) &&
                Objects.equals(userList, user.userList) &&
                Objects.equals(openRequestList, user.openRequestList);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), name, surname, dateOfBirth, email, passwordHash, role, appList, userList, openRequestList);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", email='" + email + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", role=" + role +
                ", appList=" + appList +
                ", userList=" + userList +
                ", openRequestList=" + openRequestList +
                "} " + super.toString();
    }
}
