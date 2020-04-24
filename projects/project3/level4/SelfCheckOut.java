package cs2030.simulator;
import java.util.ArrayList;
import java.util.PriorityQueue;

class SelfCheckOut extends Server {

    SelfCheckOut(int id, int maxQueueLen) {
        super(id,maxQueueLen);
    }
    /**
     * check if server is a self check out station
     *
     * @return boolean
     * @author peh jun siang
     */
    // returns true if server is back
    public boolean isSelfCheckOut() {
        return true;
    }
}