package sorcer.ssu3.junit;

import junit.framework.TestCase;
import sorcer.ssu3.provider.FirstPrime;
import sorcer.ssu3.provider.FirstPrimeException;
import sorcer.ssu3.provider.FirstPrimeImpl;

import java.rmi.RemoteException;

public class FirstPrimeTest extends TestCase {

    public void testFirstPrimeNumber() throws RemoteException, FirstPrimeException{
        FirstPrime fpn = new FirstPrimeImpl();
        assertEquals(1, fpn.search(0));
        assertEquals(2, fpn.search(1));
        assertEquals(3, fpn.search(2));
        assertEquals(5, fpn.search(3));
        assertEquals(5, fpn.search(3));
        assertEquals(10007, fpn.search(9990));
        assertEquals(214748383, fpn.search(214748364));
    }
}
