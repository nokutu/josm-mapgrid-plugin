// License: GPL. For details, see LICENSE file.
package org.openstreetmap.josm.plugins.mapgrid;

import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.gui.MainMenu;
import org.openstreetmap.josm.gui.MapFrame;
import org.openstreetmap.josm.gui.preferences.PreferenceSetting;
import org.openstreetmap.josm.plugins.Plugin;
import org.openstreetmap.josm.plugins.PluginInformation;
import org.openstreetmap.josm.tools.ImageProvider;

import javax.swing.*;

/**
 * This is the main class of the Mapillary plugin.
 *
 * @author nokutu
 */
public class MapGridPlugin extends Plugin {

  public static final ImageIcon ICON24 = new ImageProvider("icon24.png").get();

  private CreateGridManuallyAction createGridManuallyAction;
  private JMenuItem createGridManuallyMenu;

  private CreateGridAutomaticallyAction createGridAutomaticallyAction;
  private JMenuItem createGridAutomaticallyMenu;

  public SplitGridAction splitGridAction;
  public JMenuItem splitGridMenu;

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
    createGridManuallyAction = new CreateGridManuallyAction(this);
    createGridManuallyMenu = MainMenu.add(Main.main.menu.viewMenu, createGridManuallyAction, false);

    createGridAutomaticallyAction = new CreateGridAutomaticallyAction(this);
    createGridAutomaticallyMenu = MainMenu.add(Main.main.menu.viewMenu, createGridAutomaticallyAction, false);

    splitGridAction = new SplitGridAction();
    splitGridMenu = MainMenu.add(Main.main.menu.viewMenu, splitGridAction, false);
  }

  /**
   * Called when the JOSM map frame is created or destroyed.
   */
  @Override
  public void mapFrameInitialized(MapFrame oldFrame, MapFrame newFrame) {
    if (oldFrame == null && newFrame != null) {
      createGridManuallyMenu.setEnabled(true);
      createGridAutomaticallyMenu.setEnabled(true);
    } else if (oldFrame != null && newFrame == null) {
      createGridManuallyMenu.setEnabled(false);
      createGridAutomaticallyMenu.setEnabled(false);
    }
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
