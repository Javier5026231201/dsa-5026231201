package lw01.prelab;

public class ColourPrint extends PrintJob {

    public ColourPrint(String id, int pages) {
        super(id, pages);
    }

    @Override
    public int calculateCharge() {
        int pages = getPages();
        int setupCost = 2000;

        if (pages <= 10) {
            return (pages * 1500) + setupCost;
        } else {
            return (10 * 1500) + ((pages - 10) * 1000) + setupCost;
        }
    }

    @Override
    public String label() {
        return "Colour";
    }
}