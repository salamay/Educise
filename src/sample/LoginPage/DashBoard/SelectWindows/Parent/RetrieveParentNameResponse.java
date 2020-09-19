package sample.LoginPage.DashBoard.SelectWindows.Parent;

public class RetrieveParentNameResponse {
    private String fathername;
    private String mothername;

    public RetrieveParentNameResponse() {
    }

    public RetrieveParentNameResponse(String fathername, String mothername) {
        this.fathername = fathername;
        this.mothername = mothername;
    }

    public String getFathername() {
        return fathername;
    }

    public void setFathername(String fathername) {
        this.fathername = fathername;
    }

    public String getMothername() {
        return mothername;
    }

    public void setMothername(String mothername) {
        this.mothername = mothername;
    }
}
