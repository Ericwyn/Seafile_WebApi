package Interface;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

import jsonObject.StarredFile;
import okhttp3.OkHttpClient;

/**
 * Created by Ericwyn on 17-8-15.
 */
public interface QuickStartInterface {

    String ping(OkHttpClient client);

    String obtainAuthToken(OkHttpClient client,String username,String password);

    JSONObject checkAccountInfo(OkHttpClient client, String token);

    JSONObject getServerInformation(OkHttpClient client);

    List<StarredFile> listStarredFiles(OkHttpClient client, String token);
}
