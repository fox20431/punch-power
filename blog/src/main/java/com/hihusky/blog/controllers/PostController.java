package com.hihusky.blog.controllers;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hihusky.blog.models.Post;
import com.hihusky.blog.repositories.PostRepository;
import com.hihusky.blog.services.PostService;

import lombok.AllArgsConstructor;

@RestController("/posts")
@AllArgsConstructor
@Slf4j
public class PostController {

    private final PostRepository postRepository;
    private final PostService postService;

    @GetMapping("/posts/{id}")
    ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            log.warn(optionalPost.get().getCreatedAt().toString());
            return ResponseEntity.ok(optionalPost.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/posts")
    // @GetMapping(value = "/posts", params = {"page", "size", "overview"})
    public Page<Post> getPagedPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "false") boolean overview
            ) {
        if (overview) {
            return postService.getPagedPostsOverview(PageRequest.of(page, size));
        } else {
            return postRepository.findAll(PageRequest.of(page, size));
        }
    }

    @PostMapping("/posts")
    ResponseEntity<Post> createPost(@RequestBody Post post) {
        try {
            post.setCreatedAt(new Date());
            Post savedPost = postRepository.save(post);
            return ResponseEntity.ok(savedPost);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/posts/{id}")
    ResponseEntity<Post> updatePost(@PathVariable("id") Long id, @RequestBody Map<String, Object> updates) {
        Post post = postRepository.findById(id).orElse(null);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        if (updates.containsKey("title")) {
            post.setTitle((String) updates.get("title"));
        }
        if (updates.containsKey("body")) {
            post.setBody((String) updates.get("body"));
        }
        Post updatedPost = postRepository.save(post);
        return ResponseEntity.ok(updatedPost);
    }

    @PutMapping("/posts/{id}")
    ResponseEntity<Post> updatePost(@PathVariable("id") Long id, @RequestBody Post post) {
        if (post.getTitle() == null || post.getTitle().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Post existingPost = postRepository.findById(id).orElse(null);
        if (existingPost == null) {
            return ResponseEntity.notFound().build();
        }
        existingPost.setTitle(post.getTitle());
        existingPost.setBody(post.getBody());

        Post updatedPost = postRepository.save(existingPost);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        // Retrieve the user to delete
        Post post = postRepository.findById(id).orElse(null);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }

        // Delete the user
        postRepository.delete(post);

        // Return a response to the client
        return ResponseEntity.noContent().build();
    }

}
