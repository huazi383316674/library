/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import java.io.Writer;

import com.opensymphony.xwork2.util.ValueStack;

public class ClosingUIBean extends UIBean {

	protected String body;

	public ClosingUIBean(ValueStack stack) {
		super(stack);
	}

	@Override
	public boolean start(Writer writer) {
		evaluateParams();
		return true;
	}

	@Override
	public boolean end(Writer writer, String body) {
		this.body = body;
		try {
			mergeTemplate(writer);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return false;
	}

	public String getBody() {
		return body;
	}

	@Override
	final public boolean usesBody() {
		return true;
	}
}