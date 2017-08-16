/**
 * Copyright 2017 bejson.com
 */
package jsonObject;
import java.util.Date;

/**
 * Auto-generated: 2017-08-16 13:8:54
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class LibraryHistory {
    private String name;
    private String commit_id;
    private Date time;
    private String description;
    private String creator;
    private String email;
    private String contact_email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact_email() {
        return contact_email;
    }

    public void setContact_email(String contact_email) {
        this.contact_email = contact_email;
    }

    public void setCommit_id(String commit_id) {
        this.commit_id = commit_id;
    }
    public String getCommit_id() {
        return commit_id;
    }

    public void setTime(Date time) {
        this.time = time;
    }
    public Date getTime() {
        return time;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
    public String getCreator() {
        return creator;
    }

}