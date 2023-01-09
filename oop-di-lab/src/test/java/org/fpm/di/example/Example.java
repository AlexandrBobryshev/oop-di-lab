package org.fpm.di.example;

import org.fpm.di.Container;
import org.fpm.di.Environment;
import org.fpm.di.EnvironmentImpl;
import org.fpm.di.example.Injection.Person;
import org.fpm.di.example.InjectionWithException.MultipleInjection.EmployeeWithInjection;
import org.fpm.di.example.InjectionWithException.NoInjection.EmployeeWithoutInjection;
import org.fpm.di.example.baseClasses.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Example {

    private Container container;

    @Before
    public void setUp() {
        Environment env = new EnvironmentImpl();
        container = env.configure(new MyConfiguration());
    }

    @Test
    public void shouldInjectSingleton() {
        assertSame(container.getComponent(MySingleton.class), container.getComponent(MySingleton.class));
    }

    @Test
    public void shouldInjectPrototype() {
        assertNotSame(container.getComponent(MyPrototype.class), container.getComponent(MyPrototype.class));
    }

    @Test
    public void shouldBuildInjectionGraph() {
        /*
        binder.bind(A.class, B.class);
        binder.bind(B.class, new B());
        */
        final B bAsSingleton = container.getComponent(B.class);
        assertSame(container.getComponent(A.class), bAsSingleton);
        assertSame(container.getComponent(B.class), bAsSingleton);
    }

    @Test
    public void shouldBuildInjectDependencies() {
        final UseA hasADependency = container.getComponent(UseA.class);
        assertSame(hasADependency.getDependency(), container.getComponent(B.class));
    }

    @Test
    public void shouldTrowClassException(){
        NotConfigurated notConfiguratedInstance;
        try{
            notConfiguratedInstance = container.getComponent(NotConfigurated.class);
            Assert.fail("");
        } catch (RuntimeException thrown){
            Assert.assertEquals("Ви не передали цей клас в конфігурацію!", thrown.getMessage());
        }
    }

    @Test
    public void shouldUseInjectConstructor(){
        Person person;
        person = container.getComponent(Person.class);
        assertTrue(person.getAge() == 25);
    }

    @Test
    public void shouldTrowInjectException(){
        EmployeeWithInjection employeeWithInjection;
        try{
            employeeWithInjection = container.getComponent(EmployeeWithInjection.class);
            Assert.fail("");
        }catch(RuntimeException thrown){
            Assert.assertEquals("Для цього класу існує декілька конструкторів з анотацією @Inject", thrown.getMessage());
        }
    }

    @Test
    public void shouldTrowMultipleContainerException(){
        EmployeeWithoutInjection employeeWithoutInjection;
        try{
            employeeWithoutInjection = container.getComponent(EmployeeWithoutInjection.class);
            Assert.fail("");
        }catch(RuntimeException thrown){
            Assert.assertEquals("Для цього класу існує декілька конструкторів! Немає конструктора з анотацією @Inject", thrown.getMessage());
        }
    }

}