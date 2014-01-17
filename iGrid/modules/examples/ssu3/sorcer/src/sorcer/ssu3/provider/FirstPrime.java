package sorcer.ssu3.provider;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FirstPrime extends Remote {
    public int search(int n) throws RemoteException, FirstPrimeException;
}
