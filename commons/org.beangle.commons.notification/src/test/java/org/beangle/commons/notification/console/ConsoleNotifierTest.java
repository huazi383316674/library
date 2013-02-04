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
package org.beangle.commons.notification.console;

import org.beangle.commons.notification.Message;
import org.beangle.commons.notification.Notifier;
import org.beangle.commons.notification.SimpleMessage;
import org.testng.annotations.Test;

public class ConsoleNotifierTest {

  @Test
  public void testSendMessage() throws Exception {
    Notifier<Message> notifier = new ConsoleNotifier();
    SimpleMessage context = new SimpleMessage();
    context.setText("hello world");
    notifier.deliver(context);
  }
}