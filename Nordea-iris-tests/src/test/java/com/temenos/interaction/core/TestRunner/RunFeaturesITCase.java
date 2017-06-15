package com.temenos.interaction.core.TestRunner;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.cucumber.listener.Reporter;
import com.temenos.interaction.core.DashboardUtility;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@SuppressWarnings("deprecation")
@RunWith(Cucumber.class)
@CucumberOptions(features = "features", glue = { "com.temenos.interaction.core.stepDefinition" }, format = { "pretty",
        "html:target/cucumber", "json:target/cucumber/cucumber.json" }, plugin = { "com.cucumber.listener.ExtentCucumberFormatter:target/CucumberExtentsReport.html" },
// tags = {"@Service1,@Service2,@Iris1"})
tags = { "@Iris" })
public class RunFeaturesITCase {

    @BeforeClass
    public static void setup() throws Exception {
        String[] reportpath = new String[4];
        reportpath[0] = "IRIS_AutomationResult";
        DashboardUtility.d1.indexDriver(reportpath);
        DashboardUtility.d1.WriteindexReportHeader(reportpath);
    }

    @AfterClass
    public static void afterSuite() throws Exception {
        Reporter.loadXMLConfig(new File("src/test/resources/extent-config.xml"));
        Reporter.setSystemInfo("user", System.getProperty("user.name"));
        Reporter.setSystemInfo("os", "Mac OSX");
        Reporter.setTestRunnerOutput("Sample test runner output message");
        DashboardUtility.d1.indexFooter();
        DashboardUtility.d1.indexclose();
    }

}
