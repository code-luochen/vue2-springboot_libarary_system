import request from "@/utils/request";

/**
 * 查询所有的读者用户列表
 */
export const getList = (condition)=>{
    return request({
        url:'/borrow/page',
        params:condition
    })
}

// 用来获取所有的图书列表
export const getBooklist=()=>{
    return request({
        url:'/book/list'
    })
}

// 用来获取所有的用户
export const getUserlist=()=>{
    return request({
        url:'/user/list'
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
        url:'/borrow/save',
        method:"post",
        data:form
    })
}

// 根据id查询数据
export const getById=(id)=>{
    return request({
        url:`/borrow/${id}`
    })
}

// 编辑数据
export const eidtCategory=(form)=>{
    return request({
        url:'/borrow/update',
        method:'put',
        data:form
    })
}

// 根据id删除数据
export const deleteById=(id)=>{
    return request({
        url:`/borrow/delete/${id}`,
        method:'delete'
    })
}

// 还书操作
export const saveRetu=(form)=>{
    return request({
        url:'/borrow/saveRetur',
        method:"post",
        data:form
    })
}
