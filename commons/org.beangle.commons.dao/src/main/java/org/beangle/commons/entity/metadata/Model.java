/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.entity.metadata;

import java.io.Serializable;
import java.util.Map;

import org.beangle.commons.entity.metadata.impl.ConvertPopulatorBean;
import org.beangle.commons.entity.metadata.impl.DefaultModelMeta;
import org.beangle.commons.entity.metadata.impl.SimpleEntityContext;

/**
 * <p>
 * Model class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public final class Model {

  /** Constant <code>NULL="null"</code> */
  public static final String NULL = "null";

  public static DefaultModelMeta meta = new DefaultModelMeta();

  static {
    meta.setContext(new SimpleEntityContext());
    meta.setPopulator(new ConvertPopulatorBean());
  }

  private Model() {
  }

  /**
   * <p>
   * newInstance.
   * </p>
   * 
   * @param clazz
   * @param <T> a T object.
   * @return a T object.
   */
  public static <T> T newInstance(final Class<T> clazz) {
    return meta.newInstance(clazz);
  }

  /**
   * <p>
   * newInstance.
   * </p>
   * 
   * @param clazz a {@link java.lang.Class} object.
   * @param id a {@link java.io.Serializable} object.
   * @param <T> a T object.
   * @return a T object.
   */
  public static <T> T newInstance(final Class<T> clazz, final Serializable id) {
    return meta.newInstance(clazz, id);
  }

  /**
   * <p>
   * getEntityType.
   * </p>
   * 
   * @param entityName a {@link java.lang.String} object.
   * @return a {@link org.beangle.commons.entity.metadata.EntityType} object.
   */
  public static EntityType getEntityType(String entityName) {
    return meta.getEntityType(entityName);
  }

  /**
   * <p>
   * getType.
   * </p>
   * 
   * @param entityName a {@link java.lang.String} object.
   * @return a {@link org.beangle.commons.entity.metadata.Type} object.
   */
  public static Type getType(String entityName) {
    return meta.getType(entityName);
  }

  /**
   * <p>
   * getEntityName.
   * </p>
   * 
   * @param obj a {@link java.lang.Object} object.
   * @return a {@link java.lang.String} object.
   */
  public static String getEntityName(Object obj) {
    return meta.getEntityName(obj);
  }

  /**
   * <p>
   * getEntityType.
   * </p>
   * 
   * @param clazz a {@link java.lang.Class} object.
   * @return a {@link org.beangle.commons.entity.metadata.EntityType} object.
   */
  public static EntityType getEntityType(Class<?> clazz) {
    return meta.getEntityType(clazz);
  }

  /**
   * 将params中的属性([attr(string)->value(object)]，放入到实体类中。<br>
   * 如果引用到了别的实体，那么<br>
   * 如果params中的id为null，则将该实体的置为null.<br>
   * 否则新生成一个实体，将其id设为params中指定的值。 空字符串按照null处理
   * 
   * @param params a {@link java.util.Map} object.
   * @param entity a {@link java.lang.Object} object.
   */
  public static void populate(Map<String, Object> params, Object entity) {
    meta.populate(params, entity);
  }

  public static Populator getPopulator() {
    return meta.getPopulator();
  }

  public static void setMeta(DefaultModelMeta meta) {
    Model.meta = meta;
  }

}
