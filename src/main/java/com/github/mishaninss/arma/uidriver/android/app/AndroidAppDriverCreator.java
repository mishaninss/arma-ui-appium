package com.github.mishaninss.arma.uidriver.android.app;

import com.github.mishaninss.arma.data.WebDriverProperties;
import com.github.mishaninss.arma.exceptions.FrameworkConfigurationException;
import com.github.mishaninss.arma.uidriver.webdriver.ICapabilitiesProvider;
import com.github.mishaninss.arma.uidriver.webdriver.IWebDriverCreator;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Capabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("android")
@SuppressWarnings("unused")
public class AndroidAppDriverCreator implements IWebDriverCreator {

  private static final String COULD_NOT_START_SESSION_MESSAGE = "Could not start a new browser session";

  @Autowired
  private WebDriverProperties properties;
  @Autowired
  private ICapabilitiesProvider capabilitiesProvider;

  @Override
  public AndroidDriver<AndroidElement> createDriver(Capabilities desiredCapabilities) {
    AndroidDriver<AndroidElement> webDriver;
    Capabilities capabilities = capabilitiesProvider.getCapabilities();
    capabilities.merge(desiredCapabilities);

    try {
      String gridUrl = properties.driver().gridUrl;
      webDriver = new AndroidDriver<>(new URL(gridUrl), capabilities);
    } catch (Exception ex) {
      throw new FrameworkConfigurationException(COULD_NOT_START_SESSION_MESSAGE, ex);
    }

    webDriver.manage().timeouts()
        .implicitlyWait(properties.driver().timeoutsElement, TimeUnit.MILLISECONDS);
    return webDriver;
  }
}
