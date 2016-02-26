package com.sawyereffect.cucumber.documentation;

import com.sun.javadoc.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConfluenceMarkupFormat {

    public static final String LINE_BREAK = "\n";

    public static boolean start(RootDoc root) throws IOException {
        ClassDoc[] classes = root.classes();
        ConfluenceMarkupFormat confluenceMarkupFormat = new ConfluenceMarkupFormat();
        FileWriter fileWriter = new FileWriter("confluence-markup-documentation.txt");
        for (ClassDoc classDoc : classes) {
            confluenceMarkupFormat.parse(classDoc, fileWriter);
        }
        fileWriter.close();
        return true;
    }

    public void parse(ClassDoc classDoc, FileWriter fileWriter) throws IOException {
        String classComment = classDoc.commentText();
        String[] lines = classComment.split(LINE_BREAK);
        String header = lines[0];
        StringBuilder builder = new StringBuilder();
        builder.append("\\\\\n");
        builder.append("h2. ");
        builder.append(classDoc.qualifiedName());
        builder.append(LINE_BREAK);
        builder.append("*");
        builder.append(header);
        builder.append("*");
        builder.append(LINE_BREAK);

        for (int i = 1; i < lines.length; i++) {
            builder.append(lines[i]);
            builder.append(LINE_BREAK);
        }
        Set<String> actionAnnotations = new HashSet<>();
        actionAnnotations.add("When");
        List<MethodDoc> actionMethodList = new ArrayList<>();

        Set<String> verificationAnnotations = new HashSet<>();
        verificationAnnotations.add("Then");
        List<MethodDoc> verificationMethodList = new ArrayList<>();


        for (MethodDoc methodDoc : classDoc.methods()) {
            for (AnnotationDesc annotationDesc : methodDoc.annotations()) {
                String annotationType = annotationDesc.annotationType().name();

                if (actionAnnotations.contains(annotationType)) {
                    actionMethodList.add(methodDoc);
                }

                if (verificationAnnotations.contains(annotationType)) {
                    verificationMethodList.add(methodDoc);
                }
            }
        }


        parseMethodsForAnnotations(builder, "ACTIONS", actionMethodList);
        builder.append("\\\\");
        builder.append(LINE_BREAK);
        parseMethodsForAnnotations(builder, "VERIFICATIONS", verificationMethodList);

        fileWriter.write(builder.toString());
    }

    private void parseMethodsForAnnotations(StringBuilder builder, String header, List<MethodDoc> methods) {
        builder.append("||");
        builder.append(header);
        builder.append("||");
        builder.append(LINE_BREAK);
        builder.append("||Method||Step||Description||\n");
        for (MethodDoc methodDoc : methods) {
            builder.append("|");
            builder.append(methodDoc.name());
            builder.append("|");
            for (AnnotationDesc annotationDesc : methodDoc.annotations()) {
                for (AnnotationDesc.ElementValuePair valuePair : annotationDesc.elementValues()) {
                    builder.append(valuePair.value().value());
                }
            }
            builder.append("|");
            builder.append(methodDoc.commentText());
            for (ParamTag paramTag : methodDoc.paramTags()) {
                builder.append("\n@param ");
                builder.append(paramTag.parameterName());
                builder.append(" ");
                builder.append(paramTag.parameterComment());
            }
            builder.append("|");
            builder.append(LINE_BREAK);
        }
    }
}
