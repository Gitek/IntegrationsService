package cx.excite.template.service;

import cx.excite.template.api.dto.HelloRequest;
import cx.excite.template.api.dto.HelloResponse;
import org.springframework.stereotype.Service;

@Service
public class TemplateService {

    public HelloResponse hello(HelloRequest request) {
        return HelloResponse.Builder.builder()
                .goodBye("I received: " + request.getHelloWorld() + ". Good bye!")
                .build();
    }
}
