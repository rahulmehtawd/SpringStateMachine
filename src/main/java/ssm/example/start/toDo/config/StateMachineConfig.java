package ssm.example.start.toDo.config;

import java.util.EnumSet;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;
import org.springframework.statemachine.trigger.Trigger;

//import ssm.example.start.toDo.adapters.Adapter1;
import ssm.example.start.toDo.events.Events;
import ssm.example.start.toDo.states.States;


@Configuration
@EnableStateMachineFactory(name = "factory")
// @EnableStateMachine
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {
	//@Autowired
	// Adapter1 adapter1;

	@Override
	public void configure(StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
		config.withConfiguration().autoStartup(true).listener(listener()).machineId("myMachine");
	}

	@Override
	public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
		states.withStates().initial(States.INIT).states(EnumSet.allOf(States.class));
	}

	@Override
	public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
		transitions
				.withExternal().source(States.INIT).target(States.ADDED_NOT_SAVED).event(Events.ADD).action(action())
				.and().withExternal().source(States.INIT).target(States.VIEWING).event(Events.VIEW)
				.and().withExternal().source(States.INIT).target(States.DELETED).event(Events.DELETE)
				
				.and().withExternal().source(States.ADDED_NOT_SAVED).target(States.SAVED).event(Events.ADD_SAVE)
				
				.and().withExternal().source(States.VIEWING).target(States.EDITING_NOT_SAVED).event(Events.EDIT)
				.and().withExternal().source(States.VIEWING).target(States.VIEWING).event(Events.VIEW)
				.and().withExternal().source(States.VIEWING).target(States.DELETED).event(Events.DELETE)
				
				
				.and().withExternal().source(States.SAVED).target(States.EDITING_NOT_SAVED).event(Events.EDIT)
				.and().withExternal().source(States.SAVED).target(States.VIEWING).event(Events.VIEW)
				.and().withExternal().source(States.SAVED).target(States.DELETED).event(Events.DELETE)
				//.and().withExternal().source(States.SAVED).target(States.VIEWING).event(Events.VIEW)
				
				.and().withExternal().source(States.EDITING_NOT_SAVED).target(States.DELETED).event(Events.DELETE)
				.and().withExternal().source(States.EDITING_NOT_SAVED).target(States.SAVED).event(Events.EDIT_SAVE)
				
				.and().withExternal().source(States.DELETED).target(States.VIEWING).event(Events.VIEW)
				.and().withExternal().source(States.DELETED).target(States.SAVED).event(Events.UNDO);
	}

	@Bean
	public Action<States, Events> action() {
		return new Action<States, Events>() {
			@Override
			public void execute(StateContext<States, Events> context) {
				System.out.println("Method Name: action");
				System.out.println("Event :" + context.getEvent());
				System.out.println("Initial State :" + context.getSource().getId());
				System.out.println("Next State :" + context.getTarget().getId());
			}
		};
	}

	@Bean
	public StateMachineListener<States, Events> listener() {
		System.out.println("Method Name: StateMachineListener");
		return new StateMachineListenerAdapter<States, Events>() {
			@Override
			public void stateChanged(State<States, Events> from, State<States, Events> to) {
				System.out.println("State change to " + to.getId());
			}

			@Override
			public void stateMachineStarted(StateMachine<States, Events> stateMachine) {
				System.out.println(
						"StateMachine uuid " + stateMachine.getUuid() + " state " + stateMachine.getState().getId());
			}

			@Override
			public void eventNotAccepted(Message<Events> event) {
				System.out.println(event + "Not Valid");
			}

			@Override
			public void transition(Transition<States, Events> transition) {
				Trigger<States, Events> trigger = transition.getTrigger();
				State<States, Events> source = transition.getSource();
				State<States, Events> target = transition.getTarget();
				System.out.println("Transition trigger Event :" + (trigger != null ? trigger.getEvent() : trigger)
						+ " source " + (source != null ? source.getId() : source) + " target "
						+ (target != null ? target.getId() : target) + " kind " + transition.getKind());
			}

		};
	}
}
