import request from "@/utils/request";

// 登录
export const login=(admin)=>{
    return request({
        url:'/admin/login',
        method:"post",
        data:admin
    })
}

/**
 * 查询所有的读者用户列表
 */
export const getList = (condition)=>{
    return request({
        url:'/admin/page',
        params:condition
    })
}

// 添加新用户
export const saveAdmin=(form)=>{
    return request({
        url:'/admin/save',
        method:"post",
        data:form
    })
}

// 根据id查询数据
export const getById=(id)=>{
    return request({
        url:`/admin/${id}`
    })
}

// 编辑数据
export const eidtAdmin=(form)=>{
    return request({
        url:'/admin/update',
        method:'put',
        data:form
    })
}

// 根据id删除数据
export const deleteById=(id)=>{
    return request({
        url:`/admin/delete/${id}`,
        method:'delete'
    })
}

// 修改密码
export const changePass = (form)=>{
    return request({
        url:'/admin/password',
        method:'put',
        data:form
    })
}