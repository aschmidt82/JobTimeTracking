/*
 * Copyright (C) 2019 Anika Schmidt
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.ravenguard.jobtimetracking.logic;

import de.ravenguard.jobtimetracking.model.Profile;
import de.ravenguard.jobtimetracking.model.TimeType;
import de.ravenguard.jobtimetracking.model.Timetracking;
import de.ravenguard.jobtimetracking.repository.ProfileDao;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Anika.Schmidt
 */
public class TimeTrackingService {

  private LocalDateTime lastTimeStamp;
  private boolean isBreak;
  private Profile profile;

  /**
   *
   * param username param password
   *
   * @param username
   * @param password
   * @return
   */
  public List<String> login(String username, String password) {
    List<String> errors = new ArrayList<>();
    Path userFile = Paths.get(username + ".xml");
    if (Files.exists(userFile)) {
      try {
        profile = ProfileDao.getFromPath(userFile, password);
      } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException ex) {
        errors.add("Your passsword is incorrect!");
      } catch (JAXBException ex) {
        errors.add("Wrong file format!");
      } catch (IOException ex) {
        errors.add("Error reading file!");
      }
    } else {
      errors.add("Username unknown or no file found!");
    }

    return errors;
  }

  /**
   *
   * @param username
   * @param password
   * @param company
   * @param department
   * @param surename
   * @param firstname
   * @param secondname
   * @param hoursperweek
   * @param daysperweek
   * @param vacationdays
   * @return
   */
  public List<String> register(String username, String password, String company, String department, String surename, String firstname, String secondname,
          double hoursperweek, double daysperweek, double vacationdays) {

    List<String> errors = new ArrayList<>();
    profile = new Profile();
    profile.setCompany(company);
    profile.setDepartment(department);
    profile.setDaysPerWeek(daysperweek);
    profile.setFirstname(firstname);
    profile.setHoursPerWeek(hoursperweek);
    profile.setPassword(password);
    profile.setSecondname(secondname);
    profile.setSurename(surename);
    profile.setUsername(username);
    profile.setVacationDays(vacationdays);
    try {
      ProfileDao.saveToPath(profile);
    } catch (JAXBException ex) {
      errors.add("Error saving your profile!");
    } catch (IOException ex) {
      errors.add("Can't write file!");
    } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException ex) {
      errors.add("Can't encrypt file please choose another password!");
    }
    return errors;
  }

  public void startAutomaticTimeTracking() {
    lastTimeStamp = LocalDateTime.now();
    isBreak = false;
  }

  /**
   *
   * @return
   */
  public List<String> createTimeTrackingRecord() {
    Timetracking record = new Timetracking();
    record.setBegin(lastTimeStamp);
    record.setEnde(LocalDateTime.now());
    if (isBreak) {
      record.setType(TimeType.BREAK);
    } else {
      record.setType(TimeType.WORK);
    }
    lastTimeStamp = LocalDateTime.now();
    isBreak = !isBreak;
    profile.getTracking().add(record);
    List<String> errors = new ArrayList<>();
    try {
      ProfileDao.saveToPath(profile);
    } catch (JAXBException ex) {
      errors.add("Error saving your profile!");
    } catch (IOException ex) {
      errors.add("Can't write file!");
    } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException ex) {
      errors.add("Can't encrypt file please choose another password!");
    }
    return errors;
  }

  /**
   *
   * @return
   */
  public List<String> endAutomaticTimeTracking() {
    Timetracking record = new Timetracking();
    record.setBegin(lastTimeStamp);
    record.setEnde(LocalDateTime.now());
    if (isBreak) {
      record.setType(TimeType.BREAK);
    } else {
      record.setType(TimeType.WORK);
    }
    profile.getTracking().add(record);
    List<String> errors = new ArrayList<>();
    try {
      ProfileDao.saveToPath(profile);
    } catch (JAXBException ex) {
      errors.add("Error saving your profile!");
    } catch (IOException ex) {
      errors.add("Can't write file!");
    } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException ex) {
      errors.add("Can't encrypt file please choose another password!");
    }
    return errors;
  }

  /**
   *
   * @param username
   * @param password
   * @param company
   * @param department
   * @param surename
   * @param hoursPerWeek
   * @param daysPerWeek
   * @param vacationDays
   * @return
   */
  public List<String> updateProfile(String username, String password, String company, String department, String surename, double hoursPerWeek, double daysPerWeek,
          double vacationDays) {
    boolean newUsername = username.equals(profile.getUsername());
    List<String> errors = new ArrayList<>();
    if (newUsername) {
      try {
        Path path = Paths.get(profile.getUsername() + ".xml");
        ProfileDao.deletePath(path);
      } catch (IOException ex) {
        errors.add("Fail to Delete old file!");
        return errors;
      }
    }
    profile.setCompany(company);
    profile.setDaysPerWeek(daysPerWeek);
    profile.setDepartment(department);
    profile.setHoursPerWeek(hoursPerWeek);
    profile.setPassword(password);
    profile.setSurename(surename);
    profile.setUsername(username);
    profile.setVacationDays(vacationDays);
    try {
      ProfileDao.saveToPath(profile);
    } catch (JAXBException ex) {
      errors.add("Error saving your profile!");
    } catch (IOException ex) {
      errors.add("Can't write file!");
    } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException ex) {
      errors.add("Can't encrypt file please choose another password!");
    }

    return errors;
  }

  public Profile getProfile() {
    Profile returnValue = new Profile();
    returnValue.setCompany(profile.getCompany());
    returnValue.setDaysPerWeek(profile.getDaysPerWeek());
    returnValue.setDepartment(profile.getDepartment());
    returnValue.setFirstname(profile.getFirstname());
    returnValue.setHoursPerWeek(profile.getHoursPerWeek());
    returnValue.setPassword("");
    returnValue.setSecondname(profile.getSecondname());
    returnValue.setSurename(profile.getSurename());
    returnValue.setUsername(profile.getUsername());
    returnValue.setVacationDays(profile.getVacationDays());

    return returnValue;
  }

  public EvaluationData getWeekData() {
    EvaluationService evaluationService = new EvaluationService();
    return evaluationService.getStandardWeek(profile);
  }

  public EvaluationData getEvaluationMonth() {
    EvaluationService evaluationService = new EvaluationService();
    return evaluationService.getEvaluationMonth(profile);
  }

  public EvaluationData getEvaluationYear() {
    EvaluationService evaluationService = new EvaluationService();
    return evaluationService.getEvaluationYear(profile);
  }

  /**
   *
   * @param element
   * @return
   */
  public List<String> addTimeTracking(Timetracking element) {
    List<String> errors = new ArrayList<>();

    List<Timetracking> elements = profile.getTracking();
    for (Timetracking tracking : elements) {
      if (element.getBegin().isAfter(tracking.getBegin())
              && element.getBegin().isBefore(tracking.getEnde())) {
        errors.add("No overlaps in time entries allowed!");
        return errors;
      }
      if (element.getEnde().isAfter(tracking.getBegin())
              && element.getEnde().isBefore(tracking.getEnde())) {
        errors.add("No overlaps in time entries allowed!");
        return errors;
      }
      if (element.getBegin().isBefore(tracking.getBegin())
              && element.getEnde().isAfter(tracking.getEnde())) {
        errors.add("No overlaps in time entries allowed!");
        return errors;
      }
    }
    profile.getTracking().add(element);
    try {
      ProfileDao.saveToPath(profile);
    } catch (JAXBException ex) {
      errors.add("Error saving your profile!");
    } catch (IOException ex) {
      errors.add("Can't write file!");
    } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException ex) {
      errors.add("Can't encrypt file please choose another password!");
    }
    return errors;
  }
}
