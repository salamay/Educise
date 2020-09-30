package sample.LoginPage.DashBoard.SelectWindows.Score;

public class Scores {
    private String Subject;
    private double FirstCa;
    private double SecondCa;
    private double ThirdCa;
    private double FourthCa;
    private double FifthCa;
    private double SixthCa;
    private double SeventhCa;
    private double EightCa;
    private double NinthCa;
    private double TenthCa;
    private double Exam;
    private double Cumulative;
    private String term;

    public Scores() {
    }

    public Scores(String subject, double firstCa, double secondCa, double thirdCa, double fourthCa, double fifthCa, double sixthCa, double seventhCa, double eightCa, double ninthCa, double tenthCa, double exam, double cumulative, String term) {
        Subject = subject;
        FirstCa = firstCa;
        SecondCa = secondCa;
        ThirdCa = thirdCa;
        FourthCa = fourthCa;
        FifthCa = fifthCa;
        SixthCa = sixthCa;
        SeventhCa = seventhCa;
        EightCa = eightCa;
        NinthCa = ninthCa;
        TenthCa = tenthCa;
        Exam = exam;
        Cumulative = cumulative;
        this.term = term;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }


    public double getFirstCa() {
        return FirstCa;
    }

    public void setFirstCa(double firstCa) {
        FirstCa = firstCa;
    }

    public double getSecondCa() {
        return SecondCa;
    }

    public void setSecondCa(double secondCa) {
        SecondCa = secondCa;
    }

    public double getThirdCa() {
        return ThirdCa;
    }

    public void setThirdCa(double thirdCa) {
        ThirdCa = thirdCa;
    }

    public double getFourthCa() {
        return FourthCa;
    }

    public void setFourthCa(double fourthCa) {
        FourthCa = fourthCa;
    }

    public double getFifthCa() {
        return FifthCa;
    }

    public void setFifthCa(double fifthCa) {
        FifthCa = fifthCa;
    }

    public double getSixthCa() {
        return SixthCa;
    }

    public void setSixthCa(double sixthCa) {
        SixthCa = sixthCa;
    }

    public double getSeventhCa() {
        return SeventhCa;
    }

    public void setSeventhCa(double seventhCa) {
        SeventhCa = seventhCa;
    }

    public double getEightCa() {
        return EightCa;
    }

    public void setEightCa(double eightCa) {
        EightCa = eightCa;
    }

    public double getNinthCa() {
        return NinthCa;
    }

    public void setNinthCa(double ninthCa) {
        NinthCa = ninthCa;
    }

    public double getTenthCa() {
        return TenthCa;
    }

    public void setTenthCa(double tenthCa) {
        TenthCa = tenthCa;
    }

    public double getExam() {
        return Exam;
    }

    public void setExam(double exam) {
        Exam = exam;
    }

    public double getCumulative() {
        return Cumulative;
    }

    public void setCumulative(double cumulative) {
        Cumulative = cumulative;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

}
