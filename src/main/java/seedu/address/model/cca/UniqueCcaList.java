package seedu.address.model.cca;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.cca.exceptions.DuplicateCcaException;
import seedu.address.model.cca.exceptions.CcaNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code Person#isSamePerson(Person)}. As such, adding and updating of
 * persons uses Person#isSamePerson(Person) for equality so as to ensure that the person being added or updated is
 * unique in terms of identity in the UniqueCcaList. However, the removal of a person uses Person#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Person#isSamePerson(Person)
 */
public class UniqueCcaList implements Iterable<Cca> {

    private final ObservableList<Cca> internalList = FXCollections.observableArrayList();
    private final ObservableList<Cca> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Cca toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameCca);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(Cca toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateCcaException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedCca}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedCca} must not be the same as another existing person in the list.
     */
    public void setCca(Cca target, Cca editedCca) {
        requireAllNonNull(target, editedCca);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new CcaNotFoundException();
        }

        if (!target.isSameCca(editedCca) && contains(editedCca)) {
            throw new DuplicateCcaException();
        }

        internalList.set(index, editedCca);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(Cca toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new CcaNotFoundException();
        }
    }

    public void setCcas(UniqueCcaList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setCcas(List<Cca> ccas) {
        requireAllNonNull(ccas);
        if (!ccasAreUnique(ccas)) {
            throw new DuplicateCcaException();
        }

        internalList.setAll(ccas);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Cca> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Cca> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.cca.UniqueCcaList // instanceof handles nulls
                        && internalList.equals(((seedu.address.model.cca.UniqueCcaList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code persons} contains only unique ccas.
     */
    private boolean ccasAreUnique(List<Cca> ccas) {
        for (int i = 0; i < ccas.size() - 1; i++) {
            for (int j = i + 1; j < ccas.size(); j++) {
                if (ccas.get(i).isSameCca(ccas.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
