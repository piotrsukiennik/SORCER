package sorcer.ssu2.provider;

import sorcer.service.Context;

import java.rmi.Remote;
import java.rmi.RemoteException;

@SuppressWarnings("rawtypes")
public interface ServiceHoare extends Remote {

	public Context search( Context account ) throws RemoteException, sorcer.ssu2.provider.HoareException;

	public final static String SEARCH = "search";
    public final static String COMMENT = "comment";
    public final static String ARRAY = "array";
    public final static String K = "k";
    public final static String K_LARGEST = "k_largest";
}
