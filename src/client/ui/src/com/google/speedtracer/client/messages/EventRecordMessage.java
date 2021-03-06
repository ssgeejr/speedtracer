/*
 * Copyright 2009 Google Inc.
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
import com.google.gwt.coreext.client.JSON;
import com.google.speedtracer.client.model.EventRecord;

/**
 * Overlay type associated with sending event record JSON strings over
 * postMessage from a content script or some other entity over a chromium
 * extension Port.
 */
public class EventRecordMessage extends Message {
  public static final int TYPE = MessageType.PORT_EVENT_RECORD_TYPE;

  public static EventRecordMessage create(String recordStr) {
    Message message = Message.create(TYPE);
    return message.setProperty("record", recordStr).cast();
  }

  protected EventRecordMessage() {
  }

  public final EventRecord getEventRecord() {
    return JSON.parse(getRecordString()).cast();
  }

  public final native String getRecordString() /*-{
    return this.record;
  }-*/;

  public final native String getVersion() /*-{
    return this.version;
  }-*/;
}