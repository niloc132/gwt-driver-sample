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

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.ToStringValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.ListView;
import com.sencha.gxt.widget.core.client.ListViewSelectionModel;
import com.sencha.gxt.widget.core.client.button.ToolButton;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer.AccordionLayoutAppearance;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

public class MainAppView implements IsWidget {
  private HorizontalLayoutContainer root;
  private SimpleContainer placeholder;
  @Override
  public Widget asWidget() {
    if (root == null) {
      root = new HorizontalLayoutContainer();

      //left region, accordion of possible options
      AccordionLayoutContainer left = new AccordionLayoutContainer();

      AccordionLayoutAppearance appearance = GWT.create(AccordionLayoutAppearance.class);
      ContentPanel mail = new ContentPanel(appearance);
      mail.setHeadingText("Email");
      mail.setWidget(getMailWidget());
      left.add(mail);

      ContentPanel calendar = new ContentPanel(appearance);
      calendar.setHeadingText("Calendar");
      calendar.setWidget(getCalendarWidget());
      left.add(calendar);

      ContentPanel contacts = new ContentPanel(appearance);
      contacts.setHeadingText("Contacts");
      contacts.setWidget(getContactsWidget());
      left.add(contacts);

      root.add(left, new HorizontalLayoutData(200, 1));

      // main region, placeholder for whatever other content needs to be drawn
      placeholder = new SimpleContainer();
      root.add(placeholder, new HorizontalLayoutData(1, 1));
    }
    return root;
  }

  private Widget getMailWidget() {
    VerticalLayoutContainer wrapper = new VerticalLayoutContainer();

    ToolBar options = new ToolBar();
    ToolButton newBtn = new ToolButton(ToolButton.PLUS);
    ToolButton searchBtn = new ToolButton(ToolButton.SEARCH);
    ToolButton configBtn = new ToolButton(ToolButton.GEAR);
    options.add(newBtn);
    options.add(searchBtn);
    options.add(configBtn);
    wrapper.add(options, new VerticalLayoutData(1, -1));

    //TODO make a mailbox object
    //TODO consider a Tree rather than a List structure for mailboxes
    //TODO get mailbox list/tree from server
    ListStore<String> store = new ListStore<String>(new ModelKeyProvider<String>() {
      @Override
      public String getKey(String item) {
        return item;
      }
    });
    store.add("Inbox");
    store.add("Drafts");
    store.add("Sent");
    store.add("Trash");

    ListView<String, String> mailboxList = new ListView<String, String>(store, new ToStringValueProvider<String>());
    ListViewSelectionModel<String> selectionModel = mailboxList.getSelectionModel();
    selectionModel.setSelectionMode(SelectionMode.SINGLE);
    selectionModel.addSelectionHandler(new SelectionHandler<String>() {
      @Override
      public void onSelection(SelectionEvent<String> event) {
        ContentPanel panel = new ContentPanel();
        panel.setHeadingText(event.getSelectedItem());
        //TODO:
        panel.setWidget(new Label("Insert mailbox items here"));
        setMainWidget(panel);
      }

    });

    wrapper.add(mailboxList, new VerticalLayoutData(1, 1));

    return wrapper;
  }

  private Widget getContactsWidget() {
    return new Label("TODO");
  }

  private Widget getCalendarWidget() {
    return new Label("TODO");
  }


  private void setMainWidget(ContentPanel panel) {
    placeholder.setWidget(panel);
    placeholder.forceLayout();
  }

}
