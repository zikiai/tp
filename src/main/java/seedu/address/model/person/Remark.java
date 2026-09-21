package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/** An optional, immutable remark about a person. Empty remarks are allowed. */
public class Remark {
    public final String value;

    /** Creates a remark with no content restrictions. */
    public Remark(String value) {
        requireNonNull(value);
        this.value = value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof Remark remark && value.equals(remark.value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }
}
