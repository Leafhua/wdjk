package com.wdjk.webdemo624;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.talanlabs.avatargenerator.Avatar;
import com.talanlabs.avatargenerator.TriangleAvatar;
import com.talanlabs.avatargenerator.cat.CatAvatar;
import com.wdjk.webdemo624.constant.api.SetConst;
import com.wdjk.webdemo624.entity.User;
import com.wdjk.webdemo624.mapper.UserMapper;
import com.wdjk.webdemo624.service.UserService;
import com.wdjk.webdemo624.utils.StringUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = WebDemoApplication.class)
public class WebDemoApplicationTests  {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assert.assertEquals(1, userList.size());
        userList.forEach(System.out::println);
    }
    @Test
    public void add(){
        User user = new User();
        user.setFuName("瓜皮27号");
        user.setFuEmail("54524744151@qq.com");
        user.setFuPassword("31a2313");

        int result = userMapper.insert(user);
        System.out.println(result);
        System.out.println(user);

    }
    @Test
    public void updateUser(){
        User user = new User();
        user.setFuId(19);
        user.setFuName("瓜皮19号");
        int result = userMapper.updateById(user);
        System.out.println(userMapper.selectById(19));
        System.out.println(result);
    }

    @Test
    public void select(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("fu_email","51242323@qk.com");
        User user =userService.getOne(wrapper);
        if (user != null)
            System.out.println("\"emailMsg\",\"该邮箱已被注册！\"");
//        System.out.println(user);

    }
    @Test
    public void avatar(){
        Avatar avatar = TriangleAvatar.newAvatarBuilder().build();
        File file = new File("./avatars/avatar.png");
        avatar.createAsPngToFile(123456L,file);

    }
    @Test
    public void redisTest(){
        redisTemplate.opsForValue().set("222222@qq.com","code",SetConst.ONE_DAY_MS, TimeUnit.MICROSECONDS);
    }


    @Test
    public void updateWrapperTest(){
        updateWrapper.eq("fu_name","linxi")
                .set("fu_state",1);
        User user = userMapper.selectById(65);
        System.out.println(user);
        System.out.println(userMapper.selectOne(updateWrapper));
        System.out.println();

    }

}
