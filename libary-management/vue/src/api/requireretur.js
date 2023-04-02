import request from "@/utils/request";

/**
 * 查询所有的读者用户列表
 */
export const getList = (condition)=>{
    return request({
        url:'/borrow/pageRetur',
        params:condition
    })
}






// 根据id删除数据
export const deleteById=(id)=>{
    return request({
        url:`/borrow/deleteRetur/${id}`,
        method:'delete'
    })
}

