import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    public static void saveData(String fileName, List<? extends CSVConvertible> dataList){
        try (FileWriter fw = new FileWriter(fileName);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            for (var item : dataList ){
                out.println(item.toCSV());
            }
            System.out.println("data successfully saved to "+fileName);
        } catch (IOException e) {
            System.out.println("error saving data"+e.getMessage());
        }
    }

    public static List<String> loadData(String fileName){
        List<String> dataList = new ArrayList<>();
        try (FileReader fr = new FileReader(fileName);
        BufferedReader br = new BufferedReader(fr)) {
            String line;
            while((line = br.readLine()) != null){
                if (!line.trim().isEmpty()){
                    dataList.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("No data file found for "+ fileName+" Error found: "+e.getMessage());
        }
        return dataList;
    }
}
