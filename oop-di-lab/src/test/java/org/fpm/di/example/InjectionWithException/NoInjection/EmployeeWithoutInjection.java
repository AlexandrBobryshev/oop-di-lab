package org.fpm.di.example.InjectionWithException.NoInjection;

import org.fpm.di.example.InjectionWithException.Info;

public class EmployeeWithoutInjection {
    private Info info;

    public EmployeeWithoutInjection(Info info){
        this.info = info;
    }

    public EmployeeWithoutInjection(){
        info = new Info();
    }
}

