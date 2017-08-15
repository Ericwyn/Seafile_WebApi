import okhttp3.OkHttpClient;

/**
 * Created by Ericwyn on 17-8-15.
 */
public class SeafileApiTest {
    public static final String SERVICE_URL="https://cloud.meetwhy.com";

    public static void main(String[] args) {
        OkHttpClient client=new OkHttpClient();
        SeafileApi api=new SeafileApi();
        System.out.println("api.ping = " + api.ping(client,SERVICE_URL));

        System.out.println("api.obtainAuthToken = " + api.obtainAuthToken(client, SERVICE_URL,
                Account.username,Account.password));
    }

}
