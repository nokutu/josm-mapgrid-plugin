package org.openstreetmap.josm.plugins.mapgrid;

import static org.openstreetmap.josm.tools.I18n.tr;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.actions.JosmAction;
import org.openstreetmap.josm.gui.layer.Layer;
import org.openstreetmap.josm.tools.Shortcut;

/**
 * @author Jorge López
 * @since 24/03/16
 */
public class CreateGridManuallyAction extends JosmAction {

  private MapGridPlugin plugin;

  public CreateGridManuallyAction(MapGridPlugin plugin) {
    super(
            tr("Manual grid"),
            MapGridPlugin.getProvider("icon24.png"),
            tr("Create grid manually"),
            Shortcut.registerShortcut("MapGrid Manual", tr("Create grid manually "), KeyEvent.VK_COMMA, Shortcut.SHIFT),
            false,
            "createGrid",
            false
    );
    this.plugin = plugin;
    setEnabled(false); 
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    for (Layer l : Main.map.mapView.getAllLayers()) {
      if (l instanceof MapGridLayer) {
        return;
      }
    }

    MapGridLayer layer = new MapGridLayer(plugin);
    Main.map.mapView.addLayer(layer);
    layer.createManually();
  }
}
