package ch.bfh.bti7081.s2018.white.pms.common.model.app.diary;

import ch.bfh.bti7081.s2018.white.pms.common.model.PmsType;

import java.time.LocalDateTime;
import java.util.Objects;

public class DiaryEntry extends PmsType {

    private LocalDateTime time;

    private String entryText;

    public DiaryEntry() {
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getEntryText() {
        return entryText;
    }

    public void setEntryText(String entryText) {
        this.entryText = entryText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DiaryEntry that = (DiaryEntry) o;
        return Objects.equals(time, that.time) &&
                Objects.equals(entryText, that.entryText);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), time, entryText);
    }

    @Override
    public String toString() {
        return "DiaryEntry{" +
                "time=" + time +
                ", entryText='" + entryText + '\'' +
                "} " + super.toString();
    }
}
