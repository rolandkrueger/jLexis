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

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.navigator.SpringViewProvider;
import org.jlexis.ui.spring.security.VaadinAccessDecisionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.annotation.SecuredAnnotationSecurityMetadataSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.util.SimpleMethodInvocation;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.util.Collection;

/**
 * @author Roland Krüger
 */
@Component
@UIScope
public class ViewAccessDecisionManager {

    @Autowired
    private SpringViewProvider viewProvider;

    @Autowired
    private VaadinAccessDecisionManager accessDecisionManager;

    public void checkAccessRestrictionForRequestedView(Navigator navigator) {
        final View targetView = viewProvider.getView(navigator.getState());

        if (targetView != null) {
            final Collection<ConfigAttribute> attributes = new SecuredAnnotationSecurityMetadataSource()
                    .getAttributes(new SimpleMethodInvocation(targetView, ReflectionUtils.findMethod(View.class, "enter", ViewChangeListener.ViewChangeEvent.class)));
            try {
                accessDecisionManager.decide(SecurityContextHolder.getContext().getAuthentication(), targetView, attributes);
            } catch (AccessDeniedException adExc) {
                // must be ignored as this exception is already handled in the AccessDecisionManager
            }
        }
    }
}
