package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Remark;

public class RemarkCommandParserTest {
    private final RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    public void parse_text_success() {
        assertParseSuccess(parser, "1 r/Likes to swim",
                new RemarkCommand(Index.fromOneBased(1), new Remark("Likes to swim")));
    }

    @Test
    public void parse_emptyOrMissingRemark_success() {
        RemarkCommand expected = new RemarkCommand(Index.fromOneBased(1), new Remark(""));
        assertParseSuccess(parser, "1 r/", expected);
        assertParseSuccess(parser, "1", expected);
    }

    @Test
    public void parse_whitespaceAndPunctuation_success() {
        assertParseSuccess(parser, "  2   r/  Likes swimming, music & 中文!  ",
                new RemarkCommand(Index.fromOneBased(2), new Remark("Likes swimming, music & 中文!")));
    }

    @Test
    public void parse_invalidIndexes_failure() {
        String message = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE);
        for (String input : new String[]{"", "r/note", "0 r/note", "-1 r/note", "1.5 r/note",
            "one r/note", "2147483648 r/note", "1 extra r/note"}) {
            assertParseFailure(parser, input, message);
        }
    }

    @Test
    public void parse_repeatedPrefix_failure() {
        assertThrows(ParseException.class, () -> parser.parse("1 r/first r/second"));
    }

    @Test
    public void parse_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }
}
