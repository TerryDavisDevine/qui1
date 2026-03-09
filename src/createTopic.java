import java.io.File;

public class createTopic {
    String dirName;
    public createTopic(String folderName){
        dirName = folderName;
    }
    public void createDir(){
        File folder = new File("resources/"+dirName);
        if(folder.mkdir()){
            System.out.println(folder.getAbsolutePath());
            System.out.println("New topic created");
        }else{
            System.out.println("An error occured, please check if the folder already exists");
        }
    }
}
