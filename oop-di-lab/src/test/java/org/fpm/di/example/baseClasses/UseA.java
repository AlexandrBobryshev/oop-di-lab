package org.fpm.di.example.baseClasses;

import org.fpm.di.example.baseClasses.A;

import javax.inject.Inject;

public class UseA {
    private final A dependency;

    @Inject
    public UseA(A a) {
        this.dependency = a;
    }

    public A getDependency() {
        return dependency;
    }
}
