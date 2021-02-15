package sample.LoginPage.DashBoard.SelectWindows.EditStudentInformation;

public class EditInformationImageRequestEntity {
    private String id;
    //This tag instance shows which image to change
    private String tag;
    private byte[] image;

    public EditInformationImageRequestEntity() {
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
