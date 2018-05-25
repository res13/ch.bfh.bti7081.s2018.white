package ch.bfh.bti7081.s2018.white.pms.services.impl;

import ch.bfh.bti7081.s2018.white.pms.common.model.app.diary.Diary;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.diary.DiaryEntry;
import ch.bfh.bti7081.s2018.white.pms.common.model.caze.Caze;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.Doctor;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.Patient;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.Relative;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.User;
import ch.bfh.bti7081.s2018.white.pms.persistence.JpaUtility;
import ch.bfh.bti7081.s2018.white.pms.services.DiaryEntryService;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Collectors;

public class DiaryEntryServiceImpl extends BaseServiceImpl<DiaryEntry> implements DiaryEntryService {

    public DiaryEntryServiceImpl() {
        super(DiaryEntry.class);
    }

    public List<DiaryEntry> getPatientDiaryEntriesForUser(User user) {
        return getDiaryEntriesForUser(user)
                .stream()
                .filter(diaryEntry1 -> diaryEntry1.getCreator() instanceof Patient)
                .collect(Collectors.toList());
    }

    public List<DiaryEntry> getRelativeDiaryEntriesForUser(User user) {
        return getDiaryEntriesForUser(user)
                .stream()
                .filter(diaryEntry1 -> diaryEntry1.getCreator() instanceof Relative)
                .collect(Collectors.toList());
    }

    private List<DiaryEntry> getDiaryEntriesForUser(User user) {
        return new JpaUtility().execute(
                (em) -> {
                    CriteriaBuilder cb = em.getCriteriaBuilder();
                    CriteriaQuery<DiaryEntry> cq = cb.createQuery(clazz);
                    Root<DiaryEntry> diaryEntry = cq.from(clazz);
                    Join<DiaryEntry, Diary> diaryToDiaryEntryJoin = diaryEntry.join("diary");
                    Join<Diary, Caze> cazeToDiaryJoin = diaryToDiaryEntryJoin.join("caze");
                    Join<Caze, Patient> patientToCazeJoin = cazeToDiaryJoin.join("patient");
                    CriteriaQuery<DiaryEntry> where = null;
                    if (user instanceof Relative) {
                        Join<Patient, Relative> relativeToPatientJoin = patientToCazeJoin.join("relativeList");
                        where = cq.where(cb.equal(relativeToPatientJoin.get("id"), user.getId()), cb.equal(diaryEntry.get("relativeRead"), true));
                    } else if (user instanceof Patient) {
                        where = cq.where(cb.equal(patientToCazeJoin.get("id"), user.getId()), cb.equal(diaryEntry.get("patientRead"), true));
                    } else if (user instanceof Doctor) {
                        Join<Patient, Doctor> doctorToPatientJoin = patientToCazeJoin.join("doctorList");
                        where = cq.where(cb.equal(doctorToPatientJoin.get("id"), user.getId()));
                    }
                    TypedQuery<DiaryEntry> allQuery = em.createQuery(where);
                    return allQuery.getResultList();
                });
    }

}
