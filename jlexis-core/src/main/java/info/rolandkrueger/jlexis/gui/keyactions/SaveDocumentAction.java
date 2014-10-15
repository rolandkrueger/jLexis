/*
 * SaveDocumentAction.java
 * Created on 06.05.2007
 * 
 * Copyright 2007 Roland Krueger (www.rolandkrueger.info)
 * 
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
package info.rolandkrueger.jlexis.gui.keyactions;

import info.rolandkrueger.jlexis.data.I18NResources;
import info.rolandkrueger.jlexis.data.I18NResources.I18NResourceKeys;
import info.rolandkrueger.jlexis.gui.containers.EditVocabularyPanel;
import info.rolandkrueger.jlexis.managers.JLexisResourceManager;
import info.rolandkrueger.jlexis.managers.JLexisResourceManager.ResourcesEnums;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;

@Deprecated
public class SaveDocumentAction extends AbstractKeyAction
{
  private static final long serialVersionUID = 8137742453782301265L;
  private final static String sIdentifier = "keyaction.saveDocument";
  
  private EditVocabularyPanel mEditPanel;
  
  public SaveDocumentAction ()
  {
    super (I18NResources.getString (I18NResourceKeys.OPEN_FILE_KEY),
           JLexisResourceManager.getInstance ().getIcon (ResourcesEnums.OPEN_LEARNING_UNIT_ICON), sIdentifier,
           InputEvent.CTRL_MASK, 's' - 'a' + 1);
  }
  
  public void actionPerformed (ActionEvent e)
  {
    System.err.println (SaveDocumentAction.class.getName () + ".actionPerformed() not implemented");
//    mEditPanel = getMain ().getTopMostEditPanel ();
//    if (mEditPanel == null) return;
//    LexisVocabularyFile file = mEditPanel.getEditedFile ();
//    if (file == null) return;
//    mEditPanel.saveCurrentInput ();
//    try
//    {
//      file.saveToFile ();
//    } catch (FileNotFoundException e1)
//    {
//      // TODO Auto-generated catch block
//      e1.printStackTrace();
//      return;
//    } catch (ParserConfigurationException e1)
//    {
//      // TODO Auto-generated catch block
//      e1.printStackTrace();
//      return;
//    } catch (TransformerFactoryConfigurationError e1)
//    {
//      // TODO Auto-generated catch block
//      e1.printStackTrace();
//      return;
//    } catch (TransformerException e1)
//    {
//      // TODO Auto-generated catch block
//      e1.printStackTrace();
//      return;
//    } catch (IOException e2)
//    {
//      // TODO Auto-generated catch block
//      e2.printStackTrace();
//      return;
//    }
//    // TODO: I18N
//    getMain ().getStatusBar ().setTimedMessage (String.format ("%s gespeichert", 
//        file.getFile ().getAbsolutePath ()));
  }
}
