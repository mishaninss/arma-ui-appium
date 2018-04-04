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

package com.github.mishaninss.uidriver.ios;

import com.github.mishaninss.uidriver.webdriver.WdWaitingDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class AppiumWaitingDriver extends WdWaitingDriver {

    private static final Map<TemporalUnit, TimeUnit> TEMPORAL_UNIT_TIME_UNIT_MAP = new HashMap<>();

    static {
        TEMPORAL_UNIT_TIME_UNIT_MAP.put(ChronoUnit.MILLIS, TimeUnit.MILLISECONDS);
        TEMPORAL_UNIT_TIME_UNIT_MAP.put(ChronoUnit.SECONDS, TimeUnit.SECONDS);
        TEMPORAL_UNIT_TIME_UNIT_MAP.put(ChronoUnit.MINUTES, TimeUnit.MINUTES);
    }

    @Override
    protected void performWait(ExpectedCondition<?> condition, long timeout, TemporalUnit unit){
        FluentWait<WebDriver> wait = new FluentWait<>(webDriverFactory.getDriver());
        wait.withTimeout(timeout, timeUnitFromTemporalUnit(unit)).until(condition);
    }

    private static TimeUnit timeUnitFromTemporalUnit(TemporalUnit unit){
        return TEMPORAL_UNIT_TIME_UNIT_MAP.getOrDefault(unit, TimeUnit.MILLISECONDS);
    }

}
