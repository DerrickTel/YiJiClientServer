package pers.yiji.YiJiClientServer.controller;

import org.springframework.validation.ObjectError;
import pers.yiji.YiJiClientServer.dao.UserMapper;
import pers.yiji.YiJiClientServer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pers.yiji.YiJiClientServer.util.MessageCode;
import pers.yiji.YiJiClientServer.util.Utils;

import javax.validation.Valid;
import java.util.*;

import static pers.yiji.YiJiClientServer.util.Utils.md5;

@RestController
@RequestMapping(value="/user")     // 通过这里配置使下面的映射都在/user下
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

    @CrossOrigin
    @PostMapping(value="/login")
    public Map<String, Object> UserLogin(@RequestBody Map<String, Object> getObject) {
        Map<String, Object> retMap = new HashMap<>();
        String userName = (String) getObject.get("userName");
        String userNameKey = (String) getObject.get("userNameKey");
        User loginUser = userMapper.findByName(userName);
        if(loginUser == null){
            retMap.put("errorCode", MessageCode.PARAM_ERROR);
            retMap.put("msg", "账号不存在");
            return retMap;
        } else {
            if( !loginUser.getPwd().equals( md5(userNameKey) ) ) {
                retMap.put("errorCode", MessageCode.PARAM_ERROR);
                retMap.put("msg", "密码错误");
            }
        }
        retMap.put("errorCode", MessageCode.SUCCESS);
        retMap.put("msg", "");
        return retMap;
    }

    @PostMapping(value="/add")
    public Map<String, Object> addUser(@Valid @RequestBody User user, BindingResult result) {
        // 处理"/users/"的POST请求，用来创建User
        // 除了@ModelAttribute绑定参数之外，还可以通过@RequestParam从页面中传递参数
        Map<String, Object> retMap = new HashMap<>();
        if(result.hasErrors()){
            List<ObjectError> ls=result.getAllErrors();
            ls.forEach(item -> {
                retMap.put("errorMsg", item.getDefaultMessage());
            });
            retMap.put("errorCode", MessageCode.PARAM_ERROR);
            return retMap;
        }
        userMapper.insert( user.getName(), md5( user.getPwd() ), user.getAge());
        retMap.put("errorCode", MessageCode.SUCCESS);
        retMap.put("errorMsg", "创建成功");
        return retMap;
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