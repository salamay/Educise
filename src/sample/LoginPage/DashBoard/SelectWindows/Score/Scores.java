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
    public  Scores(){
        Subject ="";
        FirstCa =0.0;
        SecondCa = 0.0;
        ThirdCa = 0.0;
        FourthCa = 0.0;
        FifthCa = 0.0;
        SixthCa = 0.0;
        SeventhCa = 0.0;
        EightCa = 0.0;
        NinthCa = 0.0;
        TenthCa = 0.0;
        Exam = 0.0;
        Cumulative = 0.0;

    }
    public Scores(String subject, Double firstCa, Double secondCa, Double thirdCa, Double fourthCa, Double fifthCa,
                  Double sixthCa, Double seventhCa, Double eightCa, Double ninthCa, Double tenthCa, Double exam,
                  Double cumulative) {
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
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public Double getFirstCa() {
        return FirstCa;
    }

    public void setFirstCa(Double firstCa) {
        FirstCa = firstCa;
    }

    public Double getSecondCa() {
        return SecondCa;
    }

    public void setSecondCa(Double secondCa) {
        SecondCa = secondCa;
    }

    public Double getThirdCa() {
        return ThirdCa;
    }

    public void setThirdCa(Double thirdCa) {
        ThirdCa = thirdCa;
    }

    public Double getFourthCa() {
        return FourthCa;
    }

    public void setFourthCa(Double fourthCa) {
        FourthCa = fourthCa;
    }

    public Double getFifthCa() {
        return FifthCa;
    }

    public void setFifthCa(Double fifthCa) {
        FifthCa = fifthCa;
    }

    public Double getSixthCa() {
        return SixthCa;
    }

    public void setSixthCa(Double sixthCa) {
        SixthCa = sixthCa;
    }

    public Double getSeventhCa() {
        return SeventhCa;
    }

    public void setSeventhCa(Double seventhCa) {
        SeventhCa = seventhCa;
    }

    public Double getEightCa() {
        return EightCa;
    }

    public void setEightCa(Double eigthCa) {
        EightCa = eigthCa;
    }

    public Double getNinthCa() {
        return NinthCa;
    }

    public void setNinethCa(Double ninethCa) {
        NinthCa = ninethCa;
    }

    public Double getTenthCa() {
        return TenthCa;
    }

    public void setTenthCa(Double tenthCa) {
        TenthCa = tenthCa;
    }

    public Double getExam() {
        return Exam;
    }

    public void setExam(Double exam) {
        Exam = exam;
    }

    public Double getCumulative() {
        return Cumulative;
    }

    public void setCumulative(Double cummulative) {
        Cumulative = cummulative;
    }
}
