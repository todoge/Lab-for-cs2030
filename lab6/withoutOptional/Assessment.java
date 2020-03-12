class Assessment implements Keyable<String>{
    private final String name;
    private final String grade;

    Assessment(String name, String grade){
        this.name = name;
        this.grade = grade;
    }

    //getter for grade
    public String getGrade(){
        return this.grade;
    }

    @Override 
    public String getKey(){
        return name;
    }

    @Override
    public String toString(){
        return "{" + name + ": " + grade + "}";
    }
}
