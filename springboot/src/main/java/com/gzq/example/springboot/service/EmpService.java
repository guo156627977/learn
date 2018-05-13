package com.gzq.example.springboot.service;

import com.gzq.example.springboot.mapper.EmpMapper;
import com.gzq.example.springboot.pojo.Emp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-05-01 17:49.
 */
@Service
public class EmpService {

    @Autowired
    EmpMapper empMapper;

    //@Transactional
    @Transactional(propagation = Propagation.REQUIRES_NEW ,rollbackFor = Exception.class)
    public void insertEmp(Emp emp) throws Exception {

            empMapper.insert(emp);
            //throw new Exception();
    }
}
