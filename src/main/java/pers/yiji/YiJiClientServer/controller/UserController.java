package pers.yiji.YiJiClientServer.controller;

import pers.yiji.YiJiClientServer.dao.UserMapper;
import pers.yiji.YiJiClientServer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping(value="/users")     // 通过这里配置使下面的映射都在/users下
public class UserController {

    @SuppressWarnings("all")
    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value="/", method = RequestMethod.GET)
    public List<User> getUserList() {
        // 处理"/users/"的GET请求，用来获取用户列表
        // 还可以通过@RequestParam从页面中传递参数来进行查询条件或者翻页信息的传递
        return new ArrayList<>(userMapper.findAll());
    }

    @PostMapping(value="/")
    public String postUser(@Valid User user, BindingResult result) {
        // 处理"/users/"的POST请求，用来创建User
        // 除了@ModelAttribute绑定参数之外，还可以通过@RequestParam从页面中传递参数
        userMapper.insert(user.getName(), user.getPwd(), user.getAge());
        return "success";
    }
//
//    @GetMapping(value="/{id}")
//    public User getUser(@PathVariable Long id) {
//        // 处理"/users/{id}"的GET请求，用来获取url中id值的User信息
//        // url中的id可通过@PathVariable绑定到函数的参数中
//        return users.get(id);
//    }
//
//    @PutMapping(value="/{id}")
//    public String putUser(@PathVariable Long id, @ModelAttribute User user) {
//        // 处理"/users/{id}"的PUT请求，用来更新User信息
//        User u = users.get(id);
//        u.setName(user.getName());
//        u.setAge(user.getAge());
//        users.put(id, u);
//        return "success";
//    }
//
//    @DeleteMapping(value="/{id}")
//    public String deleteUser(@PathVariable Long id) {
//        // 处理"/users/{id}"的DELETE请求，用来删除User
//        users.remove(id);
//        return "success";
//    }

}