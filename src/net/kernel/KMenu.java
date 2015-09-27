package net.kernel;

import net.ko.kobject.KObject;
import net.ko.persistence.annotation.Entity;
import net.ko.persistence.annotation.Transient;
import net.ko.utils.KString;

/**
 * Classe KMenu
 */
@Entity
@SuppressWarnings("serial")
public class KMenu extends KObject {
	private String btId;
	private String sqlCount;
	private String action;
	private boolean comptable;
	private int ordre;
	private String caption;
	private String className;
	private String menuCaption;
	private String description;
	@Transient
	private int count = -1;

	public KMenu() {
		super();
		//

	}

	/**
	 * return the value of sqlCount
	 * 
	 * @return sqlCount
	 */
	public String getSqlCount() {
		return this.sqlCount;
	}

	/**
	 * return the value of action
	 * 
	 * @return action
	 */
	public String getAction() {
		return this.action;
	}

	/**
	 * return the value of comptable
	 * 
	 * @return comptable
	 */
	public boolean getComptable() {
		return this.comptable;
	}

	/**
	 * return the value of ordre
	 * 
	 * @return ordre
	 */
	public int getOrdre() {
		return this.ordre;
	}

	/**
	 * return the value of caption
	 * 
	 * @return caption
	 */
	public String getCaption() {
		return this.caption;
	}

	/**
	 * return the value of className
	 * 
	 * @return className
	 */
	public String getClassName() {
		return this.className;
	}

	/**
	 * set the value of sqlCount
	 * 
	 * @param aSqlCount
	 */
	public void setSqlCount(String aSqlCount) {
		this.sqlCount = aSqlCount;
	}

	/**
	 * set the value of action
	 * 
	 * @param aAction
	 */
	public void setAction(String aAction) {
		this.action = aAction;
	}

	/**
	 * set the value of comptable
	 * 
	 * @param aComptable
	 */
	public void setComptable(boolean aComptable) {
		this.comptable = aComptable;
	}

	/**
	 * set the value of ordre
	 * 
	 * @param aOrdre
	 */
	public void setOrdre(int aOrdre) {
		this.ordre = aOrdre;
	}

	/**
	 * set the value of caption
	 * 
	 * @param aCaption
	 */
	public void setCaption(String aCaption) {
		this.caption = aCaption;
	}

	/**
	 * set the value of className
	 * 
	 * @param aClassName
	 */
	public void setClassName(String aClassName) {
		this.className = aClassName;
	}

	/**
	 * @return the menuCaption
	 */
	public String getMenuCaption() {
		return menuCaption;
	}

	/**
	 * @param menuCaption
	 *            the menuCaption to set
	 */
	public void setMenuCaption(String menuCaption) {
		this.menuCaption = menuCaption;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return caption;
	}

	public String getMainItem() {
		String strCount = "";
		if ("-".equals(btId))
			return "";
		if (count != -1)
			strCount = "(" + count + ") ";
		String menuItem = "<div class='btn' id='_" + btId + "' style='display:block;margin-top:5px;'><div title='" + action + "' id='div-" + id + "' class='bigBtn " + className + "'>" + strCount + caption + "<span>" + action + "</span></div></div>";
		menuItem += "<input type='hidden' id='v-div-" + id + "' value='" + KString.htmlSpecialChars(description) + "'>";
		return menuItem;
	}

	public String getMenuItem() {
		if ("-".equals(btId))
			return "<hr>";
		String result = "<a title='" + action + "' id='" + btId + "'>" + caption + "</a>";
		return result;
	}

	/**
	 * @return the btId
	 */
	public String getBtId() {
		return btId;
	}

	/**
	 * @param btId
	 *            the btId to set
	 */
	public void setBtId(String btId) {
		this.btId = btId;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}
}