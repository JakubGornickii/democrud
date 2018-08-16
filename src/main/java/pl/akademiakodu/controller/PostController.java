package pl.akademiakodu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.akademiakodu.model.Post;
import pl.akademiakodu.other.UserData;
import pl.akademiakodu.service.PostService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PostController {

    @Autowired
    UserData userData;
    @Autowired
    PostService postService;

    @RequestMapping(value = "/user/postMenu",method = RequestMethod.GET)
    public ModelAndView postMenu()
    {
        ModelAndView modelAndView = userData.getUserData();
        modelAndView.setViewName("user/postMenu");
        return modelAndView;
    }


    @RequestMapping(value = "/user/addPost",method = RequestMethod.GET)
    public ModelAndView addPost()
    {
        Post post = new Post();
        ModelAndView modelAndView = userData.getUserData();
        modelAndView.setViewName("user/addPost");
        modelAndView.addObject("post",post);
        return modelAndView;
    }
    @RequestMapping(value = "/user/addPost", method = RequestMethod.POST)
    public ModelAndView createNewEmployer(@Valid Post post, BindingResult bindingResult) {
        ModelAndView modelAndView = userData.getUserData();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("user/addPost");
        } else {
            postService.savePost(post);
            modelAndView.setViewName("user/PostSuccess");
        }
        return modelAndView;

    }
    @RequestMapping(value = "/user/myPostMenu",method = RequestMethod.GET)
    public ModelAndView viewMyPost()
    {
        ModelAndView modelAndView = userData.getUserData();
        List<Post> posts = postService.findByUserId(userData.getUserId());
        modelAndView.addObject("posts",posts);
        modelAndView.setViewName("user/myPostMenu");
        return modelAndView;


    }
    @RequestMapping(value = "/admin/moderatePost",method = RequestMethod.GET)
    public ModelAndView moderatePosts()
    {
        ModelAndView modelAndView = userData.getUserData();
     List<Post> posts = postService.findByModerated();
     modelAndView.addObject("posts",posts);
     modelAndView.setViewName("admin/moderatePost");
       return modelAndView;
    }
    @RequestMapping(value = "/admin/moderatePost",method = RequestMethod.PUT)
    public ModelAndView confirmPosts(@RequestParam("id") Integer id)
    {
        ModelAndView modelAndView = userData.getUserData();
     postService.confirmPost(id);
     modelAndView.setViewName("redirect:/admin/moderatePost");
     return modelAndView;
    }
}
