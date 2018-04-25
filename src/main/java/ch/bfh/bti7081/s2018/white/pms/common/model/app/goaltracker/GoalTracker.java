package ch.bfh.bti7081.s2018.white.pms.common.model.app.goaltracker;

import ch.bfh.bti7081.s2018.white.pms.common.model.app.App;

import java.util.List;
import java.util.Objects;

public class GoalTracker extends App {

    private List<Goal> goalList;

    public GoalTracker() {
    }

    public List<Goal> getGoalList() {
        return goalList;
    }

    public void setGoalList(List<Goal> goalList) {
        this.goalList = goalList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GoalTracker that = (GoalTracker) o;
        return Objects.equals(goalList, that.goalList);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), goalList);
    }

    @Override
    public String toString() {
        return "GoalTracker{" +
                "goalList=" + goalList +
                "} " + super.toString();
    }
}
