package com.nuzharukiya.spapp.helpers;

import android.text.TextUtils;
import android.util.Patterns;

import com.nuzharukiya.spapp.R;

import java.util.regex.Pattern;

/**
 * Created by Nuzha Rukiya on 18/06/07.
 */
public class RegisterHelper {

    public int getErrorCode(String sPhoneNo, String sEmailId, String sPassword) {
        if (sPhoneNo.length() != 10) {
            return R.string.info_phone_no_length;
        } else if (!isValidPhoneNo(sPhoneNo)) {
            return R.string.info_invalid_phone_no;
        } else if (!isValidEmail(sEmailId)) {
            return R.string.info_invalid_email;
        }
//        else if (sPassword.length() < 8) {
//            return R.string.info_password_length;
//        } else if (!isValidPassword(sPassword)) {
//            return R.string.info_password_char;
//        }
        return -1;
    }

    public boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public boolean isValidPhoneNo(String phoneNo) {
        return !TextUtils.isEmpty(phoneNo) && Patterns.PHONE.matcher(phoneNo).matches();
    }

    private boolean isValidPassword(final String password) {
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=\\S+$).{4,}$";

        return (Pattern.compile(PASSWORD_PATTERN)).matcher(password).matches();
    }
}
