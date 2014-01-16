package sorcer.ssu1.provider;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Sort  extends Remote {
    public int[] sort(int[] base) throws RemoteException, SortException;
}
