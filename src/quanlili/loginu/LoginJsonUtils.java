package quanlili.loginu;

import android.text.TextUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import quanlili.beans.LoginPeople;
import quanlili.utils.JsonUtils;
import quanlili.utils.LogUtils;

/**
 * Created by Administrator on 2016/7/23 0023.
 */
public class LoginJsonUtils {

    private final static String TAG = "LoginJsonUtils";

    public static LoginPeople readJsonStuffBeans(String res, String value) {
        LoginPeople beans = new LoginPeople();
        try {
            JsonParser parser = new JsonParser();
            JsonObject jsonObj = parser.parse(res).getAsJsonObject();// {"status":"OK","stuff":{"stu_id":"1","stu_num":"10001","stu_name":"张鑫"}}

            JsonElement jsonElement = jsonObj.get(value);// OK
            if (!TextUtils.isEmpty(jsonElement.getAsString())
                    && jsonElement.getAsString().equals("OK")) {
                JsonElement beanElement = jsonObj.get("stuff");// {"stu_id":"1","stu_num":"10001","stu_name":"张鑫"}
                beans = JsonUtils.deserialize(beanElement.getAsJsonObject(),
                        LoginPeople.class);
            } else {

                return null;
            }

        } catch (Exception e) {
            LogUtils.e(TAG, "readJsonNewsBeans error", e);
        }
        return beans;
    }
}
