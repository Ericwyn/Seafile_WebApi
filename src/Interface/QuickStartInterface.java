package Interface;

import okhttp3.OkHttpClient;

/**
 * Created by Ericwyn on 17-8-15.
 */
public interface QuickStartInterface {

    String ping(OkHttpClient client,String SERVICE_URL);

    public String obtainAuthToken(OkHttpClient client,String SERVICE_URL,String username,String password);

}
