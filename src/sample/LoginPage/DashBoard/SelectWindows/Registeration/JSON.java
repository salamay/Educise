package sample.LoginPage.DashBoard.SelectWindows.Registeration;

public class JSON {

    private String studentname;
    private int age;
    private String fathername;
    private String mothername;
    private String nextofkin;
    private String address;
    private String phoneno;
    private String nickname;
    private String hobbies;
    private String turnon;
    private String turnoff;
    private String club;
    private String rolemodel;
    private String futureambition;
    private String gender;


    public JSON() {
    }

    public JSON(String studentname, int age, String fathername, String mothername, String nextofkin, String address, String phoneno, String nickname, String hobbies, String turnon, String turnoff, String club, String rolemodel, String futureambition, String gender) {
        this.studentname = studentname;
        this.age = age;
        this.fathername = fathername;
        this.mothername = mothername;
        this.nextofkin = nextofkin;
        this.address = address;
        this.phoneno = phoneno;
        this.nickname = nickname;
        this.hobbies = hobbies;
        this.turnon = turnon;
        this.turnoff = turnoff;
        this.club = club;
        this.rolemodel = rolemodel;
        this.futureambition = futureambition;
        this.gender = gender;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setFathername(String fathername) {
        this.fathername = fathername;
    }

    public void setMothername(String mothername) {
        this.mothername = mothername;
    }

    public void setNextofkin(String nextofkin) {
        this.nextofkin = nextofkin;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public void setTurnon(String turnon) {
        this.turnon = turnon;
    }

    public void setTurnoff(String turnoff) {
        this.turnoff = turnoff;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public void setRolemodel(String rolemodel) {
        this.rolemodel = rolemodel;
    }

    public void setFutureambition(String futureambition) {
        this.futureambition = futureambition;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}
