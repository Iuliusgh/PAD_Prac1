package es.ucm.fdi.educavial;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class UserRepository {
    private UserDAO Userdao;
    private LiveData<List<User>> user;
    UserRepository(Application application){
        Database db= Database.getDatabase(application);
        Userdao=db.userDAO();
        user=Userdao.GetAllUser();
    }
    LiveData<List<User>> GetAllUser(){
        return Userdao.GetAllUser();
    }
    void insert(User user){
        new insertAsyncTask(Userdao).execute(user);
    }
    public void deleteAllUsers() {
        new DeleteAllAsyncTask(Userdao).execute();
    }

    private static class insertAsyncTask extends AsyncTask<User,Void,Void>{
        private UserDAO taskDao;
        insertAsyncTask(UserDAO userDao){
            taskDao=userDao;
        }
        @Override
        protected Void doInBackground(User... user){
        taskDao.insertAllUser(user[0]);
        return null;
        }

    }


    private static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private UserDAO taskDao;

        DeleteAllAsyncTask(UserDAO userDao) {
            taskDao = userDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            taskDao.deleteAllUsers();
            return null;
        }
    }

}
