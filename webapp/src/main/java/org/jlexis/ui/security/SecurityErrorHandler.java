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

package org.jlexis.ui.security;

import com.google.common.eventbus.EventBus;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.ErrorEvent;
import com.vaadin.server.ErrorHandler;
import org.jlexis.ui.JLexisUI;
import org.jlexis.ui.event.NavigationEvent;
import org.jlexis.ui.navigator.view.AccessDeniedView;
import org.jlexis.ui.navigator.view.LoginView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;

/**
 * @author Roland Krüger
 */
public class SecurityErrorHandler implements ErrorHandler {

    private static Logger LOG = LoggerFactory.getLogger(SecurityErrorHandler.class);
    private EventBus eventbus;
    private Navigator navigator;

    public SecurityErrorHandler(EventBus eventbus, Navigator navigator) {
        this.eventbus = eventbus;
        this.navigator = navigator;
    }

    @Override
    public void error(ErrorEvent event) {
        LOG.error("Error handler caught exception {}", event.getThrowable().getClass().getName());
        if (event.getThrowable() instanceof AccessDeniedException || event.getThrowable().getCause() instanceof AccessDeniedException) {
            if (JLexisUI.getCurrent().isUserAnonymous() && !navigator.getState().startsWith(LoginView.NAME)) {
                eventbus.post(new NavigationEvent(this, LoginView.loginPathForRequestedView(navigator.getState())));
            } else if (!JLexisUI.getCurrent().isUserAnonymous()) {
                eventbus.post(new NavigationEvent(this, AccessDeniedView.NAME));
            }
        } else {
            // handle other exceptions a bit more graciously than this
            event.getThrowable().printStackTrace();
        }
    }
}
