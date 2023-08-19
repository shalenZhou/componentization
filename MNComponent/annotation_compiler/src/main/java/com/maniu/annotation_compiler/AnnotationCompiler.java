package com.maniu.annotation_compiler;

import com.google.auto.service.AutoService;
import com.maniu.annotation.BindPath;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

@AutoService(Processor.class)
public class AnnotationCompiler extends AbstractProcessor {
    //生成代码的工具
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new HashSet<>();
        types.add(BindPath.class.getCanonicalName()); //添加被@BindPath()注解的类
        return types;
    }

    //jdk版本
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return processingEnv.getSourceVersion();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        //MemberActivity   PersonActivity 就是   elements
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(BindPath.class);

        Map<String, String> map = new HashMap<>();

        for (Element element : elements) {
            //写文件
            TypeElement typeElement = (TypeElement) element;
            String activityName = typeElement.getQualifiedName().toString();
            //key    member/PersonActivity.class
            String key = typeElement.getAnnotation(BindPath.class).value();
            map.put(key, activityName + ".class");
        }
        if (map.size() > 0) {
            //生成工具类  然后写代码
            //工具类的类名
            String className = "ActivityUtil" + System.currentTimeMillis();
            Writer writer = null;
            try {
                writer = filer.createSourceFile("com.maniu.util." + className).openWriter(); //路径

                StringBuilder stringBuffer = new StringBuilder();
                stringBuffer.append("package com.maniu.util;\n") //包名
                        .append("import com.maniu.arouter.ARouter;\n") //导入包
                        .append("import com.maniu.arouter.IRouter;\n") //导入包
                        .append("public class ") //类
                        .append(className) //类名
                        .append(" implements IRouter {\n") //实现接口
                        .append("@Override\n") //重写
                        .append("public void putActivity() {\n"); //方法
                for (String key : map.keySet()) {
                    String activityName = map.get(key);
                    stringBuffer.append("ARouter.getInstance().addActivity(\"") //方法体
                            .append(key)
                            .append("\",")
                            .append(activityName)
                            .append(");");

                }
                stringBuffer.append("\n}\n}");
                writer.write(stringBuffer.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return false;
    }
}
