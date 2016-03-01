package com.sawyereffect.cucumber.documentation;

import com.sun.javadoc.ClassDoc;

import java.util.List;

public class CommentClass {
    private String classComment;
    private List<CommentTable> commentTables;
    private String classQualifiedName;

    public CommentClass(ClassDoc classDoc, CommentTableGenerator commentTableGenerator) {
        setClassComment(classDoc.commentText());
        setClassQualifiedName(classDoc.qualifiedName());
        setCommentTables(commentTableGenerator.generate(classDoc));
    }

    public String getClassComment() {
        return classComment;
    }

    public void setClassComment(String classComment) {
        this.classComment = classComment;
    }

    public List<CommentTable> getCommentTables() {
        return commentTables;
    }

    public void setCommentTables(List<CommentTable> commentTables) {
        this.commentTables = commentTables;
    }

    public String getClassQualifiedName() {
        return classQualifiedName;
    }

    public void setClassQualifiedName(String classQualifiedName) {
        this.classQualifiedName = classQualifiedName;
    }
}
