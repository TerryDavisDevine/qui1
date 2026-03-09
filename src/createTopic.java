import java.io.File;

public class createTopic {
    String dirName;
    public createTopic(String folderName){
        dirName = folderName;
    }
    public void createDir(){
        File folder = new File("resources/"+dirName);
        if(folder.mkdir()){
            folder.getAbsolutePath();
            System.out.println("New topic created");
            File newQFile = new File(folder.getAbsolutePath()+"/questions.txt");
            File newAFile = new File(folder.getAbsolutePath()+"/answers.txt");
            try {
                if (newQFile.createNewFile() && newAFile.createNewFile()){
                    System.out.println("Questions file created succesfully");
                }else{
                    System.out.println("Error when creating quiz files, please check if the directory already contains these");
                }
            }catch(Exception e){
                System.out.println(e);
            }
        }else{
            System.out.println("An error occured, please check if the folder already exists");
        }
    }
}
