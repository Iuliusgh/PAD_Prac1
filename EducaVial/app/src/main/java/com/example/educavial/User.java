package com.example.educavial;
import androidx.room.*;
@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public User(){};
    public String username;
    public String email;
    public User(String username,String email){
        this.username=username;
        this.email=email;
    }

}

