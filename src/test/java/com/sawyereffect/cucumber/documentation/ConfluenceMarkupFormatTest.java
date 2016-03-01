package com.sawyereffect.cucumber.documentation;


import com.sun.javadoc.ClassDoc;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class ConfluenceMarkupFormatTest {

    @Test
    public void shouldGetAllClassesDocumentation() throws IOException {
        ConfluenceMarkupFormat confluenceMarkupFormat = new ConfluenceMarkupFormat();

        ClassDoc classDoc = mock(ClassDoc.class);
        FileWriter fileWriter = mock(FileWriter.class);
        CommentTableGenerator commentTableGenerator = mock(CommentTableGenerator.class);
        String comment = "Google search page\n" +
                " This page is used to search for terms in the outside world.";

        CommentTable commentTableActions = new CommentTable("ACTIONS", "When");
        List<CommentRow> commentRowActions = new ArrayList<>();
        CommentRow action1 = new CommentRow();
        CommentRow action2 = new CommentRow();

        CommentTable commentTableVerifications = new CommentTable("VERIFICATIONS", "Then");
        List<CommentRow> commentRowVerifications = new ArrayList<>();
        CommentRow verification = new CommentRow();

        List<CommentTable> commentTables = new ArrayList<>();

        action1.setDescription("Executes a search given the term provided on the search page.\n" +
                "@param searchTerm - term to be searched.");
        action1.setMethodName("she_enters");
        action1.setStep("^She enters ([^\"]*)$");
        commentRowActions.add(action1);

        action2.setDescription("User hits the first result available.");
        action2.setMethodName("click_on_the_first_link_from_text_results");
        action2.setStep("^Click on the first link from text results$");
        commentRowActions.add(action2);

        commentTableActions.setCommentRows(commentRowActions);

        verification.setStep("^Verify ([^\"]*) results should be displayed$");
        verification.setDescription("Verify from results given an specific text is displayed.\n" +
                "@param results - text to be searched for validation.");
        verification.setMethodName("results_should_be_displayed");
        commentRowVerifications.add(verification);

        commentTableVerifications.setCommentRows(commentRowVerifications);

        commentTables.add(commentTableActions);
        commentTables.add(commentTableVerifications);

        when(commentTableGenerator.generate(classDoc)).thenReturn(commentTables);
        when(classDoc.commentText()).thenReturn(comment);
        when(classDoc.qualifiedName()).thenReturn("com.sawyereffect.steps.SearchPageSteps");

        confluenceMarkupFormat.parse(classDoc, fileWriter, commentTableGenerator);

        verify(fileWriter).write("\\\\\n" +
                "h2. com.sawyereffect.steps.SearchPageSteps\n*Google search page*\n" +
                " This page is used to search for terms in the outside world.\n||ACTIONS||\n" +
                "||Method||Step||Description||\n" +
                "|she_enters|^She enters ([^\"]*)$|Executes a search given the term provided on the search page.\n" +
                "@param searchTerm - term to be searched.|\n" +
                "|click_on_the_first_link_from_text_results|^Click on the first link from text results$|User hits the first result available.|\n" +
                "\\\\\n" +
                "||VERIFICATIONS||\n" +
                "||Method||Step||Description||\n" +
                "|results_should_be_displayed|^Verify ([^\"]*) results should be displayed$|Verify from results given an specific text is displayed.\n" +
                "@param results - text to be searched for validation.|\n");

    }

}
