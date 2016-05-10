package org.openstreetmap.josm.plugins.mapgrid;

import static org.openstreetmap.josm.tools.I18n.tr;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import org.openstreetmap.josm.actions.JosmAction;
import org.openstreetmap.josm.tools.Shortcut;

public class MarkAsTodoAction extends JosmAction {

	private MapGridLayer layer;

	/**
	 * Constructor
	 */
	public MarkAsTodoAction() {
		super(tr("ToDo"), MapGridPlugin.getProvider("icon24.png"), tr("not yet validated"), Shortcut
				.registerShortcut("ToDo", tr("ToDo"), KeyEvent.VK_UNDERSCORE, Shortcut.SHIFT),
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
				layer.getSelectedGrid().mark(Grid.STATE.TODO);
			}
		}

	}

}
