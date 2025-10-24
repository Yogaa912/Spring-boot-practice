package com.springPractice.Entity;

import java.util.List;
import lombok.Data;

@Data
public class Account {
    
    private Integer id;
    private String loginName;
    private String password;
    private String nickName;
    private Integer age;
    private String location;
    private String role;
    private String avatar;
    
    // 角色
    private List<Role> roleList;
    // 具体的权限
    // 把五张表查出来的结果都放进去, 把上述的内容都存进 Account 里面
    private List<Permission> permissionList;

}