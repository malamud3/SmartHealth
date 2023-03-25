package demo.Controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import demo.Model.Message;

@RestController
public class HelloController {
	@RequestMapping(
		path = {"/hello"},
		method = {RequestMethod.GET},
		produces = {MediaType.APPLICATION_JSON_VALUE})
	public Message hello () {
		return new Message("lions");
	}
}
