package ch.bfh.bti7081.s2018.white.pms.common.model;

import ch.bfh.bti7081.s2018.white.pms.common.model.user.User;

import java.util.Date;
import java.util.Objects;

public class PmsType {

    private long id;

    private Date lastModified;

    private User lastModifiedBy;

    public PmsType() {
    }

    public long getId() {
        return id;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public User getLastModifiedBy() {
        return lastModifiedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PmsType pmsType = (PmsType) o;
        return id == pmsType.id &&
                Objects.equals(lastModified, pmsType.lastModified) &&
                Objects.equals(lastModifiedBy, pmsType.lastModifiedBy);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, lastModified, lastModifiedBy);
    }

    @Override
    public String toString() {
        return "PmsType{" +
                "id=" + id +
                ", lastModified=" + lastModified +
                ", lastModifiedBy=" + lastModifiedBy +
                '}';
    }
}
