package com.demo.gradle.api;

import com.demo.gradle.model.Greeting;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;

@Controller
@RequestMapping("${openapi.gradleDemo.base-path:}")
public class GreetingApiController implements GreetApi {

  private final NativeWebRequest request;

  @org.springframework.beans.factory.annotation.Autowired
  public GreetingApiController(NativeWebRequest request) {
    this.request = request;
  }

  @Override
  public ResponseEntity<Greeting> greet() {
    getRequest()
        .ifPresent(
            request -> {
              String greeting = "{ \"greeting\" : \"Hello!\" }";
              ApiUtil.setExampleResponse(request, "text/plain", greeting);
            });
    return new ResponseEntity<>(HttpStatus.ACCEPTED);
  }

  @Override
  public Optional<NativeWebRequest> getRequest() {
    return Optional.ofNullable(request);
  }
}
