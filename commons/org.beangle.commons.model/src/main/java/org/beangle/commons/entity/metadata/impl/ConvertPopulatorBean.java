/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
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
package org.beangle.commons.entity.metadata.impl;

import java.lang.reflect.Method;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.beangle.commons.bean.converters.Converters;
import org.beangle.commons.entity.metadata.EntityType;
import org.beangle.commons.entity.metadata.ObjectAndType;
import org.beangle.commons.entity.metadata.Populator;
import org.beangle.commons.entity.metadata.Type;
import org.beangle.commons.lang.Objects;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.lang.reflect.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * ConvertPopulatorBean class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class ConvertPopulatorBean implements Populator {

  /** Constant <code>logger</code> */
  protected static final Logger logger = LoggerFactory.getLogger(ConvertPopulatorBean.class);

  /** Constant <code>TRIM_STR=true</code> */
  public static final boolean TRIM_STR = true;

  private BeanUtilsBean beanUtils;

  /**
   * <p>
   * Constructor for ConvertPopulatorBean.
   * </p>
   */
  public ConvertPopulatorBean() {
    this(Converters.Instance);
  }

  /**
   * <p>
   * Constructor for ConvertPopulatorBean.
   * </p>
   * 
   * @param convertUtils a {@link org.apache.commons.beanutils.ConvertUtilsBean} object.
   */
  public ConvertPopulatorBean(ConvertUtilsBean convertUtils) {
    beanUtils = new BeanUtilsBean(convertUtils);
  }

  /**
   * 初始化对象指定路径的属性。<br>
   * 例如给定属性a.b.c,方法会依次检查a a.b a.b.c是否已经初始化
   */
  public ObjectAndType initProperty(final Object target, Type type, final String attr) {
    Object propObj = target;
    Object property = null;

    int index = 0;
    String[] attrs = Strings.split(attr, ".");
    while (index < attrs.length) {
      try {
        property = PropertyUtils.getProperty(propObj, attrs[index]);
        Type propertyType = type.getPropertyType(attrs[index]);
        // 初始化
        if (null == propertyType) {
          logger.error("Cannot find property type [{}] of {}", attrs[index], propObj.getClass());
          throw new RuntimeException("Cannot find property type " + attrs[index] + " of "
              + propObj.getClass().getName());
        }
        if (null == property) {
          property = propertyType.newInstance();
          try {
            PropertyUtils.setProperty(propObj, attrs[index], property);
          } catch (NoSuchMethodException e) {
            // Try fix jdk error for couldn't find correct setter when object's Set required type is
            // diffent with Get's return type declared in interface.
            Method setter = Reflections.getSetter(propObj.getClass(), attrs[index]);
            if (null != setter) setter.invoke(propObj, property);
            else throw e;
          }
        }
        index++;
        propObj = property;
        type = propertyType;
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
    return new ObjectAndType(property, type);
  }

  /**
   * 安静的拷贝属性，如果属性非法或其他错误则记录日志
   */
  public boolean populateValue(final Object target, EntityType type, final String attr, final Object value) {
    try {
      if (attr.indexOf('.') > -1) {
        ObjectAndType ot = initProperty(target, type, Strings.substringBeforeLast(attr, "."));
        if (ot.getType().isEntityType()) {
          String foreignKey = ((EntityType) ot.getType()).getIdName();
          if (foreignKey.equals(Strings.substringAfterLast(attr, "."))) {
            beanUtils.copyProperty(target, attr, convert(ot.getType(), foreignKey, value));
          } else {
            beanUtils.copyProperty(target, attr, value);
          }
        }
      } else {
        if (type.getIdName().equals(attr)) {
          beanUtils.copyProperty(target, attr, convert(type, attr, value));
        } else {
          beanUtils.copyProperty(target, attr, value);
        }
      }
      return true;
    } catch (Exception e) {
      logger.warn("copy property failure:[class:" + type.getEntityName() + " attr:" + attr + " value:"
          + value + "]:", e);
      return false;
    }
  }

  /**
   * 将params中的属性([attr(string)->value(object)]，放入到实体类中。
   * <p>
   * 如果引用到了别的实体，那么<br>
   * 如果params中的id为null，则将该实体的置为null.<br>
   * 否则新生成一个实体，将其id设为params中指定的值。 空字符串按照null处理
   */
  public Object populate(Object entity, EntityType type, Map<String, Object> params) {
    for (final Map.Entry<String, Object> paramEntry : params.entrySet()) {
      String attr = paramEntry.getKey();
      Object value = paramEntry.getValue();
      if (value instanceof String) {
        if (Strings.isEmpty((String) value)) value = null;
        else if (TRIM_STR) value = ((String) value).trim();
      }
      // 主键
      if (type.isEntityType() && attr.equals(((EntityType) type).getIdName())) {
        setProperty(entity, attr, convert(type, attr, value));
        continue;
      }
      // 普通属性
      if (-1 == attr.indexOf('.')) {
        copyValue(attr, value, entity);
      } else {
        String parentAttr = Strings.substring(attr, 0, attr.lastIndexOf('.'));
        try {
          ObjectAndType ot = initProperty(entity, type, parentAttr);
          if (null == ot) {
            logger.error("error attr:[" + attr + "] value:[" + value + "]");
            continue;
          }
          // 属性也是实体类对象
          if (ot.getType().isEntityType()) {
            String foreignKey = ((EntityType) ot.getType()).getIdName();
            if (attr.endsWith("." + foreignKey)) {
              if (null == value) {
                copyValue(parentAttr, null, entity);
              } else {
                Object oldValue = PropertyUtils.getProperty(entity, attr);
                Object newValue = convert(ot.getType(), foreignKey, value);
                if (!Objects.equals(oldValue, newValue)) {
                  // 如果外键已经有值
                  if (null != oldValue) {
                    copyValue(parentAttr, null, entity);
                    initProperty(entity, type, parentAttr);
                  }
                  setProperty(entity, attr, newValue);
                }
              }
            } else {
              copyValue(attr, value, entity);
            }
          } else {
            copyValue(attr, value, entity);
          }
        } catch (Exception e) {
          logger.error("error attr:[" + attr + "] value:[" + value + "]", e);
        }
      }
    }
    return entity;
  }

  private Object convert(Type type, String attr, Object value) {
    Object attrValue = null;
    if (null != value) {
      Type attrType = type.getPropertyType(attr);
      Class<?> attrClass = attrType.getReturnedClass();
      if (attrClass.isAssignableFrom(value.getClass())) {
        attrValue = value;
      } else {
        attrValue = beanUtils.getConvertUtils().convert(value, attrClass);
      }
    }
    return attrValue;
  }

  private void setProperty(Object target, String attr, Object value) {
    try {
      PropertyUtils.setProperty(target, attr, value);
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  private void copyValue(final String attr, final Object value, final Object target) {
    try {
      beanUtils.copyProperty(target, attr, value);
    } catch (Exception e) {
      logger.error("copy property failure:[class:" + target.getClass().getName() + " attr:" + attr
          + " value:" + value + "]:", e);
    }
  }
}