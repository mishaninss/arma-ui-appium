package com.github.mishaninss.arma.uidriver.ios.app;

import com.github.mishaninss.arma.data.WebDriverProperties;
import com.github.mishaninss.arma.uidriver.webdriver.IWebDriverFactory;
import io.appium.java_client.AppiumDriver;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Sergey Mishanin
 */
@Component
public class AppiumAppDriver implements IAppDriver {

  @Autowired
  private WebDriverProperties properties;
  @Autowired
  private IWebDriverFactory webDriverFactory;

  private AppiumDriver<?> getDriver() {
    return (AppiumDriver<?>) webDriverFactory.getDriver();
  }

  @Override
  public String getContext() {
    return getDriver().getContext();
  }

  @Override
  public Set<String> getContextHandles() {
    return getDriver().getContextHandles();
  }

  @Override
  public void setContext(String contextName) {
    getDriver().context(contextName);
  }

}
