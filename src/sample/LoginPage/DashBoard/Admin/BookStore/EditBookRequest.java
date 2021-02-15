package sample.LoginPage.DashBoard.Admin.BookStore;

import javax.print.DocFlavor;

public class EditBookRequest {
    private String entity;
    private String column;
    private String id;
    public EditBookRequest() {
    }

    public EditBookRequest(String entity, String column, String id) {
        this.entity = entity;
        this.column = column;
        this.id = id;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
