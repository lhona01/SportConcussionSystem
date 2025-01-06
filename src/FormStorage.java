import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class FormStorage {
    int id;
    Queue<SymptomEvaluationForm> forms = new LinkedList<>();
    String message;

    // Assign the id to Athlete, like a file number
    public FormStorage(int id) {
        this.id= id;
    }

    public void addForm(SymptomEvaluationForm form) {
        form.amIatRisk = amIAtRisk(form.totalNumberOfSymptoms, form.symptomSeverityScore);

        if (forms.size() >= 5)
            forms.poll();
        forms.add(form);
    }

    public String amIAtRisk(int totalNumberOfSymptom, int symptomSeverityScore) {
        if (!forms.isEmpty()) {
            SymptomEvaluationForm previousForm = ((LinkedList<SymptomEvaluationForm>) forms).getLast();
            int totalSymptomDifference = totalNumberOfSymptom - previousForm.totalNumberOfSymptoms;

            if (totalSymptomDifference < 3 && symptomSeverityScore < 10) {
                return "No difference";
            }

            if (totalSymptomDifference < 3 && symptomSeverityScore >= 10)
                return "Unsure";

            if (totalSymptomDifference >= 3 || symptomSeverityScore >= 15)
                return "Very different";
        }

        return "not enough data";
    }

    public void printSymptomEvaluationSummary() {
        ArrayList<SymptomEvaluationForm> summary = new ArrayList<>(forms);
        System.out.println("Symptom Summary for 5 recent games:");
        for (int i = 0; i < summary.size(); i++) {
            System.out.println("-------------------------------");
            System.out.println("Game: " + (i + 1));
            System.out.println("Total Number Of Symptoms = " + summary.get(i).totalNumberOfSymptoms);
            System.out.println("Symptom Severity Score = " + summary.get(i).symptomSeverityScore);
            System.out.println("Risk Indicator = " + summary.get(i).amIatRisk);
        }
    }

    public void printSymptomEvaluationReport() {
        ArrayList<SymptomEvaluationForm> report = new ArrayList<>(forms);
        System.out.println("Symptom reports for 5 recent games:");
        for (int i = 0; i < report.size(); i++) {
            System.out.println("-------------------------------");
            System.out.println("Game: " + (i + 1));
            System.out.println("Total Number Of Symptoms = " + report.get(i).totalNumberOfSymptoms);
            System.out.println("Symptom Severity Score = " + report.get(i).symptomSeverityScore);
            System.out.println("Symptoms: ");
            for (int j = 0; j < report.get(i).rating.length; j++) {
                if (report.get(i).rating[j] > 2)
                    System.out.println(report.get(i).symptoms[j] + ": " + report.get(i).rating[j]);
            }
        }
    }

    public String anICurrentlyAtRisk() {
        ArrayList<SymptomEvaluationForm> list = new ArrayList<>(forms);
        return list.getLast().amIatRisk;
    }

    public void setMessage(String message) {
        this.message = message;
        System.out.println("Message sent!");
    }

    public String getMessage() {
        return message;
    }
}
