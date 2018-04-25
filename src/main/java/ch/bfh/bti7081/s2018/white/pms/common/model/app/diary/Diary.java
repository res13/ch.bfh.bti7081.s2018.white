package ch.bfh.bti7081.s2018.white.pms.common.model.app.diary;

import ch.bfh.bti7081.s2018.white.pms.common.model.app.App;

import java.util.List;
import java.util.Objects;

public class Diary extends App {

    private List<DiaryEntry> entryList;

    public Diary() {

    }

    public List<DiaryEntry> getEntryList() {
        return entryList;
    }

    public void setEntryList(List<DiaryEntry> entryList) {
        this.entryList = entryList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Diary diary = (Diary) o;
        return Objects.equals(entryList, diary.entryList);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), entryList);
    }

    @Override
    public String toString() {
        return "Diary{" +
                "entryList=" + entryList +
                "} " + super.toString();
    }
}
