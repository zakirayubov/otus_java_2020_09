package ru.otus.appcontainer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final Map<String, Object> appComponentsByName = new HashMap<>();
    private final Map<Class<?>, String> beanNameByClass = new HashMap<>();
    private final Map<Class<?>, Object> configClassInstanceByClass = new HashMap<>();

    private static final Logger log = LoggerFactory.getLogger(AppComponentsContainerImpl.class);

    public AppComponentsContainerImpl(Class<?>... configClasses) {
        processConfig(configClasses);
    }

    private void processConfig(Class<?>... configClasses) {
        checkConfigClasses(configClasses);

        buildConfigClassInstances(configClasses);

        buildAppComponentsContainer(configClasses);
    }

    private void checkConfigClasses(Class<?>... configClasses) {
        for (var configClass : configClasses) {
            checkConfigClass(configClass);
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    private void buildConfigClassInstances(Class<?>... configClasses) {
        for (var configClass : configClasses) {
            configClassInstanceByClass.put(configClass, getConfigClassInstance(configClass));
        }
    }

    private Object getConfigClassInstance(Class<?> configClass) {
        try {
            return configClass.getConstructor().newInstance();
        } catch (Exception e) {
            log.error("cannot instantiate config class");
            throw new RuntimeException(e);
        }
    }

    private void buildAppComponentsContainer(Class<?>... configClasses) {
        var orderedBeans = getOrderedAppBeans(configClasses);
        orderedBeans.forEach(appBean -> beanNameByClass.put(appBean.method.getReturnType(), appBean.name));
        orderedBeans.forEach(appBean -> appComponentsByName.put(appBean.name, getBeanInstance(appBean)));
    }

    private List<AppBean> getOrderedAppBeans(Class<?>... configClasses) {
        return Arrays.stream(configClasses)
                       .flatMap(configClass -> Arrays.stream(configClass.getDeclaredMethods()))
                       .filter(method -> method.isAnnotationPresent(AppComponent.class))
                       .map(this::getAppBean)
                       .sorted(Comparator.comparingInt(AppBean::getOrder))
                       .collect(Collectors.toList());
    }

    private AppBean getAppBean(Method method) {
        var annotation = method.getDeclaredAnnotation(AppComponent.class);
        return new AppBean(annotation.order(), annotation.name(), method);
    }

    private Object getBeanInstance(AppBean bean) {
        try {
            var method = bean.method;
            return method.invoke(configClassInstanceByClass.get(method.getDeclaringClass()), getBeanArgs(bean));
        } catch (Exception e) {
            log.error("cannot instantiate bean = {}", bean.name);
            throw new RuntimeException(e);
        }
    }

    private Object[] getBeanArgs(AppBean bean) {
        return Arrays.stream(bean.method.getParameters())
                       .map(Parameter::getType)
                       .map(beanNameByClass::get)
                       .map(appComponentsByName::get)
                       .toArray();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <C> C getAppComponent(Class<C> componentClass) {
        var beanName = beanNameByClass.get(componentClass);
        if (beanName == null) {
            var componentInterface =
                    Arrays.stream(componentClass.getInterfaces())
                            .filter(beanNameByClass::containsKey)
                            .findAny();
            if (componentInterface.isEmpty()) {
                return null;
            }

            beanName = beanNameByClass.get(componentInterface.get());
        }

        return (C) appComponentsByName.get(beanName);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <C> C getAppComponent(String componentName) {
        return (C) appComponentsByName.get(componentName);
    }

    private static class AppBean {
        private final Integer order;
        private final String name;
        private final Method method;

        private AppBean(Integer order, String name, Method method) {
            this.order = order;
            this.name = name;
            this.method = method;
        }

        public Integer getOrder() {
            return order;
        }
    }
}
