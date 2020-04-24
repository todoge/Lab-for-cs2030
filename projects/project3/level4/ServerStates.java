package cs2030.simulator;
/**
 * States refers to the States of the Server.
 *
 * @author peh jun siang
 */
public enum ServerStates {
    SERVER_REST(1), SERVER_BACK(2);
    private final int state;

    //private constructor to set value of state.
    private ServerStates(int i) {
        this.state = i;
    }

    /**
     * getValue returns the integer associated with each State.
     * A smaller integer takes precedence over a larger integer
     * if 2 events occur simultaneously
     *
     * @return Integer
     * @author peh jun siang
     */
    public Integer getValue() {
        return this.state;
    }

    /**
     * Overrides toString method to the stipulated format.
     *
     * @return String
     * @author peh jun siang
     * @see #toString()
     */
    // override toString method
    @Override
    public String toString() {
        switch (state) {
            case 1:
                return "server resting";
            case 2:
                return "server back";
            default:
                return "";
        }
    }

}
