package com.minorproject.tourist.guide.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "place")
public class Places {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String title;

	@Column(nullable = true, length = 64)
	private String imageFile;
	

	public Places() {

	}






	public Places(int id, String title, String imageFile) {
		super();
		this.id = id;
		this.title = title;
		this.imageFile = imageFile;
	}






	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}












	public String getImageFile() {
		return imageFile;
	}






	public void setImageFile(String imageFile) {
		this.imageFile = imageFile;
	}


	@Override
	public String toString() {
		return "Places [id=" + id + ", title=" + title + ", imageFile=" + imageFile + "]";
	}




	@Transient
	public String getImageFilePath() {
		if(imageFile==null ) return null;
		
		return "/places-photos/"+ id +"/" + imageFile;
	}
	
}
