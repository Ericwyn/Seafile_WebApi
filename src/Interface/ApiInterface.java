package Interface;

import com.alibaba.fastjson.JSONObject;
import com.sun.javaws.progress.Progress;

import java.io.File;
import java.util.List;

import javax.management.Query;

import jsonObject.DirectoryEntry;
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

    //Account API
    String ping(OkHttpClient client);

    String obtainAuthToken(OkHttpClient client,String username,String password);

    JSONObject checkAccountInfo(OkHttpClient client, String token);

    JSONObject getServerInformation(OkHttpClient client);

    //Starred File API
    List<StarredFile> listStarredFiles(OkHttpClient client, String token);

    //Library API
    List<Library> listLibraries(OkHttpClient client, String token);

    Library getLibraryInfo(OkHttpClient client,String token,String repo_id);

    List<LibraryHistory> getLibraryHistory(OkHttpClient client, String token, String repo_id);

    //File API
    String getFileDownloadLink(OkHttpClient client,String token,String repo_id,String p,boolean reuse);

    FileDetail getFileDetail(OkHttpClient client,String token,String repo_id,String p);

    List<FileCommit> getFileHistory(OkHttpClient client, String token, String repo_id, String p);

    boolean createFile(OkHttpClient client,String token,String repo_id,String p);

    boolean renameFile(OkHttpClient client,String token,String repo_id,String p,String newName);

    boolean moveFile(OkHttpClient client,String token,String repo_id,String p,String dst_repo,String dst_dir);

    boolean revertFile(OkHttpClient client,String token,String repo_id,String p,String commit_id);

    boolean deleteFile(OkHttpClient client,String token,String repo_id,String p);

    String getUploadLink(OkHttpClient client,String token,String repo_id,String p);

    List<UploadFileRes> uploadFile(OkHttpClient client,String token,String uploadLink,String parent_dir,String relative_path,File... files);

    String getUpdateLink(OkHttpClient client,String token,String repo_id,String p);

    boolean updateFile(OkHttpClient client,String token,String updataLink,File file,String target_file);

    //Directory API
    List<DirectoryEntry> listDirEntriesByP(OkHttpClient client, String token, String repo_id, String p);

//    List<DirectoryEntry> listDirectoryEntriesByID(OkHttpClient client,String token,String repo_id,String id);

    List<DirectoryEntry> listAllDirEntries(OkHttpClient client, String token, String repo_id);

    boolean createNewDir(OkHttpClient client, String token, String repo_id, String p);

    boolean renameDir(OkHttpClient client, String token, String repo_id, String p, String newName);

    boolean deleteDir(OkHttpClient client, String token, String repo_id, String p);

    String getDirDownloadToken(OkHttpClient client, String token, String repo_id, String parent_dir, String dirents);

    boolean queryZipProgress(OkHttpClient client,String token,String dirDownloadToken);

    String getDirDownloadLink(OkHttpClient client,String token,String dirDownloadToken);
}
