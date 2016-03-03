package com.sawyereffect.cucumber.documentation;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.ParamTag;

import java.util.ArrayList;
import java.util.List;

public class CommentTableGenerator {

    public static final String ACTIONS_CLASSIFICATION = "ACTIONS";
    public static final String VERIFICATIONS_CLASSIFICATION = "VERIFICATIONS";

    public List<CommentTable> generate(ClassDoc classDoc) {

        CommentTable actionTable = new CommentTable(ACTIONS_CLASSIFICATION, "When");
        CommentTable verificationTable = new CommentTable(VERIFICATIONS_CLASSIFICATION, "Then");

        for (MethodDoc methodDoc : classDoc.methods()) {
            for (AnnotationDesc annotationDesc : methodDoc.annotations()) {
                String annotationType = annotationDesc.annotationType().name();

                if (actionTable.getClassifier().contains(annotationType)) {
                    actionTable.addRow(createCommentRow(methodDoc));
                } else if (verificationTable.getClassifier().contains(annotationType)) {
                    verificationTable.addRow(createCommentRow(methodDoc));
                }
            }
        }

        List<CommentTable> commentTables = new ArrayList<>();
        commentTables.add(actionTable);
        commentTables.add(verificationTable);
        return commentTables;
    }

    private CommentRow createCommentRow(MethodDoc methodDoc) {
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
        return commentRow;
    }
}
