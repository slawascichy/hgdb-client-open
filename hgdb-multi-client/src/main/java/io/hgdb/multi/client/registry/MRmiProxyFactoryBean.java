package io.hgdb.multi.client.registry;

import java.util.Calendar;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.remoting.rmi.RmiProxyFactoryBean;

import io.hgdb.multi.client.registry.error.IRmiProxyFactoryErrorHandler;

public class MRmiProxyFactoryBean extends RmiProxyFactoryBean {

	protected final Logger localLogger = LoggerFactory.getLogger(MRmiProxyFactoryBean.class);

	private String instanceName;

	private IRmiProxyFactoryErrorHandler errorHandler;

	public MRmiProxyFactoryBean() {
		super();
		super.setRefreshStubOnConnectFailure(true);
	}

	/**
	 * @return the {@link #instanceName}
	 */
	public String getInstanceName() {
		return instanceName;
	}

	/**
	 * @param instanceName
	 *                     the {@link #instanceName} to set
	 */
	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	/**
	 * @return the {@link #errorHandler}
	 */
	public IRmiProxyFactoryErrorHandler getErrorHandler() {
		return errorHandler;
	}

	/**
	 * @param errorHandler
	 *                     the {@link #errorHandler} to set
	 */
	public void setErrorHandler(IRmiProxyFactoryErrorHandler errorHandler) {
		this.errorHandler = errorHandler;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public Object invoke(MethodInvocation arg0) throws Throwable {
		try {
			String methodName = null;
			Long startTime = 0l;
			if (localLogger.isTraceEnabled()) {
				methodName = arg0.getMethod().getName();
				localLogger.trace("-->invoke: [START] instanceName:{}, method: {}", this.instanceName, methodName);
				startTime = Calendar.getInstance().getTimeInMillis();
			}
			Object result = super.invoke(arg0);
			if (localLogger.isTraceEnabled()) {
				Long endTime = Calendar.getInstance().getTimeInMillis();
				localLogger.trace("-->invoke: [KONIEC] instanceName:{}, method: {}, czas wykonania: {}[ms]",
						this.instanceName, methodName, endTime - startTime);
			}
			return result;
		} catch (Throwable e) {
			if (errorHandler != null) {
				throw errorHandler.raiseError(this.instanceName, e);
			}
			throw e;
		}
	}

}
