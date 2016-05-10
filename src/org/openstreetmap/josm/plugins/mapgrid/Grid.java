package org.openstreetmap.josm.plugins.mapgrid;

import static org.openstreetmap.josm.tools.I18n.tr;

import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.data.Bounds;
import org.openstreetmap.josm.data.coor.LatLon;
import org.openstreetmap.josm.gui.MapView;

import java.awt.*;
import javax.swing.JOptionPane;

/**
 * @author Jorge López
 * @since 24/03/16
 */
public class Grid {

	private final static int NW = 0;
	private final static int NE = 1;
	private final static int SE = 2;
	private final static int SW = 3;

	public enum STATE {
		TODO, DOING, DONE
	}

	private boolean selected = false;

	private Grid parent;

	private LatLon[] corners;
	private Grid[] subgrids;
	private LatLon center;
	private STATE state;

	public Grid(LatLon a, LatLon b) {
		this(a, b, null);
		this.state = Grid.STATE.TODO;

	}

	public Grid(LatLon a, LatLon b, Grid parent) {
		corners = new LatLon[] { new LatLon(Math.max(a.lat(), b.lat()), Math.min(a.lon(), b.lon())),
				new LatLon(Math.max(a.lat(), b.lat()), Math.max(a.lon(), b.lon())),
				new LatLon(Math.min(a.lat(), b.lat()), Math.max(a.lon(), b.lon())),
				new LatLon(Math.min(a.lat(), b.lat()), Math.min(a.lon(), b.lon())) };
		center = new LatLon(((corners[NW].lat() + corners[SW].lat()) / 2.),
				((corners[NW].lon() + corners[NE].lon()) / 2.));
		if (parent != null) {
			this.state = parent.getState();
			this.parent = parent;
		}
	}

	public void split() {
		subgrids = new Grid[] { new Grid(corners[NW], center, this), new Grid(corners[NE], center, this),
				new Grid(corners[SE], center, this), new Grid(corners[SW], center, this) };

		Main.map.repaint();
	}

	/**
	 * Method for marking the grid a color depending on its status
	 * 
	 * @param status:
	 *            TODO, DOING or DONE
	 */
	protected void mark(Grid.STATE status) {
		this.setState(status);
		Main.map.repaint();
	}

	public STATE getState() {
		return state;
	}

	protected void setState(STATE state) {
		this.state = state;
		if (subgrids != null) {
			for (Grid subgrid : subgrids) {
				subgrid.setState(state);
			}
		}
	}

	public Grid getParent() {
		return parent;
	}

	public void select() {
		selected = true;
	}

	public void unselect() {
		selected = false;
	}

	/**
	 * Gets the smallest grid containing a given LatLon
	 *
	 * @param latlon
	 * @return
	 */
	public Grid getGrid(LatLon latlon) {
		if (!contains(latlon)) {
			return null;
		}
		if (subgrids == null) {
			return this;
		} else {
			if (latlon.lat() > center.lat()) {
				if (latlon.lon() > center.lon()) {
					return subgrids[NE].getGrid(latlon);
				} else {
					return subgrids[NW].getGrid(latlon);
				}
			} else {
				if (latlon.lon() > center.lon()) {
					return subgrids[SE].getGrid(latlon);
				} else {
					return subgrids[SW].getGrid(latlon);
				}
			}
		}
	}

	/**
	 * Fills the rectangle with the given color
	 * 
	 * @param graphics2D
	 * @param mapView
	 * @param color
	 */
	private void changeFillColor(Graphics2D graphics2D, MapView mapView, Color color) {
		// fill the inside:
		if (color != null) {
			graphics2D.setColor(color);
			graphics2D.fillRect(mapView.getPoint(corners[NW]).x, mapView.getPoint(corners[NW]).y,
					mapView.getPoint(corners[NE]).x - mapView.getPoint(corners[NW]).x,
					mapView.getPoint(corners[SW]).y - mapView.getPoint(corners[NW]).y);
		}

		// draw the border (again):
		for (int i = 0; i < 4; i++) {
			Point a = mapView.getPoint(corners[i]);
			Point b = mapView.getPoint(corners[(i + 1) % 4]);
			graphics2D.setColor(Color.WHITE);
			graphics2D.drawLine(a.x, a.y, b.x, b.y);
		}
	}

	public void paint(Graphics2D graphics2D, MapView mapView, Bounds bounds) {
		if (subgrids == null) {
			if (bounds.contains(center)) {
				if (this.state == Grid.STATE.TODO) {
					this.changeFillColor(graphics2D, mapView, new Color(255, 255, 0, 64));
				} else if (this.state == Grid.STATE.DONE) {
					this.changeFillColor(graphics2D, mapView, new Color(0, 255, 0, 64));
				} else {
					this.changeFillColor(graphics2D, mapView, null);
				}
			}
		} else {
			for (Grid subgrid : subgrids) {
				subgrid.paint(graphics2D, mapView, bounds);
			}
		}
		if (selected) {
			this.changeFillColor(graphics2D, mapView, new Color(255, 0, 0, 150));
		}
	}

	public boolean contains(LatLon latlon) {
		if (latlon.lat() < corners[NE].lat() && latlon.lat() > corners[SE].lat()) {
			if (latlon.lon() < corners[NE].lon() && latlon.lon() > corners[NW].lon()) {
				return true;
			}
		}
		return false;
	}
}
