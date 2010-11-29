/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.webapp.system.action;

import java.lang.management.ManagementFactory;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.struts2.action.BaseAction;

public class StatusAction extends BaseAction implements ServletRequestAware {

	private HttpServletRequest request;

	public String index() {
		put("MaxMem", Runtime.getRuntime().maxMemory());
		put("FreeMem", Runtime.getRuntime().freeMemory());
		put("TotalMem", Runtime.getRuntime().totalMemory());

		put("osMBean", ManagementFactory.getOperatingSystemMXBean());
		put("runtimeMBean", ManagementFactory.getRuntimeMXBean());
		put("upAt", new Date(ManagementFactory.getRuntimeMXBean().getStartTime()));

		put("memPoolMBeans", ManagementFactory.getMemoryPoolMXBeans());
		put("threadMBean", ManagementFactory.getThreadMXBean());

		Map<String, Object> serverProps = CollectUtils.newHashMap();
		serverProps.put("server.hostname", request.getServerName());
		serverProps.put("server.port", request.getServerPort());
		serverProps.put("server.protocol", request.getProtocol());
		serverProps.put("server.path", request.getSession().getServletContext().getRealPath(""));
		serverProps.put("server.info", request.getSession().getServletContext().getServerInfo());
		serverProps.put("user.dir", System.getProperty("user.dir"));
		put("now", new Date());
		put("serverProps", serverProps);
		return forward();
	}

	public String properties() {
		Properties props = System.getProperties();
		Map<String, Object> javaProps = CollectUtils.newHashMap();
		Map<String, Object> osProps = CollectUtils.newHashMap();
		Map<String, Object> userProps = CollectUtils.newHashMap();
		Map<String, Object> extraProps = CollectUtils.newHashMap();
		for (Object property : props.keySet()) {
			String key = (String) property;
			Object value = props.get(key);
			if (key.startsWith("java.")) {
				javaProps.put(key, value);
			} else if (key.startsWith("os.")) {
				osProps.put(key, value);
			} else if (key.startsWith("user.")) {
				userProps.put(key, value);
			} else {
				extraProps.put(key, value);
			}
		}
		put("javaProps", javaProps);
		put("osProps", osProps);
		put("userProps", userProps);
		put("extraProps", extraProps);
		return forward();
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

}
