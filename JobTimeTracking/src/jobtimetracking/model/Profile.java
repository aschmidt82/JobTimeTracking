/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobtimetracking.model;

import java.util.ArrayList;

/**
 *
 * @author Anika.Schmidt
 */
public class Profile {

    private String surename;
    private String firstname;
    private String secondname;
    private String company;
    private String department;
    private String username;
    private String password;
    private int hoursperweek;
    private int daysperweek;
    private int vacationdays;
    private ArrayList<Timetracking> tracking = new ArrayList<>();

    public String getSurename() {
        return surename;
    }

    public void setSurename(String surename) {
        this.surename = surename;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSecondname() {
        return secondname;
    }

    public void setSecondname(String secondname) {
        this.secondname = secondname;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getHpw() {
        return hoursperweek;
    }

    public void setHpw(int hpw) {
        this.hoursperweek = hpw;
    }

    public int getDpw() {
        return daysperweek;
    }

    public void setDpw(int dpw) {
        this.daysperweek = dpw;
    }

    public int getVd() {
        return vacationdays;
    }

    public void setVd(int vd) {
        this.vacationdays = vd;
    }

    public ArrayList<Timetracking> getTracking() {
        return tracking;
    }

    public void setTracking(ArrayList<Timetracking> tracking) {
        this.tracking = tracking;
    }
}
