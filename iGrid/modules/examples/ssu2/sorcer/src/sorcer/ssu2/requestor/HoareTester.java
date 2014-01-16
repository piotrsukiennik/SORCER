package sorcer.ssu2.requestor;

import java.rmi.RMISecurityManager;
import java.util.logging.Logger;
import sorcer.core.context.ServiceContext;
import sorcer.core.exertion.NetJob;
import sorcer.core.exertion.NetTask;
import sorcer.core.signature.NetSignature;
import sorcer.service.Job;
import sorcer.ssu2.provider.ServiceHoare;
import sorcer.util.Log;
import sorcer.util.Sorcer;

@SuppressWarnings("rawtypes")
public class HoareTester {

	private static Logger logger = Log.getTestLog();

	String CPS = "/";
	
	public static void main(String[] args) throws Exception {
		System.setSecurityManager(new RMISecurityManager());
		Job result = new HoareTester().test();
		logger.info("job context: \n" + result.getJobContext());
	}

	private Job test() throws Exception {
		Job result = (Job)getJob().exert();
		return result;
	}

	private Job getJob() throws Exception {
		NetTask task1 = getSearchTask();
		NetJob job = new NetJob("ssu2");
		job.addExertion(task1);
		return job;
	}

	private NetTask getSearchTask() throws Exception {
		ServiceContext context = new ServiceContext(ServiceHoare.SEARCH);
		context.putValue( ServiceHoare.SEARCH + CPS + ServiceHoare.ARRAY, new int[]{23, 13, 7, 3, 21});
        context.putValue( ServiceHoare.SEARCH + CPS + ServiceHoare.K, 1);
        NetSignature signature = new NetSignature("search", ServiceHoare.class, Sorcer.getActualName("SSU2"));
		NetTask task = new NetTask("ssu2-search", signature);
		task.setContext(context);
		return task;
	}


}
