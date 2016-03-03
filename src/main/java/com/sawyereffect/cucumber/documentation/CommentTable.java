package com.sawyereffect.cucumber.documentation;


import java.util.ArrayList;
import java.util.List;

public class CommentTable {
    private String classification;
    private List<CommentRow> commentRows;

    public CommentTable(String classification) {
        this.classification = classification;
        this.commentRows = new ArrayList<>();
    }

    public String getClassification() {
        return classification;
    }

    public List<CommentRow> getCommentRows() {
        return commentRows;
    }


    public void addRow(CommentRow commentRow) {
        getCommentRows().add(commentRow);
    }

    public void setCommentRows(List<CommentRow> commentRows) {
        this.commentRows = commentRows;
    }
}
