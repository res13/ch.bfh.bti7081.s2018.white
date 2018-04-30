package ch.bfh.bti7081.s2018.white.pms.services.impl;

import ch.bfh.bti7081.s2018.white.pms.common.model.app.diary.Diary;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.diary.DiaryEntry;
import ch.bfh.bti7081.s2018.white.pms.services.DiaryService;

public class DiaryServiceImpl extends AppServiceImpl<Diary> implements DiaryService {

    public DiaryServiceImpl() {
        super(Diary.class);
    }
}
