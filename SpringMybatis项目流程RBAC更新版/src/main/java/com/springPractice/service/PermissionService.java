package com.springPractice.service;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springPractice.Entity.Account;
import com.springPractice.Entity.Permission;
import com.springPractice.Entity.PermissionChangeDto;
import com.springPractice.Entity.PermissionCreateDto;
import com.springPractice.mapper.PermissionExample;
import com.springPractice.mapper.PermissionMapper;

import jakarta.validation.Valid;

@Service
public class PermissionService {

	@Autowired
	PermissionMapper permissionMapper;
	
	// 获取当前类的 Logger 对象
    // 这是标准的写法，getLogger() 的参数通常是当前类的 Class 对象
    private static final Logger log = LoggerFactory.getLogger(PermissionService.class);
	
	private static final int NAVIGATE_PAGES_COUNT = 5;
	// Thymeleaf 分页功能
	public PageInfo<Permission> findByPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		PermissionExample example = new PermissionExample();
		// 添加这一行来保证排序稳定
	    example.setOrderByClause("id asc"); 
	    // 推荐使用主键或有唯一索引的列来排序
		// 使用 PageInfo 包装查询结果，它会包含所有分页信息
		List<Permission> list = permissionMapper.selectByExample(example);
	    PageInfo<Permission> pageInfo = new PageInfo<>(list,NAVIGATE_PAGES_COUNT);
		return pageInfo;
	}
	public boolean changePermission(@Valid PermissionChangeDto permissionDto, Account operator) {
		
		// 使用 Logger 对象记录日志
        log.debug("开始处理 doSomething 方法，输入参数: {}", permissionDto); // DEBUG 级别
		
		// 1. (推荐) 使用 selectByPrimaryKey 验证 ID 是否存在
	    if (permissionMapper.selectByPrimaryKey(permissionDto.getId()) == null) {
	        throw new RuntimeException("要修改的权限不存在, ID: " + permissionDto.getId()); 
	    }
		
		// 2. 创建一个只包含 DTO 数据的实体对象
		Permission permissionToUpdate = new Permission();
		permissionToUpdate.setId(permissionDto.getId()); // ID 必须设置，用于 WHERE 条件
		permissionToUpdate.setName(permissionDto.getName());
		permissionToUpdate.setUni(permissionDto.getUni());
		permissionToUpdate.setC(permissionDto.getC());
		permissionToUpdate.setR(permissionDto.getR());
		permissionToUpdate.setU(permissionDto.getU());
		permissionToUpdate.setD(permissionDto.getD());
		
		// updateByPrimaryKey 是什么意思
		// 3. 执行选择性更新
	    int updatedRows = permissionMapper.updateByPrimaryKeySelective(permissionToUpdate);
	    // (可选) 检查更新是否真的成功执行了 (虽然 ID 存在时通常都会成功)
	    if (updatedRows == 0) {
	       // 可能并发删除了，或者其他原因
	       log.warn("输入参数为空或无效。"); // WARN 级别
	       throw new RuntimeException("更新权限失败, 可能已被删除, ID: " + permissionDto.getId());
	    }

	    // 4. 记录日志
	    log.info("用户 '{}' 修改了权限 ID '{}'...", operator, permissionDto.getId());	// INFO 级别
	    return true;
	}
	public  ResponseEntity<?> deleteById(Integer id) {
		int row = permissionMapper.deleteByPrimaryKey(id);
		if(row == 1) {
			return ResponseEntity.ok(Map.of("message", "权限删除成功"));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND) 
                    .body(Map.of("success", false, "error", "删除失败：未找到对应的权限记录。"));
		}
	}
	
	/**
     * 创建一个新的权限记录
     * @param permissionDto 包含新权限信息的 DTO 对象 (不含 ID)
     * @param operator 执行操作的用户名 (用于日志记录)
     * @return 创建成功后的 Permission 对象 (包含了数据库生成的 ID)
     */
    @Transactional // 数据库写入操作，强烈建议添加事务管理
    public Permission createPermission(@Valid PermissionCreateDto permissionDto, Account operator) {
        
        // 1. (可选但推荐) 业务逻辑校验：
        // 比如，检查是否已存在同名或同 URI 的权限？
		PermissionExample example = new PermissionExample();
		example.createCriteria().andNameEqualTo(permissionDto.getName());
		if (permissionMapper.countByExample(example) > 0) {
			throw new RuntimeException("权限名称 '" + permissionDto.getName() + "' 已存在！");
		}
        // (类似地检查 URI)

        // 2. 将 DTO 转换为 实体类 (Entity)
        Permission permissionToCreate = new Permission();
        permissionToCreate.setName(permissionDto.getName());
        permissionToCreate.setUni(permissionDto.getUni());
        permissionToCreate.setC(permissionDto.getC());
        permissionToCreate.setR(permissionDto.getR());
        permissionToCreate.setU(permissionDto.getU());
        permissionToCreate.setD(permissionDto.getD());
        // 注意：绝对不要在这里 set Id！

        // 3. 调用 Mapper 执行插入操作
        // 使用 MyBatis Generator 生成的 standard insert 方法
        // 这个方法执行后，MyBatis 会自动将数据库生成的 ID 回填到 newPermission 对象的 id 字段中
        // (前提是你的 Mapper XML 中 <insert> 标签配置了 useGeneratedKeys="true" keyProperty="id")
        permissionMapper.insert(permissionToCreate); 

        // 4. 记录成功日志 (现在可以获取到生成的 ID 了)
        log.info("用户 '{}' 成功创建了新权限。ID: {}, 名称: '{}', URI: '{}'", 
                 operator, 
                 permissionToCreate.getId(), // <-- 这里可以拿到 ID 了！
                 permissionToCreate.getName(),
                 permissionToCreate.getUni());

        // 5. 返回创建成功并包含 ID 的实体对象
        return permissionToCreate;
    }
}