package ru.osipov.service;

import org.springframework.stereotype.Service;
import ru.osipov.dto.PostDTO;
import ru.osipov.exception.NotFoundException;
import ru.osipov.mapper.PostMapper;
import ru.osipov.model.Post;
import ru.osipov.repository.PostRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository repository;
    private final PostMapper mapper;

    public PostService(PostRepository repository, PostMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<PostDTO> all() {
        return repository.all().stream()
                .map(mapper::mapToPostDTO)
                .collect(Collectors.toList());
    }

    public PostDTO getById(long id) {
        return mapper.mapToPostDTO(repository.getById(id).orElseThrow(() ->
                new NotFoundException("При попытке получить пост с id=" + id + " произошла исключительная ситуация")));
    }

    public PostDTO save(Post post) {
        return mapper.mapToPostDTO(repository.save(post).orElseThrow(() ->
                new NotFoundException("При попытке сохранить пост с id=" + post.getId() + " произошла исключительная ситуация")));
    }

    public void removeById(long id) {
        repository.removeById(id).orElseThrow(() ->
                new NotFoundException("При попытке удалить пост с id=" + id + " произошла исключительная ситуация"));
    }
}