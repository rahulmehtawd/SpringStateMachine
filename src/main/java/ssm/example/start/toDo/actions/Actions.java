package ssm.example.start.toDo.actions;

import org.springframework.statemachine.action.Action;

import ssm.example.start.toDo.events.Events;
import ssm.example.start.toDo.states.States;

public interface Actions {
	//For Add Events
	public Action<States, Events> getAddEventAction();
	public Action<States, Events> getAddSaveEventAction();
	
	//For Edit Events
	public Action<States, Events> getEditEventAction();
	public Action<States, Events> getEditSaveEventAction();
	
	public Action<States, Events> getViewEventAction();
	public Action<States, Events> getDeleteEventAction();
	public Action<States, Events> getUndoEventAction();
	
}
