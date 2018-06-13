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
        GridLayout gridLayout = new GridLayout(3, 3);

        Label lblWelcome = new Label(MessageHandler.WELCOME + relative.getFullName());
        lblWelcome.addStyleName("lblWelcome");
        gridLayout.addComponent(lblWelcome, 0, 0);

        List<Patient> patientList = relative.getPatientList();

        // TODO : Need we something like this for the relative dashboard?
        // gridLayout.addComponent(new Label(MessageHandler.COUNT_PATIENT + patientList.size()), 0, 1);

        // Layout over all patients
        VerticalLayout vlRel = new VerticalLayout();

        // Just for testing with more than one
        // patientList.add(patientList.get(0));

        for (Patient patient : patientList) {

            Label lblOverview = new Label(MessageHandler.OVERVIEW_FOR + patient.getFullName());
            lblOverview.addStyleName("lblOverview");
            vlRel.addComponent(lblOverview);

            // Structure of page
            HorizontalLayout hlEntry = new HorizontalLayout();
            VerticalLayout vlDiaryRelative = new VerticalLayout();
            VerticalLayout vlDiaryPatient = new VerticalLayout();
            VerticalLayout vlGoalPatient = new VerticalLayout();
            vlDiaryRelative.addStyleName("vlEntry");
            vlDiaryPatient.addStyleName("vlEntry");
            vlGoalPatient.addStyleName("vlEntry");


            // Make some not really aesthetic magic
            Label lblSubTitleDiaryRelative = new Label(MessageHandler.MY_DIARY_ENTRIES);
            lblSubTitleDiaryRelative.addStyleName("lblSubTitle");
            vlDiaryRelative.addComponentsAndExpand(lblSubTitleDiaryRelative); // new Label(MessageHandler.MY_DIARY_ENTRIES).addStyleName("lblSubTitle")
            List<DiaryEntry> relativeDiaryEntriesForUser = new DiaryEntryServiceImpl().getRelativeDiaryEntriesForUser(relative);

            if (!relativeDiaryEntriesForUser.isEmpty()){
                relativeDiaryEntriesForUser.stream().limit(3).forEach(diaryEntry -> {
                    Button button = new Button(diaryEntry.getTitle());
                    button.addClickListener(clickEvent -> UI.getCurrent().getNavigator().navigateTo(DashboardView.NAME + "/" + RelativeDiaryOverview.NAME));
                    vlDiaryRelative.addComponent(button);
                });
            }else{
                vlDiaryRelative.addComponent(getLblEmpty());
            }


            Label lblSubTitleDairyPatient = new Label(MessageHandler.PATIENTS_DIARY_ENTRIES);
            lblSubTitleDairyPatient.addStyleName("lblSubTitle");
            vlDiaryPatient.addComponentsAndExpand(lblSubTitleDairyPatient);
            List<DiaryEntry> patientDiaryEntriesForUser = new DiaryEntryServiceImpl().getPatientDiaryEntriesForUser(patient);

            if(!patientDiaryEntriesForUser.isEmpty()){
                patientDiaryEntriesForUser.stream().limit(3).forEach(diaryEntry -> {
                    Button button = new Button(diaryEntry.getTitle());
                    button.addClickListener(clickEvent -> UI.getCurrent().getNavigator().navigateTo(DashboardView.NAME + "/" + PatientDiaryOverview.NAME));
                    vlDiaryPatient.addComponent(button);
                });
            }else{
                vlDiaryPatient.addComponent(getLblEmpty());
            }


            Label lblSubTitleGoalPatient = new Label(MessageHandler.PATIENTS_GOALS);
            lblSubTitleGoalPatient.addStyleName("lblSubTitle");
            vlGoalPatient.addComponentsAndExpand(lblSubTitleGoalPatient);
            List<Goal> goalEntriesForUser = new GoalServiceImpl().getGoalEntriesForUser(patient);

            if (!goalEntriesForUser.isEmpty()){
                goalEntriesForUser.stream().limit(5).filter(goal -> goal.getState() == GoalState.OPEN).forEach(goal -> {
                    Button button = new Button(goal.getGoal());
                    button.addClickListener(clickEvent -> UI.getCurrent().getNavigator().navigateTo(DashboardView.NAME + "/" + GoaltrackerOverview.NAME));
                    vlGoalPatient.addComponent(button);
                });
            }else{
                vlGoalPatient.addComponent(getLblEmpty());
            }



            hlEntry.addComponent(vlDiaryRelative);
            hlEntry.addComponent(vlDiaryPatient);
            hlEntry.addComponent(vlGoalPatient);
            vlRel.addComponent(hlEntry);

            //vlRel.addComponent(getHlInfo());
            //vlRel.addComponent(getHlFunctions());
            /*vlDiaryRelative.addComponent(vlGoalPatient);
            vlRel.addComponent(vlDiaryRelative);*/
        }
        gridLayout.addComponent(vlRel, 0, 2);
        gridLayout.addStyleName("myGrid");

        return gridLayout;
    }

    HorizontalLayout getHlInfo(){

        return null;
    };

    HorizontalLayout getHlFunctions(){
        // Show functionality
        HorizontalLayout hlFunctions = new HorizontalLayout();
        Button btnDiaryEntry = new Button("Set new Diary Entry");
        btnDiaryEntry.addClickListener(clickEvent -> UI.getCurrent().getNavigator().navigateTo(DashboardView.NAME + "/" + RelativeDiaryOverview.NAME));

        Button btnGoalEntry = new Button("Set new Goal Entry");
        btnGoalEntry.addClickListener(clickEvent -> UI.getCurrent().getNavigator().navigateTo(DashboardView.NAME + "/" + GoaltrackerOverview.NAME));

        hlFunctions.addComponent(btnGoalEntry);
        hlFunctions.addComponent(btnDiaryEntry);
        return hlFunctions;
    };

    Label getLblEmpty(){
        return new Label(MessageHandler.NO_ENTRIES);
    };

}



