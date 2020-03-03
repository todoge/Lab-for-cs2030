/**
 * States refers to the current State of the customer.
 *
 * @author peh jun siang
 */
enum States {
    DONE(1), ARRIVES(2), WAITS(3), SERVED(4), LEAVES(5);
    private final int state;

    //private constructor to set value of state.
    private States(int i) {
        this.state = i;
    }

    /**
     * getValue returns the integer associated with each State.
     * A smaller integer takes precedence over a larger integer
     * if 2 events occur simultaneously
     *
     * @param void No params.
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
                return "done";
            case 2:
                return "arrives";
            case 3:
                return "waits";
            case 4:
                return "served";
            case 5:
                return "leaves";
            default:
                return "";
        }
    }

}
