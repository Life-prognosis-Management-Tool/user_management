import models.Patient;
import models.UserRole;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    private final String path = System.getProperty("user.dir");


    public static void main(String[] args) throws ParseException {
        new Main().app();
    }


    public String initializeRegisterUser(String email){
        ArrayList<String> myUserInfo = new ArrayList<String>();

        UUID myCode = UUID.randomUUID();
        ProcessBuilder myBuilder = new ProcessBuilder(System.getProperty("user.dir")+"/src/scripts/mytest.sh",email ,myCode.toString());

        try {
            Process process = myBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String result = null;

            while((result = reader.readLine()) != null){
                myUserInfo.add(result);
            }

        }
        catch (IOException e){
            System.out.println("Error: " + e);
            e.printStackTrace();
        }


        return "Your The user UUID is: "+myCode + "With Email: "+ email ;
    }

    public ArrayList<String> completeRegisterUser(Patient patient){
        ArrayList<String> myUserInfo = new ArrayList<String>();

        ProcessBuilder myBuilder = new ProcessBuilder(System.getProperty("user.dir")+"/src/scripts/register_user.sh", patient.getUser_email(),patient.getUser_password(),patient.getUUID(), patient.getF_name(), patient.getL_name(), patient.getDOB().toString(), patient.getHasHIV().toString(), patient.getHivDiagnosisDate().toString(),patient.getTakingART().toString(), patient.getArtStartDate().toString(), patient.getCountryISO(), UserRole.PATIENT.toString());

        try {
            Process process = myBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String result = null;

            while((result = reader.readLine()) != null){
                myUserInfo.add(result);
            }

        }
        catch (IOException e){
            System.out.println("Error: " + e);
            e.printStackTrace();
        }


        return myUserInfo;
    }

    public ArrayList<String> login(String email, String password) throws IOException {
        ArrayList<String> myUserInfo = new ArrayList<String>();

        try {
            String hashedPassword = hashPassword(password);
            ProcessBuilder myBuilder = new ProcessBuilder(path + "/src/scripts/login_user.sh", email, hashedPassword);
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

    public ArrayList<String> checkUUID(String UUID){
        ArrayList<String> myUserInfo = new ArrayList<String>();
        ProcessBuilder myBuilder = new ProcessBuilder(System.getProperty("user.dir")+"/src/scripts/test.command",UUID);

        try {
            Process process = myBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String result = null;

            while((result = reader.readLine()) != null){
//                System.out.println(result);
                myUserInfo.add(result);
            }

        }
        catch (IOException e){
            System.out.println("Error: " + e);
            e.printStackTrace();
        }


        return myUserInfo;
    }

    public void app() throws ParseException {
        boolean isDone = false;

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

                            if(fromLogin.get(10).equalsIgnoreCase("ADMIN")){
                                System.out.println("Welcome ADMIN");
                                isDone = true;
                            }
                            else if (fromLogin.get(10).equalsIgnoreCase("PATIENT")){
                                isDone = true;
                                Patient patient = new Patient(fromLogin.get(2),fromLogin.get(3),fromLogin.get(1),"");
                                SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
                                patient.setDOB(formatter.parse(fromLogin.get(4)));
                                patient.setHasHIV(Boolean.parseBoolean(fromLogin.get(5)));
                                patient.setHivDiagnosisDate(formatter.parse(fromLogin.get(6)));
                                patient.setTakingART(Boolean.parseBoolean(fromLogin.get(7)));
                                patient.setArtStartDate(formatter.parse(fromLogin.get(8)));
                                patient.setCountryISO(fromLogin.get(9));
                                System.out.println("Welcome PATIENT");
                            }
                            else {
                                System.out.println("No roleeeeee");
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
                        ArrayList<String> uuidValidArray = checkUUID(userUUID);
                        String uuidValid  = uuidValidArray.getFirst();

                        if (!uuidValid.contains("not found")){

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

                            if(hasHIV) {
                                System.out.println("Please enter your HIV Diagnosis Date e.g: 01-january-2000");
                                String hivDiagnosisString = scanner.nextLine();
                                Date hivDiagnosisDate = formatter.parse(hivDiagnosisString);
                                System.out.println("Do you take ART treatment? true or false");
                                boolean takingART = scanner.nextBoolean();
                                scanner.nextLine();
                                System.out.println("When did you start taking ART e.g: 01-january-2000");
                                String artStartString = scanner.nextLine();
                                Date artStartDate = formatter.parse(artStartString);

                                patient.setHasHIV(hasHIV);
                                patient.setHivDiagnosisDate(hivDiagnosisDate);
                                patient.setTakingART(takingART);
                                patient.setArtStartDate(artStartDate);
                            }
                            else {
                                patient.setHasHIV(false);
                                Date hivDiagnosisDate = formatter.parse("01-january-1000");
                                patient.setHivDiagnosisDate(hivDiagnosisDate);
                                patient.setTakingART(false);
                                Date artStartDate = formatter.parse("01-january-1000");
                                patient.setArtStartDate(artStartDate);
                                System.out.println("Enter your country ISO-CODE?");

                            }

                            ArrayList<String> fromCompleteRegistration =  completeRegisterUser(patient);
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
                        isDone = true;
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

    private static boolean initialAdminData(String email, String password, String uuid, String firstName, String lastName, String dob, String hasHIV, String hivDiagnosisDate, String onART, String artStartDate, String countryISO) throws IOException {

        String ADMIN_DATA_SCRIPT = "src\\scripts\\admin_data.sh";

        String directory = System.getProperty("user.dir");
        String absolutePath = directory + File.separator + ADMIN_DATA_SCRIPT;

        ProcessBuilder processBuilder = new ProcessBuilder(
                "bash", absolutePath, email, password, uuid, firstName, lastName, dob, hasHIV, hivDiagnosisDate, onART, artStartDate, countryISO
        );

        Process process = processBuilder.start();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            System.out.println(reader.readLine());
            while ((line = reader.readLine()) != null) {
                System.out.println(line); // Print script output
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

            return  initialAdminData( email, hashedPassword, uuid, firstName, lastName, dob, hasHIV, hivDiagnosisDate, onART, artStartDate, countryISO);

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
        String hashPasswordScript = "src\\scripts\\hash_password.sh";
        String directory = System.getProperty("user.dir");
        String absolutePath = directory + File.separator + hashPasswordScript;
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


}
