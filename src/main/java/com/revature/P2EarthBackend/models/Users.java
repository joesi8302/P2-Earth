package com.revature.P2EarthBackend.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "user")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer user_id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String user_first_name;

    @Column
    private String user_last_name;

    @Column
    private String user_img;

    @Column
    private String user_email;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    List<Posts> users = new ArrayList<>();

}
