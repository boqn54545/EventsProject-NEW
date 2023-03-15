package com.example.EventsProject.Entities;


import com.example.EventsProject.Enums.InterestsEnum;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private InterestsEnum interest;

    private String title;

    private Integer minPrice;

    private Integer maxPrice;

    private String city;

    private String description;

    private Integer minAge;

    private Integer maxAge;

    private String createdAt;

    private String expireAt;


    @ManyToMany
    private Set<User> applicants = new HashSet<>();

    public String getAgeRange() {
        return this.minAge + " - " + this.maxAge;
    }
    public String getPriceRange() {
        return this.minPrice + " - " + this.maxPrice;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public InterestsEnum getInterest() {
        return interest;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setInterest(InterestsEnum interest) {
        this.interest = interest;
    }



    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(String expireAt) {
        this.expireAt = expireAt;
    }
    public void addApplicant(User user) {
        applicants.add(user);
    }

    public void setApplicants(Set<User> applicants) {
        this.applicants = applicants;
    }

    public void removeApplicant(User user) {
        applicants.remove(user);
    }


    public Set<User> getApplicants() {
        return applicants;
    }

}
