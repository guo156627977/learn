package com.gzq.example.springboot.transaction;

import com.gzq.example.springboot.pojo.Emp;
import com.gzq.example.springboot.pojo.User;
import com.gzq.example.springboot.service.EmpService;
import com.gzq.example.springboot.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-05-01 15:20.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUser {

    @Autowired
    UserService userService;

    @Autowired
    EmpService empService;


    public Emp createEmp() {
        Emp emp = new Emp();
        emp.setEmpId(Integer.toString((int) (Math.random() * 10000)));
        emp.setEmpName("gzq");
        return emp;
    }

    public User createUser() {
        User user = new User();
        user.setUserId(Integer.toString((int) (Math.random() * 10000)));
        user.setUserName("郭志强");
        user.setUserAge(26);
        return user;
    }



    @Test
    public void insertUser()  {
        User user = createUser();
        try {
            userService.insertUser(user);
        } catch (Exception e) {
            System.out.println("捕获到异常了，回滚");
        }


    }

    @Test
    public void insertEmp()  {
        Emp emp = createEmp();
        try {
            empService.insertEmp(emp);
        } catch (Exception e) {

            System.out.println("失败回滚");
        }


    }
}
