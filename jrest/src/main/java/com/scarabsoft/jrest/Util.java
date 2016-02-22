package com.scarabsoft.jrest;

import com.scarabsoft.jrest.annotation.Interceptor;
import com.scarabsoft.jrest.annotation.Interceptors;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

public class Util {

    static List<Interceptor> getInterceptors(Class<?> clazz){
        List<Interceptor> interceptorList = new LinkedList<>();
        Interceptors interceptorsAnotation = clazz.getAnnotation(Interceptors.class);
        if (interceptorsAnotation != null) {
            for (Interceptor in : interceptorsAnotation.value()) {
                interceptorList.add(in);
            }
        }

        Interceptor interceptor = clazz.getAnnotation(Interceptor.class);
        if (interceptor != null) {
            interceptorList.add(interceptor);
        }
        return interceptorList;
    }

    static List<Interceptor> getInterceptors(Method method){
        List<Interceptor> interceptorList = new LinkedList<>();
        Interceptors interceptorsAnotation = method.getAnnotation(Interceptors.class);
        if (interceptorsAnotation != null) {
            for (Interceptor in : interceptorsAnotation.value()) {
                interceptorList.add(in);
            }
        }

        Interceptor interceptor = method.getAnnotation(Interceptor.class);
        if (interceptor != null) {
            interceptorList.add(interceptor);
        }
        return interceptorList;
    }

}
