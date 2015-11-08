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

import com.vaadin.navigator.View;
import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import org.jlexis.ui.JLexisUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;

public abstract class AbstractView extends Panel implements View {
	private Logger LOG = LoggerFactory.getLogger(AbstractView.class);

	private VerticalLayout layout;

	public AbstractView() {
		setSizeFull();
		layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);
		setContent(layout);
	}

	public void addComponent(Component c) {
		layout.addComponent(c);
	}

	protected void registerWithEventbus() {
		JLexisUI.getCurrent().getEventbus().register(this);
	}

	@PreDestroy
	public void destroy() {
		LOG.debug("About to destroy {}", getClass().getName());
		JLexisUI.getCurrent().getEventbus().unregister(this);
	}
}
