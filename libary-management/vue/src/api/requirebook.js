import request from "@/utils/request";

/**
 * 查询所有的读者用户列表
 */
export const getList = (condition)=>{
    return request({
        url:'/book/page',
        params:condition
    })
}

// 获取分级分类
export const getCategories=()=>{
    return request({
        url:'/category/tree'
    })
}

// 添加新用户
export const saveCategory=(form)=>{
    return request({
        url:'/book/save',
        method:"post",
        data:form
    })
}

// 根据id查询数据
export const getById=(id)=>{
    return request({
        url:`/book/${id}`
    })
}

// 编辑数据
export const eidtCategory=(form)=>{
    return request({
        url:'/book/update',
        method:'put',
        data:form
    })
}

// 根据id删除数据
export const deleteById=(id)=>{
    return request({
        url:`/book/delete/${id}`,
        method:'delete'
    })
}

