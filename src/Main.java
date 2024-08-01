import models.Patient;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    private static String path = "/Users/fm/Desktop/CMU OCPC/Life_Prognosis_Management_Tool/user_management-Class_creation/src/scripts/mytest.sh";
    private static String path1 = "/Users/fm/Desktop/CMU OCPC/Life_Prognosis_Management_Tool/user_management-Class_creation/src/scripts/test.command";


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

        ProcessBuilder myBuilder = new ProcessBuilder(System.getProperty("user.dir")+"/src/scripts/mytest.sh", patient.getUUID(), patient.getL_name(), patient.getF_name(), patient.getDOB().toString(), patient.getHasHIV().toString(), patient.getHivDiagnosisDate().toString(),patient.getTakingART().toString(), patient.getArtStartDate().toString(), patient.getCountryISO());

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

    public ArrayList<String> login(String email, String password){
        ArrayList<String> myUserInfo = new ArrayList<String>();

        ProcessBuilder myBuilder = new ProcessBuilder(System.getProperty("user.dir")+"/src/scripts/test.command",email,password);

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
                    System.out.println("Please choose: enter your credentials");
                    System.out.println("\nPlease enter your email");
                    String email = scanner.nextLine();
                    System.out.println("Please enter your password");
                    String password = scanner.nextLine();

                    if (email != null || password != null) {
                        System.out.println("Email: " + email + "Password: " + password);
                        ArrayList<String> fromLogin = login(email,password);

                        if(!fromLogin.isEmpty()){
                            fromLogin.forEach(line-> {
                                        System.out.println(line);
                            });
                            isDone = true;
                            clearScreen();
                            System.out.println("You logged in");
                        }
                        else{
                            System.out.println("Invalid Email or Password");
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
                         String uuidValid  = uuidValidArray.get(0);

                         if (uuidValid != null){

                             SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

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
                             System.out.println("Please enter your Date of birth");
                             String sob = scanner.nextLine();
                             Date dob = formatter.parse(sob);
                             System.out.println("Do you have HIV? true or false");
                             boolean hasHIV = scanner.nextBoolean();
                             scanner.nextLine();
                             System.out.println("Please enter your HIV Diagnosis Date");
                             String hivDiagnosisString = scanner.nextLine();
                             Date hivDiagnosisDate = formatter.parse(hivDiagnosisString);
                             System.out.println("Do you take ART treatment? true or false");
                             boolean takingART = scanner.nextBoolean();
                             scanner.nextLine();
                             System.out.println("When did you start taking ART");
                             String artStartString = scanner.nextLine();
                             Date artStartDate = formatter.parse(artStartString);
                             System.out.println("Enter your country ISO-CODE?");
                             String countryISOCode = scanner.nextLine();

                             Patient patient = new Patient(firstName,lastName,mail,userPassword);
                             patient.setUUID(userUUID);

                             patient.setDOB(dob);
                             patient.setHivDiagnosisDate(hivDiagnosisDate);
                             patient.setHasHIV(hasHIV);
                             patient.setTakingART(takingART);
                             patient.setArtStartDate(artStartDate);
                             patient.setCountryISO(countryISOCode);

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
}
