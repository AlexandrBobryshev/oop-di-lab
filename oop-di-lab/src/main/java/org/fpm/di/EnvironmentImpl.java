package org.fpm.di;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class EnvironmentImpl implements Environment{



    public EnvironmentImpl(){
    }
    @Override
    public Container configure(Configuration configuration){

        BinderImpl binder = new BinderImpl();
        configuration.configure(binder);

        return new ContainerImpl(binder);
    }

}
