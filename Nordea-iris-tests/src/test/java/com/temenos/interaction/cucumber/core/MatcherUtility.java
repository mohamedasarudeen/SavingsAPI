package com.temenos.interaction.cucumber.core;

import static org.junit.Assert.assertThat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import com.temenos.useragent.generic.Entity;
import com.temenos.useragent.generic.InteractionSession;

/**
 * TODO: Document me!
 *
 * @author mohamedasarudeen
 *
 */
public class MatcherUtility {

    private static final Class[] MATCH_REF = new Class[] { Object.class, String.class, Comparable.class,
            Matcher[].class };

    @SuppressWarnings("unchecked")
    public static void runMatchAssertion(InteractionSession session, String propertyId, Matcher matcher) {
        for (Entity entity : session.entities().collection()) {
            assertThat(entity.get(propertyId), matcher);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> Matcher<T> getMatcherFunction(List<String> methods, T value) throws IllegalAccessException {
        try {
            if (methods.size() == 0) {
                throw new IllegalAccessException("No Matchers provided");
            }
            if (methods.size() == 1) {
                Method refMethod = null;
                for (Class klass : MATCH_REF) {
                    try {
                        refMethod = Matchers.class.getMethod(methods.get(0), klass);
                        break;
                    } catch (NoSuchMethodException e) {
                        // Try for next type
                    }
                }
                if (null == refMethod) {
                    try {
                        refMethod = Matchers.class.getMethod(methods.get(0));
                        return (Matcher<T>) refMethod.invoke(null);
                    } catch (NoSuchMethodException e) {
                        // Try for next type
                    }
                }
                if (null == refMethod)
                    throw new IllegalAccessException("No Matchers available for " + methods.get(0));
                return (Matcher<T>) refMethod.invoke(null, value);
            } else {
                Method refMethod = Matchers.class.getMethod(methods.get(0), Matcher.class);
                return (Matcher<T>) refMethod.invoke(null,
                        getMatcherFunction(methods.subList(1, methods.size()), value));
            }
        } catch (NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
            throw new IllegalAccessException("Unable to get Matchers " + e.getMessage());
        }
    }

    public static boolean isIterateable(List<String> methods) {
        if (methods.size() == 0) {
            return false;
        }
        try {
            Matchers.class.getMethod(methods.get(0), Iterable.class);
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

}
