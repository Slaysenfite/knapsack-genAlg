package model;

public class Item {
	private int weight, value;
	
	public Item(int weight, int value) {
		this.weight = weight;
		this.value = value;
	}
	
	public Item() {
		this.weight = 0;
		this.value = 0;
	}

	public int getWeight() {
		return weight;
	}

	public int getValue() {
		return value;
	}
	
	public double valueVersusWeightRatio() {
		return (value*1.00)/weight;
	}

}
