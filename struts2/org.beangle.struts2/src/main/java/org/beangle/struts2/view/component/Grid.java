/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.xwork.ObjectUtils;
import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.util.MakeIterator;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.page.Page;
import org.beangle.commons.lang.StrUtils;
import org.beangle.struts2.view.template.Theme;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * Data table
 * 
 * <pre>
 * filter  FIXME
 * </pre>
 * 
 * @author chaostone
 */
public class Grid extends ClosingUIBean {
	private List<Col> cols = CollectUtils.newArrayList();
	private Set<Object> colTitles = CollectUtils.newHashSet();
	private Object items;
	private String var;
	// gridbar
	private String bar;
	private String sortable = "true";

	public Grid(ValueStack stack) {
		super(stack);
	}

	public boolean getHasbar() {
		return (null != bar || items instanceof Page);
	}

	public boolean isPageable() {
		return items instanceof Page<?>;
	}

	public boolean isNotFullPage() {
		return ((Page<?>) items).size() < ((Page<?>) items).getPageSize();
	}

	public String defaultSort(String property) {
		return StrUtils.concat(var, ".", property);
	}

	public boolean isSortable(Col cln) {
		Object sortby = cln.getParameters().get("sort");
		if (null != sortby) return true;
		return ("true".equals(sortable) && !ObjectUtils.equals(cln.getParameters().get("sortable"), "false") && null != cln
				.getProperty());
	}

	protected void addCol(Col column) {
		Object title = column.getTitle();
		if (null == title) {
			title = column.getProperty();
		}
		if (!colTitles.contains(title)) {
			colTitles.add(title);
			cols.add(column);
		}
	}

	public boolean start(Writer writer) {
		generateIdIfEmpty();
		return true;
	}

	public List<Col> getCols() {
		return cols;
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public void setItems(Object datas) {
		this.items = datas;
	}

	public Object getItems() {
		return items;
	}

	public String getSortable() {
		return sortable;
	}

	public void setSortable(String sortable) {
		this.sortable = sortable;
	}

	public String getBar() {
		return bar;
	}

	public static class Bar extends ClosingUIBean {
		private Grid table;

		public Bar(ValueStack stack) {
			super(stack);
			table = (Grid) findAncestor(Grid.class);
		}

		@Override
		public boolean end(Writer writer, String body) {
			table.bar = body;
			return false;
		}

		public Grid getTable() {
			return table;
		}
	}

	public static class Row extends IterableUIBean {
		private Grid table;
		private String var_index;
		private Iterator<?> iterator;
		private int index = -1;
		private Object obj;
		private Boolean innerTr;

		public Row(ValueStack stack) {
			super(stack);
			table = (Grid) findAncestor(Grid.class);
			Object iteratorTarget = table.items;
			if (table.items instanceof String) {
				iteratorTarget = findValue((String) table.items);
			}
			iterator = MakeIterator.convert(iteratorTarget);
			this.var_index = table.var + "_index";
		}

		public boolean isHasTr() {
			if (null != innerTr) return innerTr;
			innerTr = StringUtils.contains(body, "<tr");
			return innerTr;
		}

		@Override
		protected boolean next() {
			if (iterator != null && iterator.hasNext()) {
				index++;
				obj = iterator.next();
				stack.getContext().put(table.var, obj);
				stack.getContext().put(var_index, index);
				return true;
			} else {
				stack.getContext().remove(table.var);
				stack.getContext().remove(var_index);
			}
			return false;
		}
	}

	/**
	 * @author chaostone
	 */
	public static class Col extends ClosingUIBean {
		String property;
		String title;
		String width;
		Row row;

		public Col(ValueStack stack) {
			super(stack);
		}

		@Override
		public boolean start(Writer writer) {
			row = (Row) findAncestor(Row.class);
			if (row.index == 0) {
				row.table.addCol(this);
			}
			return true;
		}

		@Override
		public boolean end(Writer writer, String body) {
			if (getTheme().equals(Theme.DEFAULT_THEME)) {
				try {
					writer.append("<td").append(getParameterString()).append(">");
					if (StringUtils.isNotEmpty(body)) {
						writer.append(body);
					} else if (null != property) {
						Object val = getValue();
						if (null != val) writer.append(val.toString());
					}
					writer.append("</td>");
				} catch (Exception e) {
					e.printStackTrace();
				}
				return false;
			} else {
				return super.end(writer, body);
			}
		}

		public String getProperty() {
			return property;
		}

		public void setProperty(String property) {
			this.property = property;
		}

		/**
		 * find value of row.obj's property
		 * 
		 * @return
		 */
		public Object getValue() {
			return getValue(row.obj, property);
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getTitle() {
			if (null == title) {
				title = StrUtils.concat(row.table.var, ".", property);
			}
			return getText(title);
		}

		public String getWidth() {
			return width;
		}

		public void setWidth(String width) {
			this.width = width;
		}

	}

	public static class Boxcol extends Col {

		public Boxcol(ValueStack stack) {
			super(stack);
		}

		String type = "checkbox";
		// checkbox or radiobox name
		String boxname = null;
		boolean checked;

		@Override
		public boolean start(Writer writer) {
			if (null == property) {
				this.property = "id";
			}
			row = (Row) findAncestor(Row.class);
			if (null == boxname) {
				boxname = row.table.var + "." + property;
			}
			if (row.index == 0) {
				row.table.addCol(this);
			}
			return true;
		}

		@Override
		public boolean end(Writer writer, String body) {
			if (getTheme().equals(Theme.DEFAULT_THEME)) {
				try {
					writer.append("<td class=\"gridselect\"");
					if (null != id) writer.append(" id=\"").append(id).append("\"");
					writer.append(getParameterString()).append(">");
					writer.append("<input class=\"box\" name=\"").append(boxname).append("\" value=\"")
							.append(String.valueOf(getValue())).append("\" type=\"").append(type)
							.append("\"");
					if (checked) writer.append(" checked=\"checked\"");
					writer.append("/>");
					if (StringUtils.isNotEmpty(body)) {
						writer.append(body);
					}
					writer.append("</td>");
				} catch (Exception e) {
					e.printStackTrace();
				}
				return false;
			} else {
				return super.end(writer, body);
			}
		}

		public String getType() {
			return type;
		}

		@Override
		public String getTitle() {
			return StrUtils.concat(row.table.var, "_", property);
		}

		public String getBoxname() {
			return boxname;
		}

		public void setBoxname(String boxname) {
			this.boxname = boxname;
		}

		public void setType(String type) {
			this.type = type;
		}

		public boolean isChecked() {
			return checked;
		}

		public void setChecked(boolean checked) {
			this.checked = checked;
		}

	}
}
