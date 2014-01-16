package sorcer.ssu1.requestor;

import java.rmi.RMISecurityManager;
import java.util.logging.Logger;
import sorcer.core.context.ServiceContext;
import sorcer.core.exertion.NetJob;
import sorcer.core.exertion.NetTask;
import sorcer.core.signature.NetSignature;
import sorcer.service.Job;
import sorcer.ssu1.provider.ServiceSort;
import sorcer.util.Log;
import sorcer.util.Sorcer;

@SuppressWarnings("rawtypes")
public class SortTester {

	private static Logger logger = Log.getTestLog();

	String CPS = "/";
	
	public static void main(String[] args) throws Exception {
		System.setSecurityManager(new RMISecurityManager());
		Job result = new SortTester().test();
		logger.info("job context: \n" + result.getJobContext());
	}

	private Job test() throws Exception {
		Job result = (Job)getJob().exert();
		return result;
	}

	private Job getJob() throws Exception {
		NetTask task1 = getSortTask();
		NetJob job = new NetJob("ssu1");
		job.addExertion(task1);
		return job;
	}

	private NetTask getSortTask() throws Exception {
		ServiceContext context = new ServiceContext(ServiceSort.SORT);
		context.putValue(ServiceSort.SORT + CPS + ServiceSort.ARRAY, new int[]{3,2,5,6,7});
		NetSignature signature = new NetSignature("sort", ServiceSort.class, Sorcer.getActualName("SSU1"));
		NetTask task = new NetTask("ssu1-sort", signature);
		task.setContext(context);
		return task;
	}


}
