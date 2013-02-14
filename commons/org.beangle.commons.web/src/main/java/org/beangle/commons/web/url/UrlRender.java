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
package org.beangle.commons.web.url;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import org.beangle.commons.lang.Assert;
import org.beangle.commons.lang.Strings;

public class UrlRender {

  private String suffix;
  // encode
  private boolean escapeAmp;

  public UrlRender() {
    super();
  }

  public UrlRender(String suffix) {
    super();
    if (null != suffix) {
      if (suffix.charAt(0) != '.') {
        this.suffix = "." + suffix;
      } else {
        this.suffix = suffix;
      }
    }
  }

  public String render(String referer, String uri, Map<String, String> params) {
    String separator = "&";
    if (escapeAmp) {
      separator = "&amp;";
    }
    StringBuilder sb = renderUri(referer, uri);
    sb.append(separator);
    for (String key : params.keySet()) {
      try {
        sb.append(key).append('=').append(URLEncoder.encode(params.get(key), "UTF-8"));
        sb.append(separator);
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
    }
    sb.delete(sb.length() - separator.length(), sb.length());
    return sb.toString();
  }

  public String render(String referer, String uri, String... params) {
    String separator = "&";
    if (escapeAmp) {
      separator = "&amp;";
    }
    StringBuilder sb = renderUri(referer, uri);
    sb.append(separator);
    for (String param : params) {
      try {
        sb.append(URLEncoder.encode(param, "UTF-8"));
        sb.append(separator);
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
    }
    sb.delete(sb.length() - separator.length(), sb.length());
    return sb.toString();
  }

  public String render(String referer, String uri) {
    return renderUri(referer, uri).toString();
  }

  private StringBuilder renderUri(String referer, String uri) {
    Assert.notNull(referer);
    StringBuilder sb = new StringBuilder();
    if (Strings.isEmpty(uri)) {
      sb.append(referer);
      return sb;
    }
    // query string
    String queryStr = null;
    int questIndex = uri.indexOf('?');
    if (-1 == questIndex) {
      questIndex = uri.length();
    } else {
      queryStr = uri.substring(questIndex + 1);
      uri = uri.substring(0, questIndex);
    }
    // uri
    if (uri.startsWith("/")) {
      int rirstslash = referer.indexOf("/", 1);
      String context = (-1 == rirstslash) ? "" : referer.substring(0, rirstslash);
      sb.append(context);
      sb.append(uri.substring(0, questIndex));
    } else {
      int lastslash = referer.lastIndexOf("/");
      String namespace = referer.substring(0, lastslash);
      sb.append(namespace);
      if (uri.startsWith("!")) {
        int dot = referer.indexOf("!", lastslash);
        if (-1 == dot) {
          dot = referer.indexOf(".", lastslash);
        }
        dot = (-1 == dot) ? referer.length() : dot;
        String action = referer.substring(lastslash, dot);
        sb.append(action);
        sb.append(uri);
      } else {
        sb.append('/').append(uri);
      }
    }
    // prefix
    if (null != suffix) sb.append(suffix);
    if (null != queryStr) {
      sb.append('?').append(queryStr);
    }
    return sb;
  }

  public String getSuffix() {
    return suffix;
  }

  public void setSuffix(String prefix) {
    this.suffix = prefix;
  }

  public boolean isEscapeAmp() {
    return escapeAmp;
  }

  public void setEscapeAmp(boolean escapeAmp) {
    this.escapeAmp = escapeAmp;
  }
}
