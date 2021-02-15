package sample.LoginPage.DashBoard.SelectWindows.Registeration;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Platform;
import okhttp3.*;
import sample.Configuration.Configuration;
import sample.Configuration.ConfigureApplication;
import sample.ConnectionError;
import sample.LoginPage.LogInModel;

import java.io.*;
import java.util.concurrent.TimeUnit;

public class RegisterStudentThread extends  Thread {

    //file
    private File file;
    private File MotherPictureFile;
    private File FatherPictureFile;
    private File OtherPictureFile;
    private File NotAvailableFile = new File("src/image/not_available.jpg");
    private double counter = 0.0;
    private JSON json;
    private LoadingWindow loadingWindow;

    public RegisterStudentThread() {

    }

    public RegisterStudentThread(String studentname, int age, String fathername, String mothername,String guardianName, String NextOfKin,
                                 String address, int PhoneNo,int parentPhoneNumber, String NickName, String Hobbies, String TurnOn,
                                 String TurnOff, String Club, String RoleModel, String FutureAmbition, String Gender, String session,
                                 File file, File FatherPicture, File Mother,File Other,String clas,String tag) {
        // setting the json parameterJSON object
        json = new JSON();
        json.setStudentname(studentname);
        json.setAge(age);
        json.setFathername(fathername);
        json.setMothername(mothername);
        json.setGuardianname(guardianName);
        json.setNextofkin(NextOfKin);
        json.setAddress(address);
        json.setPhoneno(PhoneNo);
        json.setParentphonenumber(parentPhoneNumber);
        json.setNickname(NickName);
        json.setHobbies(Hobbies);
        json.setTurnon(TurnOn);
        json.setTurnoff(TurnOff);
        json.setClub(Club);
        json.setRolemodel(RoleModel);
        json.setFutureambition(FutureAmbition);
        json.setGender(Gender);
        json.setClas(clas);
        json.setSession(session);
        json.setTag(tag);
        this.file = file;
        this.FatherPictureFile = FatherPicture;
        this.MotherPictureFile = Mother;
        this.OtherPictureFile=Other;
    }

    @Override
    public void run() {

        Platform.runLater(() -> {
            try {
                loadingWindow=new LoadingWindow();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        ////////Checking if file is null,if file is null, then the respective file is set to nothavailable file image////
        if (file==null){
            System.out.println("[RegisterstudentThread]: "+"Student picture is equal to null");
            System.out.println("[RegisterstudentThread]: "+"initializing Student picture to nothavailable file");
            file=NotAvailableFile;
        }
        if (FatherPictureFile==null){
            System.out.println("[RegisterstudentThread]: "+"father picture is equal to null");
            System.out.println("[RegisterstudentThread]: "+"initializing Father picture to nothavailable file");
            FatherPictureFile=NotAvailableFile;
        }
        if (MotherPictureFile==null){
            System.out.println("[RegisterstudentThread]: "+"Mother picture is equal to null");
            System.out.println("[RegisterstudentThread]: "+"initializing Mother picture to nothavailable file");
            MotherPictureFile=NotAvailableFile;
        }
        if (OtherPictureFile==null){
            System.out.println("[RegisterstudentThread]: "+"Other picture is equal to null");
            System.out.println("[RegisterstudentThread]: "+"initializing Other picture to nothavailable file");
            OtherPictureFile=NotAvailableFile;
        }

        ////////////////Checking end//////////////////////////////////////////////////////////////////////////////////

        ////////////////Preparing Gson /////////////////////////////
        System.out.println("[RegisterStudentThread]: " + "Preparing json ");
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        gsonBuilder.serializeNulls();
        Gson gson = gsonBuilder.create();
        String entity = gson.toJson(json);
        System.out.println(entity);
        System.out.println("[RegisterstudentThread]: "+"Finished preparing json");
        //////////Preparing Json end///////////////////////////
        //////////////////////////Preparing to send to server///////////////////////////
            System.out.println("[RegisterstudentThread]: "+"Creating Okhttp client");
        OkHttpClient client=new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build();

            System.out.println("[RegisterstudentThread]: "+"preparing Json body");
            RequestBody jsonBody=RequestBody.create(entity,MediaType.parse("application/json"));
            System.out.println("[RegisterstudentThread]: "+"making request body");
            RequestBody requestBody=new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("json","info.txt",jsonBody)
                    .addFormDataPart("studentpicture",file.getName(),RequestBody.create(MediaType.parse("image/jpeg"),file))
                    .addFormDataPart("fatherpicture",FatherPictureFile.getName(),RequestBody.create(MediaType.parse("image/jpeg"),FatherPictureFile))
                    .addFormDataPart("motherpicture",MotherPictureFile.getName(),RequestBody.create(MediaType.parse("image/jpeg"),MotherPictureFile))
                    .addFormDataPart("otherpicture",OtherPictureFile.getName(),RequestBody.create(MediaType.parse("image/jpeg"),OtherPictureFile))
                    .build();

            System.out.println("[RegisterstudentThread]: "+" Finished preparing request body");
            System.out.println("[RegisterstudentThread]: "+"sending request");

        if (Configuration.ipaddress!=null || Configuration.port!=null){
            Request  request=new Request.Builder()
                    .url("http://"+Configuration.ipaddress+":"+Configuration.port+"/register")
                    .post(requestBody)
                    .addHeader("Authorization","Bearer "+ LogInModel.token)
                    .build();
            try {
                Response response=client.newCall(request).execute();
                System.out.println(request);
                System.out.println(response.toString());
                if (response.code()==200){
                    Platform.runLater(()->{
                        LoadingWindow.window.close();
                        boolean error=new ConnectionError().Connection("SUCCESS");
                        if (error){
                            System.out.println("[RegisterstudentThread]--> Connection Error,Window close");
                        }
                    });
                    response.close();
                }else {
                    //when something wrong happens, the server send the cause of the problem as a response body
                    String message=new String(response.body().bytes(),"UTF-8");
                    System.out.println("[RegisterstudentThread]--> server:error:"+response.code()+" Unable to Register Student");
                    Platform.runLater(()->{
                        LoadingWindow.window.close();
                        boolean error=new ConnectionError().Connection(response.code()+":"+message);
                        if (error){
                            System.out.println("[RegisterstudentThread]--> Connection Error,Window close");
                        }
                    });
                    response.close();
                }
            } catch (IOException e) {
                Platform.runLater(()->{
                    LoadingWindow.window.close();
                    boolean error=new ConnectionError().Connection("Unable to establish connection,CHECK INTERNET CONNECTION");
                    if (error){
                        System.out.println("[RegisterstudentThread]--> Connection Error,Window close");
                    }
                });
                System.out.println("[RegisterstudentThread]: "+"Request failled");
                e.printStackTrace();
            }
        }else{
            Platform.runLater(()->{
                new ConnectionError().Connection("Invalid configuration, please configure your software in the log in page");
            });
        }

        ////////////////////////////Preparing request end////////////////////////////////////
            ///////////////////////////////////////////////////////////

    }
}