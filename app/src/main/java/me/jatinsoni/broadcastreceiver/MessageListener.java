package me.jatinsoni.broadcastreceiver;

public interface MessageListener {

    void messageReceived(String address, String body);

}
