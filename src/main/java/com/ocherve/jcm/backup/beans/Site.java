package com.ocherve.jcm.backup.beans;

/**
 * @author herve_dev
 *
 * Class for Site object : extends Document
 */
public class Site extends Document {
	
	private String name;
	private String slug;
	private boolean indoor;
	private String orientation;
	private String height;
	private int pathsNumber;
	private String cotations;
	private boolean tagFriend;
	private String country;
	private String department;
	
	/**
	 * Constructor
	 */
	public Site() {
		super();
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}




	/**
	 * @return the slug
	 */
	public String getSlug() {
		return slug;
	}
	




	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
		String slug = name.replaceAll("\\s{1,}", "_");
		slug = slug.replaceAll("(é|è|ê|ë)", "e");
		slug = slug.replaceAll("(à|ä|â)", "a");
		slug = slug.replaceAll("(ö|ô)", "o");
		slug = slug.replaceAll("(û|ü|ù)", "u");
		slug = slug.replaceAll("(û|ü|ù)", "u");
		slug = slug.replaceAll("(î|ï)", "i");
		slug = slug.replaceAll("ÿ", "y");
		slug = slug.replaceAll("\\W{1,}", "_");
		this.slug = slug;
	}

	/**
	 * @return the indoor
	 */
	public boolean isIndoor() {
		return indoor;
	}

	/**
	 * @param indoor the indoor to set
	 */
	public void setIndoor(boolean indoor) {
		this.indoor = indoor;
	}

	/**
	 * @return the orientation
	 */
	public String getOrientation() {
		return orientation;
	}

	/**
	 * @param orientation the orientation to set
	 */
	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}

	/**
	 * @return the height
	 */
	public String getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(String height) {
		this.height = height;
	}

	/**
	 * @return the pathsNumber
	 */
	public int getPathsNumber() {
		return pathsNumber;
	}

	/**
	 * @param pathsNumber the pathsNumber to set
	 */
	public void setPathsNumber(int pathsNumber) {
		this.pathsNumber = pathsNumber;
	}

	/**
	 * @return the cotations
	 */
	public String getCotations() {
		return cotations;
	}

	/**
	 * @param cotations the cotations to set
	 */
	public void setCotations(String cotations) {
		this.cotations = cotations;
	}

	/**
	 * @return the tagFriend
	 */
	public boolean isTagFriend() {
		return tagFriend;
	}

	/**
	 * @param tagFriend the tagFriend to set
	 */
	public void setTagFriend(boolean tagFriend) {
		this.tagFriend = tagFriend;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * @param department the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}
	
}
