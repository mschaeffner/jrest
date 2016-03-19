package com.scarabsoft.jrest;

import com.scarabsoft.jrest.annotation.Interceptor;
import com.scarabsoft.jrest.annotation.Interceptors;

import java.lang.annotation.Annotation;
import java.util.LinkedList;
import java.util.List;

public class Util {

    static List<Interceptor> getInterceptors(Interceptors interceptors, Interceptor interceptor) {
        List<Interceptor> interceptorList = new LinkedList<>();
        if (interceptors != null) {
            for (Interceptor in : interceptors.value()) {
                interceptorList.add(in);
            }
        }

        if (interceptor != null) {
            interceptorList.add(interceptor);
        }
        return interceptorList;
    }

    static List<Annotation> getAnnotations(Annotation[][] annotations) {
        final List<Annotation> result = new LinkedList<>();

        for (int i = 0; i < annotations.length; i++) {

            if (annotations[i].length > 1) {
                throw new RuntimeException("you can only assign one annotation");
            }
            Annotation annotation = annotations[i][0];

            result.add(annotation);
        }
        return result;
    }


}
