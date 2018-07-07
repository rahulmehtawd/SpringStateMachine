package ssm.example.start.redis.repository;

import java.util.Map;

import ssm.example.start.redis.model.Student;


public interface StudentRepository {
	void save(Student student);

	Map<String, Student> findAll();

	Student findById(String id);

	void update(Student student);

	void delete(String id);
}