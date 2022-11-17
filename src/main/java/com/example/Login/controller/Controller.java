package com.example.Login.controller;

import java.util.List;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.Login.entities.User;
import com.example.Login.service.userService;

@RequestMapping("/login")
@RestController
@CrossOrigin(origins = "http://localhost:4200/")
public class Controller {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private userService uService;

	
	
	
	@GetMapping("/link")
	public String link() {
		String response = (String)restTemplate.getForObject("http://localhost:9004/camera/link", String.class);
		return "Camera link => "+response;
	}
	
	@GetMapping("/test")
	public String getDetail() {
		 String response = restTemplate.getForObject("http://localhost:9004/camera/link", String.class);
		//reponse.stream().filter(e->e.getId()==id).findFirst().get();
		System.out.println(response);
		return "login working"; //this.uService.getDetail(username, password);
	}
	
	
	
	@GetMapping("/username/{username}")
	public User getusername(@PathVariable("username") String username) {
            
		return this.uService.username(username);
	}
	
	@GetMapping("/password/{password}")
	public User getpassword(@PathVariable("password") String password) {

		//System.out.println(this.uService.password(password));
		return this.uService.password(password);
	}
	
	@PostMapping("/add")
	public User getpassword(@RequestBody User user) {

		this.uService.add(user);
		return user;
	}
	
//	@PostMapping("/login")
//	public ResponseEntity<?> login(@RequestBody User user){
//		User user1 = uService.username(user.getUsername());
//		
//		System.out.println(user1);
//		
//		if (user1==null)
//			return (ResponseEntity<?>) ResponseEntity.EMPTY;
//		
//		else if (user.getPassword().equals(user1.getPassword()))
//			return ResponseEntity.ok(user1);
//		else 
//		return (ResponseEntity<?>) ResponseEntity.EMPTY;
//	}
	
//	public RedirectView get() {
//		String url = "https://www.google.com/search?hello";//http://localhost:3000
//		RedirectView redirectView = new RedirectView();
//		redirectView.setUrl(url);
//		return redirectView;
//		}
	
	@PostMapping("/signin")
	public ResponseEntity<User> login(@RequestBody User user){
		User user1 = uService.username(user.getUsername());
		
		
		
		System.out.println(user1);
		
		if (user1==null)
 			return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).build();
		else if (user.getPassword().equals(user1.getPassword()))
		{ 
			System.out.println(ResponseEntity.status(HttpStatus.SC_CREATED).body(user1));
			
		 return ResponseEntity.status(HttpStatus.SC_CREATED).body(user1);
		 
		}
		
		return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).build();
	}
	

}
