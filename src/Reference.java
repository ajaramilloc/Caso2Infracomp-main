public class Reference {
    
    private int page;
    private String bit;
    private int category;

    public Reference(int page, String bit, int category) {
        this.page = page;
        this.bit = bit;
        this.category = category;
    }

    public int getPage() {
        return page;
    }

    public String getBit() {
        return bit;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }
}
