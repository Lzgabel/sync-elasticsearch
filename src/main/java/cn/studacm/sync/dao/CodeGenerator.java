package cn.studacm.sync.dao;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * com.shuwen.staging.mapper.CodeGenerator
 * <p>
 *
 * @author lixin
 * @version 1.0
 * @date 2018/11/27 1:46 PM
 */
@Slf4j
public class CodeGenerator {

    /**
     * 公司包名
     */
    private static final String COMPANY_PACKAGE = "cn.studacm.sync";
    /**
     * 项目包名
     */
    private static final String PROJECT_PACKAGE = "";
    /**
     * dao模块路径
     */
    private static final String DAO_MODULE_NAME = "";

    /**
     * 数据库表前缀
     */
    private static final String[] TABLE_PREFIX = new String[]{""};

    /**
     * 代码注释作者
     */
    private static final String AUTHOR = "auto create";

    private static final String DB_HOST = "localhost";
    private static final String DB_PORT = "3306";

    /**
     * 数据库名
     */
    private static final String DB_DATABASE = "oj";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "root";
    private static final String DB_URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_DATABASE
            + "?useUnicode=true&characterEncoding=UTF8&serverTimezone=UTC&useSSL=false";

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/" + DAO_MODULE_NAME  + "src/main/java");
        // XML ResultMap
        gc.setBaseResultMap(true);
        // XML columList
        gc.setBaseColumnList(true);

        gc.setAuthor(AUTHOR);
        gc.setOpen(false);
        mpg.setGlobalConfig(gc);

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        //gc.setServiceName("%sService");
        //gc.setServiceImplName("%sServiceImpl");
        //gc.setControllerName("%sController");

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(DB_URL);
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername(DB_USERNAME);
        dsc.setPassword(DB_PASSWORD);
        dsc.setTypeConvert(new MySqlTypeConvert() {
            /**
             * 自定义数据库表字段类型转换【可选】
             *
             * @param globalConfig globalConfig
             * @param fieldType    数据库字段类型
             * @return IColumnType
             */
            @Override
            public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                log.info(fieldType);
                if (StringUtils.containsIgnoreCase(fieldType, "datetime")) {
                    return DbColumnType.DATE;
                } else if (StringUtils.containsIgnoreCase(fieldType, "decimal")) {
                    return DbColumnType.DOUBLE;
                } else {
                    return super.processTypeConvert(globalConfig, fieldType);
                }
            }
        });
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(COMPANY_PACKAGE);
        //pc.setModuleName(PROJECT_PACKAGE);
        pc.setEntity("entity");
        pc.setMapper("dao");
        pc.setXml("mapper");
        mpg.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass(COMPANY_PACKAGE + PROJECT_PACKAGE + ".entity.base.BaseEntity");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);

        // 需要生成的表
        strategy.setInclude(new String[]{"user", "code", "solution"});
        // 排除生成的表
        //strategy.setExclude(new String[]{"base"});
        // 自定义实体，公共字段
        strategy.setSuperEntityColumns(new String[]{"id", "feature", "status", "deleted", "gmt_create", "gmt_modified"});
        strategy.setControllerMappingHyphenStyle(true);
        // 表前缀
        strategy.setTablePrefix(TABLE_PREFIX);
        mpg.setStrategy(strategy);
        mpg.execute();
    }


}
