package com.selfxdsd.todos;

import com.selfxdsd.api.Commit;
import com.selfxdsd.api.Project;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * Unit tests for {@link DocumentPuzzles}.
 * @author criske
 * @version $Id$
 * @since 0.0.1
 */
public final class DocumentPuzzlesTestCase {

    /**
     * DocumentPuzzles can process Puzzles from xml string.
     * @throws PuzzlesProcessingException Something went wrong.
     */
    @Test
    public void canParsePuzzlesFromXml() throws PuzzlesProcessingException {
        final Puzzles<String> puzzles = new ResourcesPuzzles(
            new DocumentPuzzles(
                Mockito.mock(Project.class),
                Mockito.mock(Commit.class)
            )
        );
        puzzles.process("puzzles.xml");

        MatcherAssert.assertThat(puzzles, Matchers.iterableWithSize(1));
        final Puzzle puzzle = puzzles.iterator().next();
        MatcherAssert.assertThat(puzzle.getId(),
            Matchers.equalTo("516-ffc97ad1"));
        MatcherAssert.assertThat(puzzle.getTicket(),
            Matchers.equalTo(516));
        MatcherAssert.assertThat(puzzle.getEstimate(),
            Matchers.equalTo(15));
        MatcherAssert.assertThat(puzzle.getRole(),
            Matchers.equalTo("DEV"));
        MatcherAssert.assertThat(puzzle.getLines(),
            Matchers.equalTo("L61-L63"));
        MatcherAssert.assertThat(puzzle.getBody(),
            Matchers.equalTo("This has to be fixed later..."));
        MatcherAssert.assertThat(puzzle.getFile(),
            Matchers.equalTo("src/test/java/org/takes/SomeTest.java"));
        MatcherAssert.assertThat(puzzle.getAuthor(),
            Matchers.equalTo("Yegor Bugayenko"));
        MatcherAssert.assertThat(puzzle.getEmail(),
            Matchers.equalTo("yegor256@gmail.com"));
        MatcherAssert.assertThat(puzzle.getTime(),
            Matchers.equalTo("2018-01-01T21:09:03Z"));
        MatcherAssert.assertThat(puzzle.issueTitle(),
            Matchers.equalTo("SomeTest.java: This has to be fixed later..."));
    }

}


