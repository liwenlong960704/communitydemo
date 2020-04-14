package demo.community.service;

import demo.community.dto.GithubUser;
import demo.community.mapper.UserMapper;
import demo.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;


    public User createOrUpdate(GithubUser githubUser) {
        User user = userMapper.findByAccountId(githubUser.getId());
        String token = UUID.randomUUID().toString();
        if(user == null){
            user = new User();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(githubUser.getAvatar_url());
            user.setBio(githubUser.getBio());
            userMapper.insert(user);
        }else{
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setBio(githubUser.getBio());
            user.setAvatarUrl(githubUser.getAvatar_url());
            user.setGmtModified(System.currentTimeMillis());
            userMapper.update(user);
        }
        return user;
    }
}
