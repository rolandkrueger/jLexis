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

import com.vaadin.data.util.ObjectProperty;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;

@SpringView(name = ErrorView.NAME)
public class ErrorView extends AbstractView {

	public final static String NAME = "error";

	private ObjectProperty<String> errorMessage;

	public ErrorView() {
		errorMessage = new ObjectProperty<String>("");
		Label errorMessageLabel = new Label(errorMessage, ContentMode.HTML);
		addComponent(errorMessageLabel);
//		addComponent(new GoToMainViewLink());
	}

	@Override
	public void enter(ViewChangeEvent event) {
		errorMessage
				.setValue("<h1>Oops, page not found!</h1><hr/>"
						+ "Unfortunately, the page with name <em>"
						+ event.getViewName()
						+ "</em> is unknown to me :-( <br/>Please try something different.");
	}
}
