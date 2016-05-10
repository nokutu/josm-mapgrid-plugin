package org.openstreetmap.josm.plugins.mapgrid;

import org.openstreetmap.josm.Main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author nokutu
 * @since 24/03/16.
 */
public class MouseManager extends MouseAdapter {

  private MapGridLayer layer;

  public MouseManager(MapGridLayer layer) {
    this.layer = layer;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    if (layer.isActive() && layer.getHead() != null) {
      if (e.getButton() == 1) {
        Grid g = layer.getGrid(Main.map.mapView.getLatLon(e.getX(), e.getY()));
        layer.setSelectedGrid(g);
      } else if (e.getButton() == 3) {
        if (layer.getSelectedGrid() != null && layer.getSelectedGrid().contains(Main.map.mapView.getLatLon(e.getX(), e.getY()))) {
          layer.setSelectedGrid(layer.getSelectedGrid().getParent());
        }
      }
    }
    Main.map.repaint();
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    Main.map.repaint();
  }
}
