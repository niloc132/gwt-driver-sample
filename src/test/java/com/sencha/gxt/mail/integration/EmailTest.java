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

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.senchalabs.gwt.gwtdriver.by.ByWidget;
import org.senchalabs.gwt.gwtdriver.by.FasterByChained;
import org.senchalabs.gwt.gwtdriver.gxt.models.Button;
import org.senchalabs.gwt.gwtdriver.gxt.models.Field;
import org.senchalabs.gwt.gwtdriver.gxt.models.ListView;
import org.senchalabs.gwt.gwtdriver.gxt.models.Panel;
import org.senchalabs.gwt.gwtdriver.gxt.models.Window;
import org.senchalabs.gwt.gwtdriver.models.GwtWidget;

public class EmailTest {
  private WebDriver driver;

  @Before
  public void setup() throws Exception {
    driver = new FirefoxDriver();
    driver.manage().timeouts().setScriptTimeout(1000, TimeUnit.MILLISECONDS);

    driver.get("http://localhost:9080/app");

    Window loginDialog = GwtWidget.find(Window.class, driver).withHeading("log in").done();

    loginDialog.find(Field.class).withLabel("User").done().sendKeys("colin");
    loginDialog.find(Field.class).withLabel("Password").done().sendKeys("mypassword");

    loginDialog.find(Button.class).withText("Login").done().click();

    //wait until ready
    GwtWidget.find(Panel.class, driver, driver.findElement(By.tagName("body"))).withHeading("Email").waitFor();
  }

  @After
  public void teardown() {
    driver.close();
  }

  @Test
  public void test() throws Exception {
    Panel email = GwtWidget.find(Panel.class, driver, driver.findElement(By.tagName("body"))).withHeading("Email").done();

    assert email.isCollapsed();//Shouldn't be, but okay for now
    email.getHeaderElement().click();
    assert !email.isCollapsed();

    //okay, cheating into regular webdriver territory
    WebElement mailboxElt = email.getElement().findElement(new FasterByChained(By.xpath(".//*"),
            new ByWidget(driver, com.sencha.gxt.widget.core.client.ListView.class)));
    ListView mailbox = new ListView(driver, mailboxElt);

    mailbox.clickItemWithText("Inbox");

    Panel inbox = GwtWidget.find(Panel.class, driver, driver.findElement(By.tagName("body"))).withHeading("Insert mailbox items here").done();

    assert inbox.getElement().isDisplayed();
  }
}
