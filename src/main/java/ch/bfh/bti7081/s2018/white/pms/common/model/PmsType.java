package ch.bfh.bti7081.s2018.white.pms.common.model;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class PmsType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private Date lastModified;

    @PrePersist
    @PreUpdate
    private void onInsert() {
        this.lastModified = new Date();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
}
