/*
 * $Id: LexisPersistenceManager.java 204 2009-12-17 15:20:16Z roland $
 * Created on 30.10.2008
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
package info.rolandkrueger.jlexis.managers;

import info.rolandkrueger.jlexis.JLexisMain;

import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

/**
 * <p>This class is the central entry-point for all database-related operations. The 
 * {@link JLexisPersistenceManager} takes care of establishing and managing Hibernate
 * database sessions and manages all data transactions from and to the database.</p>
 * <p>
 * The {@link JLexisPersistenceManager} is a singleton class.
 * </p>
 * @author Roland Krueger
 * $Id: LexisPersistenceManager.java 204 2009-12-17 15:20:16Z roland $
 */
public class JLexisPersistenceManager
{
  private final static JLexisPersistenceManager sInstance = new JLexisPersistenceManager ();
  
  private Configuration  mConfiguration;
  private SessionFactory mSessionFactory;
  private Session        mSession;
  
  private JLexisPersistenceManager () {};
  
  public static JLexisPersistenceManager getInstance ()
  {
    return sInstance;
  }

  public void selectDatabase ()
  {
    JFileChooser fileChooser = new JFileChooser ();
    // TODO: i18n
    fileChooser.setDialogTitle ("Datenbankdatei ausw\u00E4hlen");
    fileChooser.showOpenDialog (JLexisMain.getInstance ().getMainWindow ());
    File databaseFile = fileChooser.getSelectedFile ();
    if (databaseFile != null)
    {
      ConfigurationManager.getInstance ().setDatabaseFile (databaseFile);
    }
  }
  
  public boolean isDatabaseFileReadable ()
  {    
    File databaseFile = ConfigurationManager.getInstance ().getDatabaseFile ();
    System.out.println (databaseFile.getAbsolutePath ());
    return databaseFile.exists () && databaseFile.canRead ();
  }
  
  public void setUpDatabaseConnection ()
  {
    mConfiguration = new Configuration ();
    mConfiguration.setProperty ("hibernate.connection.url", 
        ConfigurationManager.getInstance ().getDatabaseURL ());
    mConfiguration.configure ();
    try
    {
      mSessionFactory = mConfiguration.buildSessionFactory ();      
    } catch (Throwable hExc)
    {
      if (hExc.getCause () instanceof FileNotFoundException)
        System.out.println ("DATABASE FILE NOT FOUND");
    }
  }
  
  public void flushSession ()
  {
    mSession.flush ();
  }
  
  public void closeDatabaseConnection ()
  {
    mSession.flush ();
    mSession.close ();
    mSessionFactory.close ();
    mSession = null;
  }
  
  public void exportSchema ()
  {
    if (mConfiguration == null)
      throw new IllegalStateException ("Connection hasn't been established yet.");
    SchemaExport export = new SchemaExport (mConfiguration);
    export.create (false, true);
  }
  
  public Session getSession ()
  {
    if (mSessionFactory.isClosed ())
      throw new IllegalStateException ("Database connection has already been closed");
    if (mSession == null)
      mSession = mSessionFactory.openSession ();
    return mSession;
  }
  
  public void saveObject (Object o)
  {
    Session session = getSession ();
    Transaction tx = session.beginTransaction ();
    session.save (o);
    tx.commit ();
  }
  
  public void saveOrUpdateObject (Object o)
  {
    Session session = getSession ();
    Transaction tx = session.beginTransaction ();
    session.saveOrUpdate (o);
    tx.commit ();
  }
  
  public void deleteObject (Object o)
  {
    Session session = getSession ();
    Transaction tx = session.beginTransaction ();
    session.delete (o);
    tx.commit ();
  }
}



