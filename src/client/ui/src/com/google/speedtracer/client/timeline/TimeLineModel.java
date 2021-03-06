/*
 * Copyright 2008 Google Inc.
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
package com.google.speedtracer.client.timeline;

import com.google.gwt.user.client.Timer;
import com.google.speedtracer.client.model.GraphCalloutModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Update, zoom and tick logic for TimeLines. This class is responsible for
 * updating the bounds of {@link TimeLineGraph}s.
 */
public class TimeLineModel implements DomainObserver, Boundable {
  /**
   * Interface for receiving notification of timeline bounds changes for the
   * currently viewed window.
   */
  public interface WindowBoundsObserver {
    void onWindowBoundsChange(double left, double right);
  }

  /**
   * Handles update ticks. Also contains logic for killing and restarting the
   * timer when we stop receiving data points.
   */
  private class UpdateTicker {
    public boolean timerActive = false;

    private Timer t;

    private UpdateTicker() {
      t = new Timer() {
        public void run() {
          timerDispatch();
        }
      };
    }

    public void restartUpdateTimer() {
      timerActive = true;
      t.schedule(100);
    }

    private void timerDispatch() {
      if (isDirty) {
        onModelDataRefreshTick(mostRecentDomainValue);
        t.schedule(Constants.REFRESH_RATE);
      } else {
        timerActive = false;
      }
    }
  }

  protected final List<TimeLineGraph> graphs = new ArrayList<TimeLineGraph>();

  private final List<DomainObserver> domainObservers = new ArrayList<DomainObserver>();

  private final GraphCalloutModel graphCalloutModel = new GraphCalloutModel(0,
      0, "", 0);

  private boolean isDirty = false;

  private double leftBound;

  private double mostRecentDomainValue = 0;

  private boolean pinLowerBound;

  private double rightBound;

  private boolean showStreaming;

  // This is basically a timer that controls updates
  private UpdateTicker updateController;

  private final List<WindowBoundsObserver> windowObservers = new ArrayList<WindowBoundsObserver>();

  public TimeLineModel(boolean streaming, boolean pinLower) {
    updateController = new UpdateTicker();

    showStreaming = streaming;
    pinLowerBound = pinLower;

    leftBound = 0;
    rightBound = Constants.DEFAULT_GRAPH_WINDOW_SIZE;
  }

  public void addDomainObserver(DomainObserver observer) {
    domainObservers.add(observer);
  }

  public void addWindowBoundsObserver(WindowBoundsObserver observer) {
    windowObservers.add(observer);
  }

  public void fireBoundsChangeEvent(double start, double end) {
    for (int i = 0, n = windowObservers.size(); i < n; i++) {
      WindowBoundsObserver observer = windowObservers.get(i);
      observer.onWindowBoundsChange(start, end);
    }
    isDirty = false;
  }

  public GraphCalloutModel getGraphCalloutModel() {
    return graphCalloutModel;
  }

  public int getGraphCount() {
    return graphs.size();
  }

  public ArrayList<TimeLineGraph> getGraphs() {
    return (ArrayList<TimeLineGraph>) graphs;
  }

  public double getLeftBound() {
    return leftBound;
  }

  public double getMostRecentDomainValue() {
    return mostRecentDomainValue;
  }

  public double getRightBound() {
    return rightBound;
  }

  public boolean isStreaming() {
    return showStreaming;
  }

  /**
   * When we get a new data point from one of the GraphModels we are observing,
   * our domain changes. We update the graphs on a tick, so we determine here if
   * we should restart the timer and mark the graph as dirty.
   */
  public void onDomainChange(double newValue) {
    mostRecentDomainValue = newValue;
    if (newValue <= getRightBound() || showStreaming) {
      isDirty = true;
    }
    if (!updateController.timerActive) {
      updateController.restartUpdateTimer();
    }

    for (int i = 0, n = domainObservers.size(); i < n; ++i) {
      domainObservers.get(i).onDomainChange(newValue);
    }
  }

  /**
   * An update tick. Lets us know we should update our window bounds.
   * 
   * @param now tick time
   */
  public void onModelDataRefreshTick(double now) {
    // we need this for the case where we are receiving ticks
    // and not streaming (makes no sense to tick anymore!)
    if (showStreaming) {
      // updateController.shouldTick = false;
      double newLowerBound = (pinLowerBound ? getLeftBound() : getLeftBound()
          + (now - getRightBound()));

      updateBounds(newLowerBound, now);
    } else {
      refresh();
    }
  }

  /**
   * Calls setBounds with the existing left and right bounds.
   */
  public void refresh() {
    updateBounds(leftBound, rightBound);
  }

  public void removeDomainObserver(DomainObserver observer) {
    domainObservers.remove(observer);
  }

  public void removeWindowBoundsObserver(WindowBoundsObserver observer) {
    windowObservers.remove(observer);
  }

  /**
   * Set whether or not we want the graph to show data streaming in. If we do
   * want to show streaming, we may or may not be receiving new data, but may
   * have gotten new data while streaming was off. In that case it synthesizes
   * an update tick.
   * 
   * @param showStreaming
   */
  public void setStreaming(boolean showStreaming) {
    this.showStreaming = showStreaming;
    if (showStreaming) {
      onModelDataRefreshTick(mostRecentDomainValue);
    }
  }

  public void toggleFixBounds() {
    pinLowerBound = !pinLowerBound;
  }

  /**
   * Sets the bounds for this TimeLine. A TimeLine must have sensible bounds.
   * Left side must be <= right side.
   */
  public void updateBounds(double leftBound, double rightBound) {
    assert (leftBound <= rightBound);
    this.leftBound = leftBound;
    this.rightBound = rightBound;
    fireBoundsChangeEvent(leftBound, rightBound);
  }

  /**
   * Adds a graph to the TimeLine. Adds the graph as a WindowObserver.
   * 
   * @param graph
   */
  void addGraph(TimeLineGraph graph) {
    graphs.add(graph);
    addWindowBoundsObserver(graph);
  }
}
