package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/** Parses a displayed person index and an optional remark. */
public class RemarkCommandParser implements Parser<RemarkCommand> {
    @Override
    public RemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap values = ArgumentTokenizer.tokenize(args, PREFIX_REMARK);
        values.verifyNoDuplicatePrefixesFor(PREFIX_REMARK);
        Index index;
        try {
            index = ParserUtil.parseIndex(values.getPreamble());
        } catch (ParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE), e);
        }
        return new RemarkCommand(index, values.getValue(PREFIX_REMARK).orElse(""));
    }
}
