package sorcer.ssu1.provider;

import sorcer.service.Context;

import java.rmi.Remote;
import java.rmi.RemoteException;

@SuppressWarnings("rawtypes")
public interface ServiceSort extends Remote {

	public Context sort( Context account ) throws RemoteException, SortException;

	public final static String SORT = "sort";
    public final static String COMMENT = "comment";
    public final static String ARRAY = "array";
}
