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
                Method refMethod = Matchers.class.getMethod(methods.get(0), Object.class);
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

}
