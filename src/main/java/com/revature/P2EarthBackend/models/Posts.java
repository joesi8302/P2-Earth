package com.revature.P2EarthBackend.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "posts")
public class Posts {

    @Id
    @GeneratedValue
    @Column
    private Integer post_id;

    @Column
    private Timestamp post_created;

    @Column
    private String post_img;

    @Column
    private String post_description;

    @ManyToOne
    private Users user;



}
