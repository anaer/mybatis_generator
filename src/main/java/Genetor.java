import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

public class Genetor {
    public static void main(String[] args) {
	generateMbgConfiguration();

    }

    private static void generateMbgConfiguration() {
	/*
	 * Mybatis自带Generator工具生成相应东西
	 */
	List<String> warnings = new ArrayList<String>();
	boolean overwrite = true;
	File configFile = new File("src/main/resources/generatorConfig.xml");
	System.out.println(configFile.getAbsolutePath());
	if (!configFile.exists()) {
	    System.out.println("配置文件不存在!");
	    return;
	}
	ConfigurationParser cp = new ConfigurationParser(warnings);
	Configuration config = null;
	try {
	    config = cp.parseConfiguration(configFile);
	} catch (IOException e) {
	    e.printStackTrace();
	} catch (XMLParserException e) {
	    e.printStackTrace();
	}
	DefaultShellCallback callback = new DefaultShellCallback(overwrite);
	try {
	    MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
	    myBatisGenerator.generate(null);
	} catch (InvalidConfigurationException e) {
	    e.printStackTrace();
	} catch (SQLException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}

	System.out.println("生成Mybatis配置成功！");
    }
}