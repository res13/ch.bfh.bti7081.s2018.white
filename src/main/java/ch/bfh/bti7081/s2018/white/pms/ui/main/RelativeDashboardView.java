package ch.bfh.bti7081.s2018.white.pms.ui.main;

import ch.bfh.bti7081.s2018.white.pms.common.i18n.MessageHandler;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.diary.DiaryEntry;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.Patient;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.Relative;
import ch.bfh.bti7081.s2018.white.pms.services.impl.DiaryEntryServiceImpl;
import ch.bfh.bti7081.s2018.white.pms.ui.app.diary.PatientDiaryOverview;
import ch.bfh.bti7081.s2018.white.pms.ui.app.diary.RelativeDiaryOverview;
import com.vaadin.ui.*;

import java.util.List;

public class RelativeDashboardView {

    private Relative relative;

    public RelativeDashboardView(Relative relative){
        this.relative = relative;
    }

    public GridLayout getGridLayout(){
        GridLayout gridLayout = new GridLayout(1, 3);
        gridLayout.addComponent(new Label(MessageHandler.WELCOME + relative.getFullName()), 0, 0);

        List<Patient> patientList = relative.getPatientList();
        gridLayout.addComponent(new Label(MessageHandler.COUNT_PATIENT + patientList.size()), 0, 1);
        VerticalLayout vlRel = new VerticalLayout();

        for (Patient patient : patientList) {
            vlRel.addComponent(new Label(MessageHandler.OVERVIEW_FOR + patient.getFullName()));
            VerticalLayout vlPat = new VerticalLayout();

            HorizontalLayout hlPat = new HorizontalLayout();
            List<DiaryEntry> relativeDiaryEntriesForUser = new DiaryEntryServiceImpl().getRelativeDiaryEntriesForUser(relative);

            relativeDiaryEntriesForUser.stream().limit(3).forEach(diaryEntry -> {
                Button button = new Button(diaryEntry.getTitle() + " - " + diaryEntry.getCreator().getFullName());
                button.addClickListener(clickEvent ->   UI.getCurrent().getNavigator().navigateTo(DashboardView.NAME + "/" + RelativeDiaryOverview.NAME));
                hlPat.addComponent(button);
            });
            List<DiaryEntry> patientDiaryEntriesForUser = new DiaryEntryServiceImpl().getPatientDiaryEntriesForUser(patient);
            patientDiaryEntriesForUser.stream().limit(3).forEach(diaryEntry -> {
                Button button = new Button(diaryEntry.getTitle() + " - " + diaryEntry.getCreator().getFullName());
                button.addClickListener(clickEvent ->   UI.getCurrent().getNavigator().navigateTo(DashboardView.NAME + "/" + PatientDiaryOverview.NAME));
                hlPat.addComponent(button);
            });
            vlPat.addComponent(hlPat);

            //Same for goal
            vlPat.addComponent(new Label("Add Goal"));

            vlRel.addComponent(vlPat);
        }
        gridLayout.addComponent(vlRel, 0, 2);
        return gridLayout;
    }
}
