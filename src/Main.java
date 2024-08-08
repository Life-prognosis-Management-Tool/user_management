import models.Patient;
import models.UserRole;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    private final String path = System.getProperty("user.dir");


    public static void main(String[] args) throws ParseException, IOException {
        new Main().app();
    }


    public String initializeRegisterUser(String email){
        ArrayList<String> myUserInfo = new ArrayList<String>();
        UUID myCode = UUID.randomUUID();
        ProcessBuilder myBuilder = new ProcessBuilder("bash", System.getProperty("user.dir")+"/src/scripts/admin_data.sh",email ,myCode.toString());

        try {
            Process process = myBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String result = null;
            System.out.println(reader.readLine());
            while((result = reader.readLine()) != null){
                myUserInfo.add(result);
            }

        }
        catch (IOException e){
            System.out.println("Error: " + e);
            e.printStackTrace();
        }
        return "The user UUID is: "+myCode + " With Email: "+ email ;
    }

    public ArrayList<String> completeRegisterUser(Patient patient) throws IOException{
        ArrayList<String> myUserInfo = new ArrayList<String>();
        try {
            int life_expectancy = yearsRemaining(patient.getHivDiagnosisDate().toString(),patient.getCountryISO(),patient.getArtStartDate().toString());

            ProcessBuilder myBuilder = new ProcessBuilder("bash", System.getProperty("user.dir")+"/src/scripts/admin_data.sh", patient.getUser_email(), patient.getUser_password(), patient.getUUID(), patient.getF_name(), patient.getL_name(), patient.getDOB().toString(), patient.getHasHIV().toString(), patient.getHivDiagnosisDate().toString(),patient.getTakingART().toString(), patient.getArtStartDate().toString(), patient.getCountryISO(),String.valueOf(life_expectancy), UserRole.PATIENT.toString());

            System.out.println(patient.getUser_email() +"-Email done-"+ patient.getUser_password()+"--"+patient.getUUID()+"--"+ patient.getF_name()+"--"+patient.getL_name()+"--"+ patient.getDOB().toString()+"--"+ patient.getHasHIV().toString()+"--"+patient.getHivDiagnosisDate().toString()+"--"+patient.getTakingART().toString()+"--"+ patient.getArtStartDate().toString()+"--"+ patient.getCountryISO()+"--"+String.valueOf(life_expectancy)+"--"+ UserRole.PATIENT.toString());

            Process process = myBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String result = null;

            while((result = reader.readLine()) != null){
                myUserInfo.add(result);
            }

        } catch (IOException e){
            System.out.println("Error: " + e);
            e.printStackTrace();
        }
        return myUserInfo;
    }

    public ArrayList<String> login(String email, String password) throws IOException {
        ArrayList<String> myUserInfo = new ArrayList<String>();

        try {
            String hashedPassword;
            if(password.length() >= 64){
                hashedPassword = password;
            }
            else {
                hashedPassword = hashPassword(password);
            }

            ProcessBuilder myBuilder = new ProcessBuilder("bash", path + "/src/scripts/login_user.sh", email, hashedPassword);
            Process process = myBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String result = null;

            while ((result = reader.readLine()) != null) {
//                System.out.println(result);
                myUserInfo.add(result);
            }


        }catch (IOException e){
            System.out.println("Error: " + e);
            e.printStackTrace();
        }

        return myUserInfo;
    }

    public boolean checkUUID(String UUID){
        ArrayList<String> myUserInfo = new ArrayList<String>();
        ProcessBuilder myBuilder = new ProcessBuilder("bash", System.getProperty("user.dir")+"/src/scripts/check_uuid.sh",UUID);

        try {
            Process process = myBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String result = null;

            while((result = reader.readLine()) != null){
                if(result.contains("UUID validation successful")){
                    return true;
                }
            }
            return false;
        }
        catch (IOException e){
            System.out.println("Error: " + e);
            e.printStackTrace();
        }

return false;
    }

    public void app() throws ParseException, IOException {
        boolean isDone = false;

        try {
            boolean sendInitialData = initialData();
            if (sendInitialData) {
                System.out.println("Admin data initialization successful.");
            } else {
                System.out.println("Admin data initialization failed.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(!isDone) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("************************************************************************");
            System.out.println("************************************************************************");
            System.out.println("************ WELCOME TO LIFE PROGNOSIS MANAGEMENT TOOL *****************");
            System.out.println("************************************************************************");
            System.out.println("************************************************************************");
            System.out.println("\nPlease choose: \n1. Login \n2. Register with UUID \n3. Help \n4. Exit ");
            String userChoice = scanner.nextLine();


            if (userChoice.equalsIgnoreCase("3")) {
                printHelp();
                continue;
            }

            if (userChoice.equalsIgnoreCase("4")) {
                break;
            }

            switch (userChoice) {
                case "1":
                    System.out.println("Please choose: Enter your credentials");
                    System.out.println("\nPlease enter your email");
                    String email = scanner.nextLine();
                    System.out.println("Please enter your password");
                    String password = scanner.nextLine();

                    if (email != null || password != null) {

                        ArrayList<String> fromLogin = null;
                        try {
                            fromLogin = login(email,password);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        if(fromLogin.size() < 2){
                            System.out.println("Login Failed");

                        } else {
                            clearScreen();
                            System.out.println(fromLogin.getFirst());
                            System.out.println(fromLogin.toString());

                            if(fromLogin.getLast().equalsIgnoreCase("ADMIN")){
                                boolean adminFlow = true;
                                while(adminFlow){
                                    System.out.println("Welcome ADMIN");
                                    System.out.println("\nPlease choose: \n1. Register Patient \n2. Logout ");
                                    String adminOption = scanner.nextLine();
                                    if(adminOption.equalsIgnoreCase("1")){
                                        System.out.println("Enter patient email: ");
                                        String patientEmail = scanner.nextLine();
                                        String idx = initializeRegisterUser(patientEmail);
                                        System.out.println(idx);

                                    } else if(adminOption.equalsIgnoreCase("2")){
                                        adminFlow = false;
                                    } else {
                                        System.out.println("Please enter valid choice!");
                                    }
                                }


                            }
                            else if (fromLogin.getLast().equalsIgnoreCase("PATIENT")){
                                isDone = true;
                                while(true) {
                                    System.out.println();
                                    Patient patient = new Patient(fromLogin.get(2), fromLogin.get(3), fromLogin.get(1), "");
                                    SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
                                    patient.setDOB(formatter.parse(fromLogin.get(4)));
                                    patient.setHasHIV(Boolean.parseBoolean(fromLogin.get(5)));
                                    patient.setHivDiagnosisDate(formatter.parse(fromLogin.get(6)));
                                    patient.setTakingART(Boolean.parseBoolean(fromLogin.get(7)));
                                    patient.setArtStartDate(formatter.parse(fromLogin.get(8)));
                                    patient.setCountryISO(fromLogin.get(9));
                                    System.out.println("Welcome PATIENT");
                                    System.out.println("Your Names: " + patient.getF_name() + " " + patient.getL_name());
                                    System.out.println("Your Email: " + patient.getUser_email());
                                    SimpleDateFormat formatterToString = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                                    System.out.println("Your Birthdate: " + formatterToString.format(patient.getDOB()));
                                    System.out.println("Your HIV status: " + (patient.getHasHIV().equals(false)? "NEGATIVE" : "POSITIVE"));
                                    if (fromLogin.get(5).contains("true")) {
                                        System.out.println("Your HIV diagnosis date: " + formatterToString.format(patient.getHivDiagnosisDate()));
                                        System.out.println( patient.getTakingART().equals(false)? "You don't take ART treatment" : "You take ART treatment");
                                        System.out.println("Your ART start date: " + formatterToString.format(patient.getArtStartDate()));
                                        System.out.println("Your Country ISO-Code: " + patient.getCountryISO());
                                    }
                                    System.out.println("\n\nPlease Choose \n 1. Get updated life-expectancy \n 2. Update your information");
                                    String logedUserChoice = scanner.nextLine();

                                    switch (logedUserChoice){
                                        case "1":
                                            System.out.println("Coming Soon");
                                            System.out.println("-->"+logedUserChoice);
                                        case "2":
                                            clearScreen();
                                            System.out.println("-->"+logedUserChoice);
                                            System.out.println("You can now edit. Leave place empty if you don't want to it to be edited.");
                                            System.out.println("/!\\ You can't edit your email and UUID.");
                                            System.out.println("Your First Name: " + fromLogin.get(2));
                                            String updatedFirstName = scanner.nextLine();
                                            System.out.println("Your Last Name: " + fromLogin.get(3));
                                            String updatedLastName = scanner.nextLine();
                                            System.out.println("Your Date of Birth " + fromLogin.get(4) +" use 01-july-2000 format");
                                            String updatedDateOfBirth = scanner.nextLine();
                                            System.out.println("Your HIV Status " + fromLogin.get(5));
                                            String updatedHIVStatus = scanner.nextLine();


                                            if(!Objects.equals(updatedFirstName, "")){
                                                patient.setF_name(updatedFirstName);
                                            }
                                            if(!Objects.equals(updatedLastName, "")){
                                                patient.setL_name(updatedLastName);
                                            }
                                            if(!Objects.equals(updatedDateOfBirth, "")){
                                                patient.setDOB( formatter.parse(updatedDateOfBirth));
                                            }
                                            if(!Objects.equals(updatedHIVStatus, "")){
                                                patient.setHasHIV(Boolean.parseBoolean(updatedHIVStatus));
                                            }


                                            if(patient.getHasHIV()) {
                                                System.out.println("Your HIV Diagnosis Date " + fromLogin.get(6) + " use 01-july-2000 format");
                                                String updatedDiagnosisDate = scanner.nextLine();
                                                System.out.println("Your ART Status " + fromLogin.get(7));
                                                String updatedARTStatus = scanner.nextLine();
                                                System.out.println("Your ART start date " + fromLogin.get(8) + " use 01-july-2000 format");
                                                String updatedARTStartingDate = scanner.nextLine();
                                                System.out.println("Your Country ISO Code " + fromLogin.get(9));
                                                String updatedCountryISOCode = scanner.nextLine();

                                                if(!Objects.equals(updatedDiagnosisDate, "")){
                                                    patient.setHivDiagnosisDate(formatter.parse(updatedDiagnosisDate));
                                                }
                                                if(!Objects.equals(updatedARTStatus, "")){
                                                    patient.setTakingART(Boolean.parseBoolean(updatedARTStatus));
                                                }
                                                if(!Objects.equals(updatedARTStartingDate, "")){
                                                    patient.setArtStartDate(formatter.parse(updatedARTStartingDate));
                                                }
                                                if(!Objects.equals(updatedCountryISOCode, "")){
                                                    patient.setCountryISO(updatedCountryISOCode);
                                                }
                                            }


                                            patient.setUser_email(fromLogin.get(1));
                                            patient.setUser_password(fromLogin.get(11));
                                            patient.setUUID(fromLogin.get(12));

//


                                            ArrayList<String> fromCompleteUpdatedRegistration = new ArrayList<String>();
                                            try {
                                                fromCompleteUpdatedRegistration = completeRegisterUser(patient);

                                                fromLogin = login(patient.getUser_email(),fromLogin.get(11));

                                            } catch (IOException e) {
                                                throw new RuntimeException(e);
                                            }
                                            if(!fromCompleteUpdatedRegistration.isEmpty()){
                                                clearScreen();
                                                fromCompleteUpdatedRegistration.forEach(line-> {
                                                    System.out.println(line);
                                                });
                                                System.out.println("Your Information is updated");

                                            }
                                            else{
                                                System.out.println("Invalid Email or Password");
                                            }

                                    }

                                }
                            }
                            else {
                                System.out.println("Sorry, No role");
                            }
                        }
                    } else {
                        System.out.println("Please enter enter both Email and Password");
                    }
                    break;
                case "2":
                    System.out.println("Please enter your UUID");
                    String userUUID = scanner.nextLine();

                    if (userUUID != null) {
                        System.out.println("Checking user UUID");
                        boolean uuidValid = checkUUID(userUUID);

                        if (uuidValid){

                            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");

                            System.out.println("You have UUID: " + userUUID );
                            System.out.println("********************* NOW YOU MAY PROCEED *********************");
                            System.out.println("***************************************************************");
                            System.out.println("Please enter your First Name");
                            String firstName = scanner.nextLine();
                            System.out.println("Please enter your Last Name");
                            String lastName = scanner.nextLine();
                            System.out.println("Please enter your Email");
                            String mail = scanner.nextLine();
                            System.out.println("Please enter your Password");
                            String userPassword = scanner.nextLine();
                            String hashedPassword = hashPassword(userPassword);
                            System.out.println("Please enter your Date of birth e.g: 01-january-2000");
                            String sob = scanner.nextLine();
                            Date dob = formatter.parse(sob);
                            System.out.println("Please enter your country ISO-CODE");
                            String countryISOCode = scanner.nextLine();
                            System.out.println("Do you have HIV? true or false");
                            boolean hasHIV = scanner.nextBoolean();
//                             scanner.nextLine(); 01-june-2001
                            Patient patient = new Patient(firstName, lastName, mail, userPassword);
                            patient.setUUID(userUUID);
                            patient.setDOB(dob);
                            patient.setCountryISO(countryISOCode);
                            patient.setUser_password(hashedPassword);

                            if(hasHIV) {
                                Scanner scannerP = new Scanner(System.in);
                                System.out.println("Please enter your HIV Diagnosis Date e.g: 01-january-2000");
                                String hivDiagnosisString = scannerP.nextLine();
                                Date hivDiagnosisDate = formatter.parse(hivDiagnosisString);
                                System.out.println("Do you take ART treatment? true or false");
                                boolean takingART = scannerP.nextBoolean();
                                scannerP.nextLine();
                                System.out.println("When did you start taking ART e.g: 01-january-2000");
                                String artStartString = scannerP.nextLine();
                                Date artStartDate = formatter.parse(artStartString);

                                patient.setHasHIV(hasHIV);
                                patient.setHivDiagnosisDate(hivDiagnosisDate);
                                patient.setTakingART(takingART);
                                patient.setArtStartDate(artStartDate);
                            }
                            else {
                                patient.setHasHIV(false);
                                Date hivDiagnosisDate = formatter.parse("01-january-2000");
                                patient.setHivDiagnosisDate(hivDiagnosisDate);
                                patient.setTakingART(false);
                                Date artStartDate = formatter.parse("01-january-2001");
                                patient.setArtStartDate(artStartDate);
                                System.out.println("Enter your country ISO-CODE?");

                            }

                            ArrayList<String> fromCompleteRegistration = null;
                            try {
                                fromCompleteRegistration = completeRegisterUser(patient);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            if(!fromCompleteRegistration.isEmpty()){
                                clearScreen();
                                fromCompleteRegistration.forEach(line-> {
                                    System.out.println(line);
                                });
                                System.out.println("You Registered");
                                continue;
                            }
                            else{
                                System.out.println("Invalid Email or Password");
                            }

                        }
                        else {
                            System.out.println("UUID: " + userUUID +" is not found.");
                        }
//                        isDone = true;


                    } else {
                        System.out.println("Please enter your UUID");
                        System.out.println("<!> CAUTION: In order to register you must have UUID. If you don't have it please find admin for help.");
                    }
                    break;
                default:
                    System.out.println("Please enter valid option");
                    break;
            }
        }



//        ArrayList<String> fromCompleteRegistration = completeRegisterUser("0nah@gmail.com","1nah@gmail.com", "2nah@gmail.com", "3nah@gmail.com", "4nah@gmail.com", "5nah@gmail.com", "6nah@gmail.com", "7nah@gmail.com", "8nah@gmail.com");
//        String fromInitRegistration = initializeRegisterUser("8nah@gmail.com");
//        ArrayList<String> fromLogin = login("myEmaaail","myPassssssword");
//        fromLogin.forEach(line->{
//        System.out.println(line);
//    });
    }

    private void createEmptyCSVFile(String fileName) {
        FileWriter fileWriter = null;
        ArrayList<String> myUserInfo = new ArrayList<String>();

        try {
            File file = new File(fileName);
            fileWriter = new FileWriter(file);

            //Just add path to script that will be retrieving users
            ProcessBuilder myBuilder = new ProcessBuilder("bash", path + "/src/scripts/login_user.sh");
            Process process = myBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String result = null;

            while ((result = reader.readLine()) != null) {
                System.out.println(result);
                myUserInfo.add(result);
            }
            fileWriter.write("Line 1");

            System.out.println(fileName + " has been created.");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void printHelp() {
        System.out.println("\n\n***************************************************************************************************************");
        System.out.println("*********************************************** HELP **********************************************************");
        System.out.println("Available options are Login, Register and Exit");
        System.out.println("i) To Login enter both email and password");
        System.out.println("ii) To Register enter 2. ");
        System.out.println("    <!> CAUTION: In order to register you must have UUID. If you don't have it please find admin for help.");
        System.out.println("iii) To Exit, simply enter 3");
        System.out.println("***************************************************************************************************************\n\n");
    }

    public static void clearScreen() {
        for( int i = 0; i < 25; i++ ) {
            System.out.println("\n\n\n");
        }
    }

    private static boolean initialAdminData(String email, String password, String uuid, String firstName, String lastName, String dob, String hasHIV, String hivDiagnosisDate, String onART, String artStartDate, String countryISO, String role) throws IOException {

        String ADMIN_DATA_SCRIPT = "/src/scripts/admin_data.sh";

        String directory = System.getProperty("user.dir");
        String absolutePath = directory + File.separator + ADMIN_DATA_SCRIPT;

        ProcessBuilder processBuilder = new ProcessBuilder(
                "bash", absolutePath, email, password, uuid, firstName, lastName, dob, hasHIV, hivDiagnosisDate, onART, artStartDate, countryISO, role
        );

        Process process = processBuilder.start();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            System.out.println("Reached Here --> "+reader.readLine());
            while ((line = reader.readLine()) != null) {
                System.out.println("LINE ---> "+line); // Print script output
                if (line.contains("Initial admin data has been written")) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean initialData() throws IOException {
        try {
            String email = "adminnewemail@example.com";
            String password = "adminpassword";
            String uuid = generateRandomUUID();
            String firstName = "John";
            String lastName = "Doe";
            String dob = "1980-01-01";
            String hasHIV = "true";
            String hivDiagnosisDate = "2000-01-01";
            String onART = "true";
            String artStartDate = "2001-01-01";
            String countryISO = "US";

            String hashedPassword = hashPassword(password);

            return  initialAdminData( email, hashedPassword, uuid, firstName, lastName, dob, hasHIV, hivDiagnosisDate, onART, artStartDate, countryISO, UserRole.ADMIN.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String generateRandomUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static String hashPassword(String password) throws IOException {
        String hashPasswordScript = "/src/scripts/hash_password.sh";
        String directory = System.getProperty("user.dir");
//        String absolutePath = directory + File.separator + hashPasswordScript;
        String absolutePath = directory + hashPasswordScript;
        // Build the command to execute the script
        ProcessBuilder processBuilder = new ProcessBuilder("bash", absolutePath, password);

        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            return reader.readLine().trim();
        }
    }

    public static boolean comparePasswords(String rawPassword, String storedHashedPassword) throws IOException {
        // Hash the raw password
        String hashedRawPassword = hashPassword(rawPassword);
        // Compare the hashed raw password with the stored hashed password
        return hashedRawPassword.equals(storedHashedPassword);
    }

    public  int yearsRemaining(String HivDate,String ISO, String ArtDate){

        int lifeExpectancy = 0;
        try {
            String LE_SCRIPT = "/src/scripts/life_expectancy.sh";
            String directory = System.getProperty("user.dir");
            String absolutePath = directory + File.separator + LE_SCRIPT;
            ProcessBuilder processBuilder = new ProcessBuilder("bash",absolutePath, ISO);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            double doubleValue = Double.parseDouble(reader.readLine());
            lifeExpectancy = (int) doubleValue;
            System.out.println(" Life Expectancy = " + lifeExpectancy);
//            while ((line = reader.readLine()) != null) {
//                lifeExpectancy = Integer.parseInt(line);
//            }
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();

        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy");
        LocalDate hivDiagnoseDate = LocalDate.parse(HivDate, formatter);
        LocalDate ArtStartDate = LocalDate.parse(ArtDate, formatter);
        LocalDate currentDate = LocalDate.now();
        Period HivPeriod = Period.between(hivDiagnoseDate, currentDate);
        Period ArtPeriod = Period.between(ArtStartDate,currentDate);


        int HivDateDiagnose = HivPeriod.getYears();
        int ArtDateStart = ArtPeriod.getYears();
        int exponent = ArtDateStart - (HivDateDiagnose - 1);

        System.out.println("DATE --->" + HivDateDiagnose + " __ " + ArtDateStart + " __ " + exponent + " -- " + lifeExpectancy);

        return (int) ((lifeExpectancy - HivDateDiagnose) * Math.pow(0.9,exponent));




    }


}
