package ssm.example.start.toDo.controllers;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import ssm.example.start.redis.model.Notes;
import ssm.example.start.redis.model.Type;
import ssm.example.start.redis.repository.NotesRepository;
import ssm.example.start.toDo.events.Events;
import ssm.example.start.toDo.states.States;

@RestController
@Component
@RequestMapping("/to-do")
public class MainContoller {

	@Autowired
	@Qualifier("factory")
	private StateMachineFactory<States, Events> factory;

	StateMachine<States, Events> stateMachine;

	@Autowired
	@Qualifier("notesRepo")
	private NotesRepository notesRepository;

	@PostConstruct
	void initStateMachine() {
		stateMachine = factory.getStateMachine("myMachine");
	}

	Gson gson = new Gson();

	@RequestMapping(value = "/save-note", method = RequestMethod.POST, consumes = { "application/json" })
	@Cacheable("Notes")
	String saveNote(@RequestBody String payload) {
		boolean sendEvent = stateMachine.sendEvent(Events.ADD) ? stateMachine.sendEvent(Events.ADD_SAVE) : false;
		System.out.println("Event Occured value:" + sendEvent);
		if (sendEvent) {
			Notes notes = gson.fromJson(payload, Notes.class);
			notesRepository.save(notes);
			return "Note Added SuccessFully";
		}
		return "Note not Added due to wrong State";
	}

	@RequestMapping(value = "/view-note/{type}/{title}", method = RequestMethod.GET, produces = "application/json")
	Notes viewtNote(@RequestBody(required = false) String payload, @PathVariable String type,
			@PathVariable String title) {
		if (stateMachine.sendEvent(Events.VIEW)) {
			return notesRepository.findByTitle(type, title);
		}
		return new Notes();
	}

	@RequestMapping(value = "/view-note/{type}", method = RequestMethod.GET, produces = "application/json")
	Map<String, String> getAllNotes(@RequestBody(required = false) String payload, @PathVariable String type) {
		if (stateMachine.sendEvent(Events.VIEW)) {
			Map<String, String> findAll = notesRepository.findAll(Type.valueOf(type));
			return findAll;
		}
		return new LinkedHashMap<>();
	}

	@RequestMapping(value = "/edit-note/{type}/{title}", method = RequestMethod.GET, produces = "application/json")
	Notes editNotes(@RequestBody(required = false) String payload, @PathVariable String type,
			@PathVariable String title) {
		if (stateMachine.sendEvent(Events.VIEW) ? stateMachine.sendEvent(Events.EDIT) : false) {
			Notes findAll = notesRepository.findByTitle(type, title);
			return findAll;
		}
		return new Notes();
	}

	@RequestMapping(value = "/delete-note/{type}/{title}", method = RequestMethod.DELETE)
	String delNotes(@RequestBody(required = false) String payload, @PathVariable("type") String type,
			@PathVariable("title") String title) {
		if (stateMachine.sendEvent(Events.DELETE)) {
			return notesRepository.delete(type, title).toString();
		}
		return "Not DELETED";
	}

}
