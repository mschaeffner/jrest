package com.scarabsoft.jrest;

import com.scarabsoft.jrest.annotation.Param;
import com.scarabsoft.jrest.annotation.Path;
import com.scarabsoft.jrest.annotation.Post;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Test implements InvocationHandler {

    public static void main(String[] args) {
        Test app = new Test();
        app.run();
    }

    interface App{

        @Post
        String test(@Path(name = "path") int i1, @Param(name = "param") String s2);

    }

    private void run() {
        App app = create(App.class);
        app.test(10,"das");
    }

    public <T> T create(Class<?> clazz) {

        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class<?>[]{clazz},
                this);

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Annotation[][] annotations = method.getParameterAnnotations();
        for(int i = 0; i < annotations.length; i++){
            for(int j = 0; j < annotations[i].length; j++){

                Annotation annotation = annotations[i][j];

                System.out.println(annotation);

            }
        }

        System.out.println(method);
        System.out.println(args[0]);
        System.out.println(args[1]);

        return null;
    }
}
