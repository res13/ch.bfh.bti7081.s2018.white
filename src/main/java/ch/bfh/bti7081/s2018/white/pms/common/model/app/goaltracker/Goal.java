package ch.bfh.bti7081.s2018.white.pms.common.model.app.goaltracker;

import ch.bfh.bti7081.s2018.white.pms.common.model.PmsEntity;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.User;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
public class Goal extends PmsEntity {

    private LocalDateTime created;

    private LocalDateTime dueTo;

    @OneToOne
    private User creator;

    private String goal;

    private GoalState state;

    @ManyToOne
    private GoalTracker goalTracker;

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

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
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

    public GoalTracker getGoalTracker() {
        return goalTracker;
    }

    public void setGoalTracker(GoalTracker goalTracker) {
        this.goalTracker = goalTracker;
    }
}
