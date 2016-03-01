package com.sawyereffect.cucumber.documentation;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;

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
                    actionTable.addRow(methodDoc);
                } else if (verificationTable.getClassifier().contains(annotationType)) {
                    verificationTable.addRow(methodDoc);
                }
            }
        }

        List<CommentTable> commentTables = new ArrayList<>();
        commentTables.add(actionTable);
        commentTables.add(verificationTable);
        return commentTables;
    }
}
