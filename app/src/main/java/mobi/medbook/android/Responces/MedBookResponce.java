package mobi.medbook.android.Responces;

import com.google.gson.annotations.SerializedName;

public class MedBookResponce {

    @SerializedName("user")
    private User user;
    @SerializedName("authorization_code")
    private String authorization_code;
    @SerializedName("expires_at")
    private long expires_at;

    public static class User {

        private int id;
        private int user_type_id;
        private String username;
        private String first_name;
        private String middle_name;
        private String last_name;
        private String email;
        private String phone;
        private String auth_key;
        private String password_hash;
        private String password_reset_token;
        private String email_confirm_token;
        private String language;
        private int terms_agreement;
        private int points_agreement;
        private int payout_agreement;
        private int points;
        private int show_payout;
        private int morion_id;
        private int specialization_id;
        private int medical_institution_id;
        private int status;
        private int role;
        private long created_at;
        private long updated_at;
        private long deleted_at;
    };


    /*{
        "user": {
        "id": 1,
                "user_type_id": 1,
                "username": "crm_user",
                "first_name": null,
                "middle_name": null,
                "last_name": null,
                "email": "kvdr.dev1@gmail.com",
                "phone": null,
                "auth_key": "VF-SRPIBW4Mv7rbAqiXsMxiuK3ba0XSz",
                "password_hash": "$2y$13$9Qn8S6x65of9uOVuTB0Qi.zyifRcDFedp4PtwvCcWFSxpQXojPZXC",
                "password_reset_token": "90yJQEVXlw06px2gu5M29zJUayAD4INX_1553080278",
                "email_confirm_token": null,
                "language": null,
                "terms_agreement": 0,
                "points_agreement": 0,
                "payout_agreement": 0,
                "points": null,
                "show_payout": 0,
                "morion_id": null,
                "specialization_id": null,
                "medical_institution_id": null,
                "status": 10,
                "role": 30,
                "created_at": 1553080278,
                "updated_at": 1553080278,
                "deleted_at": null
    },
        "authorization_code": "efc87b44e76075f9fc58ba102f7fc3d5",
            "expires_at": 1553349602
    }*/



}
