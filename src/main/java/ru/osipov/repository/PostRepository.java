package ru.osipov.repository;

import org.springframework.stereotype.Repository;
import ru.osipov.exception.NotFoundException;
import ru.osipov.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

// Stub
@Repository
public class PostRepository {
    private static final AtomicLong counter = new AtomicLong(0);
    private static final ConcurrentHashMap<Long, Post> repo = new ConcurrentHashMap<>();

    public List<Post> all() {
        var tmp = new ArrayList<>(repo.values());
        return tmp.stream().filter(post -> !post.isRemoved()).collect(Collectors.toList());
    }

    public Optional<Post> getById(long id) {
        if (!repo.get(id).isRemoved()) return Optional.ofNullable(repo.get(id));
        else {
            throw new NotFoundException("Жопа");
        }
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(counter.incrementAndGet());
            repo.put(counter.get(), post);
            return post;
        } else {
            if (repo.containsKey(post.getId())) {
                if (!repo.get(post.getId()).isRemoved())
                    repo.put(post.getId(), post);
                else throw new NotFoundException("Объект уже был удален");
            } else throw new NotFoundException("Жопа");
        }
        return post;
    }

    public void removeById(long id) {
        if (repo.containsKey(id)) {
            repo.get(id).removePost();
        }
    }
}