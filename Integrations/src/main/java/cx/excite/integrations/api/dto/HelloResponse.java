package cx.excite.integrations.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HelloResponse {
    @JsonProperty("good_bye")
    private String goodBye;

    public HelloResponse() {
    }

    private HelloResponse(Builder builder) {
        setGoodBye(builder.goodBye);
    }

    public String getGoodBye() {
        return goodBye;
    }

    public void setGoodBye(String goodBye) {
        this.goodBye = goodBye;
    }

    public static final class Builder {
        private String goodBye;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder goodBye(String val) {
            goodBye = val;
            return this;
        }

        public HelloResponse build() {
            return new HelloResponse(this);
        }
    }
}
