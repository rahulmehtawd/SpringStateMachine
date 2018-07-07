package ssm.example.start.toDo.controllers;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ssm.example.start.redis.model.Student;
import ssm.example.start.redis.model.Student.Gender;
import ssm.example.start.redis.repository.StudentRepository;
import ssm.example.start.toDo.events.Events;
import ssm.example.start.toDo.states.States;

@RestController
@Component
@RequestMapping("/ssm")
public class TestController {

	@Autowired
	@Qualifier("factory")
	private StateMachineFactory<States, Events> factory;

	StateMachine<States, Events> stateMachine;
	
	@Autowired
	@Qualifier("studentRepo")
	private StudentRepository studentRepository;

	@PostConstruct
	void initStateMachine() {
		stateMachine = factory.getStateMachine("myMachine");
	}

	@RequestMapping(value = "/create-note", method = RequestMethod.GET)
	String createNote() {

		boolean sendEvent = stateMachine.sendEvent(Events.ADD);
		System.out.println("Event Occured Successfully value:" + sendEvent);
		return "Note Created Successfully.";
	}

//	@RequestMapping(value = "/save-note", method = RequestMethod.POST, consumes = {
//			"application/json" }, produces = "application/json")
//	String saveNote(@RequestBody String payload) {
//		boolean sendEvent = stateMachine.sendEvent(Events.ADD_SAVE);
//		System.out.println("Event Occured Successfully value:" + sendEvent);
//		return payload;
//	}

	@RequestMapping(value = "/view-note", method = RequestMethod.GET, produces = "application/json")
	Object getNote(@RequestBody String payload) {
		boolean sendEvent = stateMachine.sendEvent(Events.VIEW);
		System.out.println("Event Occured Successfully value:" + sendEvent);
		return "Note Saved Successfully.";
	}

	@RequestMapping(value = "/cache-test", method = RequestMethod.GET)
	@ResponseBody
	@Cacheable("calculateResult")
	public Student addCacheTest() {
		Student student = new Student("100", "student", Gender.MALE, 12);
		studentRepository.save(student);
		return studentRepository.findById("100");

	}
	
	@RequestMapping(value = "/get-cache", method = RequestMethod.GET)
	@ResponseBody
	@Cacheable("calculateResult")
	public Student getCacheTest() {
		return studentRepository.findById("100");

	}
}
