package com.hihusky.blog.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.hihusky.blog.models.Post;

public interface PostService {
    Page<Post> getPagedPostsOverview(PageRequest pageRequest);
}
