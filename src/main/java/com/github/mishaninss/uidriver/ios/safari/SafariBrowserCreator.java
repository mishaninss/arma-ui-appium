/*
 * Copyright 2018 Sergey Mishanin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.mishaninss.uidriver.ios.safari;

import com.github.mishaninss.data.WebDriverProperties;
import com.github.mishaninss.exceptions.FrameworkConfigurationException;
import com.github.mishaninss.uidriver.webdriver.ICapabilitiesProvider;
import com.github.mishaninss.uidriver.webdriver.IWebDriverCreator;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.concurrent.TimeUnit;

@Component
@Profile("safari")
@SuppressWarnings("unused")
public class SafariBrowserCreator implements IWebDriverCreator {
    private static final String COULD_NOT_START_SESSION_MESSAGE = "Could not start a new browser session";

    @Autowired
    private WebDriverProperties properties;
    @Autowired
    private ICapabilitiesProvider capabilitiesProvider;

    @Override
    public WebDriver createDriver(Capabilities desiredCapabilities) {
        WebDriver webDriver;
        Capabilities capabilities = capabilitiesProvider.getCapabilities();
        capabilities.merge(desiredCapabilities);

        try {
            String gridUrl = properties.driver().gridUrl;
            webDriver = new IOSDriver<>(new URL(gridUrl), capabilities);
        } catch (Exception ex) {
            throw new FrameworkConfigurationException(COULD_NOT_START_SESSION_MESSAGE, ex);
        }

        webDriver.manage().timeouts().implicitlyWait(properties.driver().timeoutsElement, TimeUnit.MILLISECONDS);
        webDriver.manage().timeouts().pageLoadTimeout(properties.driver().timeoutsPageLoad, TimeUnit.MILLISECONDS);
        return webDriver;
    }
}
