package sample.LoginPage.DashBoard.SelectWindows.Score;

//this class is the Json format
public class SaveSubjectRequestEntity {
    private String id;
    private String table;
    private String subject;


    public SaveSubjectRequestEntity() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

}
