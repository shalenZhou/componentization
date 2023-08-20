package com.maniu.plugin;

import com.android.build.gradle.BaseExtension;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * 自定义 Gradle 插件
 */
public class JavassistPlugin implements Plugin<Project> {
    /**
     * 相当于main函数    插件集成到项目中就会执行apply函数
     * @param project
     */
    @Override
    public void apply(Project project) {
        System.out.println("------------David-------------------->");

        BaseExtension baseExtension = project.getExtensions()
                .getByType(BaseExtension.class);

        baseExtension.registerTransform(new JavassistTransform());
    }
}
