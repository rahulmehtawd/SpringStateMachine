package ssm.example.start.redis.repository;

import java.util.Map;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import ssm.example.start.redis.model.Notes;
import ssm.example.start.redis.model.Type;

@Repository("notesRepo")
public class NotesRepositoryImpl implements NotesRepository {
	private RedisTemplate<String, String> redisTemplate;
	private HashOperations<String, String, String> hashOperations;

	public NotesRepositoryImpl(RedisTemplate<String, String> redisTemplate) {
		super();
		this.redisTemplate = redisTemplate;
		hashOperations = redisTemplate.opsForHash();
	}

	@Override
	public void save(Notes notes) {
		hashOperations.put(notes.getType().toString(), notes.getTitle(), notes.getMessage());
	}

	@Override
	public Map<String, String> findAll(Type type) {
		return hashOperations.entries(type.toString());
	}

	@Override
	public Notes findByTitle(String type, String title) {
		String message = hashOperations.get(type, title);
		return new Notes(title, message, Type.valueOf(type));
	}

	@Override
	public void update(Notes notes) {
		hashOperations.put(notes.getType().toString(), notes.getTitle(), notes.getMessage());
	}

	@Override
	public Long delete(String key, String title) {
		String value = hashOperations.get(key, title);
		hashOperations.put(key, key, value);
		Long delete = hashOperations.delete(key, title);
		return delete;
	}

	@Override
	public Map<String, String> findAll() {
		return hashOperations.entries("*");
	}

}
