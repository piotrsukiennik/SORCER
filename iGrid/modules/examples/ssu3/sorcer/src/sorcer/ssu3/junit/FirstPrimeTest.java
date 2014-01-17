package sorcer.ssu3.junit;

import org.junit.Test;
import sorcer.core.SorcerConstants;
import sorcer.service.Context;
import sorcer.service.ServiceExertion;
import sorcer.service.Task;
import sorcer.ssu3.provider.ServiceFirstPrime;
import sorcer.util.Sorcer;
import java.rmi.RMISecurityManager;
import static org.junit.Assert.assertEquals;
import static sorcer.eo.operator.*;
import static sorcer.eo.operator.value;

public class FirstPrimeTest implements SorcerConstants {

    static {
        ServiceExertion.debug = true;
        System.setProperty("java.security.policy", Sorcer.getHome() + "/configs/policy.all");
        System.setSecurityManager(new RMISecurityManager());
        Sorcer.setCodeBase(new String[] { "jeri-ssu3-dl.jar" });
        System.out.println("CLASSPATH :" + System.getProperty("java.class.path"));
        System.setProperty("java.protocol.handler.pkgs", "sorcer.util.url|org.rioproject.url");
    }

    @Test
    public void testFirstPrimeNumber() throws Exception {
        Integer k=9990;
        Integer shouldBe = 10007;
        Task t1 = task("t1",
         sig("search", ServiceFirstPrime.class, "SSU3"),
         context("ssu3-sort-test1", in( "search/k", k  )));
        t1 = exert(t1);
        Integer kPrime = (Integer)get(t1,  "search/k/prime" );
        System.out.println(context(t1));
        assertEquals( shouldBe, kPrime );
    }
}
