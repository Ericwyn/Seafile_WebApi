import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;

import java.io.IOException;
import java.util.List;

import Interface.QuickStartInterface;
import jsonObject.StarredFile;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Ericwyn on 17-8-15.
 */
public class SeafileApi implements QuickStartInterface{
    private String SERVICE_URL;
    public SeafileApi(String SERVICE_URL){
        this.SERVICE_URL=SERVICE_URL;
    }

    @Override
    public String ping(OkHttpClient client) {
        Request request=new Request.Builder()
                .get()
                .url(SERVICE_URL+"/api2/ping/")
                .build();
        try (Response response=client.newCall(request).execute()){
            if(response.isSuccessful()){
                return response.body().string();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String obtainAuthToken(OkHttpClient client, String username, String password) {
        RequestBody body=new FormBody.Builder()
                .add("username",username)
                .add("password",password)
                .build();
        Request request=new Request.Builder()
                .url(SERVICE_URL+"/api2/auth-token/")
                .header("Content-Type","application/x-www-form-urlencoded")
                .post(body)
                .build();
        try (Response response=client.newCall(request).execute()){
            JSONObject jObj =parseJson(response.body().string());
            return jObj.getString("token");
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * check Account Info
     *
     * Sample response
            {
             "usage": 26038531,
             "total": 104857600,
             "email": "user@example.com"
            }
     * @param client
     * @param token
     * @return
     */
    @Override
    public JSONObject checkAccountInfo(OkHttpClient client,String token) {
        Request request=new Request.Builder()
                .header("Content-Type","application/x-www-form-urlencoded")
                .header("Authorization","Token "+token)
                .header("Accept","application/json")
                .header("indent","4")
                .get()
                .url(SERVICE_URL+"/api2/account/info/")
                .build();
        try (Response response=client.newCall(request).execute()){
            return parseJson(response.body().string());
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * No authentication required.
     * 无需用户验证可直接使用
     *
     * Added in seafile community edition server 4.0.5 or pro edition server 4.0.3
     * 只适用于社区版4.0.5以上 或者专业版4.0.3 以上 版本
     *
     * Sample response

         Sample response from a seafile community edition server:
             {
                 "version": "4.0.6",
                 "features": [
                 "seafile-basic",
                 ]
             }
     * @param client
     * @return
     */
    @Override
    public JSONObject getServerInformation(OkHttpClient client) {
        Request request=new Request.Builder()
                .header("Content-Type","application/x-www-form-urlencoded")
                .get()
                .url(SERVICE_URL+"/api2/server-info/")
                .build();
        try (Response response=client.newCall(request).execute()){
            return parseJson(response.body().string());
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     *
     * @param client
     * @param token
     * @return
     */
    @Override
    public List<StarredFile> listStarredFiles(OkHttpClient client, String token) {
        Request request=new Request.Builder()
                .header("Content-Type","application/x-www-form-urlencoded")
                .header("Authorization","Token "+token)
                .header("Accept","application/json")
                .header("indent","4")
                .get()
                .url(SERVICE_URL+"/api2/starredfiles/")
                .build();
        try (Response response=client.newCall(request).execute()){
            return JSON.parseArray(response.body().string(), StarredFile.class);
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    private JSONObject parseJson(String jsonStr){
        System.out.println(jsonStr);
        return JSON.parseObject(jsonStr,Feature.AutoCloseSource);
    }
}
