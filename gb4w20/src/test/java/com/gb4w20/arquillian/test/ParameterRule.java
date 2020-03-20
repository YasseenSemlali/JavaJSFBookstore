/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gb4w20.arquillian.test;

import java.lang.reflect.Field;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This code comes from https://gist.github.com/aslakknutsen/1358803
 * Unchanged except for replacing println with LOG
 * 
 */
public class ParameterRule<T> implements MethodRule {
    
    private final static Logger LOG = LoggerFactory.getLogger(ParameterRule.class);

    private final T[] params;
    private final String fieldName;

    public ParameterRule(String fieldName, T... params) {
        if (fieldName == null) {
            throw new IllegalArgumentException("fieldName must be specified");
        }
        if (params == null || params.length == 0) {
            throw new IllegalArgumentException("params must be specified and have more then zero length");
        }
        this.fieldName = fieldName;
        this.params = params;
    }

    @Override
    public Statement apply(final Statement base, final FrameworkMethod method, final Object target) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                LOG.debug("rule - before " + target.hashCode());
                if (isInContainer()) {
                    for (Object param : params) {
                        Field targetField = target.getClass().getDeclaredField(fieldName);
                        // Need to find a better way to do this with canAccess
                        if (!targetField.isAccessible()) {
                            targetField.setAccessible(true);
                        }
                        targetField.set(target, param);
                        base.evaluate();
                    }
                } else {
                    base.evaluate();
                }
                LOG.debug("rule - after " + target.hashCode());
            }
        };
    }

    private boolean isInContainer() {
        Exception e = new Exception();
        e.fillInStackTrace();
        return e.getStackTrace()[e.getStackTrace().length - 1].getClassName().equals("java.lang.Thread");
    }
}
