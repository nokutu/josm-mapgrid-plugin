package org.openstreetmap.josm.plugins.mapgrid;

import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.actions.JosmAction;
import org.openstreetmap.josm.gui.layer.Layer;
import org.openstreetmap.josm.tools.Shortcut;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import static org.openstreetmap.josm.tools.I18n.tr;

/**
 * @author nokutu
 * @since 24/03/16.
 */
public class CreateGridAutomaticallyAction extends JosmAction {

  private MapGridPlugin plugin;

  public CreateGridAutomaticallyAction(MapGridPlugin plugin) {
    super(
            tr("Automatic grid"),
            MapGridPlugin.getProvider("icon24.png"),
            tr("Create grid automatically"),
            Shortcut.registerShortcut("MapGrid Automatic", tr("Create grid automatically"), KeyEvent.VK_PERIOD, Shortcut.SHIFT),
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
    layer.createAutomatically();
  }
}
