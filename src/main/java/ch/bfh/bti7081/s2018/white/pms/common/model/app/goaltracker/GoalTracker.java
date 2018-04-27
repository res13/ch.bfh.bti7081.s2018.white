package ch.bfh.bti7081.s2018.white.pms.common.model.app.goaltracker;

import ch.bfh.bti7081.s2018.white.pms.common.i18n.MessageHandler;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.App;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table
public class GoalTracker extends App {

    @OneToMany
    private List<Goal> goalList;

    public GoalTracker() {
    }

    public List<Goal> getGoalList() {
        return goalList;
    }

    public void setGoalList(List<Goal> goalList) {
        this.goalList = goalList;
    }

}
