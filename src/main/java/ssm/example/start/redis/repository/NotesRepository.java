package ssm.example.start.redis.repository;

import java.util.Map;

import ssm.example.start.redis.model.Notes;
import ssm.example.start.redis.model.Type;

public interface NotesRepository {

	void save(Notes notes);

	Map<String, String> findAll();

	void update(Notes notes);

	Map<String, String> findAll(Type type);

	Long delete(String key, String title);

	Notes findByTitle(String type, String title);

}
