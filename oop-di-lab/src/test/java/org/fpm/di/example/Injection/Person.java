package org.fpm.di.example.Injection;

import javax.inject.Inject;

public class Person {

    private Name name;
    private int age;

    public Person(Name name, int age){
        this.name = name;
        this.age = age;
    }

    @Inject
    public Person(Name name){
        this.name = name;
        this.age = 25;
    }

    public Name getName(){
        return name;
    }

    public int getAge(){
        return age;
    }

}

