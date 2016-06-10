package com.manhdong.contactlist.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saphiro on 6/10/2016.
 */
public class Contact {

    long _id;
    String personName;
    List<Phone> phoneList;
    List<Email> emailList;

    public void addPhoneNum(Phone phone){
        phoneList.add(phone);
    }

    public void addEmail(Email email){
        emailList.add(email);
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public List<Email> getEmailList() {
        return emailList;
    }

    public void setEmailList(List<Email> emailList) {
        this.emailList = emailList;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public List<Phone> getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(List<Phone> phoneList) {
        this.phoneList = phoneList;
    }

    public Contact() {
        phoneList = new ArrayList<>();
        emailList = new ArrayList<>();

    }
}
