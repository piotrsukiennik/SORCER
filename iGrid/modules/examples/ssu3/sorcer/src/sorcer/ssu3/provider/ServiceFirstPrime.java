package sorcer.ssu3.provider;

import sorcer.service.Context;

import java.rmi.Remote;
import java.rmi.RemoteException;

@SuppressWarnings("rawtypes")
public interface ServiceFirstPrime extends Remote {

	public Context search( Context account ) throws RemoteException, FirstPrimeException;

	public final static String SEARCH = "search";
    public final static String COMMENT = "comment";
    public final static String K = "k";
    public final static String PRIME = "prime";
}
