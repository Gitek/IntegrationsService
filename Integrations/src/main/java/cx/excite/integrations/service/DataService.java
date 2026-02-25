package cx.excite.integrations.service;

import cx.excite.integrations.api.dto.HelloRequest;
import cx.excite.integrations.api.dto.HelloResponse;
import org.springframework.stereotype.Service;

@Service
public class DataService {

    public HelloResponse hello(HelloRequest request) {
        return HelloResponse.Builder.builder()
                .goodBye("I received: " + request.getHelloWorld() + ". Good bye!")
                .build();
    }
}
