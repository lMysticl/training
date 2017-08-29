package collections.TheStack;

import java.util.Arrays;
// Arrays, linked lists, trees, etc. are best for
// data that represents real objects.

// Stacks & Queues are instead used to complete a
// task and are soon after discarded.

// Stacks & Queues
// 1. Allow only a single item to be added or removed at a time
// 2. Stacks allow access to the last item inserted (LIFO)
// 3. Queues allow access to the first item inserted (FIFO)
/**
 * @author Pavel Putrenkov
 */
public class TheStack {
    private static final String MULTIPLE_VALUES = "12 11 13 15";
    private String[] stackArray;
    private int stackSize;
    private int topOfStack = -1;

    private TheStack(int size) {
        stackSize =size;
        stackArray = new String [size];
        Arrays.fill(stackArray,"-1");
    }

    private void push(String input){
        if (topOfStack+1<stackSize) {
        topOfStack++;
        stackArray[topOfStack]=input;
        }else {
            System.out.println("Sorry But the Stack is Full");
        }
        displayTheStack();
        System.out.println("Push " +input +" Was Added to");
        }


     private void pop(){
        if(topOfStack>=0){
            displayTheStack();
            System.out.println("POP "+stackArray[topOfStack]+" Was Removed From the Stack\n");
            stackArray[topOfStack]="-1";
            topOfStack--;
        }else {
            displayTheStack();
            System.out.println("Sorry but the Stack is Empty");
        }
     }

     private void peek(){
         System.out.println("PEEK " + stackArray[topOfStack] +" Is at the Top of the Stack\n");
     }

     private void pushMany(String multipleValues){
         String[] tempString = multipleValues.split(" ");
         for (String aTempString : tempString) {
             push(aTempString);
         }
     }
        private void popAll(){
         for (int i = topOfStack;i>=0;i--){
            pop();
         }
        }

    public void popDisplayAll(){

        StringBuilder theReverse = new StringBuilder();

        for(int i = topOfStack; i >= 0; i--){

            theReverse.append(stackArray[i]);

        }

        System.out.println("The Reverse: " + theReverse);

        popAll();

    }




    private void displayTheStack(){

        for(int n = 0; n < 61; n++)System.out.print("-");

        System.out.println();

        for(int n = 0; n < stackSize; n++){

            System.out.format("| %2s "+ " ", n);

        }

        System.out.println("|");

        for(int n = 0; n < 61; n++)System.out.print("-");

        System.out.println();

        for(int n = 0; n < stackSize; n++){



            if(stackArray[n].equals("-1")) System.out.print("|     ");

            else System.out.print(String.format("| %2s "+ " ", stackArray[n]));

        }

        System.out.println("|");

        for(int n = 0; n < 61; n++)System.out.print("-");

        System.out.println();

    }


    public static void main(String[] args) {
        TheStack theStack = new TheStack(10) ;
        theStack.push("10");
        theStack.push("15");
        theStack.peek();
        theStack.pop();
        theStack.pushMany(MULTIPLE_VALUES);

        theStack.displayTheStack();

        theStack.popAll();
        theStack.displayTheStack();

    }
}
