package com.revature.P2EarthBackend.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "comments")
public class Comments {
    public Comments(String comment_description,Users user,Posts post){
        this.comment_created=new Timestamp(System.currentTimeMillis());
        this.comment_description=comment_description;
        this.user=user;
        this.post=post;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long comment_id;


    @ManyToOne

//    @JsonIgnoreProperties({"comment"})
    @JsonIgnore
    private Users user;

    @ManyToOne
    @JsonIgnore
//    @JsonIgnoreProperties({"comment"})
    private Posts post;

    @Column
    private Timestamp comment_created;



    @Column
    private String comment_description;


    @Override
    public String toString() {
        return "Comments{" +
                "comment_id=" + comment_id +
                ", user=" + user.getUserId() +
                ", post=" + post.getPost_id() +
                ", comment_created=" + comment_created +
                ", comment_description='" + comment_description + '\'' +
                '}';
    }
}
