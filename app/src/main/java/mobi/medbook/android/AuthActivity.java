package mobi.medbook.android;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import mobi.medbook.android.Api.MedBookAPI;
import mobi.medbook.android.Requests.AccessTokenRequest;
import mobi.medbook.android.Requests.MedBookRequest;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class AuthActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_signUp, btn_registration;
    EditText et_mail, et_password;
    ProgressBar pb_progress;
    Retrofit retrofit;
    MedBookAPI medBookAPI;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        btn_signUp = findViewById(R.id.sign_in_button);
        btn_registration = findViewById(R.id.registration_button);
        et_mail = findViewById(R.id.mail_input);
        et_password = findViewById(R.id.promocode_input);
        pb_progress = findViewById(R.id.progress);

        btn_signUp.setOnClickListener(this);
        btn_registration.setOnClickListener(this);

        }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_in_button:
                sign_in();
                return;
            case R.id.registration_button:
                Uri linkRegistration = Uri.parse("https://medbook.mobi/medbook/i/");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(linkRegistration);
                startActivity(intent);
                return;
            default:
                return;
        }
    }

    private void sign_in() {

        String password = et_password.getText().toString().trim();
        String mail = et_mail.getText().toString().trim();

        if (password.equals("") || mail.equals("")) {

            DialogBuilder.buildInfoDialog(this, "Не заполнены логин или пароль", "").show();
            return;
        }

        beforeRequest();

        retrofit = new Retrofit.Builder()
                        .baseUrl("https://mbr.medbook.mobi/api/v1/")
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();

        medBookAPI = retrofit.create(MedBookAPI.class);
        MedBookRequest medBookRequest = new MedBookRequest(mail, password);

        medBookAPI.getToken(medBookRequest)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableSingleObserver<String>() {
                        @Override
                        public void onSuccess(String medBookResponce) {
                            JSONObject dataJson = null;
                            String token = "";
                            try {
                                dataJson = new JSONObject(medBookResponce);
                                token = dataJson.getString("authorization_code");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                afterRequest();
                                DialogBuilder.buildInfoDialog(AuthActivity.this, "Error", "Не получен токен по причине: " + e.toString()).show();
                                return;
                            }
                            if (token.equals("")){
                                afterRequest();
                                DialogBuilder.buildInfoDialog(AuthActivity.this, "Error", "Не получен токен").show();
                                return;
                            }else{
                                login(token);
                            }

                        }

                        @Override
                        public void onError(Throwable e) {
                            afterRequest();
                            DialogBuilder.buildInfoDialog(AuthActivity.this, "Error", e.toString()).show();
                        }
                    });


    }

    private void login(String token) {

        AccessTokenRequest accessTokenRequest = new AccessTokenRequest(token);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://mbr.medbook.mobi/api/v1/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        medBookAPI = retrofit.create(MedBookAPI.class);

        medBookAPI.getAccessToken(accessTokenRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<String>() {
                    @Override
                    public void onSuccess(String s) {
                        JSONObject dataJson = null;
                        String token = "";
                        try {
                            dataJson = new JSONObject(s);
                            token = dataJson.getString("access_token");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            afterRequest();
                            DialogBuilder.buildInfoDialog(AuthActivity.this, "Error", "Не получен токен по причине: " + e.toString()).show();
                            return;
                        }
                        getBaseContext().getSharedPreferences("accessToken", Context.MODE_PRIVATE).edit().putString("access_token", token);
                        afterRequest();
                        DialogBuilder.buildInfoDialog(AuthActivity.this, "Умпешное получение токена", "").show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        afterRequest();
                        DialogBuilder.buildInfoDialog(AuthActivity.this, "Error", e.toString()).show();
                    }
                });



    }

    private void afterRequest() {

        btn_signUp.setVisibility(View.VISIBLE);
        pb_progress.setVisibility(View.INVISIBLE);
        et_password.setEnabled(true);
    }

    private void beforeRequest() {
        btn_signUp.setVisibility(View.INVISIBLE);
        pb_progress.setVisibility(View.VISIBLE);
        et_password.setEnabled(false);
    }
}
