import java.util.Optional;
class Roster extends KeyableMap<String,String, Student>{

    Roster(String year){
        super(year);
    }

    public String getGrade(String studentId, String modCode, String assessmentName) throws NoSuchRecordException{
        Optional<Student> student = Optional.ofNullable(map.get(studentId));
        Optional<Module> module = student.flatMap(x->x.get(modCode));
        Optional<Assessment> exam = module.flatMap(x->x.get(assessmentName));
        return exam.map(x->x.getGrade()).orElseThrow(()->
            new NoSuchRecordException(studentId,modCode,assessmentName)
        );
    }

    @Override
    public Roster put(Student student){
        map.put(student.getKey(),student);
        return this;
    }
}
