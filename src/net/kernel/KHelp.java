package net.kernel;

import java.util.regex.Matcher;

import net.ko.kobject.KObject;
import net.ko.persistence.annotation.Entity;
import net.ko.persistence.annotation.Id;

/**
 * Classe KHelp
 */
@Entity
@SuppressWarnings("serial")
public class KHelp extends KObject {
	private String message;
	@Id
	private String key;
	private String caption;
	private int ordre;
	private String access;

	public KHelp() {
		super();
		//

	}

	/**
	 * return the value of message
	 * 
	 * @return message
	 */
	public String getMessage() {
		String result = message;
		result = result.replaceAll("\\{(.+?)\\}", Matcher.quoteReplacement("<img class='imgHelp' title='" + caption + ":") + "$1" + Matcher.quoteReplacement("' src='images/help/" + key + "/") + "$1" + Matcher.quoteReplacement("'>"));
		return result;
	}

	/**
	 * return the value of key
	 * 
	 * @return key
	 */
	public String getKey() {
		return this.key;
	}

	/**
	 * set the value of message
	 * 
	 * @param aMessage
	 */
	public void setMessage(String aMessage) {
		this.message = aMessage;
	}

	/**
	 * set the value of key
	 * 
	 * @param aKey
	 */
	public void setKey(String aKey) {
		this.key = aKey;
	}

	@Override
	public String toString() {
		return " [message] = " + message + " [key] = " + key;
	}

	/**
	 * @return the caption
	 */
	public String getCaption() {
		return caption;
	}

	/**
	 * @param caption
	 *            the caption to set
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}
}