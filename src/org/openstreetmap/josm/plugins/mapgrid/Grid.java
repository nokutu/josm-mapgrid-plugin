package org.openstreetmap.josm.plugins.mapgrid;

import static org.openstreetmap.josm.tools.I18n.tr;

import org.openstreetmap.josm.data.Bounds;
import org.openstreetmap.josm.data.coor.LatLon;
import org.openstreetmap.josm.gui.MapView;

import java.awt.*;

/**
 * @author Jorge López
 * @since 24/03/16
 */
public class Grid {

  private final static int NW = 0;
  private final static int NE = 1;
  private final static int SW = 2;
  private final static int SE = 3;

  public enum STATE {TODO, DOING, DONE}

  ;

  private LatLon[] corners;
  private Grid[] subgrids;
  private LatLon center;
  private STATE state;

  public Grid(LatLon a, LatLon b) {
    corners = new LatLon[]{
            new LatLon(Math.max(a.lat(), b.lat()), Math.min(a.lon(), b.lon())),
            new LatLon(Math.max(a.lat(), b.lat()), Math.max(a.lon(), b.lon())),
            new LatLon(Math.min(a.lat(), b.lat()), Math.min(a.lon(), b.lon())),
            new LatLon(Math.min(a.lat(), b.lat()), Math.max(a.lon(), b.lon())),
    };
    center = new LatLon((corners[NW].lat() + corners[SW].lat() / 2), (corners[NW].lat() + corners[NE].lat() / 2));
    state = STATE.TODO;
  }

  public void split() {
    subgrids = new Grid[]{
            new Grid(corners[NW], center),
            new Grid(corners[NE], center),
            new Grid(corners[SW], center),
            new Grid(corners[SE], center)
    };
  }

  public STATE getState() {
    return state;
  }

  public void setState(STATE state) {
    this.state = state;
    if (subgrids != null) {
      for (Grid subgrid : subgrids) {
        subgrid.setState(state);
      }
    }
  }

  /**
   * Gets the smallest grid containg a given LatLon
   *
   * @param latlon
   * @return
   */
  public Grid getGrid(LatLon latlon) {
    if (!contains(latlon)) {
      return null;
    }
    if (subgrids == null) {
      return this;
    } else {
      if (latlon.lat() > center.lat()) {
        if (latlon.lon() > center.lon()) {
          return subgrids[NE].getGrid(latlon);
        } else {
          return subgrids[NW].getGrid(latlon);
        }
      } else {
        if (latlon.lon() > center.lon()) {
          return subgrids[SE].getGrid(latlon);
        } else {
          return subgrids[SW].getGrid(latlon);
        }
      }
    }
  }

  public void paint(Graphics2D graphics2D, MapView mapView, Bounds bounds) {
    if (subgrids == null) {
      if (bounds.contains(center)) {
        for (int i = 0; i < 4; i++) {
          Point a = mapView.getPoint(corners[i]);
          Point b = mapView.getPoint(corners[(i + 1) % 4]);
          graphics2D.drawLine(a.x, a.y, b.x, b.y);
        }
      }
    } else {
      for (Grid subgrid : subgrids) {
        subgrid.paint(graphics2D, mapView, bounds);
      }
    }
  }

  public boolean contains(LatLon latlon) {
    if (latlon.lat() < corners[NE].lat() && latlon.lat() > corners[SE].lat()) {
      if (latlon.lon() < corners[NE].lon() && latlon.lon() > corners[NW].lon()) {
        return true;
      }
    }
    return false;
  }
}
