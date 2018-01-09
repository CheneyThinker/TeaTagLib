package org.tea.taglib.core.tag;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.tea.core.utils.ReflectUtils;

@SuppressWarnings({"serial","rawtypes"})
public class TableTag extends TagSupport {

	protected String header;
	protected String property;
	protected Object body;
	protected String split;
	protected String style;
	
	public void setHeader(String header) {
		this.header = header;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public void setBody(Object body) {
		this.body = body;
	}
	public void setSplit(String split) {
		this.split = split;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public int doStartTag() throws JspException {
		try {
			split = split == null ? "," : split;
			String[] headers = header.split(split);
			String[] properties = property.split(split);
			if (headers.length == properties.length) {
				JspWriter writer = pageContext.getOut();
				writer.print("<table" + (style != null ? " " + style : "") + ">");
				writer.print("<tr>");
				for (String string : headers) {
					writer.print("<td>" + string + "</td>");
				}
				writer.print("</tr>");
				Collection objects = (Collection) (body instanceof String ? pageContext.getAttribute((String) body) : body);
				for (Object object : objects) {
					writer.print("<tr>");
					for (String string : properties) {
						writer.print("<td>" + ReflectUtils.invokeGet(object, string) + "</td>");
					}
					writer.print("</tr>");
				}
				writer.print("</table>");
			}
		} catch (IOException e) {
			throw new JspException(e.getMessage());
		}
		return SKIP_BODY;
	}
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}
	public void release() {
		super.release();
		header = null;
		property = null;
		body = null;
		split = null;
		style = null;
	}
	
}
