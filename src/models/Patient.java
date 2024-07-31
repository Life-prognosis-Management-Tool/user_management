package models;

import java.util.Date;

public class Patient extends User {
    private String UUID;
    private Date DOB;
    private Boolean hasHIV;
    private Boolean hivDiagnosisDate;
    private Boolean takingART;
    private Date artStartDate;
    private String CountryISO;

    public Patient(String f_name, String l_name, String user_email) {
        super(f_name, l_name, user_email);
    }




    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public Date getDOB() {
        return DOB;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

    public Boolean getHasHIV() {
        return hasHIV;
    }

    public void setHasHIV(Boolean hasHIV) {
        this.hasHIV = hasHIV;
    }

    public Boolean getHivDiagnosisDate() {
        return hivDiagnosisDate;
    }

    public void setHivDiagnosisDate(Boolean hivDiagnosisDate) {
        this.hivDiagnosisDate = hivDiagnosisDate;
    }

    public Boolean getTakingART() {
        return takingART;
    }

    public void setTakingART(Boolean takingART) {
        this.takingART = takingART;
    }

    public Date getArtStartDate() {
        return artStartDate;
    }

    public void setArtStartDate(Date artStartDate) {
        this.artStartDate = artStartDate;
    }

    public String getCountryISO() {
        return CountryISO;
    }

    public void setCountryISO(String countryISO) {
        CountryISO = countryISO;
    }

    public void register(){

    }
    public int yearsRemaining(String DOB,String ISO){
        return 0;
    }
}
