package mobi.medbook.android.Responces;

import com.google.gson.annotations.SerializedName;

public class AccessTokenResponce {

    @SerializedName("access_token")
    private String access_token;
    @SerializedName("expires_at")
    private long expires_at;

}
