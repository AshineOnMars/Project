package org.profile.dao;

import org.junit.Test;
import org.profile.model.User;

import static org.junit.Assert.*;

public class LoginDAOTest {

    @Test
    public void query() {
        LoginDAO loginDAO=new LoginDAO();
        User u = loginDAO.query("唐三藏");
        System.out.println(u);

    }
}