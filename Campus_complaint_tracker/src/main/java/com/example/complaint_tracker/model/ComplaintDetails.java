package com.example.complaint_tracker.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "complaints")
public class ComplaintDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(nullable = false)
//    private String originalFileName;
    
    @Column
    private String category;
    
    @Column
    private String description;
    
    @Column
    private String dateOfIncident;
    
    @Column
    private String desiredResolution;
    
    @Lob
    @Column(name = "file_data")
    private byte[] evidence;

    public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDateOfIncident() {
		return dateOfIncident;
	}

	public void setDateOfIncident(String dateOfIncident) {
		this.dateOfIncident = dateOfIncident;
	}

	public String getDesiredResolution() {
		return desiredResolution;
	}

	public void setDesiredResolution(String desiredResolution) {
		this.desiredResolution = desiredResolution;
	}

	@Column(nullable = false)
    private String storedFileName; // Unique identifier for the stored file

    @Column(nullable = false)
    private String contentType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getStoredFileName() {
		return storedFileName;
	}

	public void setStoredFileName(String storedFileName) {
		this.storedFileName = storedFileName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

    // Getters and setters
}