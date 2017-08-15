package jsonObject;

import java.util.HashMap;
import java.util.List;

/**
 * the Json Object for Starred File
 * used in list starred files api
 *
 *
 * Created by Ericwyn on 17-8-15.
 */
public class StarredFile {
    private String repo;
    //create time of unix timestamp
    private String mtime;
    //
    private String org;
    //the path of this file in the libraries
    private String path;
    //th dir
    private String dir;
    //the file size of bytes
    private String size;

    public StarredFile(){

    }

    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }

    public String getMtime() {
        return mtime;
    }

    public void setMtime(String mtime) {
        this.mtime = mtime;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
