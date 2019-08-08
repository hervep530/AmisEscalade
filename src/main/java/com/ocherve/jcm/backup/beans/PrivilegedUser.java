package com.ocherve.jcm.backup.beans;

/**
 * @author herve_dev
 *
 * User object build in substitution of User for sessions
 */
public class PrivilegedUser extends User{

	private Access siteAccess;
	private Access topoAccess;
	private Access userAccess;
	private Access roleAccess;
	private Access commentAccess;
	private Access messageAccess;
	private Access bookingAccess;
	/**
	 * 
	 */
	public PrivilegedUser() {
		super();
	}
	/**
	 * @return the siteAccess
	 */
	public Access getSiteAccess() {
		return siteAccess;
	}
	/**
	 * @param siteAccess the siteAccess to set
	 */
	public void setSiteAccess(Access siteAccess) {
		this.siteAccess = siteAccess;
	}
	/**
	 * @return the topoAccess
	 */
	public Access getTopoAccess() {
		return topoAccess;
	}
	/**
	 * @param topoAccess the topoAccess to set
	 */
	public void setTopoAccess(Access topoAccess) {
		this.topoAccess = topoAccess;
	}
	/**
	 * @return the userAccess
	 */
	public Access getUserAccess() {
		return userAccess;
	}
	/**
	 * @param userAccess the userAccess to set
	 */
	public void setUserAccess(Access userAccess) {
		this.userAccess = userAccess;
	}
	/**
	 * @return the roleAccess
	 */
	public Access getRoleAccess() {
		return roleAccess;
	}
	/**
	 * @param roleAccess the roleAccess to set
	 */
	public void setRoleAccess(Access roleAccess) {
		this.roleAccess = roleAccess;
	}
	/**
	 * @return the commentAccess
	 */
	public Access getCommentAccess() {
		return commentAccess;
	}
	/**
	 * @param commentAccess the commentAccess to set
	 */
	public void setCommentAccess(Access commentAccess) {
		this.commentAccess = commentAccess;
	}
	/**
	 * @return the messageAccess
	 */
	public Access getMessageAccess() {
		return messageAccess;
	}
	/**
	 * @param messageAccess the messageAccess to set
	 */
	public void setMessageAccess(Access messageAccess) {
		this.messageAccess = messageAccess;
	}
	/**
	 * @return the bookingAccess
	 */
	public Access getBookingAccess() {
		return bookingAccess;
	}
	/**
	 * @param bookingAccess the bookingAccess to set
	 */
	public void setBookingAccess(Access bookingAccess) {
		this.bookingAccess = bookingAccess;
	}

}
