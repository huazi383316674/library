/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.model;

import java.util.Set;

import org.beangle.model.pojo.LongIdObject;
import org.beangle.security.blueprint.Authority;
import org.beangle.security.blueprint.Group;
import org.beangle.security.blueprint.Resource;
import org.beangle.security.blueprint.restrict.Restriction;
import org.beangle.security.blueprint.restrict.RestrictionHolder;

/**
 * 权限实体，模块及其操作的数据范围规定
 * 
 * @author dell,chaostone 2005-9-26
 */
public class AuthorityBean extends LongIdObject implements RestrictionHolder, Authority {

	private static final long serialVersionUID = -8956079356245507990L;

	/** 用户组 */
	protected Group group;

	/** 权限实体中的模块 */
	protected Resource resource;

	/** 该模块对应的数据操作范围 */
	protected Set<Restriction> restrictions;

	public AuthorityBean() {
		super();
	}

	public AuthorityBean(Long id) {
		super(id);
	}

	public AuthorityBean(Group group, Resource resource) {
		super();
		this.group = group;
		this.resource = resource;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = (Resource) resource;
	}

	public Set<Restriction> getRestrictions() {
		return restrictions;
	}

	public void setRestrictions(Set<Restriction> restrictions) {
		this.restrictions = restrictions;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Object clone() {
		AuthorityBean groupAuthority = new AuthorityBean();
		groupAuthority.setResource(resource);
		groupAuthority.setGroup(group);
		return groupAuthority;
	}

	public void merge(Authority other) {
		// TODO Auto-generated method stub
	}
}
