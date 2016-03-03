package com.sawyereffect.cucumber.documentation;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.RootDoc;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ConfluenceMarkupFormat {

    public static final String LINE_BREAK = "\n";

    public static boolean start(RootDoc root) throws IOException {
        ClassDoc[] classes = root.classes();
        ConfluenceMarkupFormat confluenceMarkupFormat = new ConfluenceMarkupFormat();
        FileWriter fileWriter = new FileWriter("confluence-markup-documentation.txt");
        CommentTableGenerator commentTableGenerator = new CommentTableGenerator();
        for (ClassDoc classDoc : classes) {
            confluenceMarkupFormat.parse(classDoc, fileWriter);
        }
        fileWriter.close();
        return true;
    }

    public void parse(ClassDoc classDoc, FileWriter fileWriter) throws IOException {
        CommentClass commentClass = createCommentClass(classDoc);
        String[] lines = commentClass.getClassComment().split(LINE_BREAK);
        String header = lines[0];
        StringBuilder builder = new StringBuilder();
        builder.append("\\\\\n");
        builder.append("h2. ");
        builder.append(commentClass.getClassQualifiedName());
        builder.append(LINE_BREAK);
        builder.append("*");
        builder.append(header);
        builder.append("*");
        builder.append(LINE_BREAK);

        for (int i = 1; i < lines.length; i++) {
            builder.append(lines[i]);
            builder.append(LINE_BREAK);
        }

        List<CommentTable> commentTableList = commentClass.getCommentTables();
        for (int index = 0; index < commentTableList.size(); index++) {
            if (index > 0) {
                builder.append("\\\\");
                builder.append(LINE_BREAK);
            }
            markupCommentTable(builder, commentTableList.get(index));
        }
        fileWriter.write(builder.toString());
    }

    private void markupCommentTable(StringBuilder builder, CommentTable commentTable) {
        builder.append("||");
        builder.append(commentTable.getClassification());
        builder.append("||");
        builder.append(LINE_BREAK);
        builder.append("||Method||Step||Description||\n");
        for (CommentRow commentRow : commentTable.getCommentRows()) {
            builder.append("|");
            builder.append(commentRow.getMethodName());
            builder.append("|");
            builder.append(commentRow.getStep());
            builder.append("|");
            builder.append(commentRow.getDescription());
            builder.append("|");
            builder.append(LINE_BREAK);
        }
    }

    public CommentClass createCommentClass(ClassDoc classDoc) {
        return new CommentClass(classDoc, new CommentTableGenerator());
    }
}
