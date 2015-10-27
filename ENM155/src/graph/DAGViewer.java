package graph;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

public class DAGViewer<V, E> extends JPanel implements ChangeListener {

	private static final long serialVersionUID = 1L;
	
	private EventListenerList changeListenerList = new EventListenerList();
	private ChangeListener    changeListener     = new ChangeListener() {
		
		@Override
		public void stateChanged(ChangeEvent e) {
			fireEvent();
		}
	};
	
	private DAGModel<V, E> model;
	
	public DAGViewer(Layout<V, E> layout, Dimension size) {
		this.model = new DAGModel<>(layout, size);
		this.model.addChangeListener(this);
		
		setDoubleBuffered(true);
		setPreferredSize(size);
		setFocusable(true);
	}
	
	// ------------------------------------------
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
	
	private void paintGraph(Graphics g) {
		Dimension size = getSize();
		
		// Draw background.
		g.setColor(Color.white);
		g.fillRect(0, 0, size.width, size.height);
		
		// Draw arrows.
		
		
		// Draw vertices.
	}

	// ------------------------------------------
	
	public void addChangeListener(ChangeListener l) {
		changeListenerList.add(ChangeListener.class, l);
	}
	
	public void removeChangeListener(ChangeListener l) {
		changeListenerList.remove(ChangeListener.class, l);
	}
	
	public void fireEvent() {
		// Hmm..
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		repaint();
		fireEvent();
	}
}
