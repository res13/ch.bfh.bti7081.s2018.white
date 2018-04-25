package ch.bfh.bti7081.s2018.white.pms.common.model.app.goaltracker;

import ch.bfh.bti7081.s2018.white.pms.common.model.PmsType;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.User;

import java.time.LocalDateTime;
import java.util.Objects;

public class Goal extends PmsType {

    private LocalDateTime created;

    private LocalDateTime dueTo;

    private User assigned;

    private String goal;

    private GoalState state;

    public Goal() {
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getDueTo() {
        return dueTo;
    }

    public void setDueTo(LocalDateTime dueTo) {
        this.dueTo = dueTo;
    }

    public User getAssigned() {
        return assigned;
    }

    public void setAssigned(User assigned) {
        this.assigned = assigned;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public GoalState getState() {
        return state;
    }

    public void setState(GoalState state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Goal goal1 = (Goal) o;
        return Objects.equals(created, goal1.created) &&
                Objects.equals(dueTo, goal1.dueTo) &&
                Objects.equals(assigned, goal1.assigned) &&
                Objects.equals(goal, goal1.goal) &&
                state == goal1.state;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), created, dueTo, assigned, goal, state);
    }

    @Override
    public String toString() {
        return "Goal{" +
                "created=" + created +
                ", dueTo=" + dueTo +
                ", assigned=" + assigned +
                ", goal='" + goal + '\'' +
                ", state=" + state +
                "} " + super.toString();
    }
}
