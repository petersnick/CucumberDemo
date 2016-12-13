package com.atos.cucumberdemo.step;

/**
 * Created by VincentFree on 27-3-2015.
 */

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

public class SharedDriver extends EventFiringWebDriver {
    //System.setProperty("webdriver.chrome.driver", "C://driver//chromedriver.exe");
    private static final WebDriver REAL_DRIVER;
    private static final Thread CLOSE_THREAD = new Thread() {
        @Override
        public void run() {
            REAL_DRIVER.close();
        }
    };

    static {
        String browserName = System.getProperty("browser");
        if(browserName == null) {
            browserName = "PhantomJS";
        }
        if(browserName == null) {
            browserName = "chrome";
        }
        if(browserName.equals("firefox")) {
            REAL_DRIVER = new FirefoxDriver();
        }
        else if(browserName.equals("chrome")) {
            String pathToDriver = System.getProperty("pathToDriver");
            if(pathToDriver == null) {
                pathToDriver = "C://driver//chromedriver.exe";
            }
            System.setProperty("webdriver.chrome.driver", pathToDriver);
            REAL_DRIVER = new ChromeDriver();
        }
        else if(browserName.equals("internetExplorer")) {
            REAL_DRIVER = new InternetExplorerDriver();
        }
        else if(browserName.equals("PhantomJS")) {
            String pathToDriver = System.getProperty("pathToDriver");
            if(pathToDriver == null) {
                pathToDriver = "C://driver//phantomjs.exe";
            }
            System.setProperty("webdriver.phantomjs.driver", pathToDriver);
            REAL_DRIVER = new PhantomJSDriver();
        }
        else {
            throw new RuntimeException("You have not specified a valid browser");
        }
        Runtime.getRuntime().addShutdownHook(CLOSE_THREAD);
    }

    public SharedDriver() {
        super(REAL_DRIVER);
    }

    @Override
    public void close() {
        if (Thread.currentThread() != CLOSE_THREAD) {
            throw new UnsupportedOperationException("You shouldn't close this WebDriver. It's shared and will close when the JVM exits.");
        }
        super.close();
    }

    @Before
    public void deleteAllCookies() {
        manage().deleteAllCookies();
    }

    @After
    public void embedScreenshot(Scenario scenario) {
        try {
            byte[] screenshot = getScreenshotAs(OutputType.BYTES);
            scenario.embed(screenshot, "image/png");
        } catch (WebDriverException somePlatformsDontSupportScreenshots) {
            System.err.println(somePlatformsDontSupportScreenshots.getMessage());
        }
    }
}
