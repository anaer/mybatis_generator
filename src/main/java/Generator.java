import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.api.ShellCallback;
import org.mybatis.generator.api.VerboseProgressCallback;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

public class Generator {

    public static void main(String[] args) {
        cleanOldOutput();
        generateMbgConfiguration();
    }

    /**
     * 清理旧的输出.
     */
    private static void cleanOldOutput() {
        String[] paths = { "src/main/java/com", "src/main/resources/com" };
        for (String path : paths) {
            File file = new File(path);
            deleteFile(file);
        }
    }

    /**
     * 删除文件.
     *
     * @param file 文件
     */
    private static void deleteFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File temp : files) {
                deleteFile(temp);
            }
        }
        file.delete();
    }

    /**
     * Mybatis自带Generator工具生成相应东西
     */
    private static void generateMbgConfiguration() {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        File configFile = new File("src/main/resources/generatorConfig-myweb.xml");
        System.out.println(configFile.getAbsolutePath());
        if (!configFile.exists()) {
            System.out.println("配置文件不存在!");
            return;
        }
        try {
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(configFile);
            ShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            // 处理回调 打印日志
            ProgressCallback proCallBack = new VerboseProgressCallback();
            myBatisGenerator.generate(proCallBack);
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (XMLParserException e) {
            e.printStackTrace();
        }

        for (String error : warnings) {
            System.out.println(error);
        }

        System.out.println("生成Mybatis配置成功！");
    }
}
