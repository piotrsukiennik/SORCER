package sorcer.ssu1.junit;

import junit.framework.TestCase;
import sorcer.ssu1.provider.MergeSortImpl;
import sorcer.ssu1.provider.Sort;

import java.util.Arrays;

public class SortImplTest extends TestCase {

    public void testMergeSort() throws Exception{
        Sort ms = new MergeSortImpl();
        int[] base = {23, 13};
        int[] result = {13, 23};
        assertTrue(Arrays.equals(result, ms.sort(base)));
    }

    public void testMergeSortForEmptyArrays()  throws Exception {
        Sort ms = new MergeSortImpl();
        int[] base = {};
        int[] result = {};
        assertTrue(Arrays.equals(result, ms.sort(base)));
    }

    public void testMergeSortForTheSameArrays() throws Exception {
        Sort ms = new MergeSortImpl();
        int[] base = {0,1,2,3,4,5,6,7,8,9,10,11,12,13};
        int[] result = {0,1,2,3,4,5,6,7,8,9,10,11,12,13};
        assertTrue(Arrays.equals(result, ms.sort(base)));
    }
}
