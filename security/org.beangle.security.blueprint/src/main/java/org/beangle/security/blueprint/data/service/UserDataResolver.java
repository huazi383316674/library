/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.data.service;

import java.util.Collection;
import java.util.List;

import org.beangle.security.blueprint.data.ProfileField;

public interface UserDataResolver {

  /**
   * Marshal list of objects to text format
   * 
   * @param field
   * @param items
   */
  public String marshal(ProfileField field, Collection<?> items);

  /**
   * Convert text to list of objects
   * 
   * @param <T>
   * @param field
   * @param text
   */
  public <T> List<T> unmarshal(ProfileField field, String text);
}
