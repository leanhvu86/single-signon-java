package com.tsolution.sso._4controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fake")
public class FakeController {
	@GetMapping("/times")
	public ResponseEntity<Object> getTimes() {
		return new ResponseEntity<>(
				"[ 	{ 		\"name\": \"Candy\", 		\"series\": [ 			{ 				\"value\": 69, 				\"name\": \"2016-09-15T19:25:07.773Z\" 			}, 			{ 				\"value\": 19, 				\"name\": \"2016-09-17T17:16:53.279Z\" 			}, 			{ 				\"value\": 85, 				\"name\": \"2016-09-15T10:34:32.344Z\" 			}, 			{ 				\"value\": 89, 				\"name\": \"2016-09-19T14:33:45.710Z\" 			}, 			{ 				\"value\": 33, 				\"name\": \"2016-09-12T18:48:58.925Z\" 			} 		] 	}, 	{ 		\"name\": \"Ice Cream\", 		\"series\": [ 			{ 				\"value\": 52, 				\"name\": \"2016-09-15T19:25:07.773Z\" 			}, 			{ 				\"value\": 49, 				\"name\": \"2016-09-17T17:16:53.279Z\" 			}, 			{ 				\"value\": 41, 				\"name\": \"2016-09-15T10:34:32.344Z\" 			}, 			{ 				\"value\": 38, 				\"name\": \"2016-09-19T14:33:45.710Z\" 			}, 			{ 				\"value\": 72, 				\"name\": \"2016-09-12T18:48:58.925Z\" 			} 		] 	}, 	{ 		\"name\": \"Pastry\", 		\"series\": [ 			{ 				\"value\": 40, 				\"name\": \"2016-09-15T19:25:07.773Z\" 			}, 			{ 				\"value\": 45, 				\"name\": \"2016-09-17T17:16:53.279Z\" 			}, 			{ 				\"value\": 51, 				\"name\": \"2016-09-15T10:34:32.344Z\" 			}, 			{ 				\"value\": 68, 				\"name\": \"2016-09-19T14:33:45.710Z\" 			}, 			{ 				\"value\": 54, 				\"name\": \"2016-09-12T18:48:58.925Z\" 			} 		] 	} ]",
				HttpStatus.OK);
	}

	@GetMapping("/multi")
	public ResponseEntity<Object> getMulti() {
		return new ResponseEntity<>(
				"[ 	{ 		\"name\": \"Candy\", 		\"series\": [ 			{ 				\"name\": \"2016\", 				\"value\": 7300000 			}, 			{ 				\"name\": \"2017\", 				\"value\": 8940000 			} 		] 	}, 	{ 		\"name\": \"Ice Cream\", 		\"series\": [ 			{ 				\"name\": \"2016\", 				\"value\": 7870000 			}, 			{ 				\"name\": \"2017\", 				\"value\": 8270000 			} 		] 	}, 	{ 		\"name\": \"Pastry\", 		\"series\": [ 			{ 				\"name\": \"2016\", 				\"value\": 5000002 			}, 			{ 				\"name\": \"2017\", 				\"value\": 5800000 			} 		] 	} ]",
				HttpStatus.OK);
	}
}