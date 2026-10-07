package mx.unison.menu;

public class Food {
    private String name;
    private double price;
    private String description;
    private int calories;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    @Override
    public String toString() {
        return String.format("%-25.25s, $%4.2f (%d calorias) \n\t%-80.80s",name, price, calories, description);
    }
}