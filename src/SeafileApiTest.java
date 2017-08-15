import java.util.List;

import jsonObject.StarredFile;
import okhttp3.OkHttpClient;

/**
 * Created by Ericwyn on 17-8-15.
 */
public class SeafileApiTest {
    public static final String SERVICE_URL="https://cloud.meetwhy.com";

    public static void main(String[] args) {

        OkHttpClient client=new OkHttpClient();
        SeafileApi api=new SeafileApi(SERVICE_URL);

//        String ping = api.ping(client);
//        System.out.println("ping = " + ping);
//
        String token = api.obtainAuthToken(client, Account.username,Account.password);
        System.out.println("token = " + token);
//
//        JSONObject accountName=api.checkAccountInfo(client,token);
//        System.out.println("accountName = " + accountName.getString("name"));

//        JSONObject serverInfo=api.getServerInformation(client);
//        System.out.println("serverInfo = " + serverInfo.getString("version"));
//        System.out.println("serverInfo features = " + serverInfo.get("features"));

        List<StarredFile> starredFiles = api.listStarredFiles(client,token);
        System.out.println(starredFiles.get(0));

    }

}
