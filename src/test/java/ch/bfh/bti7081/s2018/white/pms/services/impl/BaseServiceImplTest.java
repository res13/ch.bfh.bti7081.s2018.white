package ch.bfh.bti7081.s2018.white.pms.services.impl;

import ch.bfh.bti7081.s2018.white.pms.common.model.app.diary.DiaryEntry;
import junit.framework.TestCase;

//FIXME nik do it
public class BaseServiceImplTest extends TestCase {

    DiaryEntry diaryEntry = new DiaryEntry();
    String title = "TestTitle1";
    private BaseServiceImpl<DiaryEntry> baseService = new BaseServiceImpl(DiaryEntry.class);

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        diaryEntry.setTitle(title);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testCreate() {
        try {
            DiaryEntry savedDiaryEntry = baseService.saveOrUpdateEntity(diaryEntry);
            assertEquals(title, savedDiaryEntry.getTitle());
        } catch (Exception e) {
            fail();
        }
    }

    public void testRead() {
        try {
            DiaryEntry savedDiaryEntry = baseService.saveOrUpdateEntity(diaryEntry);
            DiaryEntry entityById = baseService.getEntityById(savedDiaryEntry.getId());
            assertEquals(title, entityById.getTitle());
        } catch (Exception e) {
            fail();
        }
    }

    public void testUpdate() {
        try {
            DiaryEntry savedDiaryEntry = baseService.saveOrUpdateEntity(diaryEntry);
            String title2 = "TestTitle2";
            savedDiaryEntry.setTitle(title2);
            DiaryEntry updatedEntry = baseService.saveOrUpdateEntity(savedDiaryEntry);
            assertEquals(title2, updatedEntry.getTitle());
        } catch (Exception e) {
            fail();
        }
    }

    public void testDelete() {
        try {
            DiaryEntry savedDiaryEntry = baseService.saveOrUpdateEntity(diaryEntry);
            baseService.deleteEntity(savedDiaryEntry);
            DiaryEntry entityById = baseService.getEntityById(savedDiaryEntry.getId());
            assertNull(entityById);
        } catch (Exception e) {
            fail();
        }
    }

}
