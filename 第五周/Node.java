package mywork;

public class Node<T> {
    private  String ip;
    private String name;
    public Node(String ip, String name) {
        this.ip = ip;
        this.name = name;
    }

    @Override
    public String toString() {
        return ip;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
