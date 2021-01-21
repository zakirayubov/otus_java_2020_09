package ru.otus;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Ioc {

    private Ioc() {
    }

    static TestLogging createMyClass() {

        InvocationHandler handler = new TestLoggingInvocationHandler(new TestLoggingImpl());
        return (TestLogging) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class<?>[]{TestLogging.class}, handler);

    }


    static class TestLoggingInvocationHandler implements InvocationHandler {
        private final TestLogging myClass;
        private final List<MethodDetails> logMethods;

        TestLoggingInvocationHandler(TestLogging myClass) {
            this.myClass = myClass;
            this.logMethods = Arrays.stream(myClass.getClass().getMethods())
                    .filter(m -> Arrays.stream(m.getDeclaredAnnotations())
                            .anyMatch(a -> a.annotationType().equals(Log.class)))
                    .map(MethodDetails::new)
                    .collect(Collectors.toList());
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            MethodDetails methodDetails = new MethodDetails(method);

            if (logMethods.contains(methodDetails)) {
                String[] parameterNames = methodDetails.getParameterNames();
                StringBuilder params = new StringBuilder();
                for (int i = 0; i < args.length; i++) {
                    params.append(", ").append(parameterNames[i]).append(": ").append(args[i]);
                }
                System.out.printf("executed method: %s%s\n", method.getName(), params.toString());
            }

            return method.invoke(myClass, args);
        }
    }

    static class MethodDetails {
        private final String name;
        private final String returnType;
        private final String parameterTypes;
        private final String[] parameterNames;

        public MethodDetails(Method method) {
            this.name = method.getName();
            this.returnType = method.getReturnType().getName();
            this.parameterTypes = Arrays.toString(method.getParameterTypes());
            parameterNames = Arrays.stream(method.getParameters())
                    .map(Parameter::getName)
                    .toArray(String[]::new);
        }

        public String[] getParameterNames() {
            return parameterNames;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MethodDetails that = (MethodDetails) o;
            return Objects.equals(name, that.name) &&
                    Objects.equals(returnType, that.returnType) &&
                    Objects.equals(parameterTypes, that.parameterTypes) &&
                    Arrays.equals(parameterNames, that.parameterNames);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(name, returnType, parameterTypes);
            result = 31 * result + Arrays.hashCode(parameterNames);
            return result;
        }
    }
}


