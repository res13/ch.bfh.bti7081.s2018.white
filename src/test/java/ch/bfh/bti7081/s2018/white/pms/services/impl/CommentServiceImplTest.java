package ch.bfh.bti7081.s2018.white.pms.services.impl;

import ch.bfh.bti7081.s2018.white.pms.common.model.app.diary.Comment;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.diary.DiaryEntry;
import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CommentServiceImplTest extends TestCase{

    DiaryEntry diaryEntry = new DiaryEntry();
    private final String comment = "test";
    private Comment testComment;
    private List<Comment> testCommentList;
    private BaseServiceImpl<DiaryEntry> baseService = new BaseServiceImpl(DiaryEntry.class);
    private CommentServiceImpl commentService;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        commentService = new CommentServiceImpl();
        testComment = new Comment();
        testComment.setCommentText(comment);
        testCommentList = Arrays.asList(testComment);

        diaryEntry.setTitle("CommentDiary");
        diaryEntry.setCommentList(testCommentList);
        baseService.saveOrUpdateEntity(diaryEntry);
    }

    public void CheckDiaryForComment() {
        try
        {
           List<Comment> returnedComments = commentService.getEntitiesByDiaryEntity(diaryEntry);
           assertEquals("Comment was correct", comment.equals(returnedComments.get(0)));
        }
        catch (Exception e)
        {
            fail("Comment wasn't correct or not existing");
        }
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        baseService.deleteEntity(diaryEntry);
    }
}
