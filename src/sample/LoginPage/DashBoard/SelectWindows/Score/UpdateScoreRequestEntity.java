package sample.LoginPage.DashBoard.SelectWindows.Score;

public class UpdateScoreRequestEntity {
    private String table;
    private String id;
    private double score;
    private String ca;

    public UpdateScoreRequestEntity() {
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

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getCa() {
        return ca;
    }

    public void setCa(String ca) {
        this.ca = ca;
    }
}
