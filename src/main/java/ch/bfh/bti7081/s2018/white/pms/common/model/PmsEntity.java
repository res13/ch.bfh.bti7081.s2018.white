package ch.bfh.bti7081.s2018.white.pms.common.model;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class PmsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date lastModified;

    @PrePersist
    @PreUpdate
    protected void onInsert() {
        this.lastModified = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
}
