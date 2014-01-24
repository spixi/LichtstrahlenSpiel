package de.bwvaachen.beamoflightgame.i18n;

/*
Copyright (C) 2013 - 2014 by Andreas Pauls, Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 
 * @author Marius
 * I18N allows converting strings to localized strings.
 * 
 * This API is modeled to be similar to use like the GNU gettext utility.
 * To use this class, just type @code{import static de.bwvaachen.beamoflightgame.i18n.I18N.*}
 * before your class definition.
 */
public final class I18N {
	private static UFT8ResourceBundle res;
	
	/**
	 * 
	 * @author Marius
	 * Since .property files are usually read in ISO-8859-1 encoding, we apply a trick and convert them to UTF-8.
	 * @see{java.util.Properties}
	 *
	 */
	private static class UFT8ResourceBundle extends ResourceBundle {
		private ResourceBundle decorated;
		private HashMap<String,String> stringBuffer;
		
		private static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
		private static final Charset UTF8       = Charset.forName("UTF-8");
		
		public UFT8ResourceBundle(ResourceBundle theBundle) {
			decorated = theBundle;
			stringBuffer = new HashMap<String,String>();
		}
		
		public String getUTF8String(String key) {
			String theString = stringBuffer.get(key);
			if(theString==null) {
				String nativeString = decorated.getString(key);
				theString = new String(nativeString.getBytes(ISO_8859_1),UTF8);
				stringBuffer.put(key, theString);
			}
			return theString;
		}

		@Override
		public Enumeration<String> getKeys() {
			return decorated.getKeys();
		}

		@Override
		protected Object handleGetObject(String key) {
			return decorated.getObject(key);
		}
	}
	
	static {
		res = new UFT8ResourceBundle(ResourceBundle.getBundle("de.bwvaachen.beamoflightgame.i18n.messages", Locale.getDefault()));
	}

	/**
	 * The _ function returns the localized form of the string ID
	 * @param the string ID
	 * @return the localized string
	 */
	public static String _(String ID) {
		return res.getUTF8String(ID);
	}
	
	/**
	 * The _p function returns the localized form of the string ID.
	 * unless the _ function it will return the plural form if
	 * n is either 1 or -1. 
	 * @param the string ID
	 * @param the number
	 * @return the localized string
	 */
	//but will consider plural forms
	public static String _p(String ID, int n) {
		return _((Math.abs(n) == 1) ? ID : ID + "_plural");
	}
	
	/**
	 * The _f function returns the localized formatted form of the string ID
	 * @param the string ID
	 * @param the arguments for String.format
	 * @return the localized string
	 * @see{java.lang.String.format}
	 */
	//The _f function returns the localized form of the formatted string ID
	public static String _f(String ID, Object... o) {
		return String.format(_(ID),o);
	}
	
	/**
	 * The _pf function returns the localized formatted form of the string ID.
	 * unless the _f function it will return the plural form if
	 * n is either 1 or -1. 
	 * @param the string ID
	 * @param the number
	 * @param the arguments for String.format
	 * @return the localized string
	 */
	//The _ function returns the localized form of the formatted string ID,
	//but will consider plural forms
	public static String _pf(String ID, int n, Object... o) {
		return String.format(_p(ID, n), o);
	}
}
