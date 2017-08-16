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
public class SeafileApiTest {
    public static final String SERVICE_URL = "https://cloud.meetwhy.com";

    public static void main(String[] args) {

        OkHttpClient client = new OkHttpClient();
        SeafileApi api = new SeafileApi(SERVICE_URL);

//        String ping = api.ping(client);
//        System.out.println("ping = " + ping);
////
        String token = api.obtainAuthToken(client, Account.username, Account.password);
//        System.out.println("token = " + token);
//
//        JSONObject accountName=api.checkAccountInfo(client,token);
//        System.out.println("accountName = " + accountName.getString("name"));
//
//        JSONObject serverInfo=api.getServerInformation(client);
//        System.out.println("serverInfo = " + serverInfo.getString("version"));
//        System.out.println("serverInfo features = " + serverInfo.get("features"));
//
//        List<StarredFile> starredFiles = api.listStarredFiles(client,token);
//        System.out.println(starredFiles.get(0));
//
//        List<Library> libraries=api.listLibraries(client,token);
//        for (Library library:libraries){
//            System.out.println(library.getId());
//        }
//
        String repo_id = api.listLibraries(client, token).get(0).getId();
//        System.out.println(repo_id);
        Library libraryInfo = api.getLibraryInfo(client, token, repo_id);
        System.out.println("libraryInfo = " + libraryInfo.getName());
//
//        List<LibraryHistory> historyLists=api.getLibraryHistory(client,token,repo_id);
//        System.out.println(historyLists.get(0).getName());
//
//        FileDetail fileDetail = api.getFileDetail(client, token, repo_id, "/头像.png");
//        String fileName = fileDetail.getName();
//        String fileId = fileDetail.getId();
//
//        List<FileCommit> fileHistories=api.getFileHistory(client,token,repo_id,"/头像.png");
//        System.out.println(fileHistories.get(0).getId());

//        api.createFile(client,token,repo_id,"/test.seanote");
//        api.renameFile(client,token,repo_id,"/test.seanote","test2.seanote");
//        api.moveFile(client,token,repo_id,"/test2.seanote",repo_id,"/test");
//        api.deleteFile(client,token,repo_id,"/test.seanote");

//        String uploadLink=api.getUploadLink(client,token,repo_id,"");
//        System.out.println(uploadLink);
//
        File file1=new File("testfiles/testfile1.txt");
//        File file2=new File("testfiles/testfile2.txt");
//        if(file1.isFile() && file2.isFile()){
//            List<UploadFileRes> uploadFileResList=api.uploadFile(client,token,uploadLink,"/test/upload/","",file1,file2);
//            for (UploadFileRes res:uploadFileResList){
//                System.out.println(res.getId());
//            }
//        }

//        file1 id:81d17cefba33e77b52d4b3fc350dc2b9ed04e8ad
//        file2 id:f19620522e4c3e86dbe2774b2f018d7a925fda8e
        String updateLink=api.getUpdateLink(client,token,repo_id,"");
        System.out.println(updateLink);

        api.updateFile(client,token,updateLink,file1,"/testfile2.txt");



    }

}
