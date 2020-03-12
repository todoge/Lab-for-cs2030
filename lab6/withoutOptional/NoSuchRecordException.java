import java.lang.Exception;
public class NoSuchRecordException extends Exception{

    private String error;

    NoSuchRecordException(String studentId, String mod, String test){
        this.error = "No such record: " + studentId + " " + mod + " " + test;
    }

    NoSuchRecordException(){
    }

    public String getMessage(){
        return error;
    }
}
