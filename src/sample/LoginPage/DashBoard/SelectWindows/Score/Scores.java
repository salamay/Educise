package sample.LoginPage.DashBoard.SelectWindows.Score;

public class Scores {
    private String id;
    private String subject;
    private double firstca;
    private double secondca;
    private double thirdca;
    private double fourthca;
    private double fifthca;
    private double sixthca;
    private double seventhca;
    private double eightca;
    private double ninthca;
    private double tenthca;
    private double exam;
    private double cumulative;
    private String term;

    public Scores() {
    }

    public Scores(String subject, double firstCa, double secondCa, double thirdCa, double fourthCa, double fifthCa, double sixthCa, double seventhCa, double eightCa, double ninthCa, double tenthCa, double exam, double cumulative, String term,String id) {
        this.subject = subject;
        this.firstca = firstCa;
        this.secondca = secondCa;
        this.thirdca = thirdCa;
        this.fourthca = fourthCa;
        this.fifthca = fifthCa;
        this.sixthca = sixthCa;
        this.seventhca = seventhCa;
        this.eightca = eightCa;
        this.ninthca = ninthCa;
        this.tenthca = tenthCa;
        this.exam = exam;
        this.cumulative = cumulative;
        this.term = term;
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public double getFirstca() {
        return firstca;
    }

    public void setFirstca(double firstca) {
        this.firstca = firstca;
    }

    public double getSecondca() {
        return secondca;
    }

    public void setSecondca(double secondca) {
        this.secondca = secondca;
    }

    public double getThirdca() {
        return thirdca;
    }

    public void setThirdca(double thirdca) {
        this.thirdca = thirdca;
    }

    public double getFourthca() {
        return fourthca;
    }

    public void setFourthca(double fourthca) {
        this.fourthca = fourthca;
    }

    public double getFifthca() {
        return fifthca;
    }

    public void setFifthca(double fifthca) {
        this.fifthca = fifthca;
    }

    public double getSixthca() {
        return sixthca;
    }

    public void setSixthca(double sixthca) {
        this.sixthca = sixthca;
    }

    public double getSeventhca() {
        return seventhca;
    }

    public void setSeventhca(double seventhca) {
        this.seventhca = seventhca;
    }

    public double getEightca() {
        return eightca;
    }

    public void setEightca(double eightca) {
        this.eightca = eightca;
    }

    public double getNinthca() {
        return ninthca;
    }

    public void setNinthca(double ninthca) {
        this.ninthca = ninthca;
    }

    public double getTenthca() {
        return tenthca;
    }

    public void setTenthca(double tenthca) {
        this.tenthca = tenthca;
    }

    public double getExam() {
        return exam;
    }

    public void setExam(double exam) {
        this.exam = exam;
    }

    public double getCumulative() {
        return cumulative;
    }

    public void setCumulative(double cumulative) {
        this.cumulative = cumulative;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

}
