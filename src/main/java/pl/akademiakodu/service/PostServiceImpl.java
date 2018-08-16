package pl.akademiakodu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.akademiakodu.model.Post;
import pl.akademiakodu.other.UserData;
import pl.akademiakodu.repository.PostRepository;

import java.util.List;

@Service("postService")
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;
    @Autowired
    UserData userData;

    @Override
    public void savePost(Post post) {
        if (userData.getUserRole().equals("ADMIN")) {
            post.setModerated(true);
        }
        else
        {
            post.setModerated(false);
        }

        post.setUserId(userData.getUserId());
        postRepository.save(post);
    }

    @Override
    public List<Post> findByUserId(Integer userId) {
        List<Post> posts = postRepository.findByUserId(userId);
        return posts;
    }

    @Override
    public List<Post> findByModerated() {
        return postRepository.findByModerated(false);
    }

    @Override
    public List<Post> findByActive() {
        return postRepository.findByModerated(true);
    }

    @Override
    public void confirmPost(Integer id) {
        Post post = postRepository.getOne(id);
        post.setModerated(true);
        postRepository.save(post);

    }
}
