package cx.excite.template.auth;

import org.json.JSONObject;

public class ValidatedToken {
    private boolean admin;
    private int ekey;
    private JSONObject payload;

    public ValidatedToken() {
    }

    private ValidatedToken(Builder builder) {
        setAdmin(builder.admin);
        setEkey(builder.ekey);
        setPayload(builder.payload);
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public int getEkey() {
        return ekey;
    }

    public void setEkey(int ekey) {
        this.ekey = ekey;
    }

    public JSONObject getPayload() {
        return payload;
    }

    public void setPayload(JSONObject payload) {
        this.payload = payload;
    }


    public static final class Builder {
        private boolean admin;
        private int ekey;
        private JSONObject payload;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder admin(boolean val) {
            admin = val;
            return this;
        }

        public Builder ekey(int val) {
            ekey = val;
            return this;
        }

        public Builder payload(JSONObject val) {
            payload = val;
            return this;
        }

        public ValidatedToken build() {
            return new ValidatedToken(this);
        }
    }
}
