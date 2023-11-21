public class Flashcard {
    private String front;
    private String back;

    /**
     * Constructor.
     * @param front front of flashcard
     * @param back back of flashcard
     */
    public Flashcard(String front, String back) {
        this.front = front;
        this.back = back;
    }

    /**
     * getter of front
     * @return front
     */
    public String getFront() {
        return front;
    }

    /**
     * setter of front
     * @param front front
     */
    public void setFront(String front) {
        this.front = front;
    }

    /**
     * getter of back
     * @return back
     */
    public String getBack() {
        return back;
    }

    /**
     * setter of back
     * @param back back
     */
    public void setBack(String back) {
        this.back = back;
    }
}