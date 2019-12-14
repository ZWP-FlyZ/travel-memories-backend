package com.zwp.travelmemories.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;


/**
 * @program: travelmemories
 * @description: 存储服务
 * @author: zwp-flyz
 * @create: 2019-12-13 16:10
 * @version: v1.0
 **/
@Service
public class StorageService {


    private final static Logger LOGGER = LoggerFactory.getLogger(StorageService.class);

    @Value("${storageBasePath}")
    String basePath;

    @Autowired
    ApplicationContext ctx;


    /**
     * 保存文件,
     * @param filename 文件名
     * @param uId 用户id
     * @param is 输入流 ,在使用完时不关闭输入流
     * @return
     * @throws IOException
     */
    public boolean saveFile(String filename,Long uId,InputStream is)
            throws IOException{

        Assert.notNull(filename, "filename is null");
        Assert.notNull(uId, "uId is null");
        Assert.notNull(is, "FileInputStream is null");
        LOGGER.debug("save file filename:{} uId:{}",filename,uId);
        Path p = checkOrCreateUserPath(uId);
        Path fp = p.resolve(filename);
        Files.createFile(fp);
        Files.copy(is, fp, StandardCopyOption.REPLACE_EXISTING);

        return true;
    }

    /**
     *
     * @param filename
     * @param uId
     * @return 返回文件resource ，若文件不存在则放回null
     * @throws FileNotFoundException
     */
    public Resource getFile(String filename,Long uId) throws FileNotFoundException {
//        Path p = Paths.get(basePath + File.separator +
//                            uId+File.separator+filename);
        Resource res = null;
        String path = "file:"+basePath + File.separator +
                uId+File.separator+filename;
        try {
            res = ctx.getResource(path);
        }catch (Exception e){
            LOGGER.error("get file:[{}] failure!"+e.getMessage(),filename);
        }
        return res;
    }







    /**
     * 检测用户文件路径是否存在，如果不存在则创建
     * @param uId
     * @return
     * @throws IOException
     */
    private Path checkOrCreateUserPath(Object uId) throws IOException {
        Path p = Paths.get(basePath + File.separator + uId);
        if(!Files.exists(p)) Files.createDirectories(p);
        return p;
    }

    /**
     * 检测并返回用户路径，若不存在路径则返回null
     * @param username
     * @return
     * @throws IOException
     */
    private Path checkOrGetUserPath(String username)  {
        Path p = Paths.get(basePath + File.separator + username);
        if(!Files.exists(p)) return null;;
        return p;
    }


    /**
     * 返回所有文件的内存数量总和，单位字节
     * @param all
     * @return
     */
    private static long getFilesSize(File[] all) {
        return Arrays.stream(all).
                filter(f->!f.isDirectory()).
                mapToLong(File::length).sum();
    }



}
