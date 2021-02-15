package sample.LoginPage.DashBoard.Admin.BookStore;

public class Book {
    public String id;
    public String title;
    public String author;
    public int price;
    public int copies;
    public String year;
    public String term;
    //This date variable is used when selling books
    private String date;

    public Book() {

    }

    public Book(String id, String title, String author, int price, int copies, String year, String term, String date) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.copies = copies;
        this.year = year;
        this.term = term;
        this.date = date;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
