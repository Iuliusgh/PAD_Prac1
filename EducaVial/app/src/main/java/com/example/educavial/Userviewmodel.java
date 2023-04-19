package com.example.educavial;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class Userviewmodel extends AndroidViewModel {
    private UserRepository repository;
    private LiveData<List<User>> Userlist;

    public Userviewmodel(Application application){
        super(application);
        repository=new UserRepository(application);
        Userlist= repository.GetAllUser();
    }
    LiveData<List<User>> getUserlist(){
        return Userlist;
    }
    public void insertUser(User user){
        repository.insert(user);
    }
    public void deleteAllUsers(){
        repository.deleteAllUsers();
    }

}
