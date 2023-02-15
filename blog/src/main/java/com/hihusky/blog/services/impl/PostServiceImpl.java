package com.hihusky.blog.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.hihusky.blog.models.Post;
import com.hihusky.blog.repositories.PostRepository;
import com.hihusky.blog.services.PostService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    /**
     * Reduce the http body content
     */
    public Page<Post> getPagedPostsOverview(PageRequest pageRequest) {
        Page<Post> pagedPosts = postRepository.findAll(pageRequest);
        pagedPosts.map((post) -> {
            String title = post.getTitle();
            String body = post.getBody();
            if (title.length() > 18) {
                title = title.subSequence(0, 18).toString() + "……";
                post.setTitle(title);
            }
            if (body.length() > 256) {
                body = body.subSequence(0, 256).toString() + "……";
                post.setBody(body);
            }
            return post;
        });
        return pagedPosts;
    }
    

}
