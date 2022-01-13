package com.revature.P2EarthBackend.models;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "posts")
public class Posts {
    public Posts(String post_description, String post_img, Users user){

        this.post_created = new Timestamp(System.currentTimeMillis());
        this.post_description = post_description;
        this.post_img = post_img;
        this.user = user;

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long post_id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm a z")
    @Column
    private Timestamp post_created;

    @Column
    private String post_img;

    @Column
    private String post_description;



    @ManyToOne
    @JsonIgnoreProperties({"posts","comments"})
    private Users user;

    @JsonIgnore
    @OneToMany(mappedBy = "post",cascade = CascadeType.REMOVE)
    private List<Comments> comments = new ArrayList<>();

    public Long getPost_id() {
        return post_id;
    }

    public void setPost_id(Long post_id) {
        this.post_id = post_id;
    }

    public Timestamp getPost_created() {
        return post_created;
    }

    public void setPost_created(Timestamp post_created) {
        this.post_created = post_created;
    }

    public String getPost_img() {
        return post_img;
    }

    public void setPost_img(String post_img) {
        this.post_img = post_img;
    }

    public String getPost_description() {
        return post_description;
    }

    public void setPost_description(String post_description) {
        this.post_description = post_description;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Posts{" +
                "post_id=" + post_id +
                ", post_created=" + post_created +
                ", post_img='" + post_img + '\'' +
                ", post_description='" + post_description + '\'' +
                ", user=" + user.getUserId() +
                ", comments=" + comments +
                '}';
    }
}
