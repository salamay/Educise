package sample.LoginPage.DashBoard.SelectWindows.Score;

public class GetScoreRequestEntity {
    private String table;
    private String name;
    private String term;

    public GetScoreRequestEntity() {
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }
}
