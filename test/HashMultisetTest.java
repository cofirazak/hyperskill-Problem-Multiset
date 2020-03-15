import static org.hamcrest.CoreMatchers.is;

import cleanver.Multiset;
import cleanver.HashMultiset;
import java.util.Set;
import org.junit.Assert;

class HashMultisetTest {

    @SuppressWarnings("MagicCharacter")
    public static void main(String[] regs) {

        final Multiset<Character> testMap = new HashMultiset<>();
        testMap.add('A');
        testMap.add('A');
        testMap.add('c');
        testMap.add('c');
        testMap.add('e');

        Assert
            .assertThat(
                "Checking methods add() and toSet(). And correctness of unique elements after add.",
                Set.of('A', 'c', 'e'),
                is(testMap.toSet()));
        Assert.assertThat(
            "Checking method getMultiplicity(). And correctness of multiplicity of elements after add.",
            new int[]{2, 2, 1},
            is(new int[]{testMap.getMultiplicity('A'), testMap.getMultiplicity('c'), testMap.getMultiplicity('e')}));
        Assert
            .assertThat("Checking method size(). And correctness of elements size after add.", 5,
                is(testMap.size()));

        testMap.remove('d');
        testMap.remove('c');
        testMap.remove('e');
        testMap.remove('e');

        Assert
            .assertThat("Checking correctness of unique elements after remove.", Set.of('A', 'c'),
                is(testMap.toSet()));

        Assert.assertThat(
            "Checking correctness of multiplicity of elements after remove.",
            new int[]{2, 1, 0},
            is(new int[]{testMap.getMultiplicity('A'), testMap.getMultiplicity('c'), testMap.getMultiplicity('e')}));

        Assert
            .assertThat("Checking method size(). And correctness of elements size after remove.", 3,
                is(testMap.size()));

        Assert
            .assertThat(
                "Checking method contains(). And correctness of elements containing after remove.",
                true,
                is(testMap.contains('A')));
        Assert
            .assertThat(
                "Checking method contains(). And correctness of elements containing after remove.",
                false,
                is(testMap.contains('e')));

        final Multiset<Character> charMSet = new HashMultiset<>();
        charMSet.add('R');
        charMSet.add('R');
        testMap.union(charMSet);

        Assert
            .assertThat(
                "Checking method union(). And correctness of elements containing after union.",
                true,
                is(testMap.contains('R')));
        Assert
            .assertThat(
                "Checking method numberOfUniqueElements(). And correctness of unique elements after union.",
                3,
                is(testMap.numberOfUniqueElements()));
        Assert
            .assertThat("Checking correctness of new element multiplicity after union.",
                2,
                is(testMap.getMultiplicity('R')));

        charMSet.add('s');
        testMap.intersect(charMSet);

        Assert
            .assertThat("Checking correctness of unique elements after intersect.",
                1,
                is(testMap.numberOfUniqueElements()));

        System.out.println("All tests passed successful!");
    }
}
