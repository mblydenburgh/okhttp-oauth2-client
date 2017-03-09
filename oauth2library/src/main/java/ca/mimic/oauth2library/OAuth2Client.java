package ca.mimic.oauth2library;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;

public class OAuth2Client {
    private final String clientId;
    private final String clientSecret;
    private final String site;
    private final OkHttpClient okHttpClient;

    private String scope;
    private String grantType;

    private String username;
    private String password;

    private Map<String, String> parameters;

    private OAuth2Client(Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.clientId = builder.clientId;
        this.clientSecret = builder.clientSecret;
        this.site = builder.site;
        this.scope = builder.scope;
        this.grantType = builder.grantType;
        this.okHttpClient= builder.okHttpClient;
        this.parameters = builder.parameters;
    }

    String getScope() {
        return scope;
    }

    String getGrantType() {
        return grantType;
    }

    String getClientId() {
        return clientId;
    }

    String getClientSecret() {
        return clientSecret;
    }

    String getSite() {
        return site;
    }

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }

    public Token refreshAccessToken(String refreshToken) throws IOException, OAuthException {
        if (this.grantType == null)
            this.grantType = Constants.GRANT_TYPE_REFRESH;
        return Access.refreshAccessToken(refreshToken, this);

    }

    public Token getAccessToken() throws IOException, OAuthException {
        if (this.grantType == null)
            this.grantType = Constants.GRANT_TYPE_PASSWORD;
        return Access.getToken(this);
    }

    OkHttpClient getOkHttpClient() {
        if (this.okHttpClient == null) {
            return new OkHttpClient();
        } else {
            return this.okHttpClient;
        }
    }

    Map<String, String> getParameters() {
        if (parameters == null) {
            return new HashMap<>();
        } else {
            return parameters;
        }
    }

    Map<String, String> getFieldsAsMap() {
        Map<String, String> oAuthParams = new HashMap<>();
        oAuthParams.put(Constants.POST_CLIENT_ID, getClientId());
        oAuthParams.put(Constants.POST_CLIENT_SECRET, getClientSecret());
        oAuthParams.put(Constants.POST_GRANT_TYPE, getGrantType());
        oAuthParams.put(Constants.POST_SCOPE, getScope());
        oAuthParams.put(Constants.POST_USERNAME, getUsername());
        oAuthParams.put(Constants.POST_PASSWORD, getPassword());
        return oAuthParams;
    }

    public static class Builder {
        private final String clientId;
        private final String clientSecret;
        private final String site;

        private String scope;
        private String grantType;

        private String username;
        private String password;

        private OkHttpClient okHttpClient;

        private Map<String, String> parameters;

        public Builder(String username, String password, String clientId, String clientSecret,
                       String site) {
            this.username = username;
            this.password = password;
            this.clientId = clientId;
            this.clientSecret = clientSecret;
            this.site = site;
            this.okHttpClient = null;
        }

        public Builder(String clientId, String clientSecret, String site) {
            this.username = null;
            this.password = null;
            this.clientId = clientId;
            this.clientSecret = clientSecret;
            this.site = site;
            this.okHttpClient = null;
        }

        public Builder grantType(String grantType) {
            this.grantType = grantType;
            return this;
        }

        public Builder scope(String scope) {
            this.scope = scope;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder okHttpClient(OkHttpClient client) {
            this.okHttpClient = client;
            return this;
        }

        public Builder parameters(Map<String, String> parameters) {
            this.parameters = parameters;
            return this;
        }

        public OAuth2Client build() {
            return new OAuth2Client(this);
        }

    }

}