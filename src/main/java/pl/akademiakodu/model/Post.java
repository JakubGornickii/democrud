package pl.akademiakodu.model;

import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id")
    private int id;
    @Column(name = "post_title")
    @NotEmpty(message = "*Proszę wpisać tytuł postu")
    private String title;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "post_moderated")
    private boolean moderated;
    @URL(message = "Proszę wpisać poprawny link url")
    @Column(name = "post_url")
    @NotEmpty(message = "*Proszę wpisać link do zawartości")
    private String url;
    @Column(name = "post_content")
    @NotNull(message = "*Proszę wpisać treść postu")
    private String content;

    public Post() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isModerated() {
        return moderated;
    }

    public void setModerated(boolean moderated) {
        this.moderated = moderated;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String contents) {
        this.content = contents;
    }
}
