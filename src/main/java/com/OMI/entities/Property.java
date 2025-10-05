package com.OMI.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String location;

    private Integer price;

    private String developer;

    private String bedrooms;

    private String bathrooms;

    private String balcony;

    private String transactionType; // e.g., Sale, Rent

    private String status; // e.g., Available, Sold, Booked

    private String facing; // e.g., North, South, East, West

    private String furnishedStatus; // e.g., Furnished, Semi-furnished, Unfurnished

    private String typeOfOwnership; // Freehold, Leasehold

    private String carpetArea; // in sq. ft

    private String floor; // floor number

    private String ageOfConstruction; // in years

    @Column(columnDefinition = "TEXT")
    private String priceBreakup; // can store JSON or descriptive text

    private String bookingAmount;

    @Column(columnDefinition = "TEXT")
    private String address;

    private String furnishing; // details about furnishing

    private String flooring; // e.g., Tiles, Marble, Wooden

    private String overlooking; // e.g., Garden, Street, Pool

    private String additionalRooms; // e.g., Study, Servant Room

    private String floorsAllowed; // floors allowed for construction

    private String authorityApproval; // e.g., RERA, Local Authority

    @Column(columnDefinition = "TEXT")
    private String description;

    private String createdBy;

    // Multiple images
    @ElementCollection
    @CollectionTable(name = "property_images", joinColumns = @JoinColumn(name = "property_id"))
    @Column(name = "image_url")
    private List<String> imageList = new ArrayList<>();

    private String amenities;

    public enum amenities{
        Swimming_Pool,
        Power_Backup,
        Lift,
        Security,
        CCTV_Camera,
        Gymnasium,
        Gardens,
        Parking,
        Club_house,
        Indoor_games,
        Outdoor_games

    }

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // Constructors
    public Property() {}

    public Property(Long id, String title, String location, Integer price, String developer, String bedrooms, String bathrooms, String balcony, String transactionType, String status, String facing, String furnishedStatus, String typeOfOwnership, String carpetArea, String floor, String ageOfConstruction, String priceBreakup, String bookingAmount, String address, String furnishing, String flooring, String overlooking, String additionalRooms, String floorsAllowed, String authorityApproval, String description, String createdBy, List<String> imageList, String amenities, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.price = price;
        this.developer = developer;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.balcony = balcony;
        this.transactionType = transactionType;
        this.status = status;
        this.facing = facing;
        this.furnishedStatus = furnishedStatus;
        this.typeOfOwnership = typeOfOwnership;
        this.carpetArea = carpetArea;
        this.floor = floor;
        this.ageOfConstruction = ageOfConstruction;
        this.priceBreakup = priceBreakup;
        this.bookingAmount = bookingAmount;
        this.address = address;
        this.furnishing = furnishing;
        this.flooring = flooring;
        this.overlooking = overlooking;
        this.additionalRooms = additionalRooms;
        this.floorsAllowed = floorsAllowed;
        this.authorityApproval = authorityApproval;
        this.description = description;
        this.createdBy = createdBy;
        this.imageList = imageList;
        this.amenities = amenities;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFacing() {
        return facing;
    }

    public void setFacing(String facing) {
        this.facing = facing;
    }

    public String getFurnishedStatus() {
        return furnishedStatus;
    }

    public void setFurnishedStatus(String furnishedStatus) {
        this.furnishedStatus = furnishedStatus;
    }

    public String getTypeOfOwnership() {
        return typeOfOwnership;
    }

    public void setTypeOfOwnership(String typeOfOwnership) {
        this.typeOfOwnership = typeOfOwnership;
    }

    public String getCarpetArea() {
        return carpetArea;
    }

    public void setCarpetArea(String carpetArea) {
        this.carpetArea = carpetArea;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getAgeOfConstruction() {
        return ageOfConstruction;
    }

    public void setAgeOfConstruction(String ageOfConstruction) {
        this.ageOfConstruction = ageOfConstruction;
    }

    public String getPriceBreakup() {
        return priceBreakup;
    }

    public void setPriceBreakup(String priceBreakup) {
        this.priceBreakup = priceBreakup;
    }

    public String getBookingAmount() {
        return bookingAmount;
    }

    public void setBookingAmount(String bookingAmount) {
        this.bookingAmount = bookingAmount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFurnishing() {
        return furnishing;
    }

    public void setFurnishing(String furnishing) {
        this.furnishing = furnishing;
    }

    public String getFlooring() {
        return flooring;
    }

    public void setFlooring(String flooring) {
        this.flooring = flooring;
    }

    public String getOverlooking() {
        return overlooking;
    }

    public void setOverlooking(String overlooking) {
        this.overlooking = overlooking;
    }

    public String getAdditionalRooms() {
        return additionalRooms;
    }

    public void setAdditionalRooms(String additionalRooms) {
        this.additionalRooms = additionalRooms;
    }

    public String getFloorsAllowed() {
        return floorsAllowed;
    }

    public void setFloorsAllowed(String floorsAllowed) {
        this.floorsAllowed = floorsAllowed;
    }

    public String getAuthorityApproval() {
        return authorityApproval;
    }

    public void setAuthorityApproval(String authorityApproval) {
        this.authorityApproval = authorityApproval;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }


    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(String bedrooms) {
        this.bedrooms = bedrooms;
    }

    public String getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(String bathrooms) {
        this.bathrooms = bathrooms;
    }

    public String getBalcony() {
        return balcony;
    }

    public void setBalcony(String balcony) {
        this.balcony = balcony;
    }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
