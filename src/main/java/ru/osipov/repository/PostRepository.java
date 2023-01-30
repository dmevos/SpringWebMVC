package ru.osipov.repository;

import org.springframework.stereotype.Repository;
import ru.osipov.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class PostRepository {
    private static final AtomicLong counter = new AtomicLong(0);
    private static final ConcurrentHashMap<Long, Post> repo = new ConcurrentHashMap<>();

    public List<Post> all() {
        var tmp = new ArrayList<>(repo.values());
        return tmp.stream().filter(post -> !post.isRemoved()).collect(Collectors.toList());
    }

    public Optional<Post> getById(long id) {
        if (repo.get(id) == null) return Optional.empty();
        else return Optional.ofNullable(repo.get(id).isRemoved() ? null : repo.get(id));
    }

    public Optional<Post> save(Post post) {
        if (post.getId() == 0) { //создаем новую запись
            post.setId(counter.incrementAndGet());
            repo.put(counter.get(), post);
            return Optional.of(post);
        } else  //должны обновить существующую или неудаленную запись, а если не существует или удалена - вернуть empty
            if (repo.containsKey(post.getId()) && !repo.get(post.getId()).isRemoved()) {
                repo.put(post.getId(), post);
                return Optional.of(post);
            } else
                return Optional.empty();
    }

    public Optional<Post> removeById(long id) {
        if (!repo.containsKey(id) || repo.get(id).isRemoved()) return Optional.empty();
        repo.get(id).removePost();
        return Optional.of(repo.get(id));
    }
}