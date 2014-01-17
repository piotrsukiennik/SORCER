package sorcer.ssu3.provider;

import com.sun.jini.start.LifeCycle;
import sorcer.core.SorcerConstants;
import sorcer.core.provider.ServiceTasker;
import sorcer.service.Context;
import sorcer.util.Log;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("rawtypes")
public class FirstPrimeProvider extends ServiceTasker implements FirstPrime, ServiceFirstPrime, SorcerConstants {

	private static Logger logger = Log.getTestLog();

    private Class<FirstPrime> firstPrimeClass;

	public FirstPrimeProvider( String[] args, LifeCycle lifeCycle ) throws Exception {
		super(args, lifeCycle);
        String clazz = getProperty("sorcer.ssu3.provider.FirstPrime.impl");
        firstPrimeClass = (Class<FirstPrime>)Class.forName( clazz );
	}

    @Override
    public int search(  int k ) throws RemoteException, FirstPrimeException {
        try{
            FirstPrime sort = firstPrimeClass.newInstance();
            return sort.search( k );
        }catch ( Exception e ){
            throw new FirstPrimeException( e );
        }

    }

    public Context search(Context context) throws RemoteException, FirstPrimeException {
		return process(context, ServiceFirstPrime.SEARCH);
	}

	private Context process(Context context, String selector)
			throws RemoteException, FirstPrimeException {
		try {
            if (logger.isLoggable( Level.INFO )){
                logger.info("input context: \n" + context);
            }
            int result=-1;
			if (ServiceFirstPrime.SEARCH.equals( selector )) {
                String kKey = ServiceFirstPrime.SEARCH + CPS  + ServiceFirstPrime.K;
                Integer inputK = (Integer) context.getValue( kKey );
                if (logger.isLoggable( Level.INFO )){
                    logger.info("Input -> "+kKey+" = " + inputK );
                }
                result = search( inputK );
			}
			// set return value
			if (context.getReturnPath() != null) {
				context.setReturnValue(result);
			}
			logger.info(selector + " result: \n" +result );
			String outputMessage = "processed by " + getHostname();
			context.putValue(selector  + CPS + ServiceFirstPrime.K + CPS + ServiceFirstPrime.PRIME, result);
			context.putValue( ServiceFirstPrime.COMMENT, outputMessage);
		} catch (Exception ex) {
			throw new FirstPrimeException(ex);
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
