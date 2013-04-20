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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.senchalabs.gwt.gwtdriver.gxt.models.Button;
import org.senchalabs.gwt.gwtdriver.gxt.models.Field;
import org.senchalabs.gwt.gwtdriver.gxt.models.Window;

public class AuthenticationTest {
  private WebDriver driver;

  @Before
  public void setup() {
    driver = new FirefoxDriver();
    driver.manage().timeouts().setScriptTimeout(1000, TimeUnit.MILLISECONDS);
  }
  @After
  public void teardown() {
    driver.close();
  }

  @Test
  public void testLogin() throws Exception {
    driver.get("http://localhost:9080/app");
    Window loginDialog = GwtDriverUtils.find(Window.class, driver).withHeading("log in").done();

    loginDialog.find(Field.class).withLabel("User").done().sendKeys("colin");
    loginDialog.find(Field.class).withLabel("Password").done().sendKeys("mypassword");

    loginDialog.find(Button.class).withText("Login").done().click();

    //gotcha!
    Thread.sleep(1000);

    Window welcome = GwtDriverUtils.find(Window.class, driver).withHeading("Welcome Back!").done();
    assert welcome.getElement().isDisplayed();
  }

  @Test
  public void testRegister() throws Exception {
    driver.get("http://localhost:9080/app");
    Window loginDialog = GwtDriverUtils.find(Window.class, driver).withHeading("log in").done();

    loginDialog.find(Button.class).withText("Register").done().click();

    Window registerDialog = GwtDriverUtils.find(Window.class, driver).withHeading("Create new Account").done();

    Field email = registerDialog.find(Field.class).withLabel("Email Address").done();
    email.sendKeys("test@test.com");
    Field password = registerDialog.find(Field.class).withLabel("Password").done();
    password.sendKeys("password123");
    Field verifyPw = registerDialog.find(Field.class).withLabel("Verify Password").done();
    verifyPw.sendKeys("password123");

    //Bug in finder code...
    registerDialog.find(Button.class).withText("Create Account").done().click();

    Window welcome = GwtDriverUtils.find(Window.class, driver).withHeading("Welcome!").done();
    assert welcome.getElement().isDisplayed();
  }

  @Test
  public void testRegisterPasswordMismatch() throws Exception {
    driver.get("http://localhost:9080/app");
    Window loginDialog = GwtDriverUtils.find(Window.class, driver).withHeading("log in").done();

    loginDialog.find(Button.class).withText("Register").done().click();

    Window registerDialog = GwtDriverUtils.find(Window.class, driver).withHeading("Create new Account").done();

    Field email = registerDialog.find(Field.class).withLabel("Email Address").done();
    email.sendKeys("test@test.com");
    Field password = registerDialog.find(Field.class).withLabel("Password").done();
    password.sendKeys("password123");
    Field verifyPw = registerDialog.find(Field.class).withLabel("Verify Password").done();
    verifyPw.sendKeys("abcdefghijk");

    registerDialog.find(Button.class).withText("Create Account").done().click();

    Window welcome = GwtDriverUtils.find(Window.class, driver).withHeading("Please re-enter matching password").done();
    assert welcome.getElement().isDisplayed();
  }
}
