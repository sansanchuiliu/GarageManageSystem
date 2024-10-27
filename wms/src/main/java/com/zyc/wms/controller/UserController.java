package com.zyc.wms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyc.wms.common.QueryPageParam;
import com.zyc.wms.common.Result;
import com.zyc.wms.entity.User;
import com.zyc.wms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zyc
 * @since 2024-10-23
 */
@RestController
@RequestMapping("/user")
public class UserController {
   @Autowired
    private UserService userService;
   @GetMapping("/list")
    public List<User> list(){
        return userService.list();
    }

    //新增
    @PostMapping("/save")
    public boolean save(@RequestBody User user){
       return userService.save(user);
    }
    //修改
    @PostMapping("/mod")
    public boolean mod(@RequestBody User user){
        return userService.updateById(user);
    }
    //新增或修改
    @PostMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody User user){
        return userService.saveOrUpdate(user);
    }
    //删除
    @GetMapping("/delete")
    public boolean delete(Integer id){
       return userService.removeById(id);
    }
    //查询（模糊、匹配）
    @PostMapping("/listP")
    public Result listP(@RequestBody User user){
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        if(StringUtils.isNotBlank(user.getName())){
            lambdaQueryWrapper.like(User::getName,user.getName());
        }

        return Result.suc(userService.list(lambdaQueryWrapper));
    }
    @PostMapping("/listPage")
    public List<User> listPage(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String name = (String)param.get("name");
        System.out.println("name==="+(String)param.get("name"));
        /*System.out.println("no==="+(String)param.get("no"));*/
        /*LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(User::getName,user.getName());

        return userService.list(lambdaQueryWrapper);*/

        Page<User> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.like(User::getName,name);


        IPage result = userService.page(page,lambdaQueryWrapper);

        System.out.println("total=="+result.getTotal());

        return result.getRecords();
    }
    @PostMapping("/listPageC")
    public List<User> listPageC(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String name = (String)param.get("name");
        System.out.println("name==="+(String)param.get("name"));



        Page<User> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.like(User::getName,name);


        //IPage result = userService.pageC(page);
        IPage result = userService.pageCC(page,lambdaQueryWrapper);

        System.out.println("total=="+result.getTotal());

        return result.getRecords();
    }

    @PostMapping("/listPageC1")
    public Result listPageC1(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String name = (String)param.get("name");
        System.out.println("name==="+(String)param.get("name"));



        Page<User> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.like(User::getName,name);


        //IPage result = userService.pageC(page);
        IPage result = userService.pageCC(page,lambdaQueryWrapper);

        System.out.println("total=="+result.getTotal());

        return Result.suc(result.getRecords(),result.getTotal());
    }
}
