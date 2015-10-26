package graph;

import java.awt.Dimension;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

public class DAGModel<V, E> {

	private Layout<V, E> layout;
	
	private EventListenerList changeListenerList = new EventListenerList();
	private ChangeListener    changeListener     = new ChangeListener() {
		
		@Override
		public void stateChanged(ChangeEvent e) {
			fireEvent();
		}
	};
	
	public DAGModel(Layout<V, E> layout, Dimension size)  {
		if (size == null) {
			throw new GraphException("Dimension of model cannot be null.");
		}
		
		this.layout = layout;
		this.layout.updateDimension(size);
		this.layout.initialize();
		
		fireEvent();
	}
	
	public void addChangeListener(ChangeListener l) {
		changeListenerList.add(ChangeListener.class, l);
	}
	
	public void removeChangeListener(ChangeListener l) {
		changeListenerList.remove(ChangeListener.class, l);
	}
	
	public void fireEvent() {
		for (ChangeListener c : changeListenerList.getListeners(ChangeListener.class)) {
			c.stateChanged(new ChangeEvent(this));
		}
	}
}
