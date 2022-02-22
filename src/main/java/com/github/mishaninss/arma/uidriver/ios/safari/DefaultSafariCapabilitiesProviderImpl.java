package com.github.mishaninss.arma.uidriver.ios.safari;

import com.github.mishaninss.arma.data.WebDriverProperties;
import com.github.mishaninss.arma.reporting.IReporter;
import com.github.mishaninss.arma.reporting.Reporter;
import com.github.mishaninss.arma.uidriver.webdriver.DesiredCapabilitiesLoader;
import com.github.mishaninss.arma.uidriver.webdriver.ICapabilitiesProvider;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("safari")
@SuppressWarnings("unused")
public class DefaultSafariCapabilitiesProviderImpl implements ICapabilitiesProvider {

  public static final String CAPABILITIES_PROPERTY_PREFIX = "arma.driver.safari.capability.";
  public static final String CAPABILITIES_FILE_PROPERTY = "arma.driver.safari.capabilities.file";
  private static final String DEFAULT_CAPABILITIES_FILE = "/safari_capabilities.properties";
  @Value("${" + CAPABILITIES_FILE_PROPERTY + ":" + DEFAULT_CAPABILITIES_FILE + "}")
  public String capabilitiesFile;
  @Reporter
  private IReporter reporter;

  @Autowired
  private WebDriverProperties properties;
  @Autowired
  private DesiredCapabilitiesLoader capabilitiesLoader;

  public void setCapabilitiesFile(String fileName) {
    capabilitiesFile = fileName;
  }

  private MutableCapabilities getSafariCapabilities() {
    MutableCapabilities caps = new DesiredCapabilities();
    caps.setCapability("automationName", "XCUITest");
    caps.setCapability(MobileCapabilityType.DEVICE_NAME, properties.driver().deviceName);
    caps.setCapability(MobileCapabilityType.PLATFORM_NAME, properties.driver().platformName);
    caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, properties.driver().platformVersion);
    caps.setCapability(CapabilityType.BROWSER_NAME, "safari");
    caps.setCapability("autoAcceptAlerts", true);
    caps.setCapability("recordVideo", false);
    caps.setCapability("recordScreenshots", false);
    caps.setCapability("maxDuration", 10800);
    caps.setCapability(IOSMobileCapabilityType.AUTO_ACCEPT_ALERTS, true);
    caps.setCapability(IOSMobileCapabilityType.SHOW_IOS_LOG, false);
    caps.setCapability(IOSMobileCapabilityType.SCREENSHOT_WAIT_TIMEOUT, 60);
    caps.setCapability("launchTimeout", 180000);

    return caps;
  }

  @Override
  public MutableCapabilities getCapabilities() {
    MutableCapabilities capabilities = getSafariCapabilities();
    capabilities.merge(
        capabilitiesLoader.loadCapabilities(capabilitiesFile, DesiredCapabilities.class));
    capabilities.merge(capabilitiesLoader.loadEnvironmentProperties());
    capabilities.merge(capabilitiesLoader.loadEnvironmentProperties(CAPABILITIES_PROPERTY_PREFIX,
        DesiredCapabilities.class));
    reporter.debug("Desired capabilities: " + capabilities);

    return capabilities;
  }
}
