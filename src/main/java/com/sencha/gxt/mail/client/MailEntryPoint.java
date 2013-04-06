package com.sencha.gxt.mail.client;

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

import com.google.gwt.core.client.EntryPoint;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.TextField;

/**
 * Simple starting point for an application - login doesn't
 * actually work, but it gets us some content on the page.
 *
 */
public class MailEntryPoint implements EntryPoint {
  public void onModuleLoad() {
    //Simple login dialog
    Window login = new Window();
    FlowLayoutContainer form = new FlowLayoutContainer();

    TextField username = new TextField();
    form.add(new FieldLabel(username, "User"));

    PasswordField password = new PasswordField();
    form.add(new FieldLabel(password, "Password"));

    login.setWidget(form);
    login.setHeadingText("Please log in");
    login.addButton(new TextButton("Login"));
    login.addButton(new TextButton("Register"));
    login.setModal(true);

    login.show();
  }
}
