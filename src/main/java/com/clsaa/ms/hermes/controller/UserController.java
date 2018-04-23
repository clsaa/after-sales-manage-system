package com.clsaa.ms.hermes.controller;

import com.clsaa.ms.hermes.entity.vo.UserV1;
import com.clsaa.ms.hermes.result.BizAssert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


/**
 * @author Administrator
 */
@RestController
public class UserController {

	@GetMapping("/user/{id}")
	public Mono<UserV1> getUser(@PathVariable Long id) {
		BizAssert.justDenied(1000,"kkkkkkkkkkk");
		int x = 1/0;
		UserV1 userV1 = new UserV1();
		userV1.setId(id.toString());
		return Mono.just(userV1);
	}

}