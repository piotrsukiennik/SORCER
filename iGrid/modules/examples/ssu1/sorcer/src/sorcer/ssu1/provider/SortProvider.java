package sorcer.ssu1.provider;

import com.sun.jini.start.LifeCycle;
import sorcer.core.SorcerConstants;
import sorcer.core.provider.ServiceTasker;
import sorcer.service.Context;
import sorcer.util.Log;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("rawtypes")
public class SortProvider extends ServiceTasker implements Sort,
 ServiceSort, SorcerConstants {

	private static Logger logger = Log.getTestLog();

    private Class<Sort> sorterClazz;

	public SortProvider( String[] args, LifeCycle lifeCycle ) throws Exception {
		super(args, lifeCycle);
        String clazz = getProperty("sorcer.ssu1.provider.Sort.impl");
        sorterClazz = (Class<Sort>)Class.forName( clazz );
	}


    @Override
    public int[] sort( int[] base ) throws SortException {
        try{
            Sort sort = sorterClazz.newInstance();
            return sort.sort( base );
        }catch ( Exception e ){
            throw new SortException(e );
        }

    }

    public Context sort(Context context) throws RemoteException, SortException {
		return process(context, ServiceSort.SORT);
	}

	private Context process(Context context, String selector)
			throws RemoteException, SortException {
		try {
            if (logger.isLoggable( Level.INFO )){
                logger.info("input context: \n" + context);
            }
            int[] result=null;
			if (ServiceSort.SORT.equals( selector )) {
                String valueKey = ServiceSort.SORT + CPS  + ServiceSort.ARRAY;
                int[] input = ( int[] ) context.getValue( valueKey );
                if (logger.isLoggable( Level.INFO )){
                    logger.info("Input -> "+valueKey+" = " + Arrays.toString( input ));
                }
                result = sort( input );
			}
			// set return value
			if (context.getReturnPath() != null) {
				context.setReturnValue(result);
			}
			logger.info(selector + " result: \n" + Arrays.toString( result ));
			String outputMessage = "processed by " + getHostname();
			context.putValue(selector  + CPS + ServiceSort.ARRAY + CPS + ServiceSort.SORT, result);
			context.putValue( ServiceSort.COMMENT, outputMessage);
		} catch (Exception ex) {
			throw new sorcer.ssu1.provider.SortException(ex);
		}
		return context;
	}

	/**
	 * Returns name of the local host.
	 *
	 * @return local host name
	 * @throws java.net.UnknownHostException
	 */
	private String getHostname() throws UnknownHostException {
		return InetAddress.getLocalHost().getHostName();
	}
}
