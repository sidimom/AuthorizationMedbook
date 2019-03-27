package mobi.medbook.android.Api;

import io.reactivex.Single;
import mobi.medbook.android.Requests.AccessTokenRequest;
import mobi.medbook.android.Requests.MedBookRequest;
import mobi.medbook.android.Responces.AccessTokenResponce;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MedBookAPI {

    @POST("authorize")
    Single<String> getToken(@Body MedBookRequest medBookRequest);

    @POST("access-token")
    Single<String> getAccessToken(@Body AccessTokenRequest accessTokenRequest);

}
