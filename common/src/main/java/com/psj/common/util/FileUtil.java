package com.psj.common.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.UUID;


/**
 * <br>
 * <b>功能：</b>文件工具类<br>
 * <b>作者：</b>Pan.ShiJu<br>
 * <b>日期：</b>2017/4/11 23:27<br>
 *
 * @author Administrator
 */
public class FileUtil {

    private static final String BACKSLASH_REGEX = "\\\\";
    private static final String SLASH = "/";
    private static final String SPOT = ".";
    private static final String COMMA = ",";
    private static final String SEMICOLON = ";";

    private static final String BASE64_START_FLAG = "data:";
    private static final Integer BASE64_SPLIT_ARRAY_LENGTH = 2;

    /**
     * base64字符串转化成图片
     */
    public static String writeImageBase64ToFile(String base64ImgData, String rootPath, String urlPrefix) {
        // 截取前面的标志，逗号分割
        String[] arr = base64ImgData.split(COMMA);
        if (arr.length != BASE64_SPLIT_ARRAY_LENGTH) {
            return null;
        }
        // UUID文件名
        String uuidFileName = getFileName();
        // 文件后缀
        String fileSuffix = SPOT + getImageSuffixByBase64(arr[0]);
        String fileName = uuidFileName + fileSuffix;
        byte[] fileBytes = Base64Util.decode(arr[1]);
        return writeSingleFileToDisk(fileBytes, fileName, rootPath, urlPrefix);
    }


    /**
     * 文件写入磁盘
     *
     * @param file 上传的文件
     * @return 文件访问url
     */
    public static String writeSingleFileToDisk(MultipartFile file, String rootPath, String urlPrefix) {
        // UUID文件名
        String uuidFileName = getFileName();
        // 文件后缀
        String fileSuffix = getFileSuffix(file.getOriginalFilename());
        String fileName = uuidFileName + fileSuffix;
        byte[] fileBytes;
        try {
            fileBytes = file.getBytes();
        } catch (IOException e) {
            return null;
        }
        return writeSingleFileToDisk(fileBytes, fileName, rootPath, urlPrefix);
    }

    /**
     * 文件后缀名
     *
     * @param fileName 原始文件名
     */
    public static String getFileSuffix(String fileName) {
        if(fileName == null){
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(SPOT)).toLowerCase();
    }

    /**
     * 判断是否有图片
     */
    public static boolean checkIsBase64Image(String base64) {
        return base64 != null && base64.startsWith(FileUtil.BASE64_START_FLAG);
    }

    /**
     * 字节数组写入磁盘
     *
     * @param fileBytes 文件字节
     * @param fileName  文件名
     * @param rootPath  文件存放物理全路径
     * @param urlPrefix 文件访问前缀
     * @return 文件访问url
     */
    private static String writeSingleFileToDisk(byte[] fileBytes, String fileName, String rootPath, String urlPrefix) {
        // 相对路径
        String relativePath = getRelativePath(rootPath);
        // 文件存储路径
        String fullPath = rootPath + relativePath + fileName;

        BufferedOutputStream out = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream(new File(fullPath)));
            out.write(fileBytes);
            out.flush();
        } catch (IOException e) {
            return null;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 访问的url
        String fileUrl = urlPrefix + relativePath + fileName;
        // 转换url的分隔符
        return fileUrl.replaceAll(BACKSLASH_REGEX, SLASH);
    }

    /**
     * 获取文件名称
     */
    private static String getFileName() {
        return UUID.randomUUID().toString();
    }

    /**
     * 上传文件的根目录，不存在就创建
     */
    private static String getRelativePath(String rootPath) {
        Calendar calendar = new GregorianCalendar();
        // 图片上传的相对路径
        String relativePath = File.separator +
                calendar.get(Calendar.YEAR) + File.separator +
                (calendar.get(Calendar.MONTH) + 1) + File.separator +
                calendar.get(Calendar.DAY_OF_MONTH) + File.separator;
        // 图片上传的完整路径
        String fullPath = rootPath + relativePath;
        File rootPathFile = new File(fullPath);
        if (!rootPathFile.exists() && !rootPathFile.isDirectory()) {
            if (!rootPathFile.mkdirs()) {
                return File.separator;
            }
        }
        return relativePath;
    }

    /**
     * 获取文件后缀
     *
     * @param base64Str base64字符串
     * @return 文件后缀
     */
    private static String getImageSuffixByBase64(String base64Str) {
        return base64Str.substring(base64Str.lastIndexOf(SLASH) + 1, base64Str.lastIndexOf(SEMICOLON));
    }


}
