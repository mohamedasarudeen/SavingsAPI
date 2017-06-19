package com.temenos.interaction.cucumber.rest.step;

import static com.temenos.interaction.cucumber.test.InteractionHelper.newInitialisedSession;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

import com.temenos.interaction.cucumber.core.ScenarioBundle;
import com.temenos.interaction.cucumber.test.EndpointConfig;
import com.temenos.useragent.generic.InteractionSession;
import com.temenos.useragent.generic.Url;

import cucumber.api.Scenario;
import cucumber.api.java.After;

/**
 * StepDefiniton Base scripts for implementing in test suites
 */
public final class StepDefinitonBase {
    private static ConcurrentHashMap<Scenario, InteractionSession> scenarioSessions = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<Scenario, Url> scenarioURL = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<Scenario, ScenarioBundle> scenarioProperties = new ConcurrentHashMap<>();

    public static InteractionSession getInteractionSession(Scenario scenario) {
        scenarioSessions.putIfAbsent(scenario, newInitialisedSession(null));
        InteractionSession session = scenarioSessions.get(scenario);
        getInteractionSessionURL(scenario, session).baseuri(EndpointConfig.getUri());
        return session;
    }

    public static Url getInteractionSessionURL(Scenario scenario, InteractionSession session) {
        scenarioURL.putIfAbsent(scenario, session.url());
        Url url = scenarioURL.get(scenario);
        return url;
    }

    public static ScenarioBundle getScenarioBundle(Scenario scenario) {
        scenarioProperties.putIfAbsent(scenario, new ScenarioBundle());
        ScenarioBundle scenarioBundle = scenarioProperties.get(scenario);
        return scenarioBundle;
    }

    public static void clearInteractionSessionURL(Scenario scenario) {
        scenarioURL.remove(scenario);

    }

    public static void executeRequest(Scenario scenario, InteractionSession session, String httpMethod)
            throws IllegalAccessException {
        Url url = getInteractionSessionURL(scenario, session);
        executeRequest(scenario, url, httpMethod);
    }

    public static void executeRequest(Scenario scenario, Url url, String httpMethod) throws IllegalAccessException {
        clearInteractionSessionURL(scenario);
        try {
            Method refMethod = url.getClass().getDeclaredMethod(httpMethod.toLowerCase());
            refMethod.invoke(url);
        } catch (NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException
                | IllegalAccessException e) {
            throw new IllegalAccessException("HTTP method not allowed " + httpMethod);
        }

    }

    @After
    public void teardown(Scenario scenario) {
        scenarioSessions.remove(scenario);
        scenarioURL.remove(scenario);
        scenarioProperties.remove(scenario);
    }
}
