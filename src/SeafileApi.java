import java.io.IOException;

import Interface.QuickStartInterface;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Ericwyn on 17-8-15.
 */
public class SeafileApi implements QuickStartInterface{
    @Override
    public String ping(OkHttpClient client,String SERVICE_URL) {
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
    public String obtainAuthToken(OkHttpClient client,String SERVICE_URL, String username, String password) {
        RequestBody body=new FormBody.Builder()
                .add("username",username)
                .add("password",password)
                .build();
        Request request=new Request.Builder()
                .url(SERVICE_URL+"/api2/auth-token/")
                .header("Content-Type","application/x-www-form-urlencoded")
                .post(body)
                .build();
        System.out.println("method:"+request.method());
        try (Response response=client.newCall(request).execute()){
            System.out.println("method:"+request.method());
            System.out.println("method2:"+response.request().method());
            System.out.println("code:"+response.code());
            return response.body().string();
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

}
