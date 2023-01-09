package org.fpm.di.example;

import org.fpm.di.Binder;
import org.fpm.di.Configuration;

import org.fpm.di.example.Injection.Name;
import org.fpm.di.example.Injection.Person;
import org.fpm.di.example.InjectionWithException.MultipleInjection.EmployeeWithInjection;
import org.fpm.di.example.InjectionWithException.Info;
import org.fpm.di.example.InjectionWithException.NoInjection.EmployeeWithoutInjection;

import org.fpm.di.example.baseClasses.*;

public class MyConfiguration implements Configuration {

    @Override
    public void configure(Binder binder){
        binder.bind(MySingleton.class);
        binder.bind(MyPrototype.class);

        binder.bind(UseA.class);

        binder.bind(A.class, B.class);
        binder.bind(B.class, new B());

        binder.bind(Person.class);
        binder.bind(Name.class);

        binder.bind(Info.class);
        binder.bind(EmployeeWithInjection.class);
        binder.bind(EmployeeWithoutInjection.class);


    }
}
