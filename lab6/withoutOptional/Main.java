import java.util.Scanner;
import java.util.ArrayList;
class Main{

    private static Roster roster = new Roster("2020");

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        int numOfStudents = scanner.nextInt();
        for (int i = 0; i < numOfStudents; i++){
            String name = scanner.next();
            String mod = scanner.next();
            String assessment = scanner.next();
            String grade = scanner.next();
            
            //create a new student
            Student student = new Student(name);

            // create Module
            Module module = new Module(mod);

            // create Assessment
            Assessment exam = new Assessment(assessment,grade);

            // set up record
            if(roster.get(name) != null){
                if(roster.get(name).get(mod) != null){
                    roster.get(name).get(mod).put(exam);
                }
            } else {
                module.put(exam);
                student.put(module);
                roster.put(student);
            }
        }

        ArrayList<String[]> queries = new ArrayList<>();
        while(scanner.hasNext()){
           // break loop if int is entered
           if(scanner.hasNextInt()){break;}
           String[] query = new String[3];
           for(int i = 0; i < 3; i++){
               query[i] = scanner.next();
           }
           queries.add(query); 
        }
       
        // handle all queries
        for (String[] query : queries){
            try{
                String name = query[0];
                String mod = query[1];
                String exam = query[2];
                System.out.println(roster.getGrade(name,mod,exam));
            } catch (NoSuchRecordException e){
                System.out.println("NoSuchRecordException: " + e.getMessage());
            }
        }
    }
}


