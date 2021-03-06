/**
 *
 * Copyright 2013 the original author or authors.
 * Copyright 2013 Sorcersoft.com S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sorcer.ex1.requestor.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.InetAddress;
import java.rmi.RMISecurityManager;
import java.util.logging.Logger;

import org.junit.Test;

import sorcer.core.SorcerConstants;
import sorcer.core.context.ControlContext;
import sorcer.core.context.ServiceContext;
import sorcer.core.exertion.NetJob;
import sorcer.core.exertion.NetTask;
import sorcer.core.exertion.ObjectTask;
import sorcer.core.signature.NetSignature;
import sorcer.core.signature.ObjectSignature;
import sorcer.ex1.bean.WhoIsItBean1;
import sorcer.ex1.provider.WhoIsItProvider1;
import sorcer.ex1.requestor.RequestorMessage;
import sorcer.service.Context;
import sorcer.service.Exertion;
import sorcer.service.Job;
import sorcer.service.Signature;
import sorcer.service.Signature.Type;
import sorcer.service.Strategy;
import sorcer.service.Task;
import sorcer.util.Sorcer;

/**
 * @author Mike Sobolewski
 */
@SuppressWarnings({ "rawtypes" })
public class WhoIsItTest implements SorcerConstants {

	private final static Logger logger = Logger
			.getLogger(WhoIsItTest.class.getName());

	static {
        System.setProperty("java.security.policy", Sorcer.getHome()
                + "/configs/policy-all");
        System.setSecurityManager(new RMISecurityManager());
		Sorcer.setCodeBase(new String[] { "whoIsIt-req-dl.jar",  "sorcer-prv-dl.jar" });
        System.out.println("CLASSPATH :" + System.getProperty("java.class.path"));
        System.out.println("CODEBASE :" + System.getProperty("java.rmi.server.codebase"));
    }

    @Test
    public void localhost() throws Exception {
        InetAddress inetAddress = InetAddress.getLocalHost();
        String hostname = inetAddress.getHostName();
        String ipAddress = inetAddress.getHostAddress();

        logger.info("inetAddress: " + inetAddress);
        logger.info("hostname: " + hostname);
        logger.info("ipAddress: " + ipAddress);
    }

    @Test
    public void helloObjectTask() throws Exception {
        InetAddress inetAddress = InetAddress.getLocalHost();
        String hostname = inetAddress.getHostName();
        String ipAddress = inetAddress.getHostAddress();

        Context context = new ServiceContext("Who Is It?");
        context.putValue("requestor/message", "Hello Objects!");
        context.putValue("requestor/hostname", hostname);
        context.putValue("requestor/address", ipAddress);

        ObjectSignature signature = new ObjectSignature("getHostName",
                WhoIsItBean1.class);

        Task task = new ObjectTask("Who Is It?", signature, context);
        Exertion result = task.exert();
        if (result.getExceptions().size() > 0)
            logger.info("exceptions: " + result.getExceptions());
        else {
            logger.info("task context: " + result.getContext());
            assertEquals(result.getContext().getValue("provider/hostname"), hostname);
        }
    }

    @Test
    public void helloNetworkTask() throws Exception {
        // using requestor/provider message types
        InetAddress inetAddress = InetAddress.getLocalHost();
        String hostname = inetAddress.getHostName();
//        String ipAddress = inetAddress.getHostAddress();
        String providerName = null;

        Context context = new ServiceContext("Who Is It?");
        context.putValue("requestor/message", new RequestorMessage("Hello Network!"));
        context.putValue("requestor/hostname", hostname);

        NetSignature signature = new NetSignature("getHostName",
                sorcer.ex1.WhoIsIt.class, providerName);

        Task task = new NetTask("Who Is It?", signature, context);
        Exertion result = task.exert();
        logger.info("task context: " + result.getContext());
        if (result.getExceptions().size() > 0)
            logger.info("exceptions: " + result.getExceptions());
        else {
            logger.info("task context: " + result.getContext());
//        assertEquals(result.getContext().getValue("provider/hostname"), hostname);
//        assertEquals(result.getContext().getValue("provider/address"), ipAddress);
        }
    }

	@Test
	public void execBatchTask() throws Exception {
        InetAddress inetAddress = InetAddress.getLocalHost();
        String hostname = inetAddress.getHostName();
        String ipAddress = inetAddress.getHostAddress();

        Context context = new ServiceContext("Who Is It?");
        context.putValue("requestor/message", new RequestorMessage("Hello Objects!"));
        context.putValue("requestor/hostname", hostname);
        context.putValue("requestor/address", ipAddress);

        Signature signature1 = new ObjectSignature("getHostAddress", WhoIsItProvider1.class);
        Signature signature2 = new ObjectSignature("getHostName", WhoIsItProvider1.class);
        Signature signature3 = new ObjectSignature("getCanonicalHostName", WhoIsItProvider1.class);
        Signature signature4 = new ObjectSignature("getTimestamp", WhoIsItProvider1.class);

        Task task = new ObjectTask("Who Is It?", signature1, signature2, signature3, signature4);
        task.setContext(context);

        Exertion result = task.exert();
        logger.info("task context: " + result.getContext());
        assertEquals(result.getContext().getValue("provider/hostname"), hostname);
        assertEquals(result.getContext().getValue("provider/address"), ipAddress);
    }

    @Test
    public void exertBatchTask() throws Exception {
        InetAddress inetAddress = InetAddress.getLocalHost();
        String hostname = inetAddress.getHostName();
        String ipAddress = inetAddress.getHostAddress();
        String providername = "*";

        Context context = new ServiceContext("Who Is It?");
        context.putValue("requestor/message", new RequestorMessage("Hello Network!"));
        context.putValue("requestor/hostname", hostname);
        context.putValue("requestor/address", ipAddress);

        Signature signature1 = new NetSignature("getHostAddress",
                sorcer.ex1.WhoIsIt.class, providername, Type.PRE);
        Signature signature2 = new NetSignature("getHostName",
                sorcer.ex1.WhoIsIt.class, providername, Type.SRV);
        Signature signature3 = new NetSignature("getCanonicalHostName",
                sorcer.ex1.WhoIsIt.class, providername, Type.POST);
        Signature signature4 = new NetSignature("getTimestamp",
                sorcer.ex1.WhoIsIt.class, providername, Type.POST);

        Task task = new NetTask("Who Is It?",
                new Signature[] { signature1, signature2, signature3, signature4 },
                context);

        Exertion result = task.exert();
        logger.info("task context: " + task.getContext());
        assertEquals(result.getContext().getValue("provider/hostname"), hostname);
        assertEquals(result.getContext().getValue("provider/address"), inetAddress.getHostAddress());
    }

    @Test
    public void exertJob() throws Exception {
        String providerName1 = Sorcer.getActualName("ABC");
        String providerName2 = Sorcer.getActualName("XYZ");

        // define requestor data
        InetAddress inetAddress = InetAddress.getLocalHost();
        String hostname = inetAddress.getHostName();
        String ipAddress = inetAddress.getHostAddress();

        Context context1 = new ServiceContext("Who is it?");
        context1.putValue("requestor/message", "Hello " + providerName1);
        context1.putValue("requestor/hostname", hostname);
        context1.putValue("requestor/address", ipAddress);

        Context context2 = new ServiceContext("Who is it?");
        context2.putValue("requestor/message", new RequestorMessage(
                providerName2));
        context2.putValue("requestor/hostname", hostname);
        context2.putValue("requestor/address", ipAddress);

        NetSignature signature1 = new NetSignature("getHostName",
                sorcer.ex1.WhoIsIt.class, providerName1);
        NetSignature signature2 = new NetSignature("getHostAddress",
                sorcer.ex1.WhoIsIt.class, providerName2);

        Task task1 = new NetTask("Who is it1?", signature1, context1);
        Task task2 = new NetTask("Who is it2?", signature2, context2);
        Job job = new NetJob("Who are they?");
        job.addExertion(task1).addExertion(task2);

        ControlContext cc = job.getControlContext();
        // PUSH or PULL provider access
        cc.setAccessType(Strategy.Access.PUSH);
        // Exertion control flow PAR or SEQ
        cc.setFlowType(Strategy.Flow.PAR);

        Job result = (Job)job.exert();

        logger.info("job context: " + result.getJobContext());
        logger.info("job exceptions: " + result.getExceptions());
        logger.info("task 1 trace: " + result.getExertion("Who is it1?").getTrace());
        logger.info("task 2 trace: " + result.getExertion("Who is it2?").getTrace());

		assertEquals(
				result.getValue("Who are they?/Who is it1?/provider/message")
						.toString(), "Hello " + providerName1 + "; "
						+ providerName1 + ":Hi " + hostname + "!");
		assertEquals(
				result.getValue("Who are they?/Who is it2?/provider/message")
						.toString(), "Hi " + providerName2 + "!; "
						+ providerName2 + ":Hi " + hostname + "!");
    }

}
