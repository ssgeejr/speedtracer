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

/**
 * Mockable interface for looking up an event record from the DataModel.
 */
public interface EventRecordLookup {

  /**
   * Retrieves an EventRecord by sequence number.
   * 
   * @param sequence A sequence number to look for.
   * @return the record with the specified sequence number.
   */
  EventRecord findEventRecordFromSequence(int sequence);

}
