/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
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
package org.beangle.security.blueprint.event;

import java.util.List;

import org.beangle.security.Securities;
import org.beangle.security.blueprint.User;

/**
 * @author chaostone
 * @version $Id: UserCreationEvent.java Jul 27, 2011 10:18:24 AM chaostone $
 */
public class UserCreationEvent extends UserEvent {

  private static final long serialVersionUID = -3314980522326237621L;

  public UserCreationEvent(List<? extends User> users) {
    super(users);
    setSubject(Securities.getUsername() + " 创建了" + getUserNames() + " 用户");
  }

}
