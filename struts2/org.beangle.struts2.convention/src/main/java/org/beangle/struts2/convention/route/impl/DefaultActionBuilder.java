/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.struts2.convention.route.impl;

import org.beangle.commons.lang.Strings;
import org.beangle.struts2.convention.Constants;
import org.beangle.struts2.convention.route.Action;
import org.beangle.struts2.convention.route.ActionBuilder;
import org.beangle.struts2.convention.route.Profile;
import org.beangle.struts2.convention.route.ProfileService;

import com.opensymphony.xwork2.inject.Inject;

public class DefaultActionBuilder implements ActionBuilder {

  private ProfileService profileService;

  /**
   * 根据class对应的profile获得ctl/action类中除去后缀后的名字。<br>
   * 如果对应profile中是uriStyle,那么类中只保留简单类名，去掉后缀，并且小写第一个字母。<br>
   * 否则加上包名，其中的.编成URI路径分割符。包名不做其他处理。<br>
   * 复杂URL,以/开始
   * 
   * @param className
   */
  public Action build(String className) {
    Profile profile = profileService.getProfile(className);
    Action action = new Action();
    StringBuilder sb = new StringBuilder();
    // namespace
    sb.append(profile.getUriPath());
    if (Constants.SHORT_URI.equals(profile.getUriPathStyle())) {
      String simpleName = className.substring(className.lastIndexOf('.') + 1);
      sb.append(Strings.uncapitalize(simpleName.substring(0, simpleName.length()
          - profile.getActionSuffix().length())));
    } else if (Constants.SIMPLE_URI.equals(profile.getUriPathStyle())) {
      sb.append(profile.getInfix(className));
    } else if (Constants.SEO_URI.equals(profile.getUriPathStyle())) {
      sb.append(Strings.unCamel(profile.getInfix(className)));
    } else {
      throw new RuntimeException("unsupported uri style " + profile.getUriPathStyle());
    }
    action.path(sb.toString()).method(profile.getDefaultMethod()).extention(profile.getUriExtension());
    return action;
  }

  @Inject
  public void setProfileService(ProfileService profileService) {
    this.profileService = profileService;
  }

  public ProfileService getProfileService() {
    return profileService;
  }

}
