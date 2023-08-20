package com.maniu.plugin;

import com.android.build.api.transform.DirectoryInput;
import com.android.build.api.transform.Format;
import com.android.build.api.transform.JarInput;
import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.api.transform.TransformOutputProvider;
import com.android.build.gradle.internal.pipeline.TransformManager;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

public class JavassistTransform extends Transform {
    ClassPool pool = ClassPool.getDefault(); //ClassPool对象是CtClass对象集合的容器

    @Override
    public String getName() {
        return "David";
    }

    /**
     * @return 代码类型 CONTENT_CLASS
     */
    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS;
    }

    /**
     * @return 整个工程的范围
     */
    @Override
    public Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT;
    }

    @Override
    public boolean isIncremental() {
        return false;
    }

    /**
     * 处理中转的函数
     *
     * @param transformInvocation the invocation object containing the transform inputs.
     * @throws TransformException
     * @throws InterruptedException
     * @throws IOException
     */
    @Override
    public void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation);

        //输入地
        Collection<TransformInput> inputs = transformInvocation.getInputs();
        //输出地
        TransformOutputProvider outputProvider = transformInvocation.getOutputProvider();

        for (TransformInput input : inputs) {
            //处理jar
            for (JarInput directoryInput : input.getJarInputs()) {
                File dest = outputProvider.getContentLocation(
                        directoryInput.getName(),
                        directoryInput.getContentTypes(),
                        directoryInput.getScopes(),
                        Format.JAR
                );

                System.out.println("dest path: " + dest.getAbsolutePath());
                //dest path: /home/meizu/AndroidStudioProjects/demo/componentization/Transform_Demo/app/build/intermediates/transforms/David/debug/x.jar

                //拷贝
                FileUtils.copyFile(directoryInput.getFile(), dest);
            }
            //处理class
            for (DirectoryInput directoryInput : input.getDirectoryInputs()) {
                //输入路径
                String preFileName = directoryInput.getFile().getAbsolutePath();
                System.out.println("src path: " + preFileName);
                //src path: /home/meizu/AndroidStudioProjects/demo/componentization/Transform_Demo/app/build/intermediates/javac/debug/classes

                //outputProvider   ----> 输出地
                File dest = outputProvider.getContentLocation(
                        directoryInput.getName(),
                        directoryInput.getContentTypes(),
                        directoryInput.getScopes(),
                        Format.DIRECTORY
                );

                try {
                    pool.insertClassPath(preFileName);
                } catch (NotFoundException e) {
                    e.printStackTrace();
                }

                //找到Router类
                findTarget(directoryInput.getFile(), preFileName);

                System.out.println("dest path: " + dest.getAbsolutePath());
                //dest path: /home/meizu/AndroidStudioProjects/demo/componentization/Transform_Demo/app/build/intermediates/transforms/David/debug/34

                //拷贝
                FileUtils.copyDirectory(directoryInput.getFile(), dest);
            }
        }
    }

    private void findTarget(File clazz, String fileName) {
        if (clazz.isDirectory()) {
            File[] files = clazz.listFiles();
            assert files != null;
            for (File file : files) {
                findTarget(file, fileName);
            }
        } else {
            String filePath = clazz.getAbsolutePath();

            if (!filePath.endsWith(".class")) {
                return;
            }
            if (filePath.contains("R$") || filePath.contains("R.class")
                    || filePath.contains("BuildConfig.class")) {
                return;
            }
            if (filePath.contains("Router")) {
                modify(clazz, fileName);
            }
        }
    }

    //修改之前加载到内存
    private void modify(File clazz, String fileName) {
        System.out.println("clazz path: " + clazz.getAbsolutePath());
        //clazz path: /home/meizu/AndroidStudioProjects/demo/componentization/Transform_Demo/app/build/intermediates/javac/debug/classes/com/maniu/router/Router.class

        String filePath = clazz.getAbsolutePath();
        String className = filePath.replace(fileName, "")
                //fileName: /home/meizu/AndroidStudioProjects/demo/componentization/Transform_Demo/app/build/intermediates/javac/debug/classes
                .replace("\\", ".")
                .replace("/", "."); //针对MAC的路径
        //全类名
        System.out.println("clazz name: " + className);
        //clazz name: .com.maniu.router.Router.class

        String name = className.replace(".class", "")
                .substring(1);

        System.out.println("class name: " + name);
        //class name: com.maniu.router.Router

        try {
            //目的 修改源码
            CtClass ctClass = pool.get(name);
            CtMethod ctMethod = ctClass.getDeclaredMethod("init");

            String body = "com.example.transform_demo.ActivityUtil.putActivity();";
            System.out.println("-->david insert Before");
            ctMethod.insertBefore(body);
            System.out.println("-->david insert after");
            ctClass.writeFile(fileName);
            ctClass.detach();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
