package ltd.icecold.encrypt;

/**
 * @author ice-cold
 */
public class Message {
    private String enData;
    private String enDesPassword;

    @Override
    public String toString() {
        return "Message{" +
                "enData='" + enData + '\'' +
                ", enDesPassword='" + enDesPassword + '\'' +
                '}';
    }

    public String getEnData() {
        return enData;
    }

    public void setEnData(String enData) {
        this.enData = enData;
    }

    public String getEnDesPassword() {
        return enDesPassword;
    }

    public void setEnDesPassword(String enDesPassword) {
        this.enDesPassword = enDesPassword;
    }
}
