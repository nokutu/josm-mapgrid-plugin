// License: GPL. For details, see LICENSE file.
package org.openstreetmap.josm.plugins.mapgrid;

import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.gui.MapFrame;
import org.openstreetmap.josm.gui.preferences.PreferenceSetting;
import org.openstreetmap.josm.plugins.Plugin;
import org.openstreetmap.josm.plugins.PluginInformation;
import org.openstreetmap.josm.tools.ImageProvider;

/**
 * This is the main class of the Mapillary plugin.
 *
 * @author nokutu
 */
public class MapGridPlugin extends Plugin {

  /**
   * OS route separator
   */
  public static final String SEPARATOR = System.getProperty("file.separator");

  /**
   * Main constructor.
   *
   * @param info Required information of the plugin. Obtained from the jar file.
   */
  public MapGridPlugin(PluginInformation info) {
    super(info);
  }

  /**
   * Called when the JOSM map frame is created or destroyed.
   */
  @Override
  public void mapFrameInitialized(MapFrame oldFrame, MapFrame newFrame) {
    // TODO
  }

  @Override
  public PreferenceSetting getPreferenceSetting() {
    // TODO
    return null;
  }

  /**
   * Returns a ImageProvider for the given string or null if in headless mode.
   *
   * @param s The name of the file where the picture is.
   * @return A ImageProvider object for the given string or null if in headless
   * mode.
   */
  public static ImageProvider getProvider(String s) {
    if (Main.main == null) {
      return null;
    }
    return new ImageProvider(s);
  }
}
