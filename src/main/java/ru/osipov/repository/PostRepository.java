package ru.osipov.repository;

import org.springframework.stereotype.Repository;
import ru.osipov.exception.NotFoundException;
import ru.osipov.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// Stub
@Repository
public class PostRepository {
    private static final AtomicLong counter = new AtomicLong(0);
    private static final ConcurrentHashMap<Long, Post> repo = new ConcurrentHashMap<>();

    public List<Post> all() {
        return new ArrayList<>(repo.values());
    }

    public Optional<Post> getById(long id) {
        if (repo.containsKey(id)) return Optional.ofNullable(repo.get(id));
        else throw new NotFoundException("Показывать нечего. Поста с id=" + id + " нет!");
    }

    public Post save(Post post) {
        if (post.getId() == 0) { //создаем новую запись
            post.setId(counter.incrementAndGet());
            repo.put(counter.get(), post);
            return post;
        } else { //должны обновить существующую запись
            if (repo.containsKey(post.getId())) {
                repo.put(post.getId(), post);
            } else {  //если же существующей записи нет
                throw new NotFoundException("Пост не найден");
            }
        }
        return post;
    }

    public void removeById(long id) {
        if (repo.containsKey(id)) repo.remove(id);
        else throw new NotFoundException("Удалять нечего. Поста с id=" + id + " нет!");
    }
}