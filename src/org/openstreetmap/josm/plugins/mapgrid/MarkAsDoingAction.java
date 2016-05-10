package org.openstreetmap.josm.plugins.mapgrid;

import static org.openstreetmap.josm.tools.I18n.tr;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import org.openstreetmap.josm.actions.JosmAction;
import org.openstreetmap.josm.tools.Shortcut;

public class MarkAsDoingAction extends JosmAction {

	private MapGridLayer layer;

	/**
	 * Constructor
	 */
	public MarkAsDoingAction() {
		super(tr("in progress"), MapGridPlugin.getProvider("icon24.png"), tr("being validated"), Shortcut
				.registerShortcut("in progress", tr("in progress"), KeyEvent.VK_UNDERSCORE, Shortcut.SHIFT),
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
				layer.getSelectedGrid().mark(Grid.STATE.DOING);
			}
		}

	}

}
