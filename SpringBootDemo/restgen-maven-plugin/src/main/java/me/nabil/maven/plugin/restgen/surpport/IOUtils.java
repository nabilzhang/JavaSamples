package me.nabil.maven.plugin.restgen.surpport;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Collection;

/**
 * 自己实现IOUtils,进行IO操作
 * <p/>
 * 不引用common-io等jar,作为底层插件,尽量减少插件依赖树
 *
 * @author zhangbi
 */
public class IOUtils {
    /**
     * 循环创建目录,如果发现父级目录不存在也会创建
     *
     * @param dir 详细目录对象
     */
    public static void mkdir(File dir) {
        while (!dir.getParentFile().exists()) {
            mkdir(dir);
        }

        if (!dir.exists()) {
            dir.mkdir();
        }

    }

    /**
     * 写入数据并关闭流，如果<code>file</code>为<code>null</code>或者<code>datas</code>为<code>null</code>，则返回<code>false</code>。
     *
     * @param file        文件对象
     * @param datas       数据集合
     * @param charsetName 编码格式
     * @return 是否成功
     * @throws IOException
     */
    public static boolean writeLinesAndClose(File file, Collection<?> datas, String charsetName)
            throws IOException {
        if (file == null || datas == null || datas.isEmpty()) {
            return false;
        }

        // 创建目录
        mkdir(file.getParentFile());

        Writer writer = null;
        try {
            writer = getWriter(file.getAbsolutePath(), false, charsetName);
            for (Object data : datas) {
                writer.append(data.toString()).append("\n");
            }
            return true;
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * 获取<code>Writer</code>
     *
     * @param filePath    文件路径
     * @param append      是否追加
     * @param charsetName 编码格式
     * @return <code>Writer</code>
     * @throws IOException
     */
    private static Writer getWriter(String filePath, boolean append, String charsetName) throws IOException {
        OutputStream output = new FileOutputStream(filePath, append);

        Writer writer = new OutputStreamWriter(output, charsetName);
        return new BufferedWriter(writer);
    }
}
