package org.fpm.di.example.InjectionWithException.MultipleInjection;

import org.fpm.di.example.InjectionWithException.Info;

import javax.inject.Inject;

public class EmployeeWithInjection {
    private Info info;

    @Inject
    public EmployeeWithInjection(Info info){
        this.info = info;
    }

    @Inject
    public EmployeeWithInjection(){
        info = new Info();
    }
}
