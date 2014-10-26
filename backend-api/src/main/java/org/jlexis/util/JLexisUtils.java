/*
 * LexisUtils.java
 * Created on 31.01.2007
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
package org.jlexis.util;


import java.io.File;

public final class JLexisUtils {
    private JLexisUtils() {
    }

    /**
     * Locates the application base directory.
     */
    public final static File getAppBaseDir() {
        // Note: the following code smells bad and should be improved
//    String mainClassName = JLexisMain.class.getCanonicalName ();
//    mainClassName = mainClassName.replace ('.', '/') + ".class";
//
//    URL url = JLexisMain.class.getClassLoader ().getResource (mainClassName);
//
//    if (url == null) return null;
//
//    File appBaseDir;
//    if (url.getProtocol ().equals ("jar"))
//    {
//      String urlPath = url.getPath ();
//      File tmpFile = new File(urlPath.substring (urlPath.indexOf (':') + 1, urlPath.indexOf (mainClassName) - 2));
//      appBaseDir = new File(tmpFile.getParent ());
//    } else if (url.getProtocol ().equals ("file"))
//    {
//      String urlPath = url.getPath ();
//      appBaseDir = new File(urlPath.substring (0, urlPath.indexOf (mainClassName)));
//    } else
//    {
//      return null;
//    }
//
//    return appBaseDir;

        return null;
    }

    public static String convertHTMLEntities(String s) {
        return s.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    }

    public static int getRandomNumber(int exclusiveMaxValue) {
        return (int) Math.round(Math.random() * ((double) exclusiveMaxValue - 1));
    }
}
