package graph;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class DAGViewer<V, E> extends JPanel implements ChangeListener {

	private static final long serialVersionUID = 1L;
	
	DAGModel<V, E> model;
	
	public DAGViewer(Layout<V, E> layout, Dimension size) {
		this.model = new DAGModel<>(layout, size);
		this.model.addChangeListener(this);
		
		setDoubleBuffered(true);
		setPreferredSize(size);
		setFocusable(true);
	}

	// ------------------------------------------
	
	private void fireEvent() {
		
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		repaint();
		fireEvent();
	}
}
