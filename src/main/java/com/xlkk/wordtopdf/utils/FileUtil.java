package com.xlkk.wordtopdf.utils;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public final class FileUtil {

    public static final String XLSX_SUFFIX = ".xlsx";

    private FileUtil() {
    }

    /**
     * 文件转比特流
     */
    public static byte[] fileToByte(String filePath) {
        File file = new File(filePath);
        return FileUtil.fileToByte(file);
    }

    /**
     * 文件转比特流
     */
    public static byte[] fileToByte(File file) {
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * 比特流转文件
     */
    public static File byteToFile(byte[] buf, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {

            file = new File(fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(buf);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return file;
    }

    /**
     * 删除临时文件
     */
    public static void deleteTempFile(File file) {
        if (file == null) {
            return;
        }
        boolean delete = file.delete();
        if (!delete) {
            log.error("删除临时文件失败");
        }
    }

    /**
     * 返回带点的后缀
     *
     * @param ext
     * @return
     */
    public static String getExt(String ext) {
        return ext.indexOf(".") == -1 ? "." + ext : ext;
    }

    /**
     * 返回一个临时文件名不带后缀
     *
     * @return
     */
    public static String createTempFileName() {
        String folder = System.getProperty("java.io.tmpdir");
        String filename = folder + UUID.randomUUID();
        return filename;
    }

    /**
     * 截取文件的后缀名 无后缀返回“”
     *
     * @param fileName
     * @return
     */
    public static String cutFileName(String fileName) {
        String ext = StringUtil.cutName(fileName, ".");
        return ext == null ? "" : ext;
    }

    /**
     * 生成一个临时文件
     */
    public static File tempFile(String suffix) {
        String folder = System.getProperty("java.io.tmpdir");
        String fileName = System.currentTimeMillis() + suffix;
        String s = folder + fileName;
        return new File(s);
    }

    /**
     * 生成临时文件和file文件名字一样的
     */
    public static File tempFile(File file) {
        String folder = System.getProperty("java.io.tmpdir");
        String s = folder + file.getName();
        return new File(s);
    }

    /**
     * 创建一个临时目录 + 文件名字符串
     */
    public static String getTempFileName(String fileName) {
        String folder = System.getProperty("java.io.tmpdir");
        return folder + "/" + fileName;

    }

    /**
     * 同名文件命名， 例如 1. a.pdf --> a(1).pdf 2. a --> a(1) 3. a(1).pdf --> a(2).pdf
     * 4. a(1) --> a(2)
     *
     * @param fileName
     * @return
     */
    public static String renameSameFileName(String fileName) {
        String newFileName = null;
        int i = fileName.lastIndexOf(".");
        if (i != -1) { // 有同名文件,有.作为后缀
            String rule = "(.*)\\.(.*)";
            Matcher matcher = Pattern.compile(rule).matcher(fileName);

            if (matcher.find()) {
                String fileNameNoSuffix = matcher.group(1); // 文件名
                String suffix = matcher.group(2);
                String rule2 = "(.*)\\((\\d)\\)";
                Matcher matcher1 = Pattern.compile(rule2).matcher(fileNameNoSuffix);
                if (matcher1.find()) {// 同名的文件名是 文件名(1).pdf 这种
                    String number = matcher1.group(2);
                    Integer newNumber = CalculateUtil.add(Integer.valueOf(number), 1);
                    newFileName = fileNameNoSuffix.replaceAll(rule2, "$1").concat("(" + newNumber + ").")
                            .concat(suffix);
                } else { // 同名的文件名是 文件名.pdf这种 直接添加 (1).pdf
                    newFileName = fileName.replaceAll(rule, "$1(1).$2");
                }
            }
        } else {// 有同名文件,没有以.作为后缀
            String rule2 = "(.*)\\((\\d)\\)";
            Matcher matcher1 = Pattern.compile(rule2).matcher(fileName);
            if (matcher1.find()) {// 同名的文件名是 文件名(1) 这种
                String number = matcher1.group(2);
                Integer newNumber = CalculateUtil.add(Integer.valueOf(number), 1);
                newFileName = fileName.replaceAll(rule2, "$1").concat("(" + newNumber + ")");
            } else {
                newFileName = fileName.concat("(1)");
            }
        }
        return newFileName;
    }

    /**
     * 截取 /sdfs/sdfsdf.pdf 中的sdfsdf.pdf
     *
     * @param filePath
     * @return
     */
    public static String getFileNameByPath(String filePath) {
        String rule = "(.*/)(.*)";
        String fileName = filePath.replaceAll(rule, "$2");
        return fileName;
    }
}
