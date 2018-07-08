package com.springboot.swagger.controller;

import com.google.common.collect.Maps;
import com.springboot.swagger.base.Spring4all;
import com.springboot.swagger.base.User;
import com.springboot.swagger.common.ResultBean;
import com.springboot.swagger.service.IUserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lierl
 * @Description:
 * @Date: 2018/7/8 10:54
 */
@RestController
@RequestMapping("/users")
@Api(value = "UserController",description = "用户信息api")
public class UserController {

    @Autowired
    private IUserService userService;

    @ApiOperation(httpMethod = "GET",value="根据用户id获取用户信息",notes = "根据用户id获取用户信息",
            response = User.class,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping("/{id}")
    public User getUser(@ApiParam(value = "用户id",required = true) @PathVariable Integer id){
        return userService.selectByPrimaryKey(id);
    }

    @ApiOperation(httpMethod = "GET", value = "根据条件查询用户信息", response = User.class,
            notes = "实现方式可以用get请求,直接传当前页以及页码大小以及排序等")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name",required = false, value = "姓名",paramType ="query", dataType = "String"),
            @ApiImplicitParam(name = "email",required = false, value = "邮箱",paramType ="query", dataType = "String"),
            @ApiImplicitParam(name = "hasConfirm",required = false, value = "是否确认",paramType ="query", dataType = "Boolean")
    })
    @ResponseBody
    @RequestMapping(value = { "/findByCondition" }, method = { RequestMethod.GET, RequestMethod.POST })
    public ResultBean<List<User>> findByCondition(
            @ApiParam(value = "姓名") @RequestParam(required = false) String name,
            @ApiParam(value = "邮箱") @RequestParam(required = false) String email,
            @ApiParam(value = "是否确认") @RequestParam(required = false) boolean hasConfirm) {
        List<User> users = userService.findByCondition(name,email,hasConfirm);
        return new ResultBean<List<User>>(200,"",users);
    }


    @ApiOperation(value = "获取所有用户信息",response = User.class)
    @GetMapping("/findAll")
    public ResultBean<List<User>> findAll(){
        List<User> users = userService.findAll();
        return new ResultBean<List<User>>(200,"",users);
    }

    @ApiOperation(value = "获取所有用户使用map返回",response = User.class,
            produces = "application/json")
    @GetMapping("/findAllUseMap")
    public ResultBean<Map<String,Object>> findAllUseMap(){
        Map<String,Object> users = new HashMap<String,Object>();
        users.put("users",userService.findAll());
        return new ResultBean<Map<String,Object>>(200,"",users);
    }

    @ApiOperation(value = "获取map数据",responseContainer="Map")
    @GetMapping("/getMapData")
    public ResultBean<Map<String,Object>> getMapData(){
        Map<String,Object> datas = Maps.newHashMap();
        datas.put("name",new User());
        datas.put("email",new Spring4all());
        return new ResultBean<Map<String,Object>>(200,"",datas);
    }

    @ApiOperation(value="更新用户")
    @PostMapping("/update")
    public ResultBean<String> update(@RequestBody @ApiParam(name="用户对象",value = "传入json格式") User user){
        int count = userService.update(user);
        return new ResultBean<String>(200,"","");
    }

    @ApiOperation(value="map参数")
    @PostMapping("/map")
    public ResultBean<String> mapParam(@RequestBody @ApiParam(name="用户对象",value = "传入json格式") Map<String,Object> params){
        return new ResultBean<String>(200,"","");
    }
}
