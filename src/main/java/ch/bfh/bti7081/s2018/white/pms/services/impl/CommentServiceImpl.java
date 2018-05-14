package ch.bfh.bti7081.s2018.white.pms.services.impl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import ch.bfh.bti7081.s2018.white.pms.common.model.app.diary.Comment;
import ch.bfh.bti7081.s2018.white.pms.persistence.JpaUtility;
import ch.bfh.bti7081.s2018.white.pms.services.CommentService;

public class CommentServiceImpl<T extends Comment> extends BaseServiceImpl<T> implements CommentService {

    public CommentServiceImpl(Class<T> clazz) {
        super(clazz);
    }
    
    public CommentServiceImpl() {
        super((Class<T>) Comment.class);
    }
    
    public List<T> getEntitiesByDiaryEntityId(long id) throws Exception {
        return new JpaUtility().execute(
                (em) -> {
                    CriteriaBuilder cb = em.getCriteriaBuilder();
                    CriteriaQuery<T> cq = cb.createQuery(clazz);
                    Root<T> rootEntry = cq.from(clazz);
                    CriteriaQuery<T> where = cq.where(cb.equal(rootEntry.get("diaryEntry"), id));
                    TypedQuery<T> allQuery = em.createQuery(where);
                    return allQuery.getResultList();
                });
    }
}
