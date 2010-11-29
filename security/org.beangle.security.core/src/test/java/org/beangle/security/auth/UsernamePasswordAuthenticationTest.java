/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.auth;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import org.beangle.security.auth.UsernamePasswordAuthentication;
import org.beangle.security.core.GrantedAuthority;
import org.beangle.security.core.authority.GrantedAuthorityBean;
import org.testng.annotations.Test;

@Test
public class UsernamePasswordAuthenticationTest {
	public void testAuthenticated() {
		UsernamePasswordAuthentication token = new UsernamePasswordAuthentication("Test",
				"Password", null);

		// check default given we passed some GrantedAuthorty[]s (well, we
		// passed null)
		assertTrue(token.isAuthenticated());

		// check explicit set to untrusted (we can safely go from trusted to
		// untrusted, but not the reverse)
		token.setAuthenticated(false);
		assertTrue(!token.isAuthenticated());

		// Now let's create a UsernamePasswordAuthentication without any
		// GrantedAuthorty[]s (different constructor)
		token = new UsernamePasswordAuthentication("Test", "Password");

		assertTrue(!token.isAuthenticated());

		// check we're allowed to still set it to untrusted
		token.setAuthenticated(false);
		assertTrue(!token.isAuthenticated());

		// check denied changing it to trusted
		try {
			token.setAuthenticated(true);
			fail("Should have prohibited setAuthenticated(true)");
		} catch (IllegalArgumentException expected) {
			assertTrue(true);
		}
	}

	public void testGetters() {
		UsernamePasswordAuthentication token = new UsernamePasswordAuthentication("Test",
				"Password", new GrantedAuthority[] { new GrantedAuthorityBean("ROLE_ONE"),
						new GrantedAuthorityBean("ROLE_TWO") });
		assertEquals("Test", token.getPrincipal());
		assertEquals("Password", token.getCredentials());
		assertEquals("ROLE_ONE", token.getAuthorities()[0].getAuthority());
		assertEquals("ROLE_TWO", token.getAuthorities()[1].getAuthority());
	}

	public void testNoArgConstructorDoesntExist() {
		Class<?> clazz = UsernamePasswordAuthentication.class;
		try {
			clazz.getDeclaredConstructor((Class[]) null);
			fail("Should have thrown NoSuchMethodException");
		} catch (NoSuchMethodException expected) {
			assertTrue(true);
		}
	}
}
