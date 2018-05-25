package ch.bfh.bti7081.s2018.white.pms.ui.app.diary;

import ch.bfh.bti7081.s2018.white.pms.common.model.app.diary.DiaryEntry;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.User;
import ch.bfh.bti7081.s2018.white.pms.services.impl.DiaryEntryServiceImpl;
import com.vaadin.server.VaadinSession;

import java.util.ArrayList;
import java.util.List;

public class PatientDiaryOverview extends DiaryOverview {

    public static final String NAME = "patientDiary";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public List<DiaryEntry> getDiaryEntries() {
        User user = (User) VaadinSession.getCurrent().getAttribute(User.class.getName());
        if (user != null) {
            return new DiaryEntryServiceImpl().getPatientDiaryEntriesForUser(user);
        }
        return new ArrayList<>();
    }

}
