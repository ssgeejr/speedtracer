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
package com.google.speedtracer.client.model;

import com.google.gwt.coreext.client.DataBag;
import com.google.speedtracer.client.model.NetworkResponseReceivedEvent.Response;
import com.google.speedtracer.client.model.NetworkResource.HeaderMap;

/**
 * Overlay type for network resource messages that are sent before issuing a
 * request for a resource.
 */
public final class NetworkRequestWillBeSentEvent extends NetworkEvent {
  
  /**
   *
   */
  public static final class Data extends NetworkEvent.Data {
    protected Data() {
    }

    public Response getRedirectResponse() {
      return getJSObjectProperty("redirectResponse").<Response>cast();
    }

    public Request getRequest() {
      return getJSObjectProperty("request").<Request>cast();
    }
  }

  /**
   *
   */
  public static final class Request extends DataBag {
    protected Request() {
    }

    public HeaderMap getHeaders() {
      return getJSObjectProperty("headers").<HeaderMap>cast();
    }
  }

  protected NetworkRequestWillBeSentEvent() {
  }
  
  /** Get the URL that was redirected to this resource, if there was a redirect
   * @return the original URL, or null if no redirect occurred
   */
  public String getRedirectUrl() {
    return getData().getStringProperty("documentURL");
  }
}
