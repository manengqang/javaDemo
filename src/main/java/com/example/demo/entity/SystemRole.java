package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

// id (角色ID)	160
// name (角色名称)	测试角色1
// code (角色权限字符串)	test_role1
// sort (显示顺序)	1
// data_scope (数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）)	1
// data_scope_dept_ids (数据范围(指定部门数组))	
// status (角色状态（0正常 1停用）)	0
// type (角色类型)	1
// remark (备注)	测试角色1备注
// creator (创建者)	1
// create_time (创建时间)	2026-04-09 15:14:58
// updater (更新者)	
// update_time (更新时间)	2026-04-09 15:14:58
// deleted (是否删除)	0
// tenant_id (租户编号)

@TableName("system_role")
public class SystemRole {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String code;
    private Integer sort;
    private Integer data_scope;
    private String data_scope_dept_ids;
    private Integer status;
    private Integer type;
    private String remark;
    private Long creator;
    private String create_time;
    private Long updater;
    private String update_time;
    private Integer deleted;
    private Long tenant_id;

    // getter & setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public Integer getSort() { return sort; }
    public void setSort(Integer sort) { this.sort = sort; }

    public Integer getDataScope() { return data_scope; }
    public void setDataScope(Integer dataScope) { this.data_scope = dataScope; }

    public String getDataScopeDeptIds() { return data_scope_dept_ids; }
    public void setDataScopeDeptIds(String dataScopeDeptIds) { this.data_scope_dept_ids = dataScopeDeptIds; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public Integer getType() { return type; }
    public void setType(Integer type) { this.type = type; }

    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }

    public Long getCreator() { return creator; }
    public void setCreator(Long creator) { this.creator = creator; }

    public String getCreateTime() { return create_time; }
    public void setCreateTime(String createTime) { this.create_time = createTime; }

    public Long getUpdater() { return updater; }
    public void setUpdater(Long updater) { this.updater = updater; }

    public String getUpdateTime() { return update_time; }
    public void setUpdateTime(String updateTime) { this.update_time = updateTime; }

    public Integer getDeleted() { return deleted; }
    public void setDeleted(Integer deleted) { this.deleted = deleted; }

    public Long getTenantId() { return tenant_id; }
    public void setTenantId(Long tenantId) { this.tenant_id = tenantId; }
}