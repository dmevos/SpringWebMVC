package ru.osipov.controller;

import org.springframework.web.bind.annotation.*;
import ru.osipov.mapper.PostMapper;
import ru.osipov.model.Post;
import ru.osipov.dto.PostDTO;
import ru.osipov.service.PostService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService service;
    private final PostMapper mapper;

    public PostController(PostService service, PostMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<PostDTO> all() {
        return service.all().stream().map(mapper::mapToPostDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PostDTO getById(@PathVariable long id) {
        return mapper.mapToPostDTO(service.getById(id));
    }

    @PostMapping
    public PostDTO save(@RequestBody Post post) {
        return mapper.mapToPostDTO(service.save(post));
    }

    @DeleteMapping("/{id}")
    public void removeById(@PathVariable long id) {
        service.removeById(id);
    }
}