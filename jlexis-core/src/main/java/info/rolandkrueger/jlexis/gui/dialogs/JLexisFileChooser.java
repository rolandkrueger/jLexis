/*
 * $Id: LexisFileChooser.java 85 2009-03-09 19:04:51Z roland $
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

import java.awt.Component;
import java.awt.HeadlessException;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

public class JLexisFileChooser extends JFileChooser
{
  private static final long serialVersionUID = 6352759838748052501L;

  public JLexisFileChooser ()
  {
    super ();
    setDirectory ();
  }

  public JLexisFileChooser (File currentDirectory, FileSystemView fsv)
  {
    super (currentDirectory, fsv);
  }

  public JLexisFileChooser (File currentDirectory)
  {
    super (currentDirectory);
  }

  public JLexisFileChooser (FileSystemView fsv)
  {
    super (fsv);
    setDirectory ();
  }

  public JLexisFileChooser (String currentDirectoryPath, FileSystemView fsv)
  {
    super (currentDirectoryPath, fsv);
  }

  public JLexisFileChooser (String currentDirectoryPath)
  {
    super (currentDirectoryPath);
  }
  
  private void setDirectory ()
  {
    String dir = ConfigurationManager.getInstance ().getFileChooserStartDirectory ();
    if (dir == null) dir = System.getProperty ("user.home");
    File directory = new File (dir);
    if ( ! directory.exists ()) directory = new File (System.getProperty ("user.home"));
    setCurrentDirectory (directory);
  }

  @Override
  public int showDialog (Component parent, String approveButtonText) throws HeadlessException
  {
    int result = super.showDialog (parent, approveButtonText);
    ConfigurationManager.getInstance ().setFileChooserStartDirectory (
        getCurrentDirectory ().getAbsolutePath ());
    return result;
  }

  @Override
  public int showOpenDialog (Component parent) throws HeadlessException
  {
    int result = super.showOpenDialog (parent);
    ConfigurationManager.getInstance ().setFileChooserStartDirectory (
        getCurrentDirectory ().getAbsolutePath ());
    return result;
  }

  @Override
  public int showSaveDialog (Component parent) throws HeadlessException
  {
    int result = super.showSaveDialog (parent);
    ConfigurationManager.getInstance ().setFileChooserStartDirectory (
        getCurrentDirectory ().getAbsolutePath ());
    return result;

  }

}
