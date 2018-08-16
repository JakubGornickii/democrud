package pl.akademiakodu.service;

import pl.akademiakodu.model.Post;

import java.util.List;

public interface PostService {
    public void savePost(Post post);
public List<Post> findByUserId(Integer userId);
public List<Post> findByModerated();
public List<Post> findByActive();
public void confirmPost(Integer id);
}
