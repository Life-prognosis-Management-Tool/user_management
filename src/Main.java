import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    private static String path = "/Users/fm/Desktop/CMU OCPC/Life_Prognosis_Management_Tool/user_management-Class_creation/src/scripts/mytest.sh";
    private static String path1 = "/Users/fm/Desktop/CMU OCPC/Life_Prognosis_Management_Tool/user_management-Class_creation/src/scripts/test.command";




    public static void main(String[] args) {
        new Main().app();
    }

    public String initializeRegisterUser(String email){
        ArrayList<String> myUserInfo = new ArrayList<String>();

        UUID myCode = UUID.randomUUID();
        ProcessBuilder myBuilder = new ProcessBuilder(path,  email ,myCode.toString());

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

    public ArrayList<String> completeRegisterUser(String UUID, String lastName, String firstName, String dob, String hasHIV, String hivDiagnosisDate, String takingART, String artStartDate, String countryISOCode){
        ArrayList<String> myUserInfo = new ArrayList<String>();

        ProcessBuilder myBuilder = new ProcessBuilder(path, UUID, lastName, firstName, dob.toString(), hasHIV.toString(), hivDiagnosisDate.toString(),takingART.toString(), artStartDate.toString(), countryISOCode);

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
String path3 = "src\\scripts\\login-user.sh";



        String directory = System.getProperty("user.dir");
        String absolutePath = directory + File.separator + path3;

        System.out.println(absolutePath);

        ProcessBuilder myBuilder = new ProcessBuilder(absolutePath,email,password);

        try {
            Process process = myBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String result = null;
            System.out.println("READER --> "+ reader.readLine());

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
        ProcessBuilder myBuilder = new ProcessBuilder(path1,UUID);

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

    public void app(){


        try {
            boolean sendInitialData = initialData();
            System.out.println("Initial Data Sent ---> " + sendInitialData);
            if (sendInitialData) {
                System.out.println("Login successful.");
                // Proceed to next steps
            } else {
                System.out.println("Login failed. Please try again.");
                // Handle failed login
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

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
                        fromLogin.forEach(line->{
                            System.out.println(line);
                        });

                        isDone = true;

                        clearScreen();
                        System.out.println("You logged in");


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
                            System.out.println("You have UUID: " + userUUID );
                        }
                        else {
                            System.out.println("UUID: " + userUUID +" is not found.");
                        }

                    } else {
                        System.out.println("Please enter your UUID");
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

        String path3 = "src\\scripts\\admin_data.sh";

        String directory = System.getProperty("user.dir");
        String absolutePath = directory + File.separator + path3;

        System.out.println(File.separator);

        System.out.println(absolutePath);

        ProcessBuilder processBuilder = new ProcessBuilder(
                  "bash", absolutePath, email, password, uuid, firstName, lastName, dob, hasHIV, hivDiagnosisDate, onART, artStartDate, countryISO
        );

        Process process = processBuilder.start();
        System.out.println("Process 1111111111111"+ process);
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
//    String filePath = "src/data/users.txt";

    private static boolean initialData() throws IOException {
        try {
            System.out.println("File Path 11111111: " );
            String email = "adminchanged@example.com";
            String password = "adminpassword";
            String uuid = "123e4567-e89b-12d3-a456-426614174000";
            String firstName = "John";
            String lastName = "Doe";
            String dob = "1980-01-01";
            String hasHIV = "true";
            String hivDiagnosisDate = "2000-01-01";
            String onART = "true";
            String artStartDate = "2001-01-01";
            String countryISO = "US";

            // Call the initialization function
            boolean success = initialAdminData(email, password, uuid, firstName, lastName, dob, hasHIV, hivDiagnosisDate, onART, artStartDate, countryISO);
            System.out.println("File Path 11111111 -->: " );
            System.out.println("Success 11111111 -->: " + success);
            if (success) {
                System.out.println("Admin data initialization successful.");
            } else {
                System.out.println("Admin data initialization failed.");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}


//
//
// import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//
//public class Main {
//    public static void main(String[] args) {
//
//
////        // Example flow for user login
////        String email = "user@example.com"; // Get this from user input
////        String password = "password123";   // Get this from user input
//
//        String filePath = "src/data/users.txt";
//
//        try {
//            boolean sendInitialData = initialData(filePath);
//            System.out.println("Initial Data Sent ---> " + sendInitialData);
//            if (sendInitialData) {
//                System.out.println("Login successful.");
//                // Proceed to next steps
//            } else {
//                System.out.println("Login failed. Please try again.");
//                // Handle failed login
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
////        try {
////            boolean authenticated = authenticateUser(email, password);
////            System.out.println("Boolean ---> " + authenticated);
////            if (authenticated) {
////                System.out.println("Login successful.");
////                // Proceed to next steps
////            } else {
////                System.out.println("Login failed. Please try again.");
////                // Handle failed login
////            }
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//    }
//
//    private static boolean initialData(String filepath) throws IOException {
//        try {
//            System.out.println("File Path 11111111: " + filepath);
//            String email = "adminchanged@example.com";
//            String password = "adminpassword";
//            String uuid = "123e4567-e89b-12d3-a456-426614174000";
//            String firstName = "John";
//            String lastName = "Doe";
//            String dob = "1980-01-01";
//            String hasHIV = "true";
//            String hivDiagnosisDate = "2000-01-01";
//            String onART = "true";
//            String artStartDate = "2001-01-01";
//            String countryISO = "US";
//
//            // Call the initialization function
//            boolean success = initialAdminData(filepath, email, password, uuid, firstName, lastName, dob, hasHIV, hivDiagnosisDate, onART, artStartDate, countryISO);
//            System.out.println("File Path 11111111 -->: " + filepath);
//            System.out.println("Success 11111111 -->: " + success);
//            if (success) {
//                System.out.println("Admin data initialization successful.");
//            } else {
//                System.out.println("Admin data initialization failed.");
//            }
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return false;
//    }
//
//
//
//
//    private static boolean initialAdminData(String filePath, String email, String password, String uuid, String firstName, String lastName, String dob, String hasHIV, String hivDiagnosisDate, String onART, String artStartDate, String countryISO) throws IOException {
//        String path12 = "/scripts/admin_data.sh";
//        ProcessBuilder processBuilder = new ProcessBuilder(
//                 "bash", path12, filePath, email, password, uuid, firstName, lastName, dob, hasHIV, hivDiagnosisDate, onART, artStartDate, countryISO
//        );
//
//        Process process = processBuilder.start();
//        System.out.println("Process 1111111111111"+ process);
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
//            String line;
//            System.out.println(reader.readLine());
//            while ((line = reader.readLine()) != null) {
//                System.out.println(line); // Print script output
//                if (line.contains("Initial admin data has been written")) {
//                    return true;
//                }
//            }
//        }
//
//        return false;
//    }
//}
//
//
////    private static boolean initialAdminData2222(String email, String password) throws IOException {
////        ProcessBuilder processBuilder = new ProcessBuilder(
////                "bash", "scripts/authenticate_user.sh", email, password
////        );
////
////        Process process = processBuilder.start();
////        System.out.println("Line 1");
////        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
////            String line;
////            System.out.println("Line 2 ---> " + reader.readLine());
////            while ((line = reader.readLine()) != null) {
////
////                System.out.println("Line 2 ---> " + reader.readLine());
////                System.out.println(line); // Print script output
////                if (line.contains("Authentication successful")) {
////                    return true;
////                }
////            }
////        }
////
////        return false;
////
////    }
//
//
//
////        private static boolean authenticateUser(String email, String password) throws IOException {
////            ProcessBuilder processBuilder = new ProcessBuilder(
////                    "bash", "scripts/authenticate_user.sh", email, password
////            );
////
////            Process process = processBuilder.start();
////            System.out.println("Line 1");
////            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
////                String line;
////                System.out.println("Line 2 ---> " + reader.readLine());
////                while ((line = reader.readLine()) != null) {
////
////                    System.out.println("Line 2 ---> " + reader.readLine());
////                    System.out.println(line); // Print script output
////                    if (line.contains("Authentication successful")) {
////                        return true;
////                    }
////                }
////            }
////
////            return false;
////
////        }
////}