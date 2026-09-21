package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class RemarkTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Remark(null));
    }

    @Test
    public void constructor_acceptsUnrestrictedText() {
        for (String value : new String[]{"", " ", "Likes swimming! 中文", "line one\nline two"}) {
            assertEquals(value, new Remark(value).value);
        }
    }

    @Test
    public void equalsAndHashCode_compareContent() {
        Remark remark = new Remark("note");
        assertEquals(remark, remark);
        assertEquals(remark, new Remark("note"));
        assertEquals(remark.hashCode(), new Remark("note").hashCode());
        assertFalse(remark.equals(null));
        assertFalse(remark.equals("note"));
        assertFalse(remark.equals(new Remark("other")));
        assertEquals("note", remark.toString());
    }
}
