package com.sawyereffect.cucumber.documentation;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.ParamTag;

import java.util.ArrayList;
import java.util.List;

public class CommentTableGenerator {

    private static final String ACTIONS_CLASSIFICATION = "ACTIONS";
    private static final String VERIFICATIONS_CLASSIFICATION = "VERIFICATIONS";
    private static final String PRECONDITIONS_CLASSIFICATION = "PRECONDITIONS";
    private static final String UNKNOWN_CLASSIFICATION = "UNKNOWN";


    private static final String ACTIONS_CLASSIFIER = "When";
    private static final String VERIFICATIONS_CLASSIFIER = "Then";
    private static final String PRECONDITIONS_CLASSIFIER = "Given";

    public List<CommentTable> generate(ClassDoc classDoc) {

        CommentTable actionTable = new CommentTable(ACTIONS_CLASSIFICATION);
        CommentTable verificationTable = new CommentTable(VERIFICATIONS_CLASSIFICATION);
        CommentTable preconditionsTable = new CommentTable(PRECONDITIONS_CLASSIFICATION);
        CommentTable unknownsTable = new CommentTable(UNKNOWN_CLASSIFICATION);

        for (MethodDoc methodDoc : classDoc.methods()) {
            for (AnnotationDesc annotationDesc : methodDoc.annotations()) {
                String annotationType = annotationDesc.annotationType().name();

                if (ACTIONS_CLASSIFIER.equals(annotationType)) {
                    actionTable.addRow(createCommentRow(methodDoc));

                } else if (VERIFICATIONS_CLASSIFIER.contains(annotationType)) {
                    verificationTable.addRow(createCommentRow(methodDoc));

                } else if (PRECONDITIONS_CLASSIFIER.equals(annotationType)) {
                    preconditionsTable.addRow(createCommentRow(methodDoc));

                } else {
                    unknownsTable.addRow(createCommentRow(methodDoc));
                }
            }
        }

        final List<CommentTable> commentTables = new ArrayList<>();
        commentTables.add(actionTable);
        commentTables.add(verificationTable);
        commentTables.add(preconditionsTable);
        commentTables.add(unknownsTable);

        return commentTables;
    }

    CommentRow createCommentRow(MethodDoc methodDoc) {
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
