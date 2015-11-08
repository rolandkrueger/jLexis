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

package org.jlexis.ui.spring.security;

import com.google.common.eventbus.EventBus;
import org.jlexis.ui.JLexisUI;
import org.jlexis.ui.event.NavigationEvent;
import org.jlexis.ui.navigator.view.AccessDeniedView;
import org.jlexis.ui.navigator.view.LoginView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class VaadinAccessDecisionManager implements AccessDecisionManager {

    private Logger LOG = LoggerFactory.getLogger(VaadinAccessDecisionManager.class);

    private AccessDecisionManager delegate;

    public void setAccessDecisionManager(AccessDecisionManager accessDecisionManager) {
        this.delegate = accessDecisionManager;
    }

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException {
        try {
            if (configAttributes == null) {
                // no action if an object with no access restrictions is visited
                return;
            }
            // delegate access decision itself to super class
            delegate.decide(authentication, object, configAttributes);
        } catch (AccessDeniedException adExc) {
            // we handle security exceptions in the Vaadin way, i. e. we publish appropriate events on the event bus instead
            // of redirecting to some error page (remember that we've a single-page application)
            final EventBus eventbus = JLexisUI.getCurrent().getEventbus();
            if (JLexisUI.getCurrent().isUserAnonymous()) {
                // if the user is not logged in, send her to the login view
                eventbus.post(new NavigationEvent(this, LoginView.loginPathForRequestedView(JLexisUI.getCurrent().getNavigator().getState())));
                throw adExc;
            } else {
                // if she is logged in but doesn't have adequate access rights, send her to the access denied view
                eventbus.post(new NavigationEvent(this, AccessDeniedView.NAME));
                throw adExc;
            }
        } catch (Exception exc) {
            LOG.info("Exception after authentication decision: {}", exc.getClass().getName());
            exc.printStackTrace();
        }
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return delegate.supports(attribute);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return delegate.supports(clazz);
    }
}
