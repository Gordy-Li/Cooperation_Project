import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SavaTotxt {
    File ExerciseFile = null;
    File AnswerFile = null;
    String filename = "";
    BufferedWriter ExerciseOut = null;
    BufferedWriter AnswerOut = null;

    public SavaTotxt() {
        this.setFilename();
        if (this.CreateFile()) {
            this.setOutBufferedWriter();
            //System.out.println("创建文件Exercise_" + filename + ".txt成功！");
            //System.out.println("创建文件Answer_" + filename + ".txt成功！");
        } else
            System.out.println("创建文件失败！");
    }

    public void setOutBufferedWriter() {
        try {
            this.ExerciseOut = new BufferedWriter(new FileWriter(ExerciseFile));
            this.AnswerOut=new BufferedWriter(new FileWriter(AnswerFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setFilename() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        filename = sdf.format(date);
    }

    public boolean CreateFile() {
        String relativelyPath=System.getProperty("user.dir");
        //System.out.println(relativelyPath);
        ExerciseFile = new File("Exercise_" + filename + ".txt"); // 相对路径，如果没有则要建立一个新的output.txt文件
        AnswerFile = new File("Answer_" + filename + ".txt");
        if (ExerciseFile.exists()) {
            ExerciseFile.delete();
        }
        if (AnswerFile.exists()) {
            AnswerFile.delete();
        }
        try {
            ExerciseFile.createNewFile();
            AnswerFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean WriteToFile(String content, int flag) {
        /* 写入Txt文件 */
        try {
            switch (flag) {
                case 0:
                    ExerciseOut.write(content); // \r\n即为换行
                    ExerciseOut.write("\r\n");
                    ExerciseOut.flush();
                    return true;
                case 1:
                    AnswerOut.write(content);
                    AnswerOut.write("\r\n");
                    AnswerOut.flush();
                    return true;
            }
            // 把缓存区内容压入文件
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean CloseOutBufferedWriter() {
        try {
            ExerciseOut.close(); // 最后记得关闭文件
            AnswerOut.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
