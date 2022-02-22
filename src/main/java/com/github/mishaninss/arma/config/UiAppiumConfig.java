package com.github.mishaninss.arma.config;

import com.github.mishaninss.arma.aspects.AppiumInteractiveExceptionBuilderImpl;
import com.github.mishaninss.arma.aspects.IInteractiveElementExceptionBuilder;
import com.github.mishaninss.arma.html.containers.AppDefaultEventHandlersProviderImpl;
import com.github.mishaninss.arma.html.containers.interfaces.IDefaultEventHandlersProvider;
import com.github.mishaninss.arma.uidriver.android.AndroidLocatorConverter;
import com.github.mishaninss.arma.uidriver.android.app.AndroidAppWebElementProvider;
import com.github.mishaninss.arma.uidriver.annotations.ElementDriver;
import com.github.mishaninss.arma.uidriver.annotations.WaitingDriver;
import com.github.mishaninss.arma.uidriver.interfaces.IElementDriver;
import com.github.mishaninss.arma.uidriver.interfaces.IWaitingDriver;
import com.github.mishaninss.arma.uidriver.ios.IosLocatorConverter;
import com.github.mishaninss.arma.uidriver.ios.app.AppElementDriver;
import com.github.mishaninss.arma.uidriver.ios.app.AppWaitingDriver;
import com.github.mishaninss.arma.uidriver.ios.app.AppWebElementProvider;
import com.github.mishaninss.arma.uidriver.webdriver.LocatorConverter;
import com.github.mishaninss.arma.uidriver.webdriver.WebElementProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Configuration
@Import(UiWdConfig.class)
@SuppressWarnings("unused")
public class UiAppiumConfig {

  @Bean
  @Profile({"ios", "android"})
  public IDefaultEventHandlersProvider defaultEventHandlersProvider() {
    return new AppDefaultEventHandlersProviderImpl();
  }

  @Bean
  public IInteractiveElementExceptionBuilder exceptionBuilder() {
    return new AppiumInteractiveExceptionBuilderImpl();
  }

  @Bean
  @WaitingDriver
  @Profile({"ios", "android"})
  public IWaitingDriver waitingDriver() {
    return new AppWaitingDriver();
  }

  @Bean
  @ElementDriver
  @Profile("ios")
  public IElementDriver elementDriver() {
    return new AppElementDriver();
  }

  @Bean("webElementProvider")
  @Profile("ios")
  public WebElementProvider webElementProvider() {
    return new AppWebElementProvider();
  }

  @Bean("webElementProvider")
  @Profile("android")
  public WebElementProvider androidWebElementProvider() {
    return new AndroidAppWebElementProvider();
  }

  @Bean("locatorConverter")
  @Profile("android")
  public LocatorConverter androidLocatorConverter() {
    return new AndroidLocatorConverter();
  }

  @Bean("locatorConverter")
  @Profile("ios")
  public LocatorConverter iosLocatorConverter() {
    return new IosLocatorConverter();
  }
}
