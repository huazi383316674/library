/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.orm.hibernate;

import org.beangle.context.inject.ConfigResource;
import org.beangle.orm.DefaultTableNamingStrategy;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DefaultTableNamingStrategyTest {

	@Test
	public void testGetSchemaName() {
		DefaultTableNamingStrategy config = new DefaultTableNamingStrategy();
		ConfigResource resource = new ConfigResource();
		resource.setGlobal(DefaultTableNamingStrategyTest.class.getClassLoader().getResource(
				"META-INF/beangle/table.properties"));
		config.setResource(resource);
		Assert.assertEquals("security_online", config.getSchema("org.beangle.security.online.model"));
		Assert.assertEquals("sys_", config.getPrefix("org.beangle.security"));
	}

}