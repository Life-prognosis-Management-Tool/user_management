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

    public ArrayList<String> login(String email, String password) throws IOException{
        ArrayList<String> myUserInfo = new ArrayList<String>();
        try{

            String hashedPassword = hashPassword(password);


            String LOGIN_USER_SCRIPT = "src\\scripts\\login-user.sh";

            String directory = System.getProperty("user.dir");
            String absolutePath = directory + File.separator + LOGIN_USER_SCRIPT;

            System.out.println(absolutePath);

            ProcessBuilder myBuilder = new ProcessBuilder("bash", absolutePath,email,hashedPassword);

                Process process = myBuilder.start();

                try(BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));){
                    String result;
                    System.out.println("READER --> "+ reader.readLine());
                    while((result = reader.readLine()) != null){
                        myUserInfo.add(result);
                    }
                }
            return myUserInfo;
        } catch (IOException e) {
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
            if (sendInitialData) {
                System.out.println("Admin data initialization successful.");
            } else {
                System.out.println("Admin data initialization failed.");
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
                    System.out.println("Please choose: Enter your credentials");
                    System.out.println("\nPlease enter your email");
                    String email = scanner.nextLine();
                    System.out.println("Please enter your password");
                    String password = scanner.nextLine();

                    if (email != null || password != null) {
                        System.out.println("Email: " + email + "Password: " + password);

                        try{

                            ArrayList<String> fromLogin = login(email,password);
                            System.out.println("Returened Array = "+ fromLogin.toString());
                            if(fromLogin.isEmpty()){
                                System.out.println("Login Failed");
                            } else {
                                fromLogin.forEach(line->{
                                    System.out.println(line);
                                });

                            }


                        } catch (IOException e) {
                            e.printStackTrace();
                        }

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
