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
package com.google.gwt.coreext.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayBoolean;
import com.google.gwt.core.client.JsArrayString;

/**
 * A specialization of {@link JsStringMap} for <code>boolean</code> values.
 */
public final class JsStringBooleanMap extends JavaScriptObject {
  public static JsStringBooleanMap create() {
    return JavaScriptObject.createObject().cast();
  }

  protected JsStringBooleanMap() {
  }

  /**
   * Removes an existing key from the map.
   * 
   * @param key
   */
  public void erase(String key) {
    JsStringMap.erase(this, key);
  }

  /**
   * Retrieves the value stored for the given key.
   * 
   * @param key
   * @return the value, <code>null</code> if the key doesn't exist
   */
  public boolean get(String key) {
    return JsStringMap.getBoolean(this, key);
  }

  /**
   * Gets all keys. The order is unspecified.
   * 
   * @return
   */
  public JsArrayString getKeys() {
    return JsStringMap.getKeys(this).cast();
  }

  /**
   * Gets all values. The order is unspecified.
   * 
   * @return
   */
  public JsArrayBoolean getValues() {
    return JsStringMap.getValues(this).cast();
  }

  /**
   * Indicates whether there is a value stored for the given key.
   * 
   * @param key
   * @return
   */
  public boolean hasKey(String key) {
    return JsStringMap.hasKey(this, key);
  }

  /**
   * Store a value for the given key.
   * 
   * @param key
   * @param value
   */
  public void put(String key, boolean value) {
    JsStringMap.put(this, key, value);
  }
}
