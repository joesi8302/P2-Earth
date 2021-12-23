package com.revature.P2EarthBackend.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "user")
public class Users {

    @Id
    @GeneratedValue
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

}
