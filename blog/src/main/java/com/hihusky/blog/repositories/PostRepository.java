package com.hihusky.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hihusky.blog.models.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}