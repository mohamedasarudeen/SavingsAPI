package com.temenos.interaction.core.stepDefinition;

import static com.temenos.interaction.test.InteractionHelper.newInitialisedSession;
import static com.temenos.interaction.test.IrisConstants.BASE_URI;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import com.temenos.interaction.core.ScenarioBundle;
import com.temenos.useragent.generic.InteractionSession;
import com.temenos.useragent.generic.Url;
import org.apache.log4j.*;
import cucumber.api.Scenario;
import cucumber.api.java.After;

/**
 * TODO: Document me!
 *
 * @author mohamedasarudeen
 *
 */
public final class StepDefinitonBase {
    private static ConcurrentHashMap<Scenario, InteractionSession> scenarioSessions = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<Scenario, Url> scenarioURL = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<Scenario, ScenarioBundle> scenarioProperties = new ConcurrentHashMap<>();
    private final static Logger logger = Logger.getLogger(StepDefinitonBase.class);

    public static InteractionSession getInteractionSession(Scenario scenario) {
        InteractionSession session = null;
        synchronized (scenario) {
            if (!scenarioSessions.containsKey(scenario)) {
                scenarioSessions.put(scenario, newInitialisedSession(null));
            }
            session = scenarioSessions.get(scenario);
            getInteractionSessionURL(scenario, session).baseuri(BASE_URI);
        }
        return session;
    }

    public static Url getInteractionSessionURL(Scenario scenario, InteractionSession session) {
        Url url = null;
        synchronized (scenario) {
            if (!scenarioURL.containsKey(scenario)) {
                scenarioURL.put(scenario, session.url());
            }
            url = scenarioURL.get(scenario);
        }
        return url;
    }

    public static ScenarioBundle getScenarioBundle(Scenario scenario) {
        ScenarioBundle scenarioBundle;
        synchronized (scenario) {
            if (!scenarioProperties.containsKey(scenario)) {
                scenarioProperties.put(scenario, new ScenarioBundle());
            }
            scenarioBundle = scenarioProperties.get(scenario);
        }
        return scenarioBundle;
    }

    public static void clearInteractionSessionURL(Scenario scenario) {
        synchronized (scenario) {
            scenarioURL.remove(scenario);
        }
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
        logger.warn("clean up map objects: " + scenario.getName());
        scenarioSessions.remove(scenario);
        scenarioURL.remove(scenario);
        scenarioProperties.remove(scenario);
    }
}
