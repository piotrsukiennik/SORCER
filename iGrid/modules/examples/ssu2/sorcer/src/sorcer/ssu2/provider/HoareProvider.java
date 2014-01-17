package sorcer.ssu2.provider;

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
public class HoareProvider extends ServiceTasker implements Hoare,
 ServiceHoare, SorcerConstants {

	private static Logger logger = Log.getTestLog();

    private Class<Hoare> sorterClazz;

	public HoareProvider( String[] args, LifeCycle lifeCycle ) throws Exception {
		super(args, lifeCycle);
        String clazz = getProperty("sorcer.ssu2.provider.Hoare.impl");
        sorterClazz = (Class<Hoare>)Class.forName( clazz );
	}

    @Override
    public int search( int[] base, int k ) throws RemoteException, HoareException {
        try{
            Hoare sort = sorterClazz.newInstance();
            return sort.search( base,k );
        }catch ( Exception e ){
            throw new sorcer.ssu2.provider.HoareException( e );
        }

    }

    public Context search(Context context) throws RemoteException, sorcer.ssu2.provider.HoareException {
		return process(context, ServiceHoare.SEARCH);
	}

	private Context process(Context context, String selector)
			throws RemoteException, sorcer.ssu2.provider.HoareException {
		try {
            if (logger.isLoggable( Level.INFO )){
                logger.info("input context: \n" + context);
            }
            int result=-1;
			if (ServiceHoare.SEARCH.equals( selector )) {
                String arrayKey = ServiceHoare.SEARCH + CPS  + ServiceHoare.ARRAY;
                String kKey = ServiceHoare.SEARCH + CPS  + ServiceHoare.K;
                int[] input = ( int[] ) context.getValue( arrayKey );
                Integer inputK = (Integer) context.getValue( kKey );
                if (logger.isLoggable( Level.INFO )){
                    logger.info("Input -> "+arrayKey+" = " + Arrays.toString( input ));
                    logger.info("Input -> "+kKey+" = " + inputK );
                }
                result = search( input, inputK );
			}
			// set return value
			if (context.getReturnPath() != null) {
				context.setReturnValue(result);
			}
			logger.info(selector + " result: \n" +result );
			String outputMessage = "processed by " + getHostname();
			context.putValue(selector  + CPS + ServiceHoare.ARRAY + CPS + ServiceHoare.K_LARGEST, result);
			context.putValue( ServiceHoare.COMMENT, outputMessage);
		} catch (Exception ex) {
			throw new sorcer.ssu2.provider.HoareException(ex);
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
