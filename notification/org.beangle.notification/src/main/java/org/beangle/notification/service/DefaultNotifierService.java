/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.notification.service;

import java.util.Map;

import org.beangle.notification.Notifier;

//$Id:DefaultNotifierService.java Mar 22, 2009 11:27:21 AM chaostone Exp $
/*
 * Copyright c 2005-2009.
 * 
 * Licensed under the GPL License, Version 2.0 (the "License")
 * http://www.gnu.org/licenses/gpl-2.0.html
 * 
 */
public class DefaultNotifierService implements NotifierService {

	private Map<String, Notifier> notifiers;

	public Notifier getNotifier(String notifierId) {
		return notifiers.get(notifierId);
	}

	public Map<String, Notifier> getNotifiers() {
		return notifiers;
	}

	public void setNotifiers(Map<String, Notifier> notifiers) {
		this.notifiers = notifiers;
	}
}
