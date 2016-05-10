package org.openstreetmap.josm.plugins.mapgrid;

import static org.openstreetmap.josm.tools.I18n.tr;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import org.openstreetmap.josm.actions.JosmAction;
import org.openstreetmap.josm.tools.Shortcut;

/**
 * @author nokutu
 * @since 24/03/16.
 */
public class SplitGridAction extends JosmAction {

	private MapGridLayer layer;

	public SplitGridAction() {
		super(tr("Split grid"), MapGridPlugin.getProvider("icon24.png"), tr("Split selected grid"), Shortcut
				.registerShortcut("MapGrid Split", tr("Split selected grid"), KeyEvent.VK_UNDERSCORE, Shortcut.SHIFT),
				false, "createGrid", false);
		setEnabled(false);
	}

	protected void setLayer(MapGridLayer layer) {
		this.layer = layer;
	}

	public void removeLayer() {
		layer = null;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (layer != null) {
			if (layer.getSelectedGrid() != null) {
				layer.getSelectedGrid().split();
			}
		}
	}
}
