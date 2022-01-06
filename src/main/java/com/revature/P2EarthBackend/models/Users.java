package com.revature.P2EarthBackend.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "users")
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

//    @JsonIgnoreProperties("user")

    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties({"posts"})
    private List<Posts> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties({"user"})
    @JsonIgnore
    private List<Comments> comments=new ArrayList<>();

    // I droped the user_id parameter here , I think when we create the user we dont need to provide the id , it should autoincreate and autogenerate ,
// other wise we have to memeorize the user id number, if we use the same id number ,it will replace the user with the same user id
    public Users( String username, String password, String firstname, String lastname, String userimg, String email) {

        this.username=username;
        this.password=password;
        this.user_first_name=firstname;
        this.user_last_name=lastname;
        this.user_img=userimg;
        this.user_email=email;
    }

    public Users( Integer id, String username, String password, String firstname, String lastname, String userimg, String email) {
        this.user_id = id;
        this.username=username;
        this.password=password;
        this.user_first_name=firstname;
        this.user_last_name=lastname;
        this.user_img=userimg;
        this.user_email=email;
    }


}
