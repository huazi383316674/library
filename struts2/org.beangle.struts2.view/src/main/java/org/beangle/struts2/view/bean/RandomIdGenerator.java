/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.bean;

import java.util.Random;

import org.beangle.commons.lang.Strings;

/**
 * 随机UI's id产生器
 * 
 * @author chaostone
 * @since 2.4
 */
public class RandomIdGenerator implements UIIdGenerator {
  final protected Random seed = new Random();

  public String generate(Class<?> clazz) {
    int nextInt = seed.nextInt();
    nextInt = (nextInt == Integer.MIN_VALUE) ? Integer.MAX_VALUE : Math.abs(nextInt);
    return Strings.uncapitalize(clazz.getSimpleName()) + String.valueOf(nextInt);
  }

}