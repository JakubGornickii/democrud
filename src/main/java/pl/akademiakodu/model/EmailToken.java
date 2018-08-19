package pl.akademiakodu.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class EmailToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String token;
    private int userId;
    private boolean active;
    private String usefor;

    public EmailToken(String token, int userId, boolean active, String usefor) {
        this.token = token;
        this.userId = userId;
        this.active = active;
        this.usefor = usefor;
    }

    public EmailToken() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getUsefor() {
        return usefor;
    }

    public void setUsefor(String usefor) {
        this.usefor = usefor;
    }
}
