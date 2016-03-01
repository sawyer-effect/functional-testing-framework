package com.sawyereffect.cucumber.documentation;


import com.sun.javadoc.*;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CommentTableGeneratorTest {

    @Test
    public void shouldRetrieveOnlyMatchingMethods() {

        ClassDoc classDoc = mock(ClassDoc.class);
        MethodDoc methodDoc1 = mock(MethodDoc.class);
        MethodDoc methodDoc2 = mock(MethodDoc.class);
        MethodDoc methodDoc3 = mock(MethodDoc.class);

        MethodDoc[] methodDocs = new MethodDoc[]{methodDoc1, methodDoc3, methodDoc2};
        ParamTag paramTag = mock(ParamTag.class);
        ParamTag paramTag3 = mock(ParamTag.class);

        ParamTag[] paramTags = new ParamTag[]{paramTag};
        ParamTag[] paramTags2 = new ParamTag[]{};
        ParamTag[] paramTags3 = new ParamTag[]{paramTag3};

        AnnotationDesc annotationDesc1 = mock(AnnotationDesc.class);
        AnnotationDesc annotationDesc2 = mock(AnnotationDesc.class);
        AnnotationDesc annotationDesc3 = mock(AnnotationDesc.class);

        AnnotationDesc[] annotationsDesc1 = new AnnotationDesc[]{annotationDesc1};
        AnnotationDesc[] annotationsDesc2 = new AnnotationDesc[]{annotationDesc2};
        AnnotationDesc[] annotationsDesc3 = new AnnotationDesc[]{annotationDesc3};

        AnnotationDesc.ElementValuePair elementValue1 = mock(AnnotationDesc.ElementValuePair.class);
        AnnotationDesc.ElementValuePair elementValue2 = mock(AnnotationDesc.ElementValuePair.class);
        AnnotationDesc.ElementValuePair elementValue3 = mock(AnnotationDesc.ElementValuePair.class);

        AnnotationDesc.ElementValuePair[] elementValues1 = new AnnotationDesc.ElementValuePair[]{elementValue1};
        AnnotationDesc.ElementValuePair[] elementValues2 = new AnnotationDesc.ElementValuePair[]{elementValue2};
        AnnotationDesc.ElementValuePair[] elementValues3 = new AnnotationDesc.ElementValuePair[]{elementValue3};

        AnnotationValue annotationValue1 = mock(AnnotationValue.class);
        AnnotationValue annotationValue2 = mock(AnnotationValue.class);
        AnnotationValue annotationValue3 = mock(AnnotationValue.class);

        AnnotationTypeDoc annotationType1 = mock(AnnotationTypeDoc.class);
        AnnotationTypeDoc annotationType2 = mock(AnnotationTypeDoc.class);
        AnnotationTypeDoc annotationType3 = mock(AnnotationTypeDoc.class);

        when(annotationValue1.value()).thenReturn("^She enters ([^\"]*)$");
        when(annotationValue2.value()).thenReturn("^Click on the first link from text results$");
        when(annotationValue3.value()).thenReturn("^Verify ([^\"]*) results should be displayed$");

        when(elementValue1.value()).thenReturn(annotationValue1);
        when(elementValue2.value()).thenReturn(annotationValue2);
        when(elementValue3.value()).thenReturn(annotationValue3);

        when(annotationType1.name()).thenReturn("When");
        when(annotationType2.name()).thenReturn("When");
        when(annotationType3.name()).thenReturn("Then");

        when(annotationDesc1.annotationType()).thenReturn(annotationType1);
        when(annotationDesc2.annotationType()).thenReturn(annotationType2);
        when(annotationDesc3.annotationType()).thenReturn(annotationType3);

        when(annotationDesc1.elementValues()).thenReturn(elementValues1);
        when(annotationDesc2.elementValues()).thenReturn(elementValues2);
        when(annotationDesc3.elementValues()).thenReturn(elementValues3);

        when(paramTag.parameterName()).thenReturn("searchTerm");
        when(paramTag.parameterComment()).thenReturn("- term to be searched.");

        when(paramTag3.parameterName()).thenReturn("results");
        when(paramTag3.parameterComment()).thenReturn("- text to be searched for validation.");

        when(methodDoc1.commentText()).thenReturn("Executes a search given the term provided on the search page.");
        when(methodDoc1.name()).thenReturn("she_enters");
        when(methodDoc1.annotations()).thenReturn(annotationsDesc1);
        when(methodDoc1.paramTags()).thenReturn(paramTags);

        when(methodDoc2.commentText()).thenReturn("User hits the first result available.");
        when(methodDoc2.name()).thenReturn("click_on_the_first_link_from_text_results");
        when(methodDoc2.annotations()).thenReturn(annotationsDesc2);
        when(methodDoc2.paramTags()).thenReturn(paramTags2);

        when(methodDoc3.commentText()).thenReturn("Verify from results given an specific text is displayed.");
        when(methodDoc3.name()).thenReturn("results_should_be_displayed");
        when(methodDoc3.annotations()).thenReturn(annotationsDesc3);
        when(methodDoc3.paramTags()).thenReturn(paramTags3);

        when(classDoc.methods()).thenReturn(methodDocs);

        CommentTableGenerator commentTableGenerator = new CommentTableGenerator();

        List<CommentTable> commentTables = commentTableGenerator.generate(classDoc);

        CommentTable commentTable1 = commentTables.get(0);
        CommentRow commentRow1 = commentTable1.getCommentRows().get(0);
        CommentRow commentRow2 = commentTable1.getCommentRows().get(1);

        assertEquals("ACTIONS", commentTable1.getClassification());

        assertEquals("Executes a search given the term provided on the search page.\n" +
                "@param searchTerm - term to be searched.", commentRow1.getDescription());
        assertEquals("^She enters ([^\"]*)$", commentRow1.getStep());
        assertEquals("she_enters", commentRow1.getMethodName());

        assertEquals("User hits the first result available.", commentRow2.getDescription());
        assertEquals("^Click on the first link from text results$", commentRow2.getStep());
        assertEquals("click_on_the_first_link_from_text_results", commentRow2.getMethodName());

        CommentTable commentTable2 = commentTables.get(1);
        CommentRow commentRow3 = commentTable2.getCommentRows().get(0);

        assertEquals("VERIFICATIONS", commentTable2.getClassification());

        assertEquals("Verify from results given an specific text is displayed.\n" +
                "@param results - text to be searched for validation.", commentRow3.getDescription());
        assertEquals("^Verify ([^\"]*) results should be displayed$", commentRow3.getStep());
        assertEquals("results_should_be_displayed", commentRow3.getMethodName());

    }

}
