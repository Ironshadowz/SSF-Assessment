package vttp2023.batch3.ssf.frontcontroller.model;

import java.io.Serializable;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class User implements Serializable
{
    @NotNull (message="Name is empty")
    @Size (min = 2, message="Name is too short")
    private String username;

    @NotNull (message="Password is empty")
    @Size (min = 2, message="Password is too short")
    private String password;
    
    private boolean authenticated;

    private String reply;

    private int count;

    private Boolean disabled = false;
    
    @Override
    public String toString() {
        return "User [name=" + username + ", password=" + password + ", authenticated=" + authenticated
                + "]";
    }
    
    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }
    public User()
    {

    }
    public User(String name, String password) {
        this.username = name;
        this.password = password;
    }
    
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public boolean isAuthenticated() {
        return authenticated;
    }
    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }
    public JsonObject toJson()
    {
        return Json.createObjectBuilder()
                    .add("username", this.getUsername())
                    .add("password", this.getPassword())
                    .build();
    }

    public User fromJson(JsonObject o)
    {
        User currUser = new User();
        currUser.setUsername(o.getString("username"));
        currUser.setPassword(o.getString("password"));
        return currUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
    
}
