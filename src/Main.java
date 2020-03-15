import java.util.HashMap;
import java.util.Map;
import java.util.Set;
// These imports are for the tests from hyperSkill to run:
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

interface Multiset<E> {

    /**
     * Add an element to the multiset. It increases the multiplicity of the element by 1.
     */
    void add(E elem);

    /**
     * Remove an element from the multiset. It decreases the multiplicity of the element by 1.
     */
    void remove(E elem);

    /**
     * Unite this multiset with another one. The result is the modified multiset (this). It will
     * contain all elements that are present in at least one of the initial multisets. The
     * multiplicity of each element is equal to the maximum multiplicity of the corresponding
     * elements in both multisets.
     */
    void union(Multiset<E> other);

    /**
     * Intersect this multiset with another one. The result is the modified multiset (this). It will
     * contain all elements that are present in the both multisets. The multiplicity of each element
     * is equal to the minimum multiplicity of the corresponding elements in the intersecting
     * multisets.
     */
    void intersect(Multiset<E> other);

    /**
     * Returns multiplicity of the given element. If the set doesn't contain it, the multiplicity is
     * 0
     */
    int getMultiplicity(E elem);

    /**
     * Check if the multiset contains an element, i.e. the multiplicity > 0
     */
    boolean contains(E elem);

    /**
     * The number of unique elements, that is how many different elements there are in a multiset.
     */
    int numberOfUniqueElements();

    /**
     * The size of the multiset, including repeated elements
     */
    int size();

    /**
     * The set of unique elements (without repeating)
     */
    Set<E> toSet();
}

class HashMultiset<E> implements Multiset<E> {

    private Map<E, Integer> map = new HashMap<>();

    @Override
    public final void add(E elem) {
        map.put(elem, getMultiplicity(elem) + 1);
    }

    @Override
    public final void remove(E elem) {
        map.put(elem, getMultiplicity(elem) - 1);
        if (getMultiplicity(elem) < 1) {
            map.remove(elem);
        }
    }

    @Override
    public final void union(Multiset<E> other) {
        other.toSet()
            .forEach(e -> map.put(e, Math.max(getMultiplicity(e), other.getMultiplicity(e))));
    }

    @Override
    public final void intersect(Multiset<E> other) {
        final Map<E, Integer> newMap = new HashMap<>(map.size());
        map.forEach((key, val) -> {
            if (other.getMultiplicity(key) > 0) {
                newMap.put(key, Math.min(val, other.getMultiplicity(key)));
            }
        });
        map = newMap;
    }

    @Override
    public final int getMultiplicity(E elem) {
        return map.getOrDefault(elem, 0);
    }

    @Override
    public final boolean contains(E elem) {
        return map.containsKey(elem) && getMultiplicity(elem) > 0;
    }

    @Override
    public final int numberOfUniqueElements() {
        return map.size();
    }

    @Override
    public final int size() {
        return map.values().stream().mapToInt(Integer::intValue).sum();
    }

    @Override
    public final Set<E> toSet() {
        //TODO Returning a new HashSet<> object can helps us avoid ConcurrentModificationException.
        return map.keySet();
    }
}
