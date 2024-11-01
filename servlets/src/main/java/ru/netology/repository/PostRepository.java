package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
// Stub
public class PostRepository {

    private AtomicLong globalId = new AtomicLong(0);
    private ConcurrentHashMap<Long, Post> hashMap = new ConcurrentHashMap<>();

    public List<Post> all() {
        return new ArrayList<>(hashMap.values());
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(hashMap.get(id));
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            var newId = globalId.getAndIncrement();
            post.setId(newId);
            hashMap.put(newId, post);
        }
        if (hashMap.containsKey(post.getId())) {
            hashMap.put(post.getId(), post);
        } else throw new NotFoundException("Id " + post.getId() + " was not found");
        return post;
    }

    public void removeById(long id) {
      if (hashMap.remove(id) == null)
        throw new NotFoundException("Id " + id + " was not found");;
    }
}
