/*
 * $Id: PersistableDialog.java 85 2009-03-09 19:04:51Z roland $
 * Created on 29.07.2007
 * 
 * Copyright 2007 Roland Krueger (www.rolandkrueger.info)
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with jLexis; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package info.rolandkrueger.jlexis.gui.dialogs;

import info.rolandkrueger.jlexis.deprecated.ConfigurationManager;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Window;

import javax.swing.JDialog;

/**
 * This class can be used as the superclass for dialogs. The boundaries of the dialog's window will
 * be saved by the {@link ConfigurationManager} each time the dialog is closed. When the application
 * starts this data will be restored from a config file. Thus, the size and position of the dialog
 * is made persistent. 
 * 
 * @author Roland Krueger
 */
public abstract class PersistableDialog extends JDialog
{
  private static final long serialVersionUID = -6171958707605151129L;

  /**
   * Provides a unique identifier for this dialog. With this identifier, the correct data can be
   * fetched from the config file and assigned to the corresponding dialog. 
   * 
   * @return a unique identifier
   */
  protected abstract String getIdentifier ();
  
  public PersistableDialog ()
  {
    init ();
  }
  
  public PersistableDialog (Dialog owner, boolean modal)
  {
    super (owner, modal);
    init ();
  }

  public PersistableDialog (Dialog owner, String title, boolean modal, GraphicsConfiguration gc)
  {
    super (owner, title, modal, gc);
    init ();
  }

  public PersistableDialog (Dialog owner, String title, boolean modal)
  {
    super (owner, title, modal);
    init ();
  }

  public PersistableDialog (Dialog owner, String title)
  {
    super (owner, title);
    init ();
  }

  public PersistableDialog (Dialog owner)
  {
    super (owner);
    init ();
  }

  public PersistableDialog (Frame owner, boolean modal)
  {
    super (owner, modal);
    init ();
  }

  public PersistableDialog (Frame owner, String title, boolean modal, GraphicsConfiguration gc)
  {
    super (owner, title, modal, gc);
    init ();
  }

  public PersistableDialog (Frame owner, String title, boolean modal)
  {
    super (owner, title, modal);
    init ();
  }

  public PersistableDialog (Frame owner, String title)
  {
    super (owner, title);
    init ();
  }

  public PersistableDialog (Frame owner)
  {
    super (owner);
    init ();
  }

  public PersistableDialog (Window owner, ModalityType modalityType)
  {
    super (owner, modalityType);
    init ();
  }

  public PersistableDialog (Window owner, String title, ModalityType modalityType, GraphicsConfiguration gc)
  {
    super (owner, title, modalityType, gc);
    init ();
  }

  public PersistableDialog (Window owner, String title, ModalityType modalityType)
  {
    super (owner, title, modalityType);
    init ();
  }

  public PersistableDialog (Window owner, String title)
  {
    super (owner, title);
    init ();
  }

  public PersistableDialog (Window owner)
  {
    super (owner);
    init ();
  }
  
  private void init ()
  {
    ConfigurationManager.getInstance ().setBoundsForDialogWindow (
        this, getIdentifier ());
  }

  @Override
  public void setVisible (boolean visible)
  {
    if ( ! visible)
      ConfigurationManager.getInstance ().storeDialogWindowBounds (this, getIdentifier ());
    super.setVisible (visible);
  }
}
