public class Page {
    
    private int number;
    private int category;

    public Page(int number, int category) {
        this.number = number;
        this.category = category;
    }

    public int getNumber() {
        return number;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }
}
