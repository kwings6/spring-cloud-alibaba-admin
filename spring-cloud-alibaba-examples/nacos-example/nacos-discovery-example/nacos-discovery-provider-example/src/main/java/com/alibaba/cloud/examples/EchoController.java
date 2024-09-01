/*
 * Copyright 2013-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.cloud.examples;

import java.util.List;
import java.util.Map;


import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.metrics.aop.interceptor.ReactiveInterceptor;
import jakarta.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


/**
 * Provide interfaces to consumers.
 *
 * @author fangjian0423, MieAh
 */
@RestController
public class EchoController {

	@Resource
	private NacosDiscoveryProperties nacosDiscoveryProperties;

	@GetMapping("/sayHello")
	public String sayHello(){
		System.out.println("beifangwen");
		return "nihao";
	}

	@GetMapping("/")
	public ResponseEntity<String> index() {
		return new ResponseEntity<>("index error", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping("/test")
	public ResponseEntity<String> test() {
		return new ResponseEntity<>("error", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping("/sleep")
	public String sleep() {
		try {
			Thread.sleep(1000L);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "ok";
	}

	@GetMapping("/echo/{string}")
	public String echo(@PathVariable String string) {
		return "hello Nacos Discovery " + string;
	}

	@GetMapping("/divide")
	public String divide(@RequestParam Integer a, @RequestParam Integer b) {
		if (b == 0) {
			return String.valueOf(0);
		}
		else {
			return String.valueOf(a / b);
		}
	}

	@GetMapping("/zone")
	public String zone() {
		Map<String, String> metadata = nacosDiscoveryProperties.getMetadata();
		return "provider zone " + metadata.get("zone");
	}

}
