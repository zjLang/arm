package com.arm.swagger.controller;

import com.arm.swagger.model.ApiResponseEntity;
import com.arm.swagger.model.Role;
import com.arm.swagger.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * api : http://127.0.0.1:8080/demo/v2/api-docs?group=test
 * swagger-ui : http://localhost:8080/demo/swagger-ui.html
 *
 * @author z-ewa
 */
@Api(tags = "测试swagger")
@Controller
@RequestMapping("test")
public class TestController {
    /*@RequestMapping("test")
    @ResponseBody
    @ApiOperation(value = "String模型测试", httpMethod = "GET")
    public User test(@RequestBody User user) {
        return new User();
    }*/

    /*@RequestMapping("test1")
    @ResponseBody
    @ApiOperation(value = "普通类型测试", httpMethod = "GET")
    public int test1(@RequestBody User user) {
        return 1;
    }*/

    @RequestMapping("role")
    @ResponseBody
    @ApiOperation(value = "单一模型测试", httpMethod = "GET")
    public Role role() {
        return new Role();
    }


    @RequestMapping("user")
    @ResponseBody
    @ApiOperation(value = "嵌套模型测试", httpMethod = "GET")
    public com.arm.swagger.model2.User user(String id, String name) {
        return new com.arm.swagger.model2.User();
    }


    @ApiOperation(value = "List类型")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ApiResponseEntity<List<User>> list() {
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        return ApiResponseEntity.success(users);
    }

    @ApiOperation(value = "List类型2")
    @RequestMapping(value = "list2", method = RequestMethod.GET)
    public ApiResponseEntity<List<com.arm.swagger.model2.User>> list2() {
        return null;
    }

    @RequestMapping("fanXing")
    @ResponseBody
    @ApiOperation(value = "普通类型", httpMethod = "GET")
    public ApiResponseEntity<User> fanXing(String id, String name) {
        return ApiResponseEntity.success(new User());
    }

    @ApiOperation(value = "Set类型")
    @RequestMapping(value = "set", method = RequestMethod.GET)
    public ApiResponseEntity<Set<Role>> set() {
        Set<Role> map = new HashSet<>();
        map.add(new Role());
        map.add(new Role());
        map.add(new Role());
        return ApiResponseEntity.success(map);
    }

    @ApiOperation(value = "只有基础类型")
    @RequestMapping(value = "deleteByPk", method = RequestMethod.GET)
    public ApiResponseEntity<Boolean> deleteByPk() {
        return ApiResponseEntity.success(Boolean.TRUE);
    }


    /*@ApiOperation(value = "Map基本类型")
    @RequestMapping(value = "baseMap", method = RequestMethod.GET)
    public Map<String, Role> baseMap() {
        Map<String, Role> map = new HashMap<>();
        map.put("1", new Role());
        map.put("2", new Role());
        map.put("3", new Role());
        return map;
    }


    @ApiOperation(value = " Map类型", notes = "ApiResponseEntity<Map<String, Integer>>")
    @RequestMapping(value = "mapOnly", method = RequestMethod.GET)
    public ApiResponseEntity<Map<String, Integer>> mapOnly() {
        Map<String, Integer> map = new HashMap<>();
        map.put("1", 1);
        map.put("2", 2);
        map.put("3", 4);
        return ApiResponseEntity.success(map);
    }

    @ApiOperation(value = "Map类型", notes = "ApiResponseEntity<Map<String, Role>>")
    @RequestMapping(value = "map", method = RequestMethod.GET)
    public ApiResponseEntity<Map<String, Role>> map() {
        Map<String, Role> map = new HashMap<>();
        map.put("1", new Role());
        map.put("2", new Role());
        map.put("3", new Role());
        return ApiResponseEntity.success(map);
    }


    @ApiOperation(value = "复杂类型复杂T", notes = "ApiResponseEntity<Map<Map<Long, User>, List<Map<String, Role>>>>")
    @RequestMapping(value = "complex", method = RequestMethod.GET)
    public ApiResponseEntity<Map<Map<Long, User>, List<Map<String, Role>>>> complex() {
        Map<Long, User> m1 = new HashMap<>(8);
        m1.put(1l, new User());

        Map<String, Role> m2 = new HashMap<>(8);
        m2.put("1", new Role());

        List<Map<String, Role>> list = new ArrayList<>();
        list.add(m2);

        Map<Map<Long, User>, List<Map<String, Role>>> map = new HashMap(8);
        map.put(m1, list);
        return ApiResponseEntity.success(map);
    }
*/
    public static void main(String[] args) throws JsonProcessingException {
        Map<String, Role> map = new HashMap<>();
        map.put("1", new Role());
        map.put("2", new Role());
        map.put("3", new Role());
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(map));
    }
}
