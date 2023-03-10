package com.knifez.fridaybootgenerator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * @author zhang
 */
@Slf4j
public class MybatisPlusGenerator {
    /**
     * 包路径
     */
    private static String packagePath;

    private static final String PROJECT_NAME = "fridayboot";

    private static final String DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/fridayboot";
    private static final String DATABASE_PASSWORD = "fridayboot";
    /**
     * 表列表
     */
    private static List<String> tableList = new ArrayList<>();

    public static void main(String[] args) {
        FastAutoGenerator.create(DATABASE_URL, PROJECT_NAME, DATABASE_PASSWORD)
                .globalConfig((scanner, builder) ->
                        builder.author("KnifeZ")
                                .enableSwagger()
                                .outputDir(getOutputPath(scanner.apply("请输入模块名称"))
                                ))
                .packageConfig((scanner, builder) -> builder.parent(packagePath))
                .strategyConfig((scanner, builder) -> builder.addInclude(getTables(scanner.apply("请输入表名，多个表名用,隔开")))
                        .entityBuilder().enableLombok()
                        .controllerBuilder().enableRestStyle())
                .templateConfig(builder -> builder.disable(TemplateType.CONTROLLER))
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
        boolean isGenerateController = false;
        Scanner sc = new Scanner(System.in);
        String str;
        log.info("是否生成Controller（yes/no）：");
        str = sc.nextLine();
        if ("yes".equals(str)) {
            isGenerateController = true;
        }
        if (isGenerateController) {
            FastAutoGenerator.create(DATABASE_URL, PROJECT_NAME, DATABASE_PASSWORD)
                    .globalConfig(builder -> builder.author("KnifeZ")
                            .enableSwagger()
                            .outputDir(getOutputPath("api")))
                    .packageConfig((scanner, builder) -> builder.parent(packagePath))
                    .strategyConfig((scanner, builder) -> builder.addInclude(tableList)
                            .entityBuilder().enableLombok()
                            .controllerBuilder().enableRestStyle())
                    .templateConfig(builder -> builder.disable(TemplateType.ENTITY)
                            .disable(TemplateType.MAPPER)
                            .disable(TemplateType.XML)
                            .disable(TemplateType.SERVICE)
                            .disable(TemplateType.SERVICEIMPL)
                            .controller("/templates/curdController.java")
                    )
                    .templateEngine(new FreemarkerTemplateEngine())
                    .execute();
        }
    }

    protected static String getOutputPath(String packageName) {
        var path = System.getProperty("user.dir") + "/fridayboot-" + packageName + "/src/main/java";
        log.info(path);
        packagePath = "com.knifez." + PROJECT_NAME + packageName;
        log.info(packagePath);
        return path;
    }

    /**
     * 处理 all 情况
     *
     * @param tables 表
     * @return {@link List}<{@link String}>
     */
    protected static List<String> getTables(String tables) {
        if ("all".equals(tables)) {
            tableList = new ArrayList<>();
        } else {
            tableList = Arrays.asList(tables.split(","));
        }
        return tableList;
    }
}
