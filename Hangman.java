import java.util.Scanner;
import java.util.Arrays;
import java.util.Random;

public class Hangman {


    //generic
    //output
    public static void print(String toPrint)
    {
        System.out.println(toPrint);
        return;
    }

    //clear display
    public static void clear()
    {
        System.out.flush();
    }

    //input character
    public static Character inputCharacter()
    {
        Scanner scanner = new Scanner(System.in);
        String temp="!!";
        while(temp.equals("!!"))
        {temp=validateCharInput(scanner.nextLine());}
        return (temp.toUpperCase()).charAt(0);
    }

    public static String validateCharInput(String inputToValidate)
    {
        if (inputToValidate.length()>1 || inputToValidate.length()<=0)
        {print("Invalid input"); return "!!";}
        else{return inputToValidate;}   
    }

    public static int pickRandomInt(int limit)
    {
        Random random= new Random();
        return random.nextInt(limit);
    }

    public static String replaceCharacterInString(String s, Character c, int index)
    {
        return (s.substring(0,index)+ c + s.substring(index+1));
    }

    public static int[] getPositonsOfCharacter(Character toFind, String secretWord)
    {
        int[] correctIndexes= new int[secretWord.length()];
        int lastIndex=0;
        for (int i=0; i< secretWord.length(); i++)
        {
            if (toFind==secretWord.charAt(i)){correctIndexes[lastIndex]= i+1; lastIndex++;}
        }
        return correctIndexes;
    }

    public static boolean isCharacterInString(Character toFind, String toSearch)
    {
        for (int i=0; i< toSearch.length(); i++)
        {if (toSearch.charAt(i)==toFind) {return true;}}

        return false;
    }

    public static String buildTextToDisplay(int characterCount)
    {
        String textToReturn="";
        for (int i=0; i< characterCount;i++){textToReturn+=" _";}
        return textToReturn;
    }

    public static void playGame(String[] graphics, String secretWord)
    {
        final int hangmanStateLimit=7;
        int hangmanState=0, correctLetters=0;
        String lettersGot="";

        String textToDisplay=buildTextToDisplay(secretWord.length());

        while (hangmanState<hangmanStateLimit)
        {
            clear();
            print(graphics[hangmanState]);
            print(textToDisplay);

            if(correctLetters==secretWord.length())
                {
                    print("CONGRATULATIONS");
                    print("GAME WON");
                    return;
                }
            Character playerInput=inputCharacter();
            //if (!isCharacterInString(playerInput, lettersGot) && isCharacterInString(playerInput, secretWord) )
            if (!isCharacterInString(playerInput, lettersGot) )
            {
                lettersGot+=playerInput;
                int[] positions=getPositonsOfCharacter(playerInput, secretWord);
                int counter=0;
                while(positions[counter]!=0)
                {
                    correctLetters++;
                    textToDisplay= replaceCharacterInString(textToDisplay, playerInput, (positions[counter]-1)*2 + 1);
                    counter++;
                }
                
            }
            else
            {
                hangmanState++;
            }
        }
        print(secretWord);
        print("GAME OVER");
        return;
    }
    
    public static void main(String[] a)
    {
        String[] availableWords= new String[]{"apple", "banana", "computer", "zebra", "mountain", "river", "dog", "cat", "ocean", "sun", 
        "pencil", "notebook", "sky", "car", "train", "house", "garden", "mouse", "keyboard", "table", 
        "phone", "chair", "bottle", "clock", "window", "cloud", "rain", "book", "tree", "light", 
        "music", "guitar", "camera", "flower", "shirt", "cup", "plate", "pizza", "coffee", "movie", 
        "beach", "forest", "bridge", "plane", "ball", "kite", "hat", "moon", "star", "mountain"};
        final String[] graphics=new String[]{"|-----\n|\n|\n|\n|", "|-----\n|    O\n|\n|\n|","|-----\n|    O\n|    |\n|\n|","|-----\n|    O\n|   -|\n|\n|", "|-----\n|    O\n|   -|-\n|\n|","|-----\n|    O\n|   -|-\n|   |\n|","|-----\n|    O\n|   -|-\n|   | |\n|"};
        availableWords=mergeSortString(availableWords);
        playGame(graphics, availableWords[pickRandomInt(availableWords.length)].toUpperCase());
        return;
    }

    public static String[] mergeSortString(String[] arrayToSort)
    {
        int temp= (arrayToSort.length/2);
        String[] leftArr= new String[temp];
        String[] rightArr= new String [arrayToSort.length-temp];

        leftArr=Arrays.copyOfRange(arrayToSort, 0, temp);
        rightArr=Arrays.copyOfRange(arrayToSort, temp, arrayToSort.length);

        if (leftArr.length>1){leftArr=mergeSortString(leftArr);}
        if (rightArr.length>1){rightArr=mergeSortString(rightArr);}

        return mergeString(leftArr, rightArr);
    }

    public static String[] mergeString(String[] left, String[] right)
    {
        String newArr[]= new String[left.length+right.length];
        int countL=0, countR=0,lastIndex=0;
        while(countL<left.length && countR <right.length)
        {
            if(left[countL].compareTo(right[countR])>=0)
            {
                newArr[lastIndex]=right[countR];
                countR++;
            }
            else
            {
                newArr[lastIndex]=left[countL];
                countL++;
            }
            lastIndex++;
        }

        while(countL<left.length){newArr[lastIndex]=left[countL]; countL++; lastIndex++;}
        while(countR<right.length){newArr[lastIndex]=right[countR];countR++; lastIndex++;}
        return newArr;
    }
}



