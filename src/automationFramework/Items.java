package automationFramework;

import java.util.Comparator;

public class Items {

	private int itemcount;
    private String itemname;
    private Float itemprice;
    private Float itemscore;

    public Items(int itemcount, String itemname, Float itemprice, Float itemscore) {
         this.itemcount = itemcount;
         this.itemname = itemname;
         this.itemprice = itemprice;
         this.itemscore = itemscore;
    }

    public int getItemcount() {
        return itemcount;
   }
   public void setItemcount(int itemcount) {
	this.itemcount = itemcount;
   }
    public String getItemname() {
         return itemname;
    }
    public void setItemname(String itemname) {
	this.itemname = itemname;
    }
    public Float getItemprice() {
	return itemprice;
    }
    public void setItemprice(Float itemprice) {
	this.itemprice = itemprice;
    }
    public Float getItemscore() {
	return itemscore;
    }
    public void setItemscore(Float itemscore) {
 	this.itemscore = itemscore;
    }	
    
    /*Comparator for sorting the list by Item Name*/
    public static Comparator<Items> ItemNameComparator = new Comparator<Items>() {

	public int compare(Items s1, Items s2) {
	   String ItemName1 = s1.getItemname().toUpperCase();
	   String ItemName2 = s2.getItemname().toUpperCase();

	   //ascending order
	   return ItemName1.compareTo(ItemName2);

	   //descending order
	   //return StudentName2.compareTo(StudentName1);
    }};

    /*Comparator for sorting the list by price*/
    public static Comparator<Items> ItemPriceComparator = new Comparator<Items>() {

	public int compare(Items s1, Items s2) {

		Float price1 = s1.getItemprice();
		Float price2 = s2.getItemprice();
		
	   /*For ascending order*/
		int result = Float.compare(price1, price2);
	   
	   /*For descending order*/
	   //int result = Float.compare(price2, price1);
		
		return result;
   }};
   
   /*Comparator for sorting the list by score*/
   public static Comparator<Items> ItemScoreComparator = new Comparator<Items>() {

	public int compare(Items s1, Items s2) {

		Float score1 = s1.getItemscore();
		Float score2 = s2.getItemscore();
		
	   /*For ascending order*/
		//int result = Float.compare(score1, score2);

	   /*For descending order*/
	    int result = Float.compare(score2, score1);
		
		return result;
  }};
    @Override
    public String toString() {
        return "[ item=" + itemcount + ", name=" + itemname + ", price=" + itemprice + ", score=" + itemscore +"]";
    }
}