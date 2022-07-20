package hackrank;

class FoodFactory  extends Solution
{
    public Food getFood(String string)
    {
        return new Food(string);
    }
}

class Food extends Solution
{
    String name;
    public Food(String string)
    {
        this.name=string;
    }
    public void servesFood()
    {
        System.out.println("I'm serving "+this.name);
    }
}

class Solution
{
    public static void main (String[] args) throws java.lang.Exception
    {
        FoodFactory myFoods = new FoodFactory();
        Food food1 = myFoods.getFood("Fastfood");
        Food food2 = myFoods.getFood("Fruits");
        System.out.println("My name is: " + food1.name);
        System.out.println("My name is: " + food2.name);
        System.out.println("Our superclass is: " + food1.getClass().getName());//modification
        food1.servesFood();
        food2.servesFood();
    }}