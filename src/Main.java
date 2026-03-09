//created by TerryDavisDevine
import java.util.Locale;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import static java.lang.System.exit;

public class Main{
    String qfilepath = "";
    String afilepath="";
    Scanner keyboard = new Scanner(System.in);
    //getting text from text files and adding each new line as an element of an array
    public String[] getText(String filepath){
        try {
            //creating a new file and scanner object for retrieving text
            File questions = new File(filepath);
            Scanner reader = new Scanner(questions);
            int count =0;
            //counting how many lines the text file is to set the array size
            while(reader.hasNextLine()){
                reader.nextLine();
                count++;
            }
            //creating a new scanner object as the last one has already went through all of its lines
            reader = new Scanner(questions);
            String[] questionArray = new String[count];
            count = 0;
            //adding text lines as elements
            while(reader.hasNextLine()){
                questionArray[count] = reader.nextLine();
                count++;
            }
            return questionArray;
        }catch(Exception e){
            System.out.println(e);
        }
        //setting a final return statement to stop errors from not having one at the end of the method, will only execute if the try block is not
        return new String[]{"error"};
    }
    //method to quiz the users
    public void askQ(String[] questions, String[] answers){
        String answer;
        int score=0;
        //checking if there are any questions
        if(questions.length == 0){
            System.out.println("There are currently no questions under this topic, please create some questions before attempting the test");
        }else {
            for (int i = 0; i < questions.length; i++) {
                System.out.println(questions[i]);
                answer = keyboard.nextLine();
                //checking if answer is correct
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
    //method to add questions to a topic
    public void addQuestion(){
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
    //method to provide a menu
    public void start(Main test) {
        System.out.println("Would you like to:\n(a) create a new topic\n(b) continue to existing topics\n(any other key) shutdown");
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
                if(allFiles.length > 0) {
                    for (int i = 0; i < allFiles.length; i++) {
                        if (allFiles[i].isDirectory()) {
                            if (i == allFiles.length - 1) {
                                topics += "or "+allFiles[i].getName();
                            } else {
                                topics += allFiles[i].getName() + ", ";
                            }
                        }
                    }
                    System.out.print("What subject would you like to study for " + topics + "?\n");
                    String choice = keyboard.nextLine().toUpperCase(Locale.ROOT);
                    qfilepath = "resources/" + choice + "/questions.txt";
                    afilepath = "resources/" + choice + "/answers.txt";
                    System.out.print("What would you like to do: (a begin quiz (b add a question\n");
                    String ans = keyboard.nextLine().toLowerCase(Locale.ROOT);
                    if (ans.equals("a")) {
                        test.askQ(test.getText(qfilepath), test.getText(afilepath));
                    } else if (ans.equals("b")) {
                        addQuestion();
                    }
                    test.start(test);
                    break;
                }else{
                    System.out.println("There are currently no topics in available, please add some topics and try again");
                }
            default:
                //exiting program
                System.out.println("Program shutting down, goodbye");
                exit(0);
        }
        //recursion, restarting method so the user can keep using the program, the user can exit on the next iteration if they want
        start(test);
    }

    public static void main(String[] args){
        //entry point to program, establishing a new instance of main class to use non-static methods
        Main test = new Main();
        test.start(test);
    }
}