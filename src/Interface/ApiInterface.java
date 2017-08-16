package Interface;

import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.util.List;

import jsonObject.FileDetail;
import jsonObject.FileCommit;
import jsonObject.Library;
import jsonObject.LibraryHistory;
import jsonObject.StarredFile;
import jsonObject.UploadFileRes;
import okhttp3.OkHttpClient;

/**
 * Created by Ericwyn on 17-8-15.
 */
public interface ApiInterface {

    String ping(OkHttpClient client);

    String obtainAuthToken(OkHttpClient client,String username,String password);

    JSONObject checkAccountInfo(OkHttpClient client, String token);

    JSONObject getServerInformation(OkHttpClient client);

    List<StarredFile> listStarredFiles(OkHttpClient client, String token);

    List<Library> listLibraries(OkHttpClient client, String token);

    Library getLibraryInfo(OkHttpClient client,String token,String repo_id);

    List<LibraryHistory> getLibraryHistory(OkHttpClient client, String token, String repo_id);

    FileDetail getFileDetail(OkHttpClient client,String token,String repo_id,String p);

    List<FileCommit> getFileHistory(OkHttpClient client, String token, String repo_id, String p);

    boolean createFile(OkHttpClient client,String token,String repo_id,String p);

    boolean renameFile(OkHttpClient client,String token,String repo_id,String p,String newName);

    boolean moveFile(OkHttpClient client,String token,String repo_id,String p,String dst_repo,String dst_dir);
//    void copyFile();
    boolean revertFile(OkHttpClient client,String token,String repo_id,String p,String commit_id);

    boolean deleteFile(OkHttpClient client,String token,String repo_id,String p);
//    void uploadFile();
    String getUploadLink(OkHttpClient client,String token,String repo_id,String p);

    List<UploadFileRes> uploadFile(OkHttpClient client,String token,String uploadLink,String parent_dir,String relative_path,File... files);

    String getUpdateLink(OkHttpClient client,String token,String repo_id,String p);

    boolean updateFile(OkHttpClient client,String token,String updataLink,File file,String target_file);

}
