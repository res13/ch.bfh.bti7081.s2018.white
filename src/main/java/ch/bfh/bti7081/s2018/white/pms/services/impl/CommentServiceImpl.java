package ch.bfh.bti7081.s2018.white.pms.services.impl;

import ch.bfh.bti7081.s2018.white.pms.common.model.app.diary.Comment;
import ch.bfh.bti7081.s2018.white.pms.persistence.JpaUtility;
import ch.bfh.bti7081.s2018.white.pms.services.CommentService;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class CommentServiceImpl extends BaseServiceImpl<Comment> implements CommentService {

    public CommentServiceImpl() {
        super(Comment.class);
    }

    public List<Comment> getEntitiesByDiaryEntityId(long id) throws Exception {
        return new JpaUtility().execute(
                (em) -> {
                    CriteriaBuilder cb = em.getCriteriaBuilder();
                    CriteriaQuery<Comment> cq = cb.createQuery(clazz);
                    Root<Comment> rootEntry = cq.from(clazz);
                    CriteriaQuery<Comment> where = cq.where(cb.equal(rootEntry.get("diaryEntry"), id));
                    TypedQuery<Comment> allQuery = em.createQuery(where);
                    return allQuery.getResultList();
                });
    }
}
