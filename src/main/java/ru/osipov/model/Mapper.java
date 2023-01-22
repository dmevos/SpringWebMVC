package ru.osipov.model;

import org.springframework.stereotype.Component;

@Component
public class Mapper {
    public PostDTO mapToPostDTO(Post post){
        return new PostDTO(post.getId(), post.getContent());
    }
}
