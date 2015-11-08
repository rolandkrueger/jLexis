/*
 * Copyright 2007-2015 Roland Krueger (www.rolandkrueger.info)
 *
 * This file is part of jLexis.
 *
 * jLexis is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * jLexis is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with jLexis; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package org.jlexis.ui;

import com.google.common.eventbus.EventBus;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.UI;
import info.rolandkrueger.userservice.api.model.UserApiData;
import org.jlexis.ui.navigator.view.ErrorView;
import org.jlexis.ui.security.SecurityErrorHandler;
import org.jlexis.ui.security.ViewAccessDecisionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.PostConstruct;

/**
 * @author Roland Krüger
 */
@SpringUI(path = "")
@Theme("valo")
@PreserveOnRefresh
public class JLexisUI extends UI {

    @Autowired
    private SpringViewProvider viewProvider;
    private EventBus eventbus;

    @Autowired
    private ViewAccessDecisionManager viewAccessDecisionManager;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        buildNavigator();

        VaadinSession.getCurrent().setErrorHandler(new SecurityErrorHandler(eventbus, getNavigator()));

        viewAccessDecisionManager.checkAccessRestrictionForRequestedView(getNavigator());

        Page.getCurrent().setTitle("jLexis");
    }

    public UserApiData getCurrentUser() {
        if (isUserAnonymous()) {
            return null;
        } else {
            return (UserApiData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
    }

    private void buildNavigator() {
        Navigator navigator = new Navigator(this, this);
        navigator.addProvider(viewProvider);
        navigator.setErrorView(ErrorView.class);
        setNavigator(navigator);
    }

    public static JLexisUI getCurrent() {
        return (JLexisUI) UI.getCurrent();
    }

    @PostConstruct
    private void initEventbus() {
        eventbus = new EventBus("main");
        eventbus.register(this);
    }

    public EventBus getEventbus() {
        return eventbus;
    }

    public boolean isUserAnonymous() {
        return SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken;
    }
}
