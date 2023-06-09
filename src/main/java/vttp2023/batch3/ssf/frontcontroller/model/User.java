package vttp2023.batch3.ssf.frontcontroller.model;



import java.io.Serializable;
import java.util.Currency;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class User implements Serializable
{
    @NotNull (message="Name is empty")
    @Size (min=2, message="Name is too short")
    @NotEmpty (message="Name is empty")
    private String name;

    @NotNull (message="Password is empty")
    @Size (min=2, message="Password is too short")
    @NotEmpty (message="Password is empty")
    private String password;
    
    private boolean authenticated;

    private int count=0;
    
    @Override
    public String toString() {
        return "User [name=" + name + ", password=" + password + ", authenticated=" + authenticated + ", count=" + count
                + "]";
    }
    public User()
    {

    }
    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public JsonObject toJson()
    {
        return Json.createObjectBuilder()
                    .add("name", this.getName())
                    .add("password", this.getPassword())
                    .build();
    }

    public User fromJson(JsonObject o)
    {
        User currUser = new User();
        currUser.setName(o.getString("name"));
        currUser.setPassword(o.getString("password"));
        return currUser;
    }
    
    
}
