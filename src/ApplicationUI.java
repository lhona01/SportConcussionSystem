import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import static java.lang.System.out;

public class ApplicationUI {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RESET = "\u001B[0m";

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        ArrayList<Athlete> athlete = new ArrayList<>();
        ArrayList<MedicalPractitioner> medicalPractitioner = new ArrayList<>();
        ArrayList<FormStorage> formStorage = new ArrayList<>();

        // test
        athlete.add(new Athlete("apple", 0, 0));
        formStorage.add(new FormStorage(0));
        medicalPractitioner.add(new MedicalPractitioner("Sherlock", 0));

        while(true) {
            int input = handleDisplayMenu();

            // Athlete's usages
            if (input == 1) {
                Athlete currentAthlete = handleAthleteLogin(athlete);
                boolean loopAthleteMenu = true;

                while (loopAthleteMenu) {
                    int athleteInput = handleAthleteMenu(currentAthlete.name, currentAthlete.id);

                    if (athleteInput == 1) { // Symptom Evaluation Form
                        SymptomEvaluationForm form = fillSYmptomEvaluationForm();
                        formStorage.get(currentAthlete.id).addForm(form);
                    }
                    else if (athleteInput == 2) { // Symptom Evaluation Summary
                        formStorage.get(currentAthlete.id).printSymptomEvaluationSummary();
                    }
                    else if (athleteInput == 3) { // Am I at RIsk
                        out.println("Am I at Risk ----------------------------------");
                        String atRisk = formStorage.get(currentAthlete.id).anICurrentlyAtRisk();

                        if (Objects.equals(atRisk, "Unsure"))
                            out.println(ANSI_YELLOW + atRisk);
                        else if (Objects.equals(atRisk, "No difference"))
                            out.println(ANSI_GREEN + atRisk);
                        else if (Objects.equals(atRisk, "Very different"))
                            out.println(ANSI_RED + atRisk);
                        out.println(ANSI_RESET);
                    }
                    else if (athleteInput == 4) {
                        out.println("Doctor's message-------------------------------");
                        out.println(formStorage.get(currentAthlete.id).getMessage());
                    }
                    else if (athleteInput == 5) { // back to main menu/loop
                        loopAthleteMenu = false;
                    }
                }
            }
            // Athlete Create New Account
            else if (input == 2) {
                athlete.add(athleteCreateNewAccount(athlete.size()));
                formStorage.add(createStorageForNewAthlete(athlete.getLast().id));
                out.println("Hello " + athlete.getLast().name + ". Your Id is: " + athlete.getLast().id);
            }
            // Medical Practitioner Menu
            else if (input == 3) {
                MedicalPractitioner currentPractitioner = handlePractitionerLogin(medicalPractitioner);
                boolean loopPractitionerMenu = true;

                while (loopPractitionerMenu) {
                    int practitionerInput = handlePractitionerMenu(currentPractitioner.name, currentPractitioner.id, athlete);

                    if (practitionerInput == 1) { // View Athlete's symptom report
                        int athleteId;
                        out.println();
                        out.print("Enter Athlete's id: ");
                        athleteId = scanner.nextInt();
                        formStorage.get(athleteId).printSymptomEvaluationReport();
                    } else if (practitionerInput == 2) { // leave advice for athlete
                        int athleteId;
                        out.println();
                        out.print("Enter Athlete's id: ");
                        athleteId = scanner.nextInt();
                        out.print("Type your message: ");
                        scanner.nextLine();
                        String message = scanner.nextLine();
                        formStorage.get(athleteId).setMessage(message);
                    } else if (practitionerInput == 3) { // back to menu
                        loopPractitionerMenu = false;
                    }
                }
            }
            // Medical Practitioner create account
            else if (input == 4) {
                medicalPractitioner.add(practitionerCreateNewAccount(medicalPractitioner.size()));
                out.println("Hello " + medicalPractitioner.getLast().name + ". Your Id is: " + medicalPractitioner.getLast().id);            }
        }
    }

    public static boolean isValidInput(int input, int initialOptionNumber, int finalOptionNumber) {
        if (input == finalOptionNumber) {
            out.println("Exiting the program.");
            System.exit(0); // Exiting with status code 0
        }

        if (input < initialOptionNumber || input > finalOptionNumber) {
            out.println();
            out.println("Sorry, Invalid input! Try again.");
            out.println();
            return false;
        }
        return true;
    }

    // Main page
    public static void displayMenu() {
        out.println("Welcome to the Sports Concussion Assessment System");
        out.println("--------------------------------------------------");
        out.println("Select from the following options (1-5)");
        out.println();
        out.println("1. Existing Athlete login");
        out.println("2. New Athlete Create account");
        out.println("3. Existing Medical Practitioner login");
        out.println("4. New Medical Practitioner Create account");
        out.println("5. Exit program");
        out.println();
        out.print("Enter your option here: ");
    }

    public static int handleDisplayMenu() {
        int input;
        boolean isValidInput;

        do {
            displayMenu();
            input = scanner.nextInt();
            isValidInput = isValidInput(input, 1, 5);
        } while(!isValidInput);

        return input;
    }
    // End Main page

    // Athlete Login Page
    public static void displayAthleteLogin() {
            out.println();
            out.print("Enter your login Id: ");
    }

    public static Athlete handleAthleteLogin(ArrayList<Athlete> athlete) {
        Athlete currentAthlete = null;
        int id;
        boolean isValidId;

        do {
            displayAthleteLogin();
            id = scanner.nextInt();
            if (id < athlete.size()) {
                isValidId = true;
                currentAthlete = athlete.get(id);
            }
            else {
                isValidId = false;
                out.println("Invalid Id, try again.");
            }
        } while(!isValidId);

        return currentAthlete;
    }
    // End Athlete login page

    // Athlete's Main page-------------------------------------------------
    public static void displayAthleteMenu(String name, int id) {
        out.println();
        out.println("Hello " + name + ". Id: " + id);
        out.println("---------------------------------");
        out.println("Select from the following options (1-5)");
        out.println("1. New Symptom evaluation form");
        out.println("2. Symptom evaluation summary");
        out.println("3. Am I at risk?");
        out.println("4. Doctor's message");
        out.println("5. Logout + Back to main menu");
        out.println("6. Exit Program");
        out.println();
        out.print("Enter your option here: ");
    }

    public static int handleAthleteMenu(String name, int id) {
        int input;
        boolean isValidInput;

        do {
            displayAthleteMenu(name, id);
            input = scanner.nextInt();
            isValidInput = isValidInput(input, 1, 6);
        } while(!isValidInput);

        return input;
    }

    // New Symptom Evaluation form
    public static SymptomEvaluationForm fillSYmptomEvaluationForm() {
        SymptomEvaluationForm form = new SymptomEvaluationForm();
        form.getRatings();
        return form;
    }

    // Athlete Create Account page----------------------------------------------
    public static Athlete athleteCreateNewAccount(int id) {
        scanner.nextLine(); // consume newline left over by nextInt()
        out.println("Athlete Create new Account----------------");
        out.print("Enter your name: ");
        String name = scanner.nextLine();
        out.print("Enter your practitioner's id: ");
        int doctorId = scanner.nextInt();
        return new Athlete(name, id, doctorId);
    }

    public static FormStorage createStorageForNewAthlete(int id) {
        return new FormStorage(id);
    }

    // Medical Practitioner Main page
    public static void displayPractitionerMenu(String name, int id, ArrayList<Athlete> athlete) {
        out.println();
        out.println("Hello " + name + ". Id: " + id);
        out.println("---------------------------------");
        out.println("Select from the following options (1-3)");
        out.println("1. View symptom report for Athlete");
        out.println("2. Give Advice to an Athlete");
        out.println("3. Logout + Back to main menu");
        out.println("4. Exit Program");
        out.println("---List of Athletes----");
        for (Athlete value : athlete) {
            if (value.doctorsId == id)
                System.out.println("Id: " + value.id + ", Name: " + value.name);
        }
        out.println();
        out.print("Enter your option here: ");
    }

    public static int handlePractitionerMenu(String name, int id, ArrayList<Athlete> athlete) {
        int input;
        boolean isValidInput;

        do {
            displayPractitionerMenu(name, id, athlete);
            input = scanner.nextInt();
            isValidInput = isValidInput(input, 1, 4);
        } while(!isValidInput);

        return input;
    }

    // Practitioner login
    public static void displayPractitionerLogin() {
        out.println();
        out.print("Enter your login Id: ");
    }

    public static MedicalPractitioner handlePractitionerLogin(ArrayList<MedicalPractitioner> practitioner) {
        MedicalPractitioner currentPractitioner = null;
        int id;
        boolean isValidId;

        do {
            displayPractitionerLogin();
            id = scanner.nextInt();
            if (id < practitioner.size()) {
                isValidId = true;
                currentPractitioner = practitioner.get(id);
            }
            else {
                isValidId = false;
                out.println("Invalid Id, try again.");
            }
        } while(!isValidId);

        return currentPractitioner;
    }

    // Athlete Create Account page----------------------------------------------
    public static MedicalPractitioner practitionerCreateNewAccount(int id) {
        scanner.nextLine(); // consume newline left over by nextInt()
        out.println("Practitioner Create new Account----------------");
        out.print("Enter your name: ");
        String name = scanner.nextLine();
        return new MedicalPractitioner(name, id);
    }
}
