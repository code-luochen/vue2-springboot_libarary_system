package com.example.springboot.controller;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.example.springboot.common.Result;
import com.example.springboot.controller.request.BookRequest;
import com.example.springboot.entity.Admin;
import com.example.springboot.entity.Book;
import com.example.springboot.service.IBookService;
import com.example.springboot.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

@CrossOrigin
@RestController
@Slf4j
@RequestMapping("/book")
public class BookController {

    @Autowired
    IBookService bookService;

    private static final String BASE_FILE_PATH = System.getProperty("user.dir") + "/files/";


    //    =====================================================================================  //
//    文件上传
    @PostMapping("/file/upload")
    public Result uploadFile(MultipartFile file) throws IOException {

        //        String userDir = System.getProperty("user.dir");//获取当前项目的根目录
//       String baseFilePath = userDir + "/files/"; //在根目录下创建出一个文件目录  将这两行代码提到上面
        String originalFilename = file.getOriginalFilename();//创建出一个文件名

        if(StrUtil.isBlank(originalFilename)){  //创建失败
            return Result.error("文件上传失败");
        }

        long flag = System.currentTimeMillis();//创建一个事件戳，保证每一个文件名是唯一的
        String filePath = BASE_FILE_PATH + flag + "_" + originalFilename;//最终的文件路径和名称

        try {
//       如果他的父级文件不存在，那么就创建
            FileUtil.mkParentDirs(filePath);//创建父级目录
            file.transferTo(FileUtil.file(filePath));
            Admin currentAdmin = TokenUtils.getCurrentAdmin();
            String token = TokenUtils.genToken(currentAdmin.getId().toString(), currentAdmin.getPassword(),15);
            String url ="http://localhost:9090/api/book/file/download/" + flag + "?token=" + token;
            if(url.endsWith("png") || url.endsWith("jpg") || url.endsWith("pdf")){
                url += "&play=1";
            }
            return Result.success(url);
        } catch (Exception e) {
            log.info("文件上传失败",e);
        }
        return Result.error("文件上传失败");
    }

    @GetMapping("/file/download/{flag}")
    public void download(@PathVariable String flag, @RequestParam(required = false) String play, HttpServletResponse response) {
        OutputStream os;
        List<String> fileNames = FileUtil.listFileNames(BASE_FILE_PATH);
        String fileName = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");//System.currentTimeMillis()+originalFilename
        try {
            if (StrUtil.isNotEmpty(fileName)) {
                String realName = fileName.substring(fileName.indexOf("_") + 1);
                if ("1".equals(play)) {
                    response.addHeader("Content-Disposition", "inline;filename=" + URLEncoder.encode(realName, "UTF-8"));
                } else {
                    response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(realName, "UTF-8"));
                }
                byte[] bytes = FileUtil.readBytes(BASE_FILE_PATH + fileName);
                os = response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (Exception e) {
            log.error("文件下载失败", e);
        }
    }

    //    ====================================================================================================  //
//    根究id查询用户数据
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        Book book = bookService.getById(id);
        return Result.success(book);
    }


    //    添加新用户
    @PostMapping("/save")
    public Result save(@RequestBody Book obj) {
        bookService.save(obj);
        return Result.success();
    }

    //    根据id删除数据
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        bookService.deleteById(id);
        return Result.success();
    }

    //     编辑修改用户
    @PutMapping("/update")
    public Result update(@RequestBody Book obj) {
        bookService.update(obj);
        return Result.success();
    }

    //    查询全部
    @GetMapping("/list")
    public Result list() {
        List<Book> list = bookService.list();
        return Result.success(list);
    }

    //    分页查询，可以根据条件查询
    @GetMapping("/page")
    public Result page(BookRequest bookRequest) {
        return Result.success(bookService.page(bookRequest));
    }

}
