package sorcer.ssu2.junit;

import junit.framework.TestCase;
import sorcer.ssu2.provider.Hoare;
import sorcer.ssu2.provider.HoareException;
import sorcer.ssu2.provider.HoareImpl;

import java.rmi.RemoteException;

public class HoareTest extends TestCase {

    public void testSearchKElement() throws Exception{
        Hoare ha = new HoareImpl();
        int[] base = {23, 13, 7, 3, 21};
        assertEquals(3, ha.search(base, 1));
        assertEquals(7, ha.search(base, 2));
        assertEquals(13, ha.search(base, 3));
        assertEquals(21, ha.search(base, 4));
        assertEquals( 23, ha.search( base, 5 ) );
    }

    public void testException() throws IllegalArgumentException {
        Hoare ha = new HoareImpl();
        int[] base = {23, 13};
        try {
            ha.search(base, base.length + 1);
            fail("Searched array has fewer elements");
        } catch (HoareException e) {

        } catch ( RemoteException e){

        }

    }
}
