package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.testutil.PersonBuilder;

public class RemarkCommandTest {
    @TempDir
    public Path temporaryFolder;

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void execute_addOverwriteRemove_preservesOtherFields() throws Exception {
        Person original = model.getFilteredPersonList().get(0);
        parser.parseCommand("remark 1 r/Likes swimming").execute(model);
        assertEquals(new PersonBuilder(original).withRemark("Likes swimming").build(),
                model.getFilteredPersonList().get(0));
        parser.parseCommand("remark 1 r/Likes running").execute(model);
        assertEquals("Likes running", model.getFilteredPersonList().get(0).getRemark().value);
        CommandResult result = parser.parseCommand("remark 1 r/").execute(model);
        assertEquals(original, model.getFilteredPersonList().get(0));
        assertEquals(String.format(RemarkCommand.MESSAGE_DELETE_REMARK_SUCCESS,
                seedu.address.logic.Messages.format(original)), result.getFeedbackToUser());
    }

    @Test
    public void execute_filteredList_targetsDisplayedPersonAndRejectsOutOfRange() throws Exception {
        Person target = model.getFilteredPersonList().get(1);
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(
                List.of(target.getName().fullName.split(" ")[0])));
        assertEquals(1, model.getFilteredPersonList().size());
        assertThrows(CommandException.class, () -> parser.parseCommand("remark 2 r/Wrong person").execute(model));
        parser.parseCommand("remark 1 r/Selected person").execute(model);
        assertEquals(new PersonBuilder(target).withRemark("Selected person").build(),
                model.getAddressBook().getPersonList().get(1));
        assertEquals("", model.getAddressBook().getPersonList().get(0).getRemark().value);
    }

    @Test
    public void execute_editOtherDetails_preservesRemark() throws Exception {
        parser.parseCommand("remark 1 r/Keep this note").execute(model);
        parser.parseCommand("edit 1 p/12345678").execute(model);
        assertEquals("Keep this note", model.getFilteredPersonList().get(0).getRemark().value);
    }

    @Test
    public void storage_roundTripAndLegacyFile_preservesContacts() throws Exception {
        parser.parseCommand("remark 1 r/Likes swimming").execute(model);
        Path file = temporaryFolder.resolve("addressbook.json");
        JsonAddressBookStorage storage = new JsonAddressBookStorage(file);
        storage.saveAddressBook(model.getAddressBook());
        assertEquals(model.getAddressBook(), storage.readAddressBook().orElseThrow());
        String json = Files.readString(file);
        json = json.replaceAll("(?m)^.*\"remark\".*\\R", "");
        Files.writeString(file, json);
        assertEquals(getTypicalAddressBook(), storage.readAddressBook().orElseThrow());
    }
}
