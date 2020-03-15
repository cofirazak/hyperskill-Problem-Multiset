package cleanver;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HashMultiset<E> implements Multiset<E> {

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
