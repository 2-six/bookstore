package com.book.books.service.impl;

import com.book.books.domain.User;
import com.book.books.domain.UserExample;
import com.book.books.domain.UserExample.Criteria;
import com.book.books.mapper.UserMapper;
import com.book.books.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class UserService implements IUserService{

    @Resource
    private UserMapper userMapper;

    @Override
    public boolean userReg(User user) {
        UserExample userExample=new UserExample();
        List<User> userList=userMapper.selectByExample(userExample);
        for(User u:userList){
            if (u.getUname().equals(user.getUname())){
                return false;
            }
        }
        if(userMapper.insert(user)>0){
            return true;
        }
        return false;
    }

    @Override
    public User userLogin(User user) {
        UserExample example=new UserExample();
        Criteria cri = example.createCriteria();
        cri.andUnameEqualTo(user.getUname());
        List<User> users = userMapper.selectByExample(example);
        for(User u:users){
            if(u.getUname().equals(user.getUname())){
                if(u.getPassword().equals(user.getPassword())){
                    return u;
                }
            }
        }
        return null;
    }

    @Override
    public List<User> findAllUser() {
        UserExample example=new UserExample();
        List<User> users = userMapper.selectByExample(example);
        return users;
    }

    @Override
    public boolean userModify(User user) {
        User u = userMapper.selectByPrimaryKey(user.getUid());
        if(user.getUname()!=null&&!user.getUname().equals("")){
            u.setUname(user.getUname());
        }
        if(user.getPassword()!=null&&!user.getPassword().equals("")){
            u.setPassword(user.getPassword());
        }
        if(user.getGender()!=null&&!user.getGender().equals("")){
            u.setGender(user.getGender());
        }
        if(user.getPhone()!=null&&!user.getPhone().equals("")){
            u.setPhone(user.getPhone());
        }
        if(user.getEmail()!=null&&!user.getEmail().equals("")){
            u.setEmail(user.getEmail());
        }
        if(user.getAdress()!=null&&!user.getAdress().equals("")){
            u.setAdress(user.getAdress());
        }
        UserExample example=new UserExample();
        Criteria cri = example.createCriteria();
        cri.andUidEqualTo(user.getUid());
        if(userMapper.updateByExample(u, example)>0){
            return true;
        }
        return false;
    }

    @Override
    public User findUserById(Integer uid) {
        return userMapper.selectByPrimaryKey(uid);
    }

    @Override
    public int deleteById(Integer uid) {
        return userMapper.deleteByPrimaryKey(uid);
    }

    @Override
    public int addUser(User user) {
        return userMapper.insert(user);
    }

    @Override
    public boolean updatePwd(Integer uid,String password, String newPassword) {
        UserExample example = new UserExample();
        Criteria cri = example.createCriteria();
        cri.andUidEqualTo(uid);
        User user=userMapper.selectByPrimaryKey(uid);
        if(user.getPassword().equals(password)){
            user.setPassword(newPassword);
            userMapper.updateByExample(user, example);
            return true;
        }
        return false;
    }

}