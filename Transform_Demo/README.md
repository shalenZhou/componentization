# 编译过程中，通过javassist技术修改代码

先 Clean Project，再 Make Project

在Build运行框内看输出log

编译完成后的Router类👇

public class Router {
    public void init() {
        ActivityUtil.putActivity();    
    }
}