/*  Copyright (C) 2003-2011 JabRef contributors.
    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License along
    with this program; if not, write to the Free Software Foundation, Inc.,
    51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
*/
package net.sf.jabref.autocompleter;

import java.util.SortedSet;
import java.util.TreeSet;

import net.sf.jabref.BibtexEntry;

/**
 * An autocompleter delivers possible completions for a given String. There are
 * different types of autocompleters for different use cases.
 * 
 * Example: {@link NameFieldAutoCompleter}, {@link EntireFieldAutoCompleter}
 * 
 * @author kahlert, cordes
 * @see AutoCompleterFactory
 */
public abstract class AbstractAutoCompleter {

	public static final int SHORTEST_TO_COMPLETE = 2;
	public static final int SHORTEST_WORD = 4;

	private TreeSet<String> _index = new TreeSet<String>();

	/**
	 * Add a BibtexEntry to this autocompleter. The autocompleter (respectively
	 * to the concrete implementations of {@link AbstractAutoCompleter}) itself
	 * decides which information should be stored for later completion.
	 * 
	 */
	abstract public void addBibtexEntry(BibtexEntry entry);

	abstract public boolean isSingleUnitField();

	/**
	 * Returns one or more possible completions for a given String. The returned
	 * completion depends on which informations were stored while adding
	 * BibtexEntries by the used implementation of {@link AbstractAutoCompleter}
	 * .
	 * 
	 * @see AbstractAutoCompleter#addBibtexEntry(BibtexEntry)
	 */
	public String[] complete(String str) {
		if (stringMinLength(str))
			return null;
		String ender = incrementLastCharacter(str);
		SortedSet<String> subset = _index.subSet(str, ender);
		return subset.toArray(new String[0]);
	}

	/**
	 * Increments the last character of a string.
	 * 
	 * Example: incrementLastCharacter("abc") returns "abd".
	 */
	private static String incrementLastCharacter(String str) {
		char lastChar = str.charAt(str.length() - 1);
		String ender = str.substring(0, str.length() - 1) + Character.toString((char) (lastChar + 1));
		return ender;
	}

	private static boolean stringMinLength(String str) {
		return str.length() < AbstractAutoCompleter.SHORTEST_TO_COMPLETE;
	}

	public void addWordToIndex(String word) {
		if (word.length() >= SHORTEST_WORD)
			_index.add(word);
	}

	public boolean indexContainsWord(String word) {
		return _index.contains(word);
	}

    public String getPrefix() {
        return "";
    }

}
