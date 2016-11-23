package org.launchcode.blogz.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.launchcode.blogz.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AuthenticationController extends AbstractController {
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signupForm() {
		return "signup";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(HttpServletRequest request, Model model) {
		
	// TODO - implement signup
		
		//get parameters from request
		String username = request.getParameter("username");
		model.addAttribute("username", username);
		String password = request.getParameter("password");
		String verify = request.getParameter("verify");
		
		
		//validate parameters(username, password, verify)
		boolean validUser = User.isValidUsername(username);
		if (!validUser){
			String username_error = "Invalid Username";
			model.addAttribute("username_error", username_error);
		}
		boolean validPassword = User.isValidPassword(password);
		if (!validPassword){
			String password_error = "Invalid Password";
			model.addAttribute("password_error", password_error);
		}
		boolean matchingPassword = password.equals(verify);
		if (!matchingPassword){
			String verify_error = "Password doesn't match";
			model.addAttribute("verify_error", verify_error);
		}
		
		//if they validate, create new user, store in session
		//use setUserInSession from abstractController
		//Session thisSession = request.getSession();
		//UserDao.save(user);		
		
		if (validUser && validPassword && matchingPassword){
			User user = new User(username, password);
			userDao.save(user);
			
			HttpSession session = request.getSession();
			setUserInSession(session, user);
			
			return "redirect:blog/newpost";
		}
				
		return "signup";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginForm() {
		return "login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, Model model) {
		
		// TODO - implement login
		
		//get parameters from request
		String username = request.getParameter("username");
		model.addAttribute("username", username);
		String password = request.getParameter("password");
		User user;
		boolean existingUser=true;
		boolean validPassword;
		
		//if user exists, get user by username from database
		try{
			//get user by username
			user = userDao.findByUsername(username);
			//check on how to catch error no user exists
			
			//check that password is correct, otherwise redirect to form again
			validPassword = user.isMatchingPassword(password);
			if(!validPassword){
				String error = "Invalid password";
				model.addAttribute("error", error);
				return "login";
			}
		} catch (NullPointerException e){
			existingUser = false;
			String error = "Non-existent user";
			model.addAttribute("error", error);
			return "login";
		}
		
		//if so, login
			//by setting user in session
			//possibly create helper method for setting user in session for both signup and login 
		if (validPassword){
			HttpSession session = request.getSession();
			setUserInSession(session, user);
			
			return "redirect:blog/newpost";
		}
		
		
		//validate parameters(username, password, verify)
//		boolean validUser = User.isValidUsername(username);
//		if (!validUser){
//			String username_error = "Invalid Username";
//			model.addAttribute("username_error", username_error);
//		}
//		boolean validPassword = User.isValidPassword(password);
//		if (!validPassword){
//			String password_error = "Invalid Password";
//			model.addAttribute("password_error", password_error);
//		}
//		boolean matchingPassword = password.equals(verify);
//		if (!matchingPassword){
//			String verify_error = "Password doesn't match";
//			model.addAttribute("verify_error", verify_error);
//		}
//		
//		//if they validate, create new user, store in session
//		//use setUserInSession from abstractController
//		//Session thisSession = request.getSession();
//		//UserDao.save(user);		
//		
//		if (validUser && validPassword && matchingPassword){
//			User user = new User(username, password);
//			userDao.save(user);
//			
//			HttpSession session = request.getSession();
//			setUserInSession(session, user);
//			
//			return "redirect:blog/newpost";
//		}
//				
//		return "signup";
//	}
//		
//		
//		
//		
		return "redirect:blog/newpost";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request){
        request.getSession().invalidate();
		return "redirect:/";
	}
}
