package ssm.example.start.toDo.actions;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Service;

import ssm.example.start.toDo.events.Events;
import ssm.example.start.toDo.states.States;
@Service("actions")
public class ActionsImpl implements Actions {

	@Override
	public Action<States, Events> getAddEventAction() {
		return new Action<States, Events>() {
			@Override
			public void execute(StateContext<States, Events> context) {
				System.out.println("Method Name: getAddEventAction");
				System.out.println("Event Fired:" + context.getEvent());
				System.out.println("Initial State :" + context.getSource().getId());
				System.out.println("Next State :" + context.getTarget().getId());
				System.out.println("Choose Appropriate Event::" + Events.ADD_SAVE);
			}
		};
	}

	@Override
	public Action<States, Events> getAddSaveEventAction() {
		return new Action<States, Events>() {
			@Override
			public void execute(StateContext<States, Events> context) {
				System.out.println("Method Name: getAddSaveEventAction");
				System.out.println("Event Fired:" + context.getEvent());
				System.out.println("Initial State :" + context.getSource().getId());
				System.out.println("Next State :" + context.getTarget().getId());
				System.out.println("Choose Appropriate Event::" + Events.EDIT+"/n2::"+Events.VIEW+"/n3::"+Events.DELETE);
			}
		};
	}

	@Override
	public Action<States, Events> getEditEventAction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Action<States, Events> getEditSaveEventAction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Action<States, Events> getViewEventAction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Action<States, Events> getDeleteEventAction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Action<States, Events> getUndoEventAction() {
		// TODO Auto-generated method stub
		return null;
	}

}
