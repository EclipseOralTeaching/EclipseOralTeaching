package quanlili.utils;

/**
 * Created by Administrator on 2016/7/23 0023.
 */

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

/**
 * Description : Jsonת�������� Author : lauren Email : lauren.liuling@gmail.com Blog
 * : http://www.liuling123.com Date : 15/12/17
 */
public class JsonUtils {

    private static Gson mGson = new Gson();

    /**
     * ������׼��Ϊjson�ַ���
     *
     * @param object
     * @param <T>
     * @return
     */
    public static <T> String serialize(T object) {
        return mGson.toJson(object);
    }

    /**
     * ��json�ַ���ת��Ϊ����
     *
     * @param json
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> T deserialize(String json, Class<T> clz)
            throws JsonSyntaxException {
        return mGson.fromJson(json, clz);
    }

    /**
     * ��json����ת��Ϊʵ�����
     *
     * @param json
     * @param clz
     * @param <T>
     * @return
     * @throws JsonSyntaxException
     */
    public static <T> T deserialize(JsonObject json, Class<T> clz)
            throws JsonSyntaxException {
        return mGson.fromJson(json, clz);
    }

    /**
     * ��json�ַ���ת��Ϊ����
     *
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T deserialize(String json, Type type)
            throws JsonSyntaxException {
        return mGson.fromJson(json, type);
    }

}
