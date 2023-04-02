import request from "@/utils/request";


/**
 * 查询所有的读者用户列表
 */
export const getList = (condition)=>{
    return request({
        url:'/user/page',
        params:condition
    })
}

// 添加新用户
export const saveUser=(form)=>{
    return request({
        url:'/user/save',
        method:"post",
        data:form
    })
}

// 根据id查询数据
export const getById=(id)=>{
    return request({
        url:`/user/${id}`
    })
}

// 编辑数据
export const eidtUser=(form)=>{
    return request({
        url:'/user/update',
        method:'put',
        data:form
    })
}

// 根据id删除数据
export const deleteById=(id)=>{
    return request({
        url:`/user/delete/${id}`,
        method:'delete'
    })
}

// 充值金额
export const addAccount=(form)=>{
    return request({
        url:'/user/account',
        method:'post',
        data:form
    })
}