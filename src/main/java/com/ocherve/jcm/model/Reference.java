package com.ocherve.jcm.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.Normalizer;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ocherve.jcm.utils.JcmDate;

/**
 * The persistent class for the jcm_document database table.
 * 
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="jcm_reference")
@NamedQueries({
	@NamedQuery(name="Reference.findAll", query="SELECT r FROM Reference r"),
	@NamedQuery(name="Reference.getAuthor", 
	query="SELECT r.author.id FROM Reference r WHERE r.id = :id "),
	@NamedQuery(name="Reference.getIdFromSlug", 
	query="SELECT r.id FROM Reference r WHERE r.slug = :slug AND r.type= :type")
})
public class Reference implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String name;

	private String slug;

	private boolean published;

	private String summary;
	
	private String content;
	
	private String type;

	@Column(name="ts_created")
	private Timestamp tsCreated;

	@Column(name="ts_modified")
	private Timestamp tsModified;
	
	//bi-directional many-to-one association to User - fetchType = EAGER (default with ManyToOne)
	@ManyToOne
	@JoinColumn(name="fk_document_user")
	private User author;

	//bi-directional one-to-many association to Comment - fetchType = LAZY (default with OneToMany)
	@OneToMany(mappedBy="reference", cascade = CascadeType.ALL)
	private List<Comment> comments;

	/**
	 * 
	 */
	public Reference() {
	}

	/**
	 * @return id
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return document name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
		String slug = Normalizer.normalize(name, Normalizer.Form.NFD).replaceAll("[\u0300-\u036F]", "");
		this.setSlug(slug.replaceAll("\\W", "_").replaceAll("_{1,}","_").toLowerCase());
	}

	/**
	 * @return the slug
	 */
	public String getSlug() {
		return slug;
	}
	

	/**
	 * @param slug the slug to set
	 */
	public void setSlug(String slug) {
		this.slug = slug;
	}
	

	/**
	 * @return published status
	 */
	public boolean isPublished() {
		return this.published;
	}

	/**
	 * @param published
	 */
	public void setPublished(boolean published) {
		this.published = published;
	}

	/**
	 * @return summary
	 */
	public String getSummary() {
		return this.summary;
	}

	/**
	 * @param summary
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return type
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * @param type
	 */
	public void setType(String type) {
		if (ReferenceType.valueOf(type) == null) return;
		this.type = type;
	}


	/**
	 * @return creation time
	 */
	public Timestamp getTsCreated() {
		return this.tsCreated;
	}

	/**
	 * @param tsCreated
	 */
	public void setTsCreated(Timestamp tsCreated) {
		this.tsCreated = tsCreated;
	}

	/**
	 * @return revision time
	 */
	public Timestamp getTsModified() {
		return this.tsModified;
	}
	
	/**
	 * @return elapsed time as string (usefull for jsp)
	 */
	public String getModifiedElapsedTime() {
		return JcmDate.getElapseTime(tsModified);
	}

	/**
	 * @param tsModified
	 */
	public void setTsModified(Timestamp tsModified) {
		this.tsModified = tsModified;
	}

	/**
	 * @return author
	 */
	public User getAuthor() {
		return this.author;
	}

	/**
	 * @param author
	 */
	public void setAuthor(User author) {
		this.author = author;
	}

	/**
	 * @return the comments
	 */
	public List<Comment> getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	/**
	 * @param comment
	 * @return comment added
	 */
	public Comment addComment(Comment comment) {
		this.getComments().add(comment);
		comment.setReference(this);

		return comment;
	}

	/**
	 * @param comment
	 */
	public void removeComment(Comment comment) {
		this.getComments().remove(comment);
		if ( comment != null ) comment.setReference(null);
	}

}
