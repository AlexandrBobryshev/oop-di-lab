package org.fpm.di;

import java.util.*;

public class BinderImpl implements Binder{

    private Map<Class<?>, Class<?>> classMap = new HashMap<>();
    private Map<Class<?>, Object> instanceMap = new HashMap<>();

    public BinderImpl(){
        classMap = new HashMap<>();
        instanceMap = new HashMap<>();
    }

    @Override
    public <T> void bind(Class<T> clazz){

        classMap.put(clazz, clazz);
    }

    @Override
    public <T> void bind(Class<T> clazz, Class<? extends T> implementation){

        classMap.put(clazz, implementation);
    }

    @Override
    public <T> void bind(Class<T> clazz, T instance) {
        instanceMap.put(clazz, instance);
    }

    public Map<Class<?>, Class<?>> getClassMap(){
        return classMap;
    }

    public Map<Class<?>, Object> getInstanceMap(){
        return instanceMap;
    }

}