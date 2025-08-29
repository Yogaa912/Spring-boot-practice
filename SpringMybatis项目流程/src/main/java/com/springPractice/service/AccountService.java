package com.springPractice.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springPractice.Entity.Account;
import com.springPractice.Entity.RegisterData;
import com.springPractice.mapper.AccountExample;
import com.springPractice.mapper.AccountMapper;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;

@Service
public class AccountService {
	@Value("${file.upload-dir}")
	private String uploadDir;
	
	private Path uploadPath;
	@PostConstruct
	public void init() throws IOException {
		this.uploadPath = Paths.get(uploadDir);
		if(!Files.exists(uploadPath)) {
			// 如果不存在, 则创建
			Files.createDirectories(uploadPath);
		}
		System.out.println("头像上传目录已初始化为：" + this.uploadPath.toString());
	}
	
	// 比如我们希望导航栏只显示 5 个页码
	// 将其定义为类级别的静态常量
    private static final int NAVIGATE_PAGES_COUNT = 5; 

	@Autowired
	AccountMapper accMapper;
	public Account findByLoginNameAndPassword(String loginName, String password) {
		AccountExample example = new AccountExample();
		example.createCriteria().andLoginNameEqualTo(loginName).andPasswordEqualTo(password);
		List<Account> list = accMapper.selectByExample(example);
		return list.size() == 0 ? null : list.get(0);
	}
	public List<Account> findAll() {
		AccountExample example = new AccountExample();
		List<Account> list = accMapper.selectByExample(example);
		return list;
	}
	public PageInfo<Account> findByPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		AccountExample example = new AccountExample();
		// 添加这一行来保证排序稳定
	    example.setOrderByClause("id asc"); 
	    // 推荐使用主键或有唯一索引的列来排序
		// 使用 PageInfo 包装查询结果，它会包含所有分页信息
		List<Account> list = accMapper.selectByExample(example);
	    PageInfo<Account> pageInfo = new PageInfo<>(list,NAVIGATE_PAGES_COUNT);
		return pageInfo;
	}
	
	/**
	 * @param id
	 * 1. 要提示用户这件事的重要性, 删了就没有了
	 * 2. 软删除, 通过删除标记来完成操作, 数据永久存在; update这件事也是只做增加不做改, 历史数据表 -> 文本log
	 * 3. 
	 */
	public void deleteById(Integer id) {
		// number of affected rows = SQL 语句在数据库中影响的行数
		int row = accMapper.deleteByPrimaryKey(id);
		if(row == 1) {
			// 成功删除了记录 return RespStat.build(200);
			System.out.println("成功删除了记录: " + id);
		} else {
			// 未删除记录 return RespStat.build(500, "删除失败");
			System.out.println("未能删除记录: " + id);
		}
	}
	public Account findByLoginName(String loginName) {
		AccountExample example = new AccountExample();
		example.createCriteria().andLoginNameEqualTo(loginName);
		List<Account> list = accMapper.selectByExample(example);
		return list.size() == 0 ? null : list.get(0);
	}
	public void createAccount(String loginName, String password) {
		Account row = new Account();
		row.setLoginName(loginName);
		row.setPassword(password);
		int num = accMapper.insert(row);
		System.out.println("Creating account: " + num);
	}
	public Account createAccount(@Valid RegisterData registerData) throws Exception {
		// 1. 校验账号是否存在
		Account existingAcc = findByLoginName(registerData.getLoginName());
		if(existingAcc != null) {
			// 存在
			System.out.println("【校验账号是否存在】存在 -- 创建失败");
			throw new Exception("该用户名已被注册");
		} else {
			// 数据持久化
			Account row = new Account();
			row.setLoginName(registerData.getLoginName());
			row.setPassword(registerData.getPassword());
			int num = accMapper.insert(row);	// 使用 insertSelective 更灵活
			System.out.println("Creating account: " + num);
			return row;
		}
	}
	public boolean changePassword(Integer accountId, String oldPassword, String newPassword) {
		// 找到账号, 这里也应该保留旧密码在某个位置, 然后把新密码更新上去
		AccountExample example = new AccountExample();
		example.createCriteria().andIdEqualTo(accountId).andPasswordEqualTo(oldPassword);
		List<Account> list = accMapper.selectByExample(example);
		// 这里的 list 应该只有一个元素
		if(list.size() != 1) {
			// 有错误, 比如说是0 说明没找到, 比如说是2, 那就有未知问题
			return false;
		}
		Account row = list.get(0);
		row.setPassword(newPassword);
		accMapper.updateByPrimaryKey(row);
		return true;
	}
	public boolean updateAvatarUrl(Integer accountId, String avatarMsg) {
		// 需要在这里再检查一遍登录状态吗
		AccountExample example = new AccountExample();
		example.createCriteria().andIdEqualTo(accountId);
		List<Account> list = accMapper.selectByExample(example);
		if(list.size() != 1) {
			// 有错误, 比如说是0 说明没找到, 比如说是2, 那就有未知问题
			return false;
		}
		Account row = list.get(0);
		row.setAvatar(avatarMsg);
		accMapper.updateByPrimaryKey(row);
		return true;
	}
	public String saveAvatar(MultipartFile file) throws IOException {
		try {
			// 1. 获取文件名称 -> 文件扩展名
			String originalFileName = file.getOriginalFilename();
			String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
			
			// 2. 生成唯一的文件名, 避免重复
			String uniqueFileName = UUID.randomUUID().toString() + extension;
			
			// 3. 解析最终的文件路径
	        Path filePath = this.uploadPath.resolve(uniqueFileName);
	        
	        // 4. 保存文件
	        file.transferTo(filePath.toFile());
	        
	        // 5. 返回可以存储在数据库中的相对URL路径
	        // 这个 URL 路径需要被 Spring Boot 映射到我们的外部目录
			return "/uploads/" + uniqueFileName;
		} catch (IOException e) {
            e.printStackTrace();
            return "redirect:/error";
        }
	}

}