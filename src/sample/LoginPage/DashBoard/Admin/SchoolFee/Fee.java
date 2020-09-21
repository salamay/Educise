package sample.LoginPage.DashBoard.Admin.SchoolFee;

public class Fee {
    public String studentname;
    public String depositorname;
    public String clas;
    public String term;
    public String year;
    public String modeofpayment;
    public String date;
    public String id;
    public String amount;
    public String tag;

    public Fee() {
    }

    public Fee(String studentname, String depositorname, String clas, String term, String year, String modeofpayment, String date, String id, String amount,String tag) {
        this.studentname = studentname;
        this.depositorname = depositorname;
        this.clas = clas;
        this.term = term;
        this.year = year;
        this.modeofpayment = modeofpayment;
        this.date = date;
        this.id = id;
        this.amount = amount;
        this.tag=tag;
    }

    public String getStudentname() {
        return studentname;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }

    public String getDepositorname() {
        return depositorname;
    }

    public void setDepositorname(String depositorname) {
        this.depositorname = depositorname;
    }

    public String getClas() {
        return clas;
    }

    public void setClas(String clas) {
        this.clas = clas;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getModeofpayment() {
        return modeofpayment;
    }

    public void setModeofpayment(String modeofpayment) {
        this.modeofpayment = modeofpayment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
