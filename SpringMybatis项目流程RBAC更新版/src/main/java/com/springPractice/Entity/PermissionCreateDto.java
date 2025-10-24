package com.springPractice.Entity;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class PermissionCreateDto {
	// 创建 DTO 不需要 permissionId, 而更新 DTO 需要, 并且需要验证 id 不能为空
    private String uni;
    @NotEmpty(message = "权限名称不能为空")
    private String name;
    
//  直接对应 Permission 实体的 c, r, u, d
    private Boolean c = false; // 建议给一个默认值 false
    private Boolean r = false;
    private Boolean u = false;
    private Boolean d = false;
}