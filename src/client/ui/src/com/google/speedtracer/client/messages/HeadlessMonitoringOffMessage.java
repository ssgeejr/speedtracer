/*
 * Copyright 2010 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.speedtracer.client.messages;

import com.google.gwt.chrome.crx.client.Port.Message;

/**
 * Message sent from the content script to the background page to turn off
 * monitoring.
 */
public class HeadlessMonitoringOffMessage extends Message {
  public static final int TYPE = MessageType.PORT_HEADLESS_MONITORING_OFF;

  protected HeadlessMonitoringOffMessage() {
  }
}
