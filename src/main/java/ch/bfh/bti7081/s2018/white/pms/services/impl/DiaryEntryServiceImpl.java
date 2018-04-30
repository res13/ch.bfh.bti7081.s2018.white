package ch.bfh.bti7081.s2018.white.pms.services.impl;

import ch.bfh.bti7081.s2018.white.pms.common.model.app.diary.DiaryEntry;
import ch.bfh.bti7081.s2018.white.pms.common.model.caze.Case;
import ch.bfh.bti7081.s2018.white.pms.services.DiaryEntryService;

public class DiaryEntryServiceImpl extends BaseServiceImpl<DiaryEntry> implements DiaryEntryService {

    public DiaryEntryServiceImpl() {
        super(DiaryEntry.class);
    }
}
