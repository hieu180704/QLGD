package controller;

import DAO.UserDAO;
import Model.UserAccountModel;

public class LoginController {
    private UserDAO userDAO;

    public LoginController() {
        userDAO = new UserDAO();
    }

    public UserAccountModel login(String username, String password) {
        return userDAO.checkLogin(username, password);
    }
}
