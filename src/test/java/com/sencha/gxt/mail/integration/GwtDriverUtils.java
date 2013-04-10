package com.sencha.gxt.mail.integration;

/*
 * #%L
 * gxt-mail-sample
 * %%
 * Copyright (C) 2013 Sencha Inc
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.openqa.selenium.WebDriver;
import org.senchalabs.gwt.gwtdriver.models.GwtWidget;
import org.senchalabs.gwt.gwtdriver.models.GwtWidgetFinder;

public class GwtDriverUtils {
  public static <W extends GwtWidget<T>, T extends GwtWidgetFinder<W>> T find(Class<W> widgetType, WebDriver driver) {
    Type i = widgetType;
    do {
      if (i instanceof ParameterizedType) {
        ParameterizedType t = (ParameterizedType) i;
        if (t.getRawType() == GwtWidget.class) {
          @SuppressWarnings("unchecked")
          Class<T> finderType = (Class<T>) t.getActualTypeArguments()[0];

          T instance = createInstance(finderType);
          if (instance != null) {
            instance.withDriver(driver);
            return instance;
          }
        }
      }
      i = (i instanceof Class) ? ((Class<?>)i).getGenericSuperclass() : null;
    } while(i != null);
    return null;
  }
  private static <T extends GwtWidgetFinder<?>> T createInstance(Class<T> type) {
    try {
      return type.newInstance();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    return null;
  }
}
