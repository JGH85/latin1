package org.launchcode.blogz.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.launchcode.blogz.models.Post;
import org.launchcode.blogz.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PostController extends AbstractController {

	@RequestMapping(value = "/blog/newpost", method = RequestMethod.GET)
	public String newPostForm() {
		return "newpost";
	}
	
	@RequestMapping(value = "/blog/newpost", method = RequestMethod.POST)
	public String newPost(HttpServletRequest request, Model model) {
		
		// TODO - implement newPost
		
		//get request parameters
		String title = request.getParameter("title");
		model.addAttribute("title", title);
		String body = request.getParameter("body");
		model.addAttribute("body", body);
		HttpSession thisSession = request.getSession();
		User author = getUserFromSession(thisSession);

		//validate request parameters
		//if not, send back to form with error message
		boolean postError = false;
		if (title.equals("") || title.equals(null)){postError=true;};
		if (body.equals("") || body.equals(null)){postError=true;};
		if (postError){
			String error = "Please fill in all fields.";
			model.addAttribute("error", error);
			return "newpost";
		}
		
		//if valid, create new post. Use post model to create, post dao to save. 
		Post post = new Post(title, body, author);
		postDao.save(post);
		

		//		return "redirect:index"; 
		// TODO - this redirect should go to the new post's page
		//this will come from single post method
		
		int uid = post.getUid();
		String username = author.getUsername();
		return singlePost(username, uid, model);
		
	}
	
	//handles requests like /blog/chris/5
	@RequestMapping(value = "/blog/{username}/{uid}", method = RequestMethod.GET)
	public String singlePost(@PathVariable String username, @PathVariable int uid, Model model) {
		
		// TODO - implement singlePost
		
		//get the given post
		Post singlePost = postDao.findByUid(uid);
		//pass the post into the template
		model.addAttribute("post", singlePost);
				
		return "post";
	}
	
	@RequestMapping(value = "/blog/{username}", method = RequestMethod.GET)
	public String userPosts(@PathVariable String username, Model model) {
		
		// TODO - implement userPosts
		
		//get all the posts for the user
		User author = userDao.findByUsername(username);
		model.addAttribute("author", author);
		
		List<Post> posts = postDao.findByAuthor(author);
		
		//pass the posts into the template
			//model.addAttribute("name", listOfPosts
		model.addAttribute("posts", posts);
		
		return "bloguser";
	}
	
}
