import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;

import java.io.File;
import java.io.IOException;
import java.util.List;

import Interface.ApiInterface;
import jsonObject.DirectoryEntry;
import jsonObject.FileDetail;
import jsonObject.FileCommit;
import jsonObject.Library;
import jsonObject.LibraryHistory;
import jsonObject.StarredFile;
import jsonObject.UploadFileRes;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Ericwyn on 17-8-15.
 */
public class SeafileApi implements ApiInterface {
    // SERVICE_URL and FILE_SERVER_ROOT NOT endswith "/";
    private String SERVICE_URL;
    private String FILE_SERVER_ROOT;

    public SeafileApi(String SERVICE_URL,String FILE_SERVER_ROOT){
        this.SERVICE_URL=SERVICE_URL;
        this.FILE_SERVER_ROOT=FILE_SERVER_ROOT;
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
     *list all starred files
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

    /**
     * list all the library,can get the repo_id of the libraries
     *
     * @param client
     * @param token
     * @return
     */
    @Override
    public List<Library> listLibraries(OkHttpClient client, String token) {
        Request request=new Request.Builder()
                .header("Content-Type","application/x-www-form-urlencoded")
                .header("Authorization","Token "+token)
                .header("Accept","application/json")
                .header("indent","4")
                .get()
                .url(SERVICE_URL+"/api2/repos")
                .build();
        try (Response response=client.newCall(request).execute()){
            return JSON.parseArray(response.body().string(), Library.class);
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * use the repo_id to get the library info
     * the repo_id can get by method : List<Library> listLibraries(OkHttpClient client, String token)
     *
     * @param client
     * @param token
     * @param repo_id
     * @return
     */
    @Override
    public Library getLibraryInfo(OkHttpClient client, String token, String repo_id) {
        Request request=new Request.Builder()
                .header("Content-Type","application/x-www-form-urlencoded")
                .header("Authorization","Token "+token)
                .header("Accept","application/json")
                .header("indent","4")
                .get()
                .url(SERVICE_URL+"/api2/repos/"+repo_id)
                .build();
        try (Response response=client.newCall(request).execute()){
            return JSON.parseObject(response.body().string(), Library.class);
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<LibraryHistory> getLibraryHistory(OkHttpClient client, String token, String repo_id) {
        Request request=new Request.Builder()
                .header("Content-Type","application/x-www-form-urlencoded")
                .header("Authorization","Token "+token)
                .header("Accept","application/json")
                .header("indent","4")
                .get()
                .url(SERVICE_URL+"/api/v2.1/repos/"+repo_id+"/history/")
                .build();
        try (Response response=client.newCall(request).execute()){
            return JSONObject.parseArray(parseJson(response.body().string()).getString("data"),LibraryHistory.class);
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * return the file download link
     *
     * @param client
     * @param token
     * @param repo_id
     * @param p
     * @param reuse  if you want the generated download link can be accessed more than once in one hour.
     *               set this parameter be true
     * @return
     */
    @Override
    public String getFileDownloadLink(OkHttpClient client, String token, String repo_id, String p,boolean reuse) {
        String reuse_Temp=reuse?"&reuse=1":"";
        Request request=new Request.Builder()
                .header("Content-Type","application/x-www-form-urlencoded")
                .header("Authorization","Token "+token)
                .header("Accept","application/json")
                .header("indent","4")
                .get()
                .url(SERVICE_URL+"/api2/repos/"+repo_id+"/file/?p="+p+reuse_Temp)
                .build();
        try (Response response=client.newCall(request).execute()){
            if(response.isSuccessful()){
                return response.body().string();
            }else {
                System.out.println(response.code());
                System.out.println(response.body().string());
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * get file detail
     *
     * @param client
     * @param token
     * @param repo_id
     * @param p     p is the full path of the file, the root path is it's library
     * @return
     */
    @Override
    public FileDetail getFileDetail(OkHttpClient client, String token, String repo_id, String p) {
        Request request=new Request.Builder()
                .header("Content-Type","application/x-www-form-urlencoded")
                .header("Authorization","Token "+token)
                .header("Accept","application/json")
                .header("indent","4")
                .get()
                .url(SERVICE_URL+"/api2/repos/"+repo_id+"/file/detail/?p="+p)
                .build();
        try (Response response=client.newCall(request).execute()){
            return JSONObject.parseObject(response.body().string(),FileDetail.class);
        }catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<FileCommit> getFileHistory(OkHttpClient client, String token, String repo_id, String p) {
        Request request=new Request.Builder()
                .header("Content-Type","application/x-www-form-urlencoded")
                .header("Authorization","Token "+token)
                .header("Accept","application/json")
                .header("indent","4")
                .get()
                .url(SERVICE_URL+"/api2/repos/"+repo_id+"/file/history/?p="+p)
                .build();
        try (Response response=client.newCall(request).execute()){
            return JSONObject.parseArray(parseJson(response.body().string()).getString("commits"), FileCommit.class);
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean createFile(OkHttpClient client, String token, String repo_id, String p) {
        RequestBody body=new FormBody.Builder()
                .add("operation","create")
                .build();
        Request request=new Request.Builder()
                .header("Content-Type","application/x-www-form-urlencoded")
                .header("Authorization","Token "+token)
                .header("Accept","application/json")
                .header("indent","4")
                .url(SERVICE_URL+"/api2/repos/"+repo_id+"/file/?p="+p)
                .post(body)
                .build();
        try (Response response=client.newCall(request).execute()){
            if (response.isSuccessful()){
                return true;
            }else {
                System.out.println(response.code());
                System.out.println(response.body().string());
                return false;
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean renameFile(OkHttpClient client, String token, String repo_id, String p, String newName) {
        RequestBody body=new FormBody.Builder()
                .add("operation","rename")
                .add("newname",newName)
                .build();
        Request request=new Request.Builder()
                .header("Content-Type","application/x-www-form-urlencoded")
                .header("Authorization","Token "+token)
                .header("Accept","application/json")
                .header("indent","4")
                .url(SERVICE_URL+"/api2/repos/"+repo_id+"/file/?p="+p)
                .post(body)
                .build();
        try (Response response=client.newCall(request).execute()){
            if (response.isSuccessful()){
                return true;
            }else {
                System.out.println(response.code());
                System.out.println(response.body().string());
                return false;
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean moveFile(OkHttpClient client, String token, String repo_id, String p, String dst_repo, String dst_dir) {
        RequestBody body=new FormBody.Builder()
                .add("operation","move")
                .add("dst_repo",dst_repo)
                .add("dst_dir",dst_dir)
                .build();
        Request request=new Request.Builder()
                .header("Content-Type","application/x-www-form-urlencoded")
                .header("Authorization","Token "+token)
                .header("Accept","application/json")
                .header("indent","4")
                .url(SERVICE_URL+"/api2/repos/"+repo_id+"/file/?p="+p)
                .post(body)
                .build();
        try (Response response=client.newCall(request).execute()){
            if (response.isSuccessful()){
                return true;
            }else {
                System.out.println(response.code());
                System.out.println(response.body().string());
                return false;
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * this method never had test
     *
     * @param client
     * @param token
     * @param repo_id
     * @param p
     * @param commit_id
     * @return
     */
    @Override
    public boolean revertFile(OkHttpClient client, String token, String repo_id, String p, String commit_id) {
        RequestBody body=new FormBody.Builder()
                .add("commit_id",commit_id)
                .add("p",p)
                .build();
        Request request=new Request.Builder()
                .header("Content-Type","application/x-www-form-urlencoded")
                .header("Authorization","Token "+token)
                .header("Accept","application/json")
                .header("indent","4")
                .url(SERVICE_URL+"/api2/repos/"+repo_id+"/file/revert/")
                .put(body)
                .build();
        try (Response response=client.newCall(request).execute()){
            if (response.isSuccessful()){
                return true;
            }else {
                System.out.println(response.code());
                System.out.println(response.body().string());
                return false;
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteFile(OkHttpClient client, String token, String repo_id, String p) {
        Request request=new Request.Builder()
                .header("Content-Type","application/x-www-form-urlencoded")
                .header("Authorization","Token "+token)
                .header("Accept","application/json")
                .header("indent","4")
                .url(SERVICE_URL+"/api2/repos/"+repo_id+"/file/?p="+p)
                .delete()
                .build();
        try (Response response=client.newCall(request).execute()){
            if (response.isSuccessful()){
                System.out.println(response.code());
                return true;
            }else {
                System.out.println(response.code());
                System.out.println(response.body().string());
                return false;
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }


    /**
     * get the upload link before upload file
     *
     * look likes that this link is always point to the library's root path , so the p maybe a invalid parameter
     * 测试中发现这个链接似乎只会指向资料库的根目录。即使传入了p，所以p似乎是一个无效的参数？
     *
     * @param client
     * @param token
     * @param repo_id
     * @param p p is the upload dir of the link ,use "/" as default;
     * @return
     */
    @Override
    public String getUploadLink(OkHttpClient client, String token, String repo_id,String p) {
        Request request=new Request.Builder()
                .header("Content-Type","application/x-www-form-urlencoded")
                .header("Authorization","Token "+token)
                .url(SERVICE_URL+"/api2/repos/"+repo_id+"/upload-link/?p="+p)
                .get()
                .build();
        try (Response response=client.newCall(request).execute()){
            if (response.isSuccessful()){
                return response.body().string().replaceAll("\"","");
            }else {
                System.out.println(response.code());
                System.out.println(response.body().string());
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * the upload file method
     * You can upload multiple files at once
     *
     * @param client
     * @param token
     * @param uploadLink
     * @param parent_dir    must endswith "/"
     * @param relative_path must NOT startswith "/" ,
     *                      but in my test , this parameter was always invalid
     *                      so i suggest that use the parameter 'parent_dir' to define the upload path of the file
     *                      such upload a file 'test.txt' to the path '/upload/upload2/upload3' of a library
     *                      you can use this method like that
     *
     *                      new SeafileApi.uploadFile(client,token,uploadLink,"/upload/upload2/upload3/","",new File("test.txt"));
     * @param files
     * @return
     */
    @Override
    public List<UploadFileRes> uploadFile(OkHttpClient client, String token,String uploadLink,String parent_dir, String relative_path, File... files) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        for (File file:files){
            builder.addFormDataPart("file",file.getName(),RequestBody.create(MediaType.parse("application/octet-stream"), file));
        }

        builder.addFormDataPart("parent_dir",parent_dir);
        builder.addFormDataPart("relative_path",relative_path);
        RequestBody body=builder.build();

        Request request=new Request.Builder()
                .header("Content-Type","application/x-www-form-urlencoded")
                .header("Authorization","Token "+token)
                .post(body)
                .url(uploadLink+"?ret-json=1")
                .build();
        try (Response response=client.newCall(request).execute()){
            if (response.isSuccessful()){
                return JSONObject.parseArray(response.body().string(),UploadFileRes.class);
            }else {
                System.out.println(response.code());
                System.out.println(response.body().string());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * look likes that this link is always point to the library's root path , the p maybe a invalid parameter
     * same of the upload link
     *
     * @param client
     * @param token
     * @param repo_id
     * @param p
     * @return
     */
    @Override
    public String getUpdateLink(OkHttpClient client, String token, String repo_id, String p) {
        Request request=new Request.Builder()
                .header("Content-Type","application/x-www-form-urlencoded")
                .header("Authorization","Token "+token)
                .url(SERVICE_URL+"/api2/repos/"+repo_id+"/update-link/?p="+p)
                .get()
                .build();
        try (Response response=client.newCall(request).execute()){
            if (response.isSuccessful()){
                return response.body().string().replaceAll("\"","");
            }else {
                System.out.println(response.code());
                System.out.println(response.body().string());
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * update file method
     *
     * in my test ,the request parameter 'filename' maybe is invalid
     * so , when you use this method ,the change of the cloud file is it's content
     * and i find that ,when the content is changed ,the id of this file will changed too ?
     * you can do test more
     *
     * @param client
     * @param token
     * @param updataLink
     * @param file
     * @param target_file the full path of the targe_file in the library
     * @return
     */
    @Override
    public boolean updateFile(OkHttpClient client, String token, String updataLink,File file,String target_file) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("file",file.getName(),RequestBody.create(MediaType.parse("application/octet-stream"), file));

        builder.addFormDataPart("filename",file.getName());
        builder.addFormDataPart("target_file",target_file);
        RequestBody body=builder.build();

        Request request=new Request.Builder()
                .header("Content-Type","application/x-www-form-urlencoded")
                .header("Authorization","Token "+token)
                .post(body)
                .url(updataLink)
                .build();
        try (Response response=client.newCall(request).execute()){
            if (response.isSuccessful()){
                System.out.println(response.body().string());
                return true;
            }else {
                System.out.println(response.code());
                System.out.println(response.body().string());
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        return false;
    }

    /**
     * notice
     * when you use this method to get the DirectoryEntry of the dir ,the parent_dir maybe is null
     * if you want to get all parameter in the DirectoryEntry of the dir ,you need to use the next method
     * List<DirectoryEntry> listAllDirEntries()
     *
     * @param client
     * @param token
     * @param repo_id
     * @param p
     * @return
     */
    @Override
    public List<DirectoryEntry> listDirEntriesByP(OkHttpClient client, String token, String repo_id, String p) {
        Request request=new Request.Builder()
                .header("Content-Type","application/x-www-form-urlencoded")
                .header("Authorization","Token "+token)
                .url(SERVICE_URL+"/api2/repos/"+repo_id+"/dir/?p="+p)
                .get()
                .build();
        try (Response response=client.newCall(request).execute()){
            if (response.isSuccessful()){
                return JSONObject.parseArray(response.body().string(),DirectoryEntry.class);
            }else {
                System.out.println(response.code());
                System.out.println(response.body().string());
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

//    /**
//     * look likes that this method is not work
//     * The corresponding url of web api is:
//     * https://manual.seafile.com/develop/web_api_v2.1.html#list-directory-entries
//     *
//     * @param client
//     * @param token
//     * @param repo_id
//     * @param id
//     * @return
//     */
//    @Override
//    public List<DirectoryEntry> listDirectoryEntriesByID(OkHttpClient client, String token, String repo_id,String id) {
//        Request request=new Request.Builder()
//                .header("Content-Type","application/x-www-form-urlencoded")
//                .header("Authorization","Token "+token)
//                .url(SERVICE_URL+"/api2/repos/"+repo_id+"/dir/?oid="+id)
//                .get()
//                .build();
//        try (Response response=client.newCall(request).execute()){
//            if (response.isSuccessful()){
//                return JSONObject.parseArray(response.body().string(),DirectoryEntry.class);
//            }else {
//                System.out.println(response.code());
//                System.out.println(response.body().string());
//            }
//
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//        return null;
//    }

    @Override
    public List<DirectoryEntry> listAllDirEntries(OkHttpClient client, String token, String repo_id) {
        Request request=new Request.Builder()
                .header("Content-Type","application/x-www-form-urlencoded")
                .header("Authorization","Token "+token)
                .url(SERVICE_URL+"/api2/repos/"+repo_id+"/dir/?t=d&recursive=1")
                .get()
                .build();
        try (Response response=client.newCall(request).execute()){
            if (response.isSuccessful()){
                return JSONObject.parseArray(response.body().string(),DirectoryEntry.class);
            }else {
                System.out.println(response.code());
                System.out.println(response.body().string());
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean createNewDir(OkHttpClient client, String token, String repo_id, String p) {
        RequestBody body=new FormBody.Builder()
                .add("operation","mkdir")
                .build();
        Request request=new Request.Builder()
                .header("Content-Type","application/x-www-form-urlencoded")
                .header("Authorization","Token "+token)
                .header("Accept","application/json")
                .header("indent","4")
                .url(SERVICE_URL+"/api2/repos/"+repo_id+"/dir/?p="+p)
                .post(body)
                .build();
        try (Response response=client.newCall(request).execute()){
            if (response.isSuccessful()){
                return true;
            }else {
                System.out.println(response.code());
                System.out.println(response.body().string());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     *
     *
     * @param client
     * @param token
     * @param repo_id
     * @param p         startswith "/"
     * @param newName   Not startswith "/"
     * @return
     */
    @Override
    public boolean renameDir(OkHttpClient client, String token, String repo_id, String p, String newName) {
        RequestBody body=new FormBody.Builder()
                .add("operation","rename")
                .add("newname",newName)
                .build();
        Request request=new Request.Builder()
                .header("Content-Type","application/x-www-form-urlencoded")
                .header("Authorization","Token "+token)
                .header("Accept","application/json")
                .header("indent","4")
                .url(SERVICE_URL+"/api2/repos/"+repo_id+"/dir/?p="+p)
                .post(body)
                .build();
        try (Response response=client.newCall(request).execute()){
            if (response.isSuccessful()){
                return true;
            }else {
                System.out.println(response.code());
                System.out.println(response.body().string());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteDir(OkHttpClient client, String token, String repo_id, String p) {
        Request request=new Request.Builder()
                .header("Content-Type","application/x-www-form-urlencoded")
                .header("Authorization","Token "+token)
                .header("Accept","application/json")
                .header("indent","4")
                .url(SERVICE_URL+"/api2/repos/"+repo_id+"/dir/?p="+p)
                .delete()
                .build();
        try (Response response=client.newCall(request).execute()){
            if (response.isSuccessful()){
                return true;
            }else {
                System.out.println(response.code());
                System.out.println(response.body().string());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * get the download token for the dir download
     * the downloadToken is the zip_token in web api document
     *
     * @param client
     * @param token
     * @param repo_id
     * @param parent_dir    the parent path, use "/" when the parent path is root path of the library
     * @param dirents   NOT startswith "/" of endswith "/"
     * @return
     */
    @Override
    public String getDirDownloadToken(OkHttpClient client, String token, String repo_id, String parent_dir, String dirents) {
        Request request=new Request.Builder()
                .header("Content-Type","application/x-www-form-urlencoded")
                .header("Authorization","Token "+token)
                .header("Accept","application/json")
                .header("indent","4")
                .url(SERVICE_URL+"/api/v2.1/repos/"+repo_id+"/zip-task/?parent_dir="+parent_dir+"&dirents="+dirents)
                .get()
                .build();
        try (Response response=client.newCall(request).execute()){
            if (response.isSuccessful()){
                return JSONObject.parseObject(response.body().string()).getString("zip_token");
            }else {
                System.out.println(response.code());
                System.out.println(response.body().string());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * use this method to query the zip progress of the dir download
     * before you use the dir download method , you need to ensure the zip progress of the dir is finish
     *
     * if the progress id finish , this method will return true
     * else return false and it means that you can not download the dir by download-token
     *
     * @param client
     * @param token
     * @param dirDownloadToken
     * @return
     */
    @Override
    public boolean queryZipProgress(OkHttpClient client, String token, String dirDownloadToken) {
        Request request=new Request.Builder()
                .header("Content-Type","application/x-www-form-urlencoded")
                .header("Authorization","Token "+token)
                .header("Accept","application/json")
                .header("indent","4")
                .url(SERVICE_URL+"/api/v2.1/query-zip-progress/?token="+dirDownloadToken)
                .get()
                .build();
        try (Response response=client.newCall(request).execute()){
            if (response.isSuccessful()){
                String temp=response.body().string();
                if(JSONObject.parseObject(temp).getString("zipped").equals(
                        JSONObject.parseObject(temp).getString("total")
                )){
                    return true;
                }else {
                    return false;
                }
            }else {
                System.out.println(response.code());
                System.out.println(response.body().string());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * get the download link of the dir download
     *
     * before generate the download link
     * this method will use queryZipProgress() method to checkoout the dirDownloadToken(zip_token)
     * if the dirDownloadToken is invalid ,this method will return null
     *
     * a download link can only use once ,after that , the dirDownloadToken of the link will be invalid
     *
     * @param client
     * @param token
     * @param dirDownloadToken
     * @return
     */
    @Override
    public String getDirDownloadLink(OkHttpClient client, String token, String dirDownloadToken) {
        if(queryZipProgress(client,token,dirDownloadToken)){
            return FILE_SERVER_ROOT+"/zip/"+dirDownloadToken;
        }
        return null;
    }

    /**
     * parse json String to JsonObject
     *
     * @param jsonStr
     * @return
     */
    private JSONObject parseJson(String jsonStr){
        //show the json Str for test
//        System.out.println(jsonStr);
        return JSON.parseObject(jsonStr,Feature.AutoCloseSource);
    }
}
