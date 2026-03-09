import java.util.Locale;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import static java.lang.System.exit;

public class Main{
    String qfilepath = "";
    String afilepath="";
    Scanner keyboard = new Scanner(System.in);
    public String[] getText(String filepath){
        try {
            File questions = new File(filepath);
            Scanner reader = new Scanner(questions);
            int count =0;
            while(reader.hasNextLine()){
                reader.nextLine();
                count++;
            }
            reader = new Scanner(questions);
            String[] questionArray = new String[count];
            count = 0;
            while(reader.hasNextLine()){
                questionArray[count] = reader.nextLine();
                count++;
            }
            return questionArray;
        }catch(Exception e){
            System.out.println(e);
        }
        return new String[]{"error"};
    }
    public void askQ(String[] questions, String[] answers){
        String answer;
        int score=0;
        if(questions.length == 0){
            System.out.println("There are currently no questions under this topic, please create some questions before attempting the test");
        }else {
            for (int i = 0; i < questions.length; i++) {
                System.out.println(questions[i]);
                answer = keyboard.nextLine();
                if (answer.toLowerCase().equals(answers[i].toLowerCase(Locale.ROOT))) {
                    System.out.println("Correct");
                    score++;
                } else {
                    System.out.println("Incorrect, the answer is :" + answers[i]);
                }
            }
            System.out.println("Score: " + score + "/" + questions.length);
        }
    }
    public void addQuestion(){
        try(FileWriter newq = new FileWriter(qfilepath,true)){
            newq.write("\r\n");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try(FileWriter newq = new FileWriter(afilepath,true)){
            newq.write("\r\n");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        while(true){
            System.out.println("What question would you like to add?");
            String Question = keyboard.nextLine();
            System.out.println("Answer: ");
            String answer = keyboard.nextLine();
            try(FileWriter newq = new FileWriter(qfilepath,true)){
                newq.write(Question+"\r\n");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            try(FileWriter newq = new FileWriter(afilepath,true)){
                newq.write(answer+"\r\n");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println("Question and answer pair added\nWould you like to add another question?(y/n)");
            String choice = keyboard.nextLine().toLowerCase(Locale.ROOT);
            if (!choice.equals("y")){
                break;
            }
        }
    }
    public void start(Main test) {
        System.out.println("Would you like to (a create a new topic (b continue to existing topics (c shutdown");
        String decision = keyboard.nextLine().toLowerCase(Locale.ROOT);
        switch (decision) {
            case "a":
                System.out.println("What is the name of the topic?");
                createTopic folderMake = new createTopic(keyboard.nextLine().toUpperCase(Locale.ROOT));
                folderMake.createDir();
                break;
            case "b":
                File files = new File("resources/");
                File[] allFiles = files.listFiles();
                String topics = "";
                for (int i = 0; i < allFiles.length; i++) {
                    if (allFiles[i].isDirectory()) {
                        if(i== allFiles.length-1){
                            topics+=allFiles[i].getName();
                        }else{
                            topics += allFiles[i].getName() + ", ";
                        }
                    }
                }
                System.out.println("What subject would you like to study for " + topics + "?\n");
                String choice = keyboard.nextLine().toUpperCase(Locale.ROOT);
                qfilepath = "resources/" + choice + "/questions.txt";
                afilepath = "resources/" + choice + "/answers.txt";
                System.out.print("What would you like to do: (a begin quiz (b add a question (press any other key for exit)\n");
                String ans = keyboard.nextLine().toLowerCase(Locale.ROOT);
                if (ans.equals("a")) {
                    test.askQ(test.getText(qfilepath), test.getText(afilepath));
                } else if (ans.equals("b")) {
                    addQuestion();
                }
                test.start(test);
                break;
            default:
                System.out.println("Program shutting down");
                exit(0);
        }
        start(test);
    }

    public static void main(String[] args){
        Main test = new Main();
        test.start(test);
    }
}