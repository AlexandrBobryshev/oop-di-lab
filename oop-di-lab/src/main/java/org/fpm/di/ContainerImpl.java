package org.fpm.di;

import javassist.ClassMap;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.lang.reflect.*;
import java.util.*;

public class ContainerImpl implements Container{

    private Map<Class<?>, Class<?>> classMap;
    private Map<Class<?>, Object> instanceMap;
    private static Map<Class<?>, Object> singletonMap = new HashMap<>();

    public ContainerImpl(BinderImpl binder){
        this.classMap = binder.getClassMap();
        this.instanceMap = binder.getInstanceMap();
    }

    // injectionGraph
    private void removeFromMap(Class<?> clazz){
        List<Class<?>> keys = new ArrayList<>();
        for(Class<?> i: classMap.keySet()){
            Class<?> cls = classMap.get(i);
            if(clazz.equals(cls)){
                keys.add(i);
            }
        }

        if(keys.size() != 0){
            for (Class<?> key : keys){
                classMap.remove(key);
                instanceMap.put(key, instanceMap.get(clazz));
            }
            for(Class<?> key: keys){
                removeFromMap(key);
            }
        }
    }

    @Override
    public <T> T getComponent(Class<T> clazz){

        for(Class<?> i: instanceMap.keySet()){
            removeFromMap(i);
        }

        boolean isSingleton = clazz.isAnnotationPresent(Singleton.class);
        if (isSingleton) {
            if(singletonMap.containsKey(clazz)) return (T) singletonMap.get(clazz);
        }

        Class<?> implementationClass = null;
        if(classMap.containsKey(clazz)) implementationClass = classMap.get(clazz);

        T instance = null;
        if(instanceMap.containsKey(clazz)) instance = (T) instanceMap.get(clazz);

        if(implementationClass == null && instance == null){
            throw new RuntimeException("Ви не передали цей клас в конфігурацію!");
        }

        if(instance == null){

            Constructor<?>[] constructors = implementationClass.getDeclaredConstructors();
            List <Constructor<?>> actualConstructors = new ArrayList<>();
            if(constructors.length > 1){
                for(Constructor<?> constructor: constructors){
                    if (constructor.isAnnotationPresent(Inject.class)){
                        actualConstructors.add(constructor);
                    }
                }

                if(actualConstructors.size() == 0){
                    throw new RuntimeException("Для цього класу існує декілька конструкторів! Немає конструктора з анотацією @Inject");
                } else if(actualConstructors.size() > 1){
                    throw new RuntimeException("Для цього класу існує декілька конструкторів з анотацією @Inject");

                }
            }

            actualConstructors.add(constructors[0]);
            Class[] paramTypes = actualConstructors.get(0).getParameterTypes();

            Object[] arg = new Object[paramTypes.length];
            if (paramTypes.length > 0) {
                for (int i = 0; i < paramTypes.length; i++) {
                    if (instanceMap.containsKey(paramTypes[i])) {
                        arg[i] = instanceMap.get(paramTypes[i]);
                    } else {
                        arg[i] = getComponent(paramTypes[i]);
                    }
                }
            }

            T implementationClassInstance;

            try {
                implementationClassInstance = (T) implementationClass.getDeclaredConstructor(paramTypes).newInstance(arg);
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                     IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            if(isSingleton) singletonMap.put(clazz, implementationClassInstance);

            return implementationClassInstance;
        }

        return instance;
    }
}

// Juicer juicer = container.getComponent(Juicer.class)
// Juicer juicer = new Juicer(getComponent(Peelable.class), getComponent(Peeler.class))
// Juicer juicer = new Juicer(apple, new Peeler(getComponent(Peelable.class))
// Juicer juicer = new Juicer(apple, new Peeler(apple))
// Apple apple = new Apple();
