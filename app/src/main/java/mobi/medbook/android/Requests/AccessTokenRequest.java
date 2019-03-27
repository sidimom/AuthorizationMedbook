package mobi.medbook.android.Requests;

public class AccessTokenRequest {
    private final String authorization_code;

    public AccessTokenRequest(String authorization_code) {
        this.authorization_code = authorization_code;
    }
}
