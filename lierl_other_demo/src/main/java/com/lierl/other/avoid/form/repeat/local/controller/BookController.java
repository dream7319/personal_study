package com.lierl.other.avoid.form.repeat.local.controller;

import com.lierl.other.avoid.form.repeat.local.annotation.LocalLock;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lierlei@xingyoucai.com
 * @create 2018-06-20 10:34
 **/
@RestController
@RequestMapping("/books")
public class BookController {

	@LocalLock(key = "book:arg[0]")
	@GetMapping("/query")
	public String query(@RequestParam String token){
		return "success-"+token;
	}
}
