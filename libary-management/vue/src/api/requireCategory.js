import request from "@/utils/request";


/**
 * 查询所有的读者用户列表
 */
export const getList = (condition)=>{
    return request({
        url:'/category/page',
        params:condition
    })
}

// 添加新用户
export const saveCategory=(form)=>{
    return request({
        url:'/category/save',
        method:"post",
        data:form
    })
}

// 根据id查询数据
export const getById=(id)=>{
    return request({
        url:`/category/${id}`
    })
}

// 编辑数据
export const eidtCategory=(form)=>{
    return request({
        url:'/category/update',
        method:'put',
        data:form
    })
}

// 根据id删除数据
export const deleteById=(id)=>{
    return request({
        url:`/category/delete/${id}`,
        method:'delete'
    })
}

