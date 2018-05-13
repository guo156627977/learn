package com.gzq.example.springboot.service;

import com.gzq.example.springboot.mapper.UserMapper;
import com.gzq.example.springboot.pojo.Emp;
import com.gzq.example.springboot.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-05-01 17:30.
 */
@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    EmpService empService;

    @Transactional(rollbackFor = Exception.class)
    public void insertUser(User user) throws Exception {

        userMapper.insert(user);
        Emp emp = createEmp();
        try {
            empService.insertEmp(emp);
        } catch (Exception e) {
            System.out.println("抓到emp抛出的异常");
        }
        User user1 = createUser();
        userMapper.insert(user1);

            throw new Exception();

    }

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
}
