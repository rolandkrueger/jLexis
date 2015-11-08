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

package org.jlexis.ui.navigator.view;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;

@SpringView(name = AccessDeniedView.NAME)
public class AccessDeniedView extends AbstractView  {

    public static final String NAME = "accessDenied";

    public AccessDeniedView() {
        addComponent(new Label("<h1>Access Denied!</h1>", ContentMode.HTML));
        addComponent(new Label("You don't have required permission to access this resource."));
        Link homeLink = new Link("Home", new ExternalResource("#"));
        homeLink.setIcon(FontAwesome.HOME);
        addComponent(homeLink);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

}
