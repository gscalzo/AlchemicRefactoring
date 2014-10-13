import java.util.ArrayList;
import java.util.List;

public class GildedRose {

    public static final int ZERO_QUALITY = 0;
    public static final int CLOSE_CONCERT = 11;
    public static final int VERY_CLOSE_CONCERT = 6;
    List<Item> items = null;

    /**
     * @param args
     */
    public static void main(String[] args) {

        System.out.println("OMGHAI!");

        List<Item> items = new ArrayList<Item>();
        items.add(new Item("+5 Dexterity Vest", 10, 20));
        items.add(new Item("Aged Brie", 2, 0));
        items.add(new Item("Elixir of the Mongoose", 5, 7));
        items.add(new Item("Sulfuras, Hand of Ragnaros", 0, 80));
        items.add(new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20));
        items.add(new Item("Conjured Mana Cake", 3, 6));

        GildedRose gildedRose = new GildedRose(items);
        gildedRose.updateQuality();
    }

    public GildedRose(List<Item> items) {
        this.items = items;
    }

    public void updateQuality() {
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);

            if (isABackstagePass(item)) {
                int newQuality = increaseQuality(item);
                newQuality += deltaQualityForConcertForDays(item, CLOSE_CONCERT);
                newQuality += deltaQualityForConcertForDays(item, VERY_CLOSE_CONCERT);
                item.setQuality(newQuality);
            } else if ((isAgedBrie(item)) || isSulfuras(item)) {
                int newQuality = increaseQuality(item);
                item.setQuality(newQuality);
            } else {
                item.setQuality(decreaseQuality(item));
            }

            if (isExpired(item)) {
                if (isAgedBrie(item)) {
                    item.setQuality(increaseQuality(item));
                } else if (isABackstagePass(item)) {
                    item.setQuality(ZERO_QUALITY);
                } else if (!isSulfuras(item)) {
                    item.setQuality(decreaseQuality(item));
                }
            }
            if (!isSulfuras(item)) {
                item.setSellIn(item.getSellIn() - 1);
            }
        }
    }

    private boolean isExpired(Item item) {
        return item.getSellIn() <= 0;
    }

    static private int deltaQualityForConcertForDays(Item item, int upperBound) {
        int qual = item.getQuality();
        if (item.getSellIn() < upperBound) {
            return increaseQuality(item) - qual;
        }
        return 0;
    }

    static private boolean isABackstagePass(Item item) {
        return "Backstage passes to a TAFKAL80ETC concert".equals(item.getName());
    }

    static private boolean isAgedBrie(Item item) {
        return "Aged Brie".equals(item.getName());
    }

    static private boolean isSulfuras(Item item) {
        return "Sulfuras, Hand of Ragnaros".equals(item.getName());
    }

    static private int decreaseQuality(Item item) {
        if (item.getQuality() > 0) {
            return item.getQuality() - 1;
        } else {
            return item.getQuality();
        }
    }

    static private int increaseQuality(Item item) {
        if (canIncreaseQuality(item)) {
            return item.getQuality() + 1;
        } else {
            return item.getQuality();
        }
    }

    static private boolean canIncreaseQuality(Item item) {
        return item.getQuality() < 50;
    }

}