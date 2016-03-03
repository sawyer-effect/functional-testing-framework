package com.sawyereffect.cucumber.documentation;


import java.util.ArrayList;
import java.util.List;

public class CommentTable {
    private String classification;
    private String classifier;
    private List<CommentRow> commentRows;

    public CommentTable(String classification, String classifier) {
        this.classification = classification;
        this.classifier = classifier;
        this.commentRows = new ArrayList<>();
    }

    public String getClassification() {
        return classification;
    }

    public List<CommentRow> getCommentRows() {
        return commentRows;
    }

    public String getClassifier() {
        return classifier;
    }

    public void addRow(CommentRow commentRow) {
        getCommentRows().add(commentRow);
    }

    public void setCommentRows(List<CommentRow> commentRows) {
        this.commentRows = commentRows;
    }
}
