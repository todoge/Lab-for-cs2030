class Roster extends KeyableMap<String,String, Student>{

    Roster(String year){
        super(year);
    }

    public String getGrade(String studentId, String modCode, String assessmentName) throws NoSuchRecordException{
        try {
            return map.get(studentId).get(modCode).get(assessmentName).getGrade();
        }
        catch(NullPointerException e){
            throw new NoSuchRecordException(studentId,modCode,assessmentName);
        }
    }

    @Override
    public Roster put(Student student){
        map.put(student.getKey(),student);
        return this;
    }
}
