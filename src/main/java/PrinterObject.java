import java.util.ArrayList;
import java.util.List;

public class PrinterObject {
    public List<String> jobs;
    public PrinterObject(){
        ResetJobs();
    }
    public String PrintJobs(){
        String output = "";
        output += "Printing jobs in form <job position> <filename> \n";
        for(int i = 0; i < jobs.size(); i++){
            output += i + " " + jobs.get(i) + "\n";
        }
        if (jobs.size() == 0){
            output += "no jobs on this printer was found \n";
        }
        return output;
    }
    public void ResetJobs(){
        jobs = new ArrayList<>();
    }
}