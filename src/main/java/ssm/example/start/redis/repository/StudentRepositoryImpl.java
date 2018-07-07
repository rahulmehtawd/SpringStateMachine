package ssm.example.start.redis.repository;

import java.util.Map;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import ssm.example.start.redis.model.Student;

@Repository("studentRepo")
//@Repository
public class StudentRepositoryImpl implements StudentRepository {

	private RedisTemplate<String, Student> redisTemplate;
	private HashOperations<String, String, Student> hashOperations;

	public StudentRepositoryImpl(RedisTemplate<String, Student> redisTemplate) {
		super();
		this.redisTemplate = redisTemplate;
		hashOperations = redisTemplate.opsForHash();
	}

	@Override
	public void save(Student student) {
		hashOperations.put("stud", student.getId(), student);
	}

	@Override
	public Map<String, Student> findAll() {
		return hashOperations.entries("stud");
	}

	@Override
	public Student findById(String id) {
		return (Student) hashOperations.get("stud", id);
	}

	@Override
	public void update(Student student) { 
		save(student);
	}

	@Override
	public void delete(String id) {
		hashOperations.delete("stud", id);
	}

}
