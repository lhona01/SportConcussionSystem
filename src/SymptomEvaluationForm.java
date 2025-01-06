import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;

public class SymptomEvaluationForm {
    String[] symptoms = {
            "Headache", "Pressure in head", "Neck pain", "Nausea or vomiting", "Dizziness",
            "Blurred vision", "Balance problems", "Sensitivity to light", "Sensitivity to noise",
            "Feeling slowed down", "Feeling like \"in a fog\"", "Don't feel right",
            "Difficulty concentrating", "Difficulty remembering", "Fatigue or low energy",
            "Confusion", "Drowsiness", "Trouble falling asleep", "More emotional",
            "Irritability", "Sadness", "Nervous or anxious"
    };
    int[] rating = new int[symptoms.length];
    int ratingLowerBound = 0;
    int ratingUpperBound = 6;
    int totalNumberOfSymptoms;
    int symptomSeverityScore;
    ArrayList<String> symptomFelt = new ArrayList<>();
    ArrayList<Integer> symptomRating = new ArrayList<>();
    String amIatRisk;
    Date currentDate;

    public void getRatings() {
        Scanner scanner = new Scanner(System.in);
        currentDate = new Date();

        System.out.println("Rate how you are feeling on scale of (0 normal to 6 severe) for the following.");
        for (int i = 0; i < symptoms.length; i++) {
            System.out.print(symptoms[i] + ": ");
            int input = scanner.nextInt();

            if (input >= ratingLowerBound && input <= ratingUpperBound)
                rating[i] = input;
            else
            {
                System.out.println("Invalid ranting! input number between 0 normal to 6 severe!");
                i--;
            }
        }

        totalNumberOfSymptoms = calculateTotalNumberOfSymptom();
        symptomSeverityScore = calculateSymptomSeverityScore();


        System.out.println("---------------------------------------------------------------");
        System.out.println("Form complete, go to symptom evaluation summary for the result.");
    }

    private int calculateTotalNumberOfSymptom()
    {
        int symptomCount = 0;
        for (int j : rating) {
            if (j > 2)
            {
                symptomCount++;
                symptomFelt.add(symptoms[j]);
                symptomRating.add(rating[j]);
            }
        }

        return symptomCount;
    }

    private int calculateSymptomSeverityScore() {
        int scoreCount = 0;
        for (int i : rating) {
            scoreCount += i;
        }

        return scoreCount;
    }
}
