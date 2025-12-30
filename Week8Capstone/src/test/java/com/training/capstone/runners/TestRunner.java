package com.training.capstone.runners;

import org.junit.platform.suite.api.*;

import static io.cucumber.junit.platform.engine.Constants.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.training.capstone.stepdefs")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME,
        value = "pretty, html:reports/cucumber.html, json:reports/cucumber.json")
@ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "not @wip")
public class TestRunner {
}
