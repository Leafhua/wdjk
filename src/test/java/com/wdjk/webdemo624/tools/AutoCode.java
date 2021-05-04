package com.wdjk.webdemo624.tools;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: webDemo
 * @description
 * @author: zhuhua
 * @create: 2021-05-03 23:21
 **/
public class AutoCode {
    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        //获得当前项目路径
        String projectPath = System.getProperty("user.dir");
        //设置生成路径
        gc.setOutputDir(projectPath + "/src/main/java");
        //作者名字
        gc.setAuthor("zhuhua");
        //生成后是否打开资源管理器
        gc.setOpen(false);
        //同文件覆盖
        gc.setFileOverride(true);
        //实体属性 Swagger2 注解
        gc.setSwagger2(true);
        //设置业务逻辑类名名称
        gc.setServiceName("%sService");
        //设置日期类型
        gc.setDateType(DateType.ONLY_DATE);
        //会在mapper.xml生成基础的<ResultMap>映射所有字段
        gc.setBaseResultMap(true);
        //将全局配置设置到生成器
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/wdjk?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=Asia/Shanghai");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("zhuhua");
        dsc.setPassword("123456");
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        //模块名
        pc.setModuleName("webdemo624");
        //包名
        pc.setParent("com.wdjk");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        // 如果模板引擎是 velocity
        String templatePath = "/templates/mapper.xml.vm";
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        //把已有的xml生成策略置空
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // 要生成的表名 多个用逗号分割
        //strategy.setInclude("forum_user","forum_ipaddress","forum_topic","forum_topic_action","forum_topic_category","forum_topic_content","forum_topic_reply","forum_user_action","forum_user_dynamic");
        //按前缀生成表名
        strategy.setLikeTable(new LikeTable("forum_"));
        //表名下划线转驼峰
        strategy.setNaming(NamingStrategy.underline_to_camel);
        //列名生成策略 下划线转驼峰
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //Lombok支持
        strategy.setEntityLombokModel(true);
        //Entity类公共字段
        //strategy.setSuperEntityColumns("id");
        //替换表前缀
        strategy.setTablePrefix("forum_");
        //RestFUL
        strategy.setRestControllerStyle(true);
        //url驼峰命名转化为 _
        //strategy.setControllerMappingHyphenStyle(true);

        //自动填充
        TableFill fuCreatetime = new TableFill("fu_createtime", FieldFill.INSERT);
        TableFill ftrCreatetime = new TableFill("ftr_createtime", FieldFill.INSERT);
        TableFill ftLastReplytime = new TableFill("ft_last_replytime", FieldFill.INSERT);
        TableFill ftCreatetime = new TableFill("ft_createtime", FieldFill.INSERT);
        ArrayList<TableFill> tableFills = new ArrayList<>();
        tableFills.add(fuCreatetime);
        tableFills.add(ftCreatetime);
        tableFills.add(ftrCreatetime);
        tableFills.add(ftLastReplytime);
        strategy.setTableFillList(tableFills);

        mpg.setStrategy(strategy);
        mpg.execute();
    }
}
