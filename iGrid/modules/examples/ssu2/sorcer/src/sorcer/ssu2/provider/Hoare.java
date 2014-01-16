package sorcer.ssu2.provider;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Hoare extends Remote {
    public int search(int[] base, int k) throws RemoteException, HoareException;
}
