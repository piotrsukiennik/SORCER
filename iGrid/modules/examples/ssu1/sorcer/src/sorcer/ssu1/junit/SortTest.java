package sorcer.ssu1.junit;

import sorcer.core.SorcerConstants;
import sorcer.service.Context;
import sorcer.service.ServiceExertion;
import sorcer.service.Task;
import sorcer.ssu1.provider.MergeSortImpl;
import sorcer.ssu1.provider.ServiceSort;
import sorcer.util.Sorcer;

import java.rmi.RMISecurityManager;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static sorcer.eo.operator.*;
import static sorcer.eo.operator.value;

public class SortTest implements SorcerConstants {

    static {
        ServiceExertion.debug = true;
        System.setProperty("java.security.policy", Sorcer.getHome() + "/configs/policy.all");
        System.setSecurityManager(new RMISecurityManager());
        Sorcer.setCodeBase(new String[] { "jeri-ssu1-dl.jar" });
        System.out.println("CLASSPATH :" + System.getProperty("java.class.path"));
        System.setProperty("java.protocol.handler.pkgs", "sorcer.util.url|org.rioproject.url");
    }

    @Test
    public void testMergeSort() throws Exception{
        int[] shouldBe = new int[] { 1 ,5 ,12 ,13 ,23 ,99 };
        Task t1 = task("t1",
         sig("sort", ServiceSort.class, "SSU1"),
         context("ssu1-sort-test1", in( "sort/array", new int[] { 99, 12, 1, 5, 23, 13 } )));
        t1 = exert( t1 );
        int[] arraySorted = (int[])get( t1, "sort/array/sort" );
        System.out.println(context(t1));
        assertArrayEquals( arraySorted, shouldBe );
    }
    @Test
    public void testMergeSortForEmptyArrays()  throws Exception {
        int[] base = {};
        Task t2 = task("t2",
         sig("sort", ServiceSort.class, "SSU1"),
         context("ssu1-sort-test2", in( "sort/array", base )));
        t2 = exert( t2 );
        int[] arraySorted = (int[])get( t2,  "sort/array/sort" );
        System.out.println(context(t2));
        assertArrayEquals( arraySorted, base );
    }
    @Test
    public void testMergeSortForTheSameArrays() throws Exception {
        int[] base = {0,1,2,3,4,5,6,7,8,9,10,11,12,13};
        Task t3 = task("t3",
         sig("sort", ServiceSort.class, "SSU1"),
         context("ssu1-sort-test3", in( "sort/array", base )));
        t3 = exert(t3);
        int[] arraySorted = (int[])get( t3,  "sort/array/sort" );
        System.out.println(context(t3));
        assertArrayEquals( arraySorted, base );
    }

}
