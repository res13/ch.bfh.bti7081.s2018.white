package ch.bfh.bti7081.s2018.white.pms.common.model.app.goaltracker;

import ch.bfh.bti7081.s2018.white.pms.common.model.PmsType;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.User;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
public class Goal extends PmsType {

    private LocalDateTime created;

    private LocalDateTime dueTo;

    @OneToOne
    private User creator;

    private String goal;

    private GoalState state;

    @ManyToOne
    private GoalTracker goalTracker;

}
