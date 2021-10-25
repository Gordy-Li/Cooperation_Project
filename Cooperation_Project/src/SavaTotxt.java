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
            //System.out.println("�����ļ�Exercise_" + filename + ".txt�ɹ���");
            //System.out.println("�����ļ�Answer_" + filename + ".txt�ɹ���");
        } else
            System.out.println("�����ļ�ʧ�ܣ�");
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
        ExerciseFile = new File("Exercise_" + filename + ".txt"); // ���·�������û����Ҫ����һ���µ�output.txt�ļ�
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
        /* д��Txt�ļ� */
        try {
            switch (flag) {
                case 0:
                    ExerciseOut.write(content); // \r\n��Ϊ����
                    ExerciseOut.write("\r\n");
                    ExerciseOut.flush();
                    return true;
                case 1:
                    AnswerOut.write(content);
                    AnswerOut.write("\r\n");
                    AnswerOut.flush();
                    return true;
            }
            // �ѻ���������ѹ���ļ�
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean CloseOutBufferedWriter() {
        try {
            ExerciseOut.close(); // ���ǵùر��ļ�
            AnswerOut.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
