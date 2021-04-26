package sample.LoginPage.DashBoard.Admin.BookStore;

public class BookHistoryResponse {
    private String id;
    private String title;
    private String author;
    private String amountsold;
    private String buyer;
    private String date;
    private String session;
    private String term;

    public BookHistoryResponse() {
    }

    public BookHistoryResponse(String id, String title, String author, String amountsold, String buyer, String date, String session, String term) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.amountsold = amountsold;
        this.buyer = buyer;
        this.date = date;
        this.session = session;
        this.term = term;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAmountsold() {
        return amountsold;
    }

    public void setAmountsold(String amountsold) {
        this.amountsold = amountsold;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }
}
