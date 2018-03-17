/*
lab08 - StudentRecord.java
Mustafa Al-Azzawe
100617392
*/

package sample;

public class StudentRecord {
    private String sid;
    private String letter;

    private float assignments;
    private float midterm;
    private float finalExam;
    private float finalMark;

    private boolean finalGrade;

    public StudentRecord() { }

    public StudentRecord(String sid, float assignments, float midterm, float finalExam) {
        this.sid = sid;
        this.assignments = assignments;
        this.midterm = midterm;
        this.finalExam = finalExam;

        calcMark();
        calcLetter();
    }

    public String getSid() { return this.sid; }

    public float getAssignments() {
        return assignments;
    }

    public void setAssignments(float assignments) {
        if((assignments >= 0.0) && (assignments <= 100.0)){
            this.assignments = assignments;
        }
    }

    public float getMidterm() {
        return midterm;
    }

    public void setMidterm(float midterm) {
        if((midterm >= 0.0) && (midterm <= 100.0)){
            this.midterm = midterm;
        }
    }

    public float getFinalExam() {
        return finalExam;
    }

    public void setFinalExam(float finalExam) {
        if((finalExam >= 0.0f) && (finalExam <= 100.0f)) {
            this.finalExam = finalExam;
        }
    }

    public float getFinalMark() {
        return finalMark;
    }

    public void calcMark() {
        //assignments - 20%, midterm - 30%, finalExam - 50%
        finalMark = (0.2f * assignments + 0.3f * midterm + 0.5f * finalExam);

        //check if the finalMark has been calculated
        finalGrade = true;
    }

    public String getLetter() {
        return letter;
    }

    public void calcLetter() {
        //if finalMark not calculated return
        if (!finalGrade) return;

        //determine letter grade
        if (finalMark >= 80.0) {
            letter = "A";
        } else if (finalMark >= 70.0 && finalMark < 80.0) {
            letter = "B";
        } else if (finalMark >= 60.0 && finalMark < 70.0){
            letter = "C";
        } else if (finalMark >= 50.0 && finalMark < 60.0){
            letter = "D";
        } else if (finalMark < 50){
            letter = "F";
        } else {
            letter = "N/A";
        }
    }
}