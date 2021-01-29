package Classes;

import Annotations.AfterSuite;
import Annotations.BeforeSuite;
import Annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;

public class TestExecutor {

    public static void start (Class<?> testClass) {
        Method[] allMethods = testClass.getDeclaredMethods();
        Method beforeSuiteMethod = getSuiteMethod(BeforeSuite.class, allMethods);
        Method afterSuiteMethod = getSuiteMethod(AfterSuite.class, allMethods);

        runAllTests(allMethods, beforeSuiteMethod, afterSuiteMethod);
    }

    private static Method getSuiteMethod(Class<? extends Annotation> suiteAnnotationClass, Method[] methods) {
        Method suiteMethod = null;

        for (Method method : methods) {
            if (method.isAnnotationPresent(suiteAnnotationClass)) {
                if (suiteMethod != null) {
                    throw new RuntimeException(suiteAnnotationClass.getName() + "more than one");
                } else {
                 suiteMethod = method;
                }
            }
        }
        return suiteMethod;
    }

    private static void runAllTests (Method[] methods, Method beforeSuite, Method afterSuite) {
        invokeMethod(beforeSuite);
        runOrdinaryTests(methods);
        invokeMethod(afterSuite);
    }

    private static void runOrdinaryTests(Method[] methods) {
        Arrays.stream(methods).filter(method -> method.isAnnotationPresent(Test.class)).sorted(Comparator.comparingInt(method -> method.
                getAnnotation(Test.class).priority())).forEach(method1 -> invokeMethod(method1));
    }

    public static void invokeMethod (Method method) {
        try {
            method.invoke(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
