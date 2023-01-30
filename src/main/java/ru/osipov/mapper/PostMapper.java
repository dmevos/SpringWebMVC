package ru.osipov.mapper;

import org.springframework.stereotype.Component;
import ru.osipov.model.Post;
import ru.osipov.dto.PostDTO;

@Component
public class PostMapper {
    public PostDTO mapToPostDTO(Post post) {
        return new PostDTO(post.getId(), post.getContent());
    }
}