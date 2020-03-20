package com.browserstack.app.android;
import com.browserstack.local.Local;

import java.io.FileReader;
import java.net.URL;
import java.util.*;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import static org.junit.Assert.assertNotNull;


public class BrowserStackAndroidTest {
    public AndroidDriver<AndroidElement> driver;
    private Local l;

    private static JSONObject config;
    public Map<String, String> envCapabilities;
    public Map<String, String> commonCapabilities;
    public DesiredCapabilities capabilities;

    @BeforeEach
    public void setUp(TestInfo testInfo,RepetitionInfo repetitionInfo) throws Exception {

        if (System.getProperty("config") != null) {
            //System.out.println("BSTest:" + System.getProperty("config"));

            JSONParser parser = new JSONParser();
            config = (JSONObject) parser.parse(new FileReader("src/test/resources/conf/" + System.getProperty("config")));
        }
        //System.out.println(repetitionInfo.getCurrentRepetition()+"/"+ repetitionInfo.getTotalRepetitions());
        int taskID = repetitionInfo.getCurrentRepetition();

        JSONArray envs = (JSONArray) config.get("environments");
        capabilities = new DesiredCapabilities();
        envCapabilities = (Map<String, String>) envs.get((taskID-1));

        Iterator it = envCapabilities.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
        }

        commonCapabilities = (Map<String, String>) config.get("capabilities");
        it = commonCapabilities.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (capabilities.getCapability(pair.getKey().toString()) == null) {
                capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
            }
        }

        String username = System.getenv("BROWSERSTACK_USERNAME");
        if (username == null) {
            username = (String) config.get("user");
        }

        String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
        if (accessKey == null) {
            accessKey = (String) config.get("key");
        }

        if (capabilities.getCapability("browserstack.local") != null && capabilities.getCapability("browserstack.local") == "true") {
            l = new Local();
            Map<String, String> options = new HashMap<String, String>();
            options.put("key", accessKey);
            l.start(options);
        }
        driver = new AndroidDriver(new URL("http://"+username+":"+accessKey+"@"+config.get("server")+"/wd/hub"), capabilities);
    }

    @AfterEach
    public void tearDown() throws Exception {
        driver.quit();
        if(l != null) l.stop();
    }
}
