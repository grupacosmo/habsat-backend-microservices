package pl.edu.pk.cosmo.habsatbackend.postsservice.controllers;

import io.sentry.spring.tracing.SentrySpan;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pk.cosmo.habsatbackend.postsservice.converters.PostResourceConverter;
import pl.edu.pk.cosmo.habsatbackend.postsservice.entities.PostEntity;
import pl.edu.pk.cosmo.habsatbackend.postsservice.exceptions.MediaNotFoundException;
import pl.edu.pk.cosmo.habsatbackend.postsservice.exceptions.PostNotFoundException;
import pl.edu.pk.cosmo.habsatbackend.postsservice.exceptions.PostSlugIsNotUnique;
import pl.edu.pk.cosmo.habsatbackend.postsservice.request.ModifyPostRequest;
import pl.edu.pk.cosmo.habsatbackend.postsservice.services.PostService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@SentrySpan
public class PostController {
    private final PostService postService;
    private final PostResourceConverter postResourceConverter;

    @GetMapping
    public ResponseEntity<?> findAllPosts() {
        List<PostEntity> posts = postService.findAllPosts();
        return new ResponseEntity<>(posts.stream().map(postResourceConverter::of).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findPostById(@PathVariable Long id) throws PostNotFoundException {
        PostEntity post = postService.findPostById(id);
        return new ResponseEntity<>(postResourceConverter.of(post), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody ModifyPostRequest modifyPostRequest) throws MediaNotFoundException, PostSlugIsNotUnique {
        PostEntity post = postService.createPost(modifyPostRequest);
        return new ResponseEntity<>(postResourceConverter.of(post), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @Valid @RequestBody ModifyPostRequest modifyPostRequest) throws PostSlugIsNotUnique, PostNotFoundException, MediaNotFoundException {
        PostEntity post = postService.updatePost(id, modifyPostRequest);
        return new ResponseEntity<>(postResourceConverter.of(post), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
