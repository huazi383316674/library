/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
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
package org.beangle.security.blueprint.service;

import java.util.List;

import org.beangle.security.blueprint.Dimension;
import org.beangle.security.blueprint.Profile;
import org.beangle.security.blueprint.User;
import org.beangle.security.blueprint.function.FuncResource;

public interface ProfileService {
  /**
   * Get field enumerated values.
   * 
   * @param field
   * @param profile
   */
  Object getProperty(Profile profile, Dimension field);

  /**
   * 查找用户在指定资源上对应的数据配置
   * 
   * @param user
   */
  List<Profile> getProfiles(User user, FuncResource resource);

  /**
   * Search field values
   * 
   * @param field
   * @param keys
   */
  List<?> getDimensionValues(Dimension field, Object... keys);

  /**
   * Search field
   * 
   * @param fieldName
   */
  Dimension getDimension(String fieldName);

  /**
   * find profile
   * 
   * @param id
   * @return
   */
  Profile get(Long id);

}
