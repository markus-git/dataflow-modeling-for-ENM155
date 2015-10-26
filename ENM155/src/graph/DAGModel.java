package graph;

import java.awt.Dimension;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

public class DAGModel<V, E> {

	private EventListenerList changeListenerList = new EventListenerList();
	private ChangeListener    changeListener     = new ChangeListener() {
		
		@Override
		public void stateChanged(ChangeEvent e) {
			fireEvent();
		}
	};
	
	public DAGModel(Layout<V, E> layout, Dimension size)  {
		
	}
	
	public void addChangeListener(ChangeListener l) {
		
	}
	
	public void removeChangeListener(ChangeListener l) {
		
	}
	
	private void fireEvent() {
		for (ChangeListener c : changeListenerList.getListeners(ChangeListener.class)) {
			c.stateChanged(new ChangeEvent(this));
		}
	}
}
