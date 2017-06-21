package com.okgays.example.processorlearning;

import android.app.Activity;

import com.okgays.annotation.DIView;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by HuFei on 2017/6/21.
 */

public final class BindUtil {
    private BindUtil() {
        //no instance
    }

    /**
     * 通过反射的方式初始化activity内部的ui控件
     * @param context
     */
    public static final void bindView(Activity context) {
        Class<? extends Activity> contextClass = context.getClass();
        Field[] fields = contextClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(DIView.class)) {
                field.setAccessible(true);
                Annotation[] annotations = field.getDeclaredAnnotations();
                int resId = 0;
                for (Annotation annotation : annotations) {
                    if (annotation instanceof DIView) {
                        DIView bindAnnotation = (DIView) annotation;
                        resId = bindAnnotation.value();
                        break;
                    }
                }
                try {
                    field.set(context, context.findViewById(resId));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
