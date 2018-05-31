package ch.bfh.bti7081.s2018.white.pms.ui.main;

import ch.bfh.bti7081.s2018.white.pms.common.i18n.MessageHandler;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.diary.DiaryEntry;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.goaltracker.Goal;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.goaltracker.GoalState;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.Patient;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.Relative;
import ch.bfh.bti7081.s2018.white.pms.services.impl.DiaryEntryServiceImpl;
import ch.bfh.bti7081.s2018.white.pms.services.impl.GoalServiceImpl;
import ch.bfh.bti7081.s2018.white.pms.ui.app.diary.PatientDiaryOverview;
import ch.bfh.bti7081.s2018.white.pms.ui.app.diary.RelativeDiaryOverview;
import ch.bfh.bti7081.s2018.white.pms.ui.app.goaltracker.GoaltrackerOverview;
import com.vaadin.ui.*;

import java.util.List;

public class RelativeDashboardView {

    private Relative relative;

    public RelativeDashboardView(Relative relative) {
        this.relative = relative;
    }

    public GridLayout getGridLayout() {
        GridLayout gridLayout = new GridLayout(1, 3);
        gridLayout.addComponent(new Label(MessageHandler.WELCOME + relative.getFullName()), 0, 0);

        List<Patient> patientList = relative.getPatientList();
        gridLayout.addComponent(new Label(MessageHandler.COUNT_PATIENT + patientList.size()), 0, 1);
        VerticalLayout vlRel = new VerticalLayout();

        for (Patient patient : patientList) {
            vlRel.addComponent(new Label(MessageHandler.OVERVIEW_FOR + patient.getFullName()));
            VerticalLayout vlPat = new VerticalLayout();

            VerticalLayout hlDiaryPatient = new VerticalLayout();
            hlDiaryPatient.addComponentsAndExpand(new Label(MessageHandler.MY_DIARY_ENTRIES));
            List<DiaryEntry> relativeDiaryEntriesForUser = new DiaryEntryServiceImpl().getRelativeDiaryEntriesForUser(relative);
            relativeDiaryEntriesForUser.stream().limit(3).forEach(diaryEntry -> {
                Button button = new Button(diaryEntry.getTitle() + " - " + diaryEntry.getCreator().getFullName());
                button.addClickListener(clickEvent -> UI.getCurrent().getNavigator().navigateTo(DashboardView.NAME + "/" + RelativeDiaryOverview.NAME));
                hlDiaryPatient.addComponent(button);
            });
            hlDiaryPatient.addComponentsAndExpand(new Label(MessageHandler.PATIENTS_DIARY_ENTRIES));
            List<DiaryEntry> patientDiaryEntriesForUser = new DiaryEntryServiceImpl().getPatientDiaryEntriesForUser(patient);
            patientDiaryEntriesForUser.stream().limit(3).forEach(diaryEntry -> {
                Button button = new Button(diaryEntry.getTitle() + " - " + diaryEntry.getCreator().getFullName());
                button.addClickListener(clickEvent -> UI.getCurrent().getNavigator().navigateTo(DashboardView.NAME + "/" + PatientDiaryOverview.NAME));
                hlDiaryPatient.addComponent(button);
            });
            vlPat.addComponent(hlDiaryPatient);

            VerticalLayout hlGoalPatient = new VerticalLayout();
            hlGoalPatient.addComponentsAndExpand(new Label(MessageHandler.PATIENTS_GOALS));
            List<Goal> goalEntriesForUser = new GoalServiceImpl().getGoalEntriesForUser(patient);
            goalEntriesForUser.stream().limit(5).filter(goal -> goal.getState() == GoalState.OPEN).forEach(goal -> {
                Button button = new Button(goal.getGoal() + " - " + goal.getCreator().getFullName());
                button.addClickListener(clickEvent -> UI.getCurrent().getNavigator().navigateTo(DashboardView.NAME + "/" + GoaltrackerOverview.NAME));
                hlGoalPatient.addComponent(button);
            });
            vlPat.addComponent(hlGoalPatient);
            vlRel.addComponent(vlPat);
        }
        gridLayout.addComponent(vlRel, 0, 2);
        return gridLayout;
    }
}
