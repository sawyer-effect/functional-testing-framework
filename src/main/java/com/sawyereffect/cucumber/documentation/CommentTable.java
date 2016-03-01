package com.sawyereffect.cucumber.documentation;


import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.ParamTag;

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

    public void addRow(MethodDoc methodDoc) {
        CommentRow commentRow = new CommentRow();
        StringBuilder description = new StringBuilder();
        description.append(methodDoc.commentText());
        for (ParamTag paramTag : methodDoc.paramTags()) {
            description.append("\n@param ");
            description.append(paramTag.parameterName());
            description.append(" ");
            description.append(paramTag.parameterComment());
        }

        commentRow.setDescription(description.toString());
        for (AnnotationDesc annotationDesc : methodDoc.annotations()) {
            for (AnnotationDesc.ElementValuePair valuePair : annotationDesc.elementValues()) {
                commentRow.setStep(valuePair.value().value().toString());
            }
        }

        commentRow.setMethodName(methodDoc.name());

        getCommentRows().add(commentRow);
    }

    public void setCommentRows(List<CommentRow> commentRows) {
        this.commentRows = commentRows;
    }
}
