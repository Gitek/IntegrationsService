package cx.excite.template.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HelloRequest {
    @JsonProperty("hello_world")
    private String helloWorld;

    public HelloRequest() {
    }

    private HelloRequest(Builder builder) {
        setHelloWorld(builder.helloWorld);
    }

    public String getHelloWorld() {
        return helloWorld;
    }

    public void setHelloWorld(String helloWorld) {
        this.helloWorld = helloWorld;
    }

    public static final class Builder {
        private String helloWorld;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder helloWorld(String val) {
            helloWorld = val;
            return this;
        }

        public HelloRequest build() {
            return new HelloRequest(this);
        }
    }
}
