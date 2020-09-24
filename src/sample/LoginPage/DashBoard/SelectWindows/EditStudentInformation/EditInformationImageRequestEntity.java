package sample.LoginPage.DashBoard.SelectWindows.EditStudentInformation;

public class EditInformationImageRequestEntity {
    private String session;
    private String studentname;
    //This tag instance shows which image to change
    private String tag;
    //this class instance shows the student class
    private String clas;
    private byte[] image;

    public EditInformationImageRequestEntity() {
    }

    public EditInformationImageRequestEntity(String session, String studentname, String tag, String clas, byte[] image) {
        this.session = session;
        this.studentname = studentname;
        this.tag = tag;
        this.clas = clas;
        this.image = image;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getStudentname() {
        return studentname;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getClas() {
        return clas;
    }

    public void setClas(String clas) {
        this.clas = clas;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
