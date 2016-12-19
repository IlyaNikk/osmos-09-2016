package ru.mail.park.mechanics.avatar;


@SuppressWarnings("unused")
public class TimingPart {
    //All timings are in ms
    private long clientPing = 0;
    private long clientTimeshift = 0;

    public long getClientPing() {
        return clientPing;
    }

    public void setClientPing(long clientPing) {
        this.clientPing = clientPing;
    }

    public long getClientTimeshift() {
        return clientTimeshift;
    }

    public void setClientTimeshift(long clientTimeshift) {
        this.clientTimeshift = clientTimeshift;
    }
}
