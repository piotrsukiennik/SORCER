package sorcer.ssu3.requestor;

import java.rmi.RMISecurityManager;
import java.util.logging.Logger;
import sorcer.core.context.ServiceContext;
import sorcer.core.exertion.NetJob;
import sorcer.core.exertion.NetTask;
import sorcer.core.signature.NetSignature;
import sorcer.service.Job;
import sorcer.ssu3.provider.ServiceFirstPrime;
import sorcer.util.Log;
import sorcer.util.Sorcer;

@SuppressWarnings("rawtypes")
public class FirstPrimeTester {

	private static Logger logger = Log.getTestLog();

	String CPS = "/";
	
	public static void main(String[] args) throws Exception {
		System.setSecurityManager(new RMISecurityManager());
		Job result = new FirstPrimeTester().test();
		logger.info("job context: \n" + result.getJobContext());
	}

	private Job test() throws Exception {
		Job result = (Job)getJob().exert();
		return result;
	}

	private Job getJob() throws Exception {
		NetTask task1 = getSearchTask();
		NetJob job = new NetJob("ssu3");
		job.addExertion(task1);
		return job;
	}

	private NetTask getSearchTask() throws Exception {
		ServiceContext context = new ServiceContext( ServiceFirstPrime.SEARCH);
        context.putValue( ServiceFirstPrime.SEARCH + CPS + ServiceFirstPrime.K, 3);
        NetSignature signature = new NetSignature("search", ServiceFirstPrime.class, Sorcer.getActualName("SSU3"));
		NetTask task = new NetTask("ssu3-search", signature);
		task.setContext(context);
		return task;
	}


}
