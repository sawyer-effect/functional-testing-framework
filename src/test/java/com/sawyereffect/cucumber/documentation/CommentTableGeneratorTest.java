package com.sawyereffect.cucumber.documentation;


import com.sun.javadoc.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CommentTableGeneratorTest {

    private CommentTableGenerator commentTableGenerator;

    @Before
    public void setUp() {
        commentTableGenerator = new CommentTableGenerator();
    }

    @Test
    public void shouldRetrieveOnlyMatchingMethods() {

        ClassDoc classDoc = mock(ClassDoc.class);
        MethodDoc methodWhen = mock(MethodDoc.class);
        MethodDoc methodThen = mock(MethodDoc.class);
        MethodDoc methodGiven = mock(MethodDoc.class);
        MethodDoc methodAnd = mock(MethodDoc.class);
        MethodDoc methodBut = mock(MethodDoc.class);

        MethodDoc[] methodDocs = new MethodDoc[]{methodWhen, methodGiven, methodThen, methodAnd, methodBut};

        AnnotationDesc annotationDescWhen = mock(AnnotationDesc.class);
        AnnotationDesc annotationDescGiven = mock(AnnotationDesc.class);
        AnnotationDesc annotationDescThen = mock(AnnotationDesc.class);
        AnnotationDesc annotationDescAnd = mock(AnnotationDesc.class);
        AnnotationDesc annotationDescBut = mock(AnnotationDesc.class);

        AnnotationDesc[] annotationsDescWhen = new AnnotationDesc[]{annotationDescWhen};
        AnnotationDesc[] annotationsDescGiven = new AnnotationDesc[]{annotationDescGiven};
        AnnotationDesc[] annotationsDescThen = new AnnotationDesc[]{annotationDescThen};
        AnnotationDesc[] annotationsDescAnd = new AnnotationDesc[]{annotationDescAnd};
        AnnotationDesc[] annotationsDescBut = new AnnotationDesc[]{annotationDescBut};

        AnnotationTypeDoc annotationWhen = mock(AnnotationTypeDoc.class);
        AnnotationTypeDoc annotationGiven = mock(AnnotationTypeDoc.class);
        AnnotationTypeDoc annotationThen = mock(AnnotationTypeDoc.class);
        AnnotationTypeDoc annotationAnd = mock(AnnotationTypeDoc.class);
        AnnotationTypeDoc annotationBut = mock(AnnotationTypeDoc.class);

        when(annotationWhen.name()).thenReturn("When");
        when(annotationGiven.name()).thenReturn("Given");
        when(annotationThen.name()).thenReturn("Then");
        when(annotationAnd.name()).thenReturn("And");
        when(annotationBut.name()).thenReturn("But");

        when(annotationDescWhen.annotationType()).thenReturn(annotationWhen);
        when(annotationDescGiven.annotationType()).thenReturn(annotationGiven);
        when(annotationDescThen.annotationType()).thenReturn(annotationThen);
        when(annotationDescAnd.annotationType()).thenReturn(annotationAnd);
        when(annotationDescBut.annotationType()).thenReturn(annotationBut);

        when(methodWhen.annotations()).thenReturn(annotationsDescWhen);
        when(methodGiven.annotations()).thenReturn(annotationsDescGiven);
        when(methodThen.annotations()).thenReturn(annotationsDescThen);
        when(methodAnd.annotations()).thenReturn(annotationsDescAnd);
        when(methodBut.annotations()).thenReturn(annotationsDescBut);


        when(classDoc.methods()).thenReturn(methodDocs);

        CommentTableGenerator commentTableGenerator = new CommentTableGenerator() {
            @Override
            public CommentRow createCommentRow(MethodDoc methodDoc) {
                return new CommentRow();
            }
        };

        List<CommentTable> commentTables = commentTableGenerator.generate(classDoc);

        CommentTable commentActionsTable = commentTables.get(0);
        assertEquals("ACTIONS", commentActionsTable.getClassification());
        assertEquals("Should have only one item", 1, commentActionsTable.getCommentRows().size());

        CommentTable commentVerificationsTable = commentTables.get(1);
        assertEquals("VERIFICATIONS", commentVerificationsTable.getClassification());
        assertEquals("Should have only one item", 1, commentVerificationsTable.getCommentRows().size());


        CommentTable commentPreconditionsTable = commentTables.get(2);
        assertEquals("PRECONDITIONS", commentPreconditionsTable.getClassification());
        assertEquals("Should have only one item", 1, commentPreconditionsTable.getCommentRows().size());

        CommentTable commentUnknownTable = commentTables.get(3);
        assertEquals("UNKNOWN", commentUnknownTable.getClassification());
        assertEquals("Should have two items", 2, commentUnknownTable.getCommentRows().size());


    }

    @Test
    public void shouldCreateCommentRowWithParametersProperly() {
        MethodDoc methodDoc = mock(MethodDoc.class);
        AnnotationValue annotationValue1 = mock(AnnotationValue.class);
        AnnotationDesc.ElementValuePair elementValue1 = mock(AnnotationDesc.ElementValuePair.class);
        AnnotationDesc.ElementValuePair[] elementValues1 = new AnnotationDesc.ElementValuePair[]{elementValue1};
        AnnotationDesc annotationDesc1 = mock(AnnotationDesc.class);
        AnnotationDesc[] annotationsDesc1 = new AnnotationDesc[]{annotationDesc1};
        ParamTag paramTag = mock(ParamTag.class);
        ParamTag[] paramTags = new ParamTag[]{paramTag};

        when(annotationValue1.value()).thenReturn("^She enters ([^\"]*)$");
        when(elementValue1.value()).thenReturn(annotationValue1);
        when(annotationDesc1.elementValues()).thenReturn(elementValues1);
        when(paramTag.parameterName()).thenReturn("searchTerm");
        when(paramTag.parameterComment()).thenReturn("- term to be searched.");
        when(methodDoc.commentText()).thenReturn("Executes a search given the term provided on the search page.");
        when(methodDoc.name()).thenReturn("she_enters");
        when(methodDoc.annotations()).thenReturn(annotationsDesc1);
        when(methodDoc.paramTags()).thenReturn(paramTags);

        CommentRow actual = commentTableGenerator.createCommentRow(methodDoc);

        assertEquals("Executes a search given the term provided on the search page.\n" +
                "@param searchTerm - term to be searched.", actual.getDescription());
        assertEquals("^She enters ([^\"]*)$", actual.getStep());
        assertEquals("she_enters", actual.getMethodName());
    }

    @Test
    public void shouldCreateCommentRowNoParameters() {
        MethodDoc methodDoc2 = mock(MethodDoc.class);
        AnnotationDesc annotationDesc2 = mock(AnnotationDesc.class);
        AnnotationDesc[] annotationsDesc2 = new AnnotationDesc[]{annotationDesc2};
        ParamTag[] paramTags2 = new ParamTag[]{};
        AnnotationDesc.ElementValuePair elementValue2 = mock(AnnotationDesc.ElementValuePair.class);
        AnnotationDesc.ElementValuePair[] elementValues2 = new AnnotationDesc.ElementValuePair[]{elementValue2};
        AnnotationValue annotationValue2 = mock(AnnotationValue.class);

        when(annotationValue2.value()).thenReturn("^Click on the first link from text results$");
        when(elementValue2.value()).thenReturn(annotationValue2);
        when(methodDoc2.commentText()).thenReturn("User hits the first result available.");
        when(methodDoc2.name()).thenReturn("click_on_the_first_link_from_text_results");
        when(methodDoc2.annotations()).thenReturn(annotationsDesc2);
        when(methodDoc2.paramTags()).thenReturn(paramTags2);
        when(annotationDesc2.elementValues()).thenReturn(elementValues2);

        CommentRow commentRow2 = commentTableGenerator.createCommentRow(methodDoc2);

        assertEquals("User hits the first result available.", commentRow2.getDescription());
        assertEquals("^Click on the first link from text results$", commentRow2.getStep());
        assertEquals("click_on_the_first_link_from_text_results", commentRow2.getMethodName());

    }

}
