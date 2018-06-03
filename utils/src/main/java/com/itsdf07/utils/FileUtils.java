package com.itsdf07.utils;

import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description ：文件相关操作工具类
 * @Author itsdf07
 * @Time 2018/6/3
 */

public class FileUtils {

    /**
     * 删除垃圾文件完成后（不论成功与否，只要跳出删除）后的回调
     */
    public interface IDelJunkFileCallback {
        /**
         * 删除成功
         *
         * @param files 成功删除的文件；列表
         */
        void delSuccess(ArrayList<File> files);

        /**
         * 删除失败
         *
         * @param files 删除失败的文件列表
         */
        void delFailed(ArrayList<File> files);

    }

    /**
     * 判断是否为【目录】
     *
     * @param path
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isDir(String path) {
        File file = getFileByPath(path);
        return isDir(file);
    }

    /**
     * 判断是否为【目录】
     *
     * @param file
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isDir(File file) {
        return isFileExists(file) && file.isDirectory();
    }

    /**
     * 判断是否为【文件】
     *
     * @param path
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isFile(String path) {
        File file = getFileByPath(path);
        return isFile(file);
    }

    /**
     * 判断是否为【文件】
     *
     * @param file
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isFile(File file) {
        return isFileExists(file) && file.isFile();
    }

    /**
     * 根据【文件路径】获取对应的【文件】
     *
     * @param path
     * @return 文件
     */
    public static File getFileByPath(String path) {
        return TextUtils.isEmpty(path.trim()) ? null : new File(path);
    }

    /**
     * 判断【文件】是否存在
     *
     * @param path
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isFileExists(String path) {
        File file = getFileByPath(path);
        return isFileExists(file);
    }

    /**
     * 判断【文件】是否存在
     *
     * @param file
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isFileExists(File file) {
        return null != file && file.exists();
    }

    /**----------------------------------------------------*/

    /**
     * 创建目标【目录】：如果父级目录不存在，则一同创建<br/>
     * 1、如果目标file已存在，则返回是否为路径<br/>
     * 2、如果目标file不存在，则返回创建目标目录是否成功<br/>
     *
     * @param path
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static boolean createOrExistsDir(String path) {
        File file = getFileByPath(path);
        return createOrExistsDir(file);
    }

    /**
     * 创建目标【目录】：如果父级目录不存在，则一同创建<br/>
     * 1、如果目标file已存在，则返回是否为路径<br/>
     * 2、如果目标file不存在，则返回创建目标目录是否成功<br/>
     *
     * @param file
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static boolean createOrExistsDir(File file) {
//        if (null == file) {
//            return false;
//        }
//        if (file.exists()) {
//            return file.isDirectory();
//        }
//        return file.mkdirs();
        return null != file && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    /**
     * 创建目标【文件】
     * 1、如果目标file已存在，则返回是否为文件<br/>
     * 2、如果file不存在，则优先创建其父目录<br/>
     * 2.1、创建成功，则继续创建文件<br/>
     * 2.2、创建失败，则返回创建文件失败<br/>
     * 2、如果目标file不存在，则返回创建目标目录是否成功<br/>
     *
     * @param path 文件路径
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static boolean createOrExistsFile(String path) {
        File file = getFileByPath(path);
        return createOrExistsFile(file);
    }

    /**
     * 创建目标【文件】
     * 1、如果目标file已存在，则返回是否为文件<br/>
     * 2、如果file不存在，则优先创建其父目录<br/>
     * 2.1、创建成功，则继续创建文件<br/>
     * 2.2、创建失败，则返回创建文件失败<br/>
     * 2、如果目标file不存在，则返回创建目标目录是否成功<br/>
     *
     * @param file 文件
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static boolean createOrExistsFile(File file) {
        if (null == file) {
            return false;
        }
        if (file.exists()) {
            return file.isFile();
        }

        if (!createOrExistsDir(file.getParentFile())) {//创建文件目录
            return false;
        }
        try {
            return file.createNewFile();//创建文件
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 创建【文件】：如果存在则在创建之前删除原有文件
     *
     * @param filePath 文件路径
     * @return {@code true}: 创建成功<br>{@code false}: 创建失败
     */
    public static boolean createFileByDeleteOldFile(String filePath) {
        File file = getFileByPath(filePath);
        return createFileByDeleteOldFile(file);
    }

    /**
     * 创建【文件】：如果存在则在创建之前删除原有文件
     *
     * @param file 文件
     * @return {@code true}: 创建成功<br>{@code false}: 创建失败
     */
    public static boolean createFileByDeleteOldFile(File file) {
        if (null == file) {
            return false;
        }

        // 文件存在并且删除失败返回false
        if (isFile(file)) {
            String filePath = file.getPath() + System.currentTimeMillis();
            File oldFile = new File(filePath);
            if (rename(file, oldFile.getName())) {
                delJunkFileInThread(oldFile, false, null);
            } else {
                return false;
            }

        }
        // 创建目录失败返回false
        if (!createOrExistsDir(file.getParentFile())) {
            return false;
        }
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**----------------------------------------------------*/
    /**
     * 重命名【文件】
     *
     * @param path    文件路径
     * @param newName 新名称
     * @return {@code true}: 重命名成功<br>{@code false}: 重命名失败
     */
    public static boolean rename(String path, String newName) {
        File file = getFileByPath(path);
        return rename(file, newName);
    }

    /**
     * 重命名文件
     *
     * @param file    文件
     * @param newName 新名称
     * @return {@code true}: 重命名成功<br>{@code false}: 重命名失败
     */
    public static boolean rename(File file, String newName) {
        // 文件为空返回false
        if (null == file) {
            return false;
        }
        // 文件不存在返回false
        if (!file.exists()) {
            return false;
        }
        // 新的文件名为空返回false
        if (TextUtils.isEmpty(newName)) {
            return false;
        }
        // 如果文件名没有改变返回true
        if (newName.equals(file.getName())) {
            return true;
        }
        File newFile = new File(file.getParent() + File.separator + newName);
        // 如果重命名的文件已存在返回false
        return !newFile.exists() && file.renameTo(newFile);
    }
    /**----------------------------------------------------*/

    /**
     * 在线程中删除【文件】
     *
     * @param path       需要删除的垃圾文件(单个File)
     * @param needRename 是否需要重命名的方式来删除
     * @param callback   文件删除完成之后的回调，不需要的话可以传入null
     */
    public static void delJunkFileInThread(final String path, final boolean needRename, final IDelJunkFileCallback callback) {
        File file = getFileByPath(path);
        delJunkFileInThread(file, needRename, callback);
    }

    /**
     * 在线程中删除【文件】
     *
     * @param file       需要删除的垃圾文件(单个File)
     * @param needRename 是否需要重命名的方式来删除
     * @param callback   文件删除完成之后的回调，不需要的话可以传入null
     */
    public static void delJunkFileInThread(final File file, final boolean needRename, final IDelJunkFileCallback callback) {
        if (!isFileExists(file)) {
            return;
        }
        ArrayList<File> files = new ArrayList<>();
        files.add(file);
        delJunkFile(files, needRename, callback);
    }

    /**
     * 在线程中删除【文件】
     * */
    /**
     * @param files      需要删除的垃圾文件(多个)
     * @param needRename 是否需要重命名的方式来删除
     * @param callback   文件删除完成之后的回调，不需要的话可以传入null
     */
    public static void delJunkFile(final ArrayList<File> files, final boolean needRename, final IDelJunkFileCallback callback) {
        if (null == files || files.size() <= 0) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<File> delFiles = new ArrayList<File>();
                ArrayList<File> unDelFiles = new ArrayList<File>();
                for (File file : files) {
                    if (isFileExists(file)) {
                        if (needRename) {
                            File fileRename = new File(file.getPath() + System.currentTimeMillis());
                            if (file.renameTo(fileRename)) {
                                file = fileRename;
                            }
                        }
                        boolean success = file.delete();
                        if (success) {
                            delFiles.add(file);
                        } else {
                            unDelFiles.add(file);
                        }
                    }
                }
                if (null != callback) {
                    callback.delSuccess(delFiles);
                    callback.delFailed(unDelFiles);
                }
            }
        }).start();
    }
}
