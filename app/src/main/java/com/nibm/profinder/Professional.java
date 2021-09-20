package com.nibm.profinder;

public class Professional {

    public String type, name, category, designation, bio, location, keywords, contactno, email, pimage;

    public Professional() {
    }

    public Professional(String type, String name, String category, String designation, String bio, String location, String keywords, String contactno, String email, String pimage) {
        this.type = type;
        this.name = name;
        this.category = category;
        this.designation = designation;
        this.bio = bio;
        this.location = location;
        this.keywords = keywords;
        this.contactno = contactno;
        this.email = email;
        this.pimage = pimage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getPimage() {
        return pimage;
    }

    public void setPimage(String pimage) {
        this.pimage = pimage;
    }
}
