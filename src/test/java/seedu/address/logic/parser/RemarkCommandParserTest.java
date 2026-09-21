package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class RemarkCommandParserTest {
    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_remark_acceptsTextAndRemoval() throws Exception {
        assertEquals(new RemarkCommand(Index.fromOneBased(1), "Likes to swim"),
                parser.parseCommand("remark 1 r/Likes to swim"));
        assertEquals(new RemarkCommand(Index.fromOneBased(1), ""), parser.parseCommand("remark 1 r/"));
        assertEquals(new RemarkCommand(Index.fromOneBased(1), ""), parser.parseCommand("remark 1"));
    }

    @Test
    public void parseCommand_remark_rejectsInvalidIndexAndRepeatedPrefix() {
        assertThrows(ParseException.class, () -> parser.parseCommand("remark 0 r/test"));
        assertThrows(ParseException.class, () -> parser.parseCommand("remark r/test"));
        assertThrows(ParseException.class, () -> parser.parseCommand("remark 1 r/first r/second"));
    }
}
