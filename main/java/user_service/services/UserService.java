package user_service.services;

import user_service.DAO.UserDAO;
import user_service.models.User;

public class UserService {
    private UserDAO userDao = new UserDAO();
    public UserService(){}
    public User findUser(int id){
        return userDao.read(id);
    }
    public void saveUser(User user){
        if(user == null){
            throw new NullPointerException("Not null");
        }
        userDao.save(user);
    }
    public void updateUser(User user){
        if(user == null){
            throw new NullPointerException("Not null");
        }
        userDao.update(user);
    }
    public void deleteUser(User user){
        if(user == null){
            throw new NullPointerException("Not null");
        }
        userDao.delete(user);
    }
}
