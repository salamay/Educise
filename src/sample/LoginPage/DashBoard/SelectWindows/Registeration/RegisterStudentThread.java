package sample.LoginPage.DashBoard.SelectWindows.Registeration;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Platform;
import okhttp3.*;
import sample.ConnectionError;

import java.io.*;

public class RegisterStudentThread extends  Thread {

    //file
    private File file;
    private File MotherPictureFile;
    private File FatherPictureFile;
    private File NotAvailableFile = new File("src/image/not_available.jpg");
    private double counter = 0.0;
    private JSON json;
    private String clas;
    private LoadingWindow loadingWindow;

    public RegisterStudentThread() {

    }

    public RegisterStudentThread(String studentname, int age, String fathername, String mothername, String NextOfKin,
                                 String address, float PhoneNo, String NickName, String Hobbies, String TurnOn,
                                 String TurnOff, String Club, String RoleModel, String FutureAmbition, String Gender, String clas,
                                 File file, File FatherPicture, File Mother) {
        this.clas = clas;
        // setting the json parameterJSON object
        json = new JSON();
        json.setStudentname(studentname);
        json.setAge(age);
        json.setFathername(fathername);
        json.setMothername(mothername);
        json.setNextofkin(NextOfKin);
        json.setAddress(address);
        json.setPhoneno(String.valueOf(PhoneNo));
        json.setNickname(NickName);
        json.setHobbies(Hobbies);
        json.setTurnon(TurnOn);
        json.setTurnoff(TurnOff);
        json.setClub(Club);
        json.setRolemodel(RoleModel);
        json.setFutureambition(FutureAmbition);
        json.setGender(Gender);
        this.file = file;
        this.FatherPictureFile = FatherPicture;
        this.MotherPictureFile = Mother;
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
            OkHttpClient client=new OkHttpClient();
            System.out.println("[RegisterstudentThread]: "+"preparing Json body");
            RequestBody jsonBody=RequestBody.create(entity,MediaType.parse("application/json"));
            System.out.println("[RegisterstudentThread]: "+"making request body");
            RequestBody requestBody=new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("json","info.txt",jsonBody)
                    .addFormDataPart("studentpicture",file.getName(),RequestBody.create(MediaType.parse("image/jpeg"),file))
                    .addFormDataPart("fatherpicture",FatherPictureFile.getName(),RequestBody.create(MediaType.parse("image/jpeg"),FatherPictureFile))
                    .addFormDataPart("motherpicture",MotherPictureFile.getName(),RequestBody.create(MediaType.parse("image/jpeg"),MotherPictureFile))
                    .build();
            System.out.println("[RegisterstudentThread]: "+" Finished preparing request body");
            System.out.println("[RegisterstudentThread]: "+"sending request");
            Request  request=new Request.Builder()
                    .url("http://localhost:8080/register/"+clas)
                    .post(requestBody)
                    .addHeader("Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYWxhbWF5IiwiaWF0IjoxNTk4NTA5MDU2LCJleHAiOjE1OTg2ODkwNTZ9.rJnYfPefYX8tapQkTmyKuv15tprmJEyYl6FHvB21AZg")
                    .build();
            try {

                Response response=client.newCall(request).execute();
                System.out.println(request);
                System.out.println(response.toString());
                if (response.code()==200|| response.code()==201|| response.code()==202|| response.code()==203-209){
                    Platform.runLater(()->{
                        LoadingWindow.window.close();
                        boolean error=new ConnectionError().Connection("SUCCESS");
                        if (error){
                            System.out.println("[RegisterstudentThread]--> Connection Error,Window close");
                        }
                    });
                    response.close();
                }else {
                    System.out.println("[RegisterstudentThread]--> server:error:"+response.code()+" Unable to Register Student");
                    Platform.runLater(()->{
                        LoadingWindow.window.close();
                        boolean error=new ConnectionError().Connection("server:error "+response.code()+" Unable to Register Student,CHECK INTERNET CONNECTION");
                        if (error){
                            RegisterationWindow.window.close();
                            System.out.println("[RegisterstudentThread]--> Connection Error,Window close");
                        }
                    });
                }
            } catch (IOException e) {
                Platform.runLater(()->{
                    LoadingWindow.window.close();
                    boolean error=new ConnectionError().Connection("Unable to establish connection,CHECK INTERNET CONNECTION");
                    if (error){
                        RegisterationWindow.window.close();
                        System.out.println("[RegisterstudentThread]--> Connection Error,Window close");
                    }
                });
                System.out.println("[RegisterstudentThread]: "+"Request failled");
                e.printStackTrace();
            }
        ////////////////////////////Preparing request end////////////////////////////////////
            /////////////////////////////////////////////////////////////

            //Increase the progress bar
//            while (true) {
//                System.out.println(counter);
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                Platform.runLater(() -> {
//
//                });
//                counter += 0.01;
//                if (counter > 1) {
//                    break;
//                }
//            }

    }
}