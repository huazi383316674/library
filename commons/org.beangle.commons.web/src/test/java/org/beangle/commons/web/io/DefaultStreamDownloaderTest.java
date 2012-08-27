/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.web.io;

import java.net.URL;
import java.net.URLDecoder;

import org.apache.commons.codec.net.URLCodec;
import org.beangle.commons.http.mime.MimeTypeProvider;
import org.beangle.commons.lang.ClassLoaders;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class DefaultStreamDownloaderTest {

  StreamDownloader streamDownloader = new DefaultStreamDownloader(new MimeTypeProvider());

  public void download() {
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();
    URL testDoc = ClassLoaders.getResource("download.txt",getClass());
    streamDownloader.download(request, response, testDoc, null);
  }

  public void ecode() throws Exception {
    String value = "汉字-english and .;";
    String ecodedValue = new URLCodec().encode(value, "utf-8");
    String orginValue = URLDecoder.decode(ecodedValue, "utf-8");
    Assert.assertEquals(orginValue, value);
  }

  public void ecode2() throws Exception {
    String value = "汉字-english and .;";
    String encodedValue = new org.apache.commons.codec.net.BCodec().encode(value);
    String orginValue = new org.apache.commons.codec.net.BCodec().decode(encodedValue);
    Assert.assertEquals(orginValue, value);
  }
}
