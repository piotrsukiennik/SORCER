package sorcer.ssu2.junit;

import org.junit.Test;
import sorcer.core.SorcerConstants;
import sorcer.service.Context;
import sorcer.service.ServiceExertion;
import sorcer.service.Task;
import sorcer.ssu2.provider.ServiceHoare;
import sorcer.util.Sorcer;
import java.rmi.RMISecurityManager;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static sorcer.eo.operator.*;
import static sorcer.eo.operator.value;

public class HoareTest implements SorcerConstants {

    static {
        ServiceExertion.debug = true;
        System.setProperty("java.security.policy", Sorcer.getHome() + "/configs/policy.all");
        System.setSecurityManager(new RMISecurityManager());
        Sorcer.setCodeBase(new String[] { "jeri-ssu2-dl.jar" });
        System.out.println("CLASSPATH :" + System.getProperty("java.class.path"));
        System.setProperty("java.protocol.handler.pkgs", "sorcer.util.url|org.rioproject.url");
    }

    @Test
    public void testSearchKElement() throws Exception{
        int[] base = {23, 13, 7, 3, 21};
        Integer secondLargest = 7;
        Task t1 = task( "t1",
         sig( "search", ServiceHoare.class, "SSU2" ),
         context( "ssu2-search-test1",
          in( "search/array", base ),
          in( "search/k", 2 )
         ) );
        t1 = exert(t1);
        Integer kLargest =  (Integer) get( t1,"search/array/k_largest" );
        System.out.println( context(t1) );
        assertEquals( kLargest, secondLargest );
    }

    @Test
    public void testException()  throws Exception{
        int[] base = {23, 13};
        Task t2 = task("t2",
         sig("search", ServiceHoare.class, "SSU2"),
         context("ssu2-search-test2",
          in( "search/array", base),
          in( "search/k", base.length+1)
         ));
        t2 = exert(t2);
        Integer kLargest = (Integer) get(t2, "search/array/k_largest" );
        System.out.println(context(t2));
        assertNull(kLargest);
        //ha.search(base, base.length + 1);
    }


}
