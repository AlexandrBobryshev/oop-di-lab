package org.fpm.di;

import java.lang.reflect.InvocationTargetException;

public interface Configuration {
    void configure(Binder binder);
}
