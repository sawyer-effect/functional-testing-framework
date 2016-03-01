package com.sawyereffect.cucumber.documentation;


import com.sun.javadoc.ClassDoc;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CommentClassTest {

    @Test
    public void shouldSubstractInformationAsNeeded() {
        ClassDoc classDoc = mock(ClassDoc.class);
        CommentTableGenerator commentTableGenerator = mock(CommentTableGenerator.class);
        String comment = "Google search page\n" +
                " This page is used to search for terms in the outside world.";
        String qualifiedName = "com.sawyereffect.steps.SearchPageSteps";

        List<CommentTable> commentTables = new ArrayList<>();
        when(commentTableGenerator.generate(classDoc)).thenReturn(commentTables);
        when(classDoc.commentText()).thenReturn(comment);
        when(classDoc.qualifiedName()).thenReturn(qualifiedName);

        CommentClass commentClass = new CommentClass(classDoc, commentTableGenerator);

        assertEquals(comment, commentClass.getClassComment());
        assertEquals(qualifiedName, commentClass.getClassQualifiedName());
        assertSame(commentTables, commentClass.getCommentTables());

    }

}
