package ch.bfh.bti7081.s2018.white.pms.common.model.app;

import ch.bfh.bti7081.s2018.white.pms.common.model.PmsType;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.Role;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.User;

import java.util.List;
import java.util.Objects;

public abstract class App extends PmsType {

    private String name;

    private List<Role> readRole;

    private List<Role> updateRole;

    private List<User> userList;

    public App() {
    }

    public String getName() {
        return name;
    }

    public List<Role> getReadRole() {
        return readRole;
    }

    public List<Role> getUpdateRole() {
        return updateRole;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        App app = (App) o;
        return Objects.equals(name, app.name) &&
                Objects.equals(readRole, app.readRole) &&
                Objects.equals(updateRole, app.updateRole) &&
                Objects.equals(userList, app.userList);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), name, readRole, updateRole, userList);
    }

    @Override
    public String toString() {
        return "App{" +
                "name='" + name + '\'' +
                ", readRole=" + readRole +
                ", updateRole=" + updateRole +
                ", userList=" + userList +
                "} " + super.toString();
    }
}
