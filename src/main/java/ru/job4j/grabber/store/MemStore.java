package ru.job4j.grabber.store;

import ru.job4j.grabber.model.Post;

import java.util.ArrayList;
import java.util.List;

public class MemStore implements Store {
    List<Post> postList;
    int i;

    public MemStore() {
        postList = new ArrayList<>();
    }

    @Override
    public void save(Post post) {
        post.setId(i++);
        postList.add(post);
    }

    @Override
    public List<Post> getAll() {
        return postList;
    }

    @Override
    public Post findById(int id) {
        if (id < 0 || id > postList.size()) {
            throw new IllegalArgumentException("id less then 0  or more then size");
        }
        return postList.get(id);
    }
}
