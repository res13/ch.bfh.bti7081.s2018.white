package ch.bfh.bti7081.s2018.white.pms.common.model.app.goaltracker;

import ch.bfh.bti7081.s2018.white.pms.common.i18n.MessageHandler;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.App;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "goaltracker")
public class GoalTracker extends App {

    @OneToMany
    private List<Goal> goalList;

    public GoalTracker() {
        name = MessageHandler.GOAL_TRACKER_NAME;
    }

}
