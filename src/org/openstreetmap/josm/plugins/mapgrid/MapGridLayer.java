package org.openstreetmap.josm.plugins.mapgrid;

import static org.openstreetmap.josm.tools.I18n.tr;

import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.data.Bounds;
import org.openstreetmap.josm.data.coor.LatLon;
import org.openstreetmap.josm.data.osm.visitor.BoundingXYVisitor;
import org.openstreetmap.josm.gui.MapView;
import org.openstreetmap.josm.gui.layer.Layer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static org.openstreetmap.josm.tools.I18n.tr;

/**
 * Layer of the plugin.
 *
 * @author nokutu
 * @since 12/02/16.
 */
public class MapGridLayer extends Layer {

  private Grid head;
  private MapGridPlugin plugin;
  private MouseManager mm;

  private Grid selectedGrid;

  public MapGridLayer(MapGridPlugin plugin) {
    super(tr("MapGrid layer"));
    this.plugin = plugin;
    mm = new MouseManager(this);
    Main.map.mapView.addMouseListener(mm);
    Main.map.mapView.addMouseMotionListener(mm);
    plugin.splitGridAction.setLayer(this);
  }

  public void createManually() {
    Main.map.mapView.setActiveLayer(this);
    Main.map.mapView.addMouseListener(new MouseAdapter() {
      LatLon from;
      LatLon to;

      @Override
      public void mouseClicked(MouseEvent e) {
        if (!isActive()) {
          return;
        }
        if (from == null) {
          from = Main.map.mapView.getLatLon(e.getX(), e.getY());
        } else if (to == null) {
          to = Main.map.mapView.getLatLon(e.getX(), e.getY());
          head = new Grid(from, to);
          Main.map.mapView.removeMouseListener(this);
          Main.map.mapView.removeMouseMotionListener(this);
          Main.map.mapView.repaint();
        }
      }
    });
  }

  public Grid getHead() {
    return head;
  }

  public void createAutomatically() {
    // TODO
  }

  public Grid getGrid(LatLon latLon) {
    return head == null ? null : head.getGrid(latLon);
  }

  public boolean isActive() {
    if (Main.map.mapView.getActiveLayer().equals(MapGridLayer.this)) {
      return true;
    }
    return false;
  }

  public void setSelectedGrid(Grid grid) {
    if (selectedGrid != null) {
      selectedGrid.unselect();
    }
    selectedGrid = grid;
    plugin.splitGridMenu.setEnabled(false);
    if (grid != null) {
      selectedGrid.select();
      plugin.splitGridMenu.setEnabled(true);
    }
    Main.map.mapView.repaint();
  }

  public Grid getSelectedGrid() {
    return selectedGrid;
  }

  @Override
  public void paint(Graphics2D graphics2D, MapView mapView, Bounds bounds) {
    if (head != null) {
      head.paint(graphics2D, mapView, bounds);
    }
  }

  @Override
  public Icon getIcon() {
    return MapGridPlugin.ICON24;
  }

  @Override
  public String getToolTipText() {
    return "Mapgrid layer";
  }

  @Override
  public void mergeFrom(Layer layer) {

  }

  @Override
  public boolean isMergable(Layer layer) {
    return false;
  }

  @Override
  public void visitBoundingBox(BoundingXYVisitor boundingXYVisitor) {

  }

  @Override
  public Object getInfoComponent() {
    return null;
  }

  @Override
  public Action[] getMenuEntries() {
    return new Action[0];
  }

  @Override
  public void destroy() {
    super.destroy();
    plugin.splitGridAction.removeLayer();
  }
}
