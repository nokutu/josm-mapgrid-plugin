package org.openstreetmap.josm.plugins.mapgrid;

import org.openstreetmap.josm.data.Bounds;
import org.openstreetmap.josm.data.osm.visitor.BoundingXYVisitor;
import org.openstreetmap.josm.gui.MapView;
import org.openstreetmap.josm.gui.layer.Layer;

import javax.swing.*;
import java.awt.*;

import static org.openstreetmap.josm.tools.I18n.tr;

/**
 * Layer of the plugin.
 *
 * @author nokutu
 * @since 12/02/16.
 */
public class MapGridLayer extends Layer {


  public MapGridLayer() {
    super(tr("MapGrid layer"));
  }

  @Override
  public void paint(Graphics2D graphics2D, MapView mapView, Bounds bounds) {

  }

  @Override
  public Icon getIcon() {
    return null;
  }

  @Override
  public String getToolTipText() {
    return null;
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
}
