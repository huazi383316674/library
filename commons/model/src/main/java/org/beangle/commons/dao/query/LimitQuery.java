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
package org.beangle.commons.dao.query;

import org.beangle.commons.collection.page.PageLimit;

/**
 * <p>
 * LimitQuery interface.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public interface LimitQuery<T> extends Query<T> {

  /**
   * <p>
   * getLimit.
   * </p>
   * 
   * @return a {@link org.beangle.commons.collection.page.PageLimit} object.
   */
  PageLimit getLimit();

  /**
   * <p>
   * limit.
   * </p>
   * 
   * @param limit a {@link org.beangle.commons.collection.page.PageLimit} object.
   * @return a {@link org.beangle.commons.dao.query.LimitQuery} object.
   */
  LimitQuery<T> limit(final PageLimit limit);

  /**
   * <p>
   * getCountQuery.
   * </p>
   * 
   * @return a {@link org.beangle.commons.dao.query.Query} object.
   */
  Query<T> getCountQuery();
}
