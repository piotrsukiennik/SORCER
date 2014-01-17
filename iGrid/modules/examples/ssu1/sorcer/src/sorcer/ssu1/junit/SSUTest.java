package sorcer.ssu1.junit;

import org.junit.Test;
import sorcer.core.SorcerConstants;
import sorcer.core.exertion.NetJob;
import sorcer.service.Context;
import sorcer.service.Job;
import sorcer.service.ServiceExertion;
import sorcer.service.Task;
import sorcer.ssu1.provider.ServiceSort;
import sorcer.ssu2.provider.ServiceHoare;
import sorcer.ssu3.provider.ServiceFirstPrime;
import sorcer.util.Sorcer;

import java.rmi.RMISecurityManager;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static sorcer.eo.operator.*;
import static sorcer.eo.operator.in;

public class SSUTest implements SorcerConstants {

    static {
        ServiceExertion.debug = true;
        System.setProperty("java.security.policy", Sorcer.getHome() + "/configs/policy.all");
        System.setSecurityManager(new RMISecurityManager());
        Sorcer.setCodeBase(new String[] {
         "jeri-ssu1-dl.jar",
         "jeri-ssu2-dl.jar",
         "jeri-ssu3-dl.jar"
        });
        System.out.println("CLASSPATH :" + System.getProperty("java.class.path"));
        System.setProperty("java.protocol.handler.pkgs", "sorcer.util.url|org.rioproject.url");
    }

    /**
     *
     * Janusz ma strasznie nudną prace (może i nudną ale bardzo dobrze płatną! - wtrącił Janusz).
     * Polega ona na znajdowaniu k-tej co do wielkości liczby należącej do zbioru A
     * i wyznaczanie większej od niej liczby pierwszej (mówiłem że nudna...).
     *
     * W związku z postępem technologicznym i coraz większymi zbiorami danych,
     * praca Janusza stała się bardzo żmudna i jako spryciarz zaczął automatyzować proces.
     * Postanowił zrobić algorytm, który będzie sortował zbiór liczb
     * (wykorzystał MergeSort - nice!).
     *
     * Jak się okazało była to pewna optymalizacja ale chyba nie o taką automatyzację Januszowi
     * chodziło bo i tak większość rzeczy musiał robić ręcznie. Niestety jego wiedza programistyczna
     * nie była zbyt duża w związku z czym postanowił zainwestowac w literature.
     * Thinking in Java (hmmm każdy z nas tak zaczynał).
     *
     * Był bardzo pilnym czytelnikiem i już po paru dniach miał gotową funkcję
     * do znajdowania k-tej co do wielkości liczby. Oczywiście dopisanie do tego Sita Eratostenesa
     * służącego do znajdowania liczby pierwszej nie było już dużym problemem.
     *
     * Powyższy opis postaramy się przedstawić poniższym TestCase:
     *
     * SSU1 - testMergeSort()
     * sleep(3) - Janusz czyta Thinking in Java
     * SSU2 - testHoareAlghoritm
     * SSU3 - testFirstPrimeNumber
     * @throws Exception
     */
    @Test
    public void testMergeSort() throws Exception{
        int[] set_a = new int[] { 99, 12, 1, 5, 23, 13 };
        int k =2;

        Task t1 = task("t1",
        sig("sort", ServiceSort.class, "SSU1"),
            context("ssu1-sort-test1",
            in( "sort/array", set_a )
        ));

        Task t2 = task("t2",
         sig("search", ServiceHoare.class, "SSU2"),
            context("ssu2-search-test2",
            in( "search/k", k )
         ));

        Task t3 = task("t3",
         sig("search", ServiceFirstPrime.class, "SSU3"),
            context("ssu3-sort-test1")
        );

        Job job = new NetJob("3tasks");
        job.addExertion( t1 );
        job.addExertion( t2 );
        job.addExertion( t3 );
        t1.getContext().connect("sort/array/sort",  "search/array", t2.getContext());
        t2.getContext().connect("search/array/k_largest", "search/k", t3.getContext());
        job = exert(job);

        Integer largerPrime = (Integer) get( job, "3tasks/t3/search/k/prime" );
        System.out.println(context(job));
        System.out.println(largerPrime);
    }

}
