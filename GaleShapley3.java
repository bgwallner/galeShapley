/**
 *    Java Program to Implement Gale Shapley Algorithm 
 **/

import java.util.*;

/** Class GaleShapley **/
public class GaleShapley3
{
    private int N;
    private String[][] menPref;
    private String[][] womenPref;
    private String[] women;
    private String[] womenPartner;
    
    /* Datatype for man's name and  */
    /* current proposal index.      */
    private class manClass
    {
        public String name;
        public int prefIndex;   
    }
    
    private Stack<String> menFree;
    private manClass[] men;
 
    /** Constructor **/
    public GaleShapley3(String[] m, String[] w, String[][] mp, String[][] wp)
    {
        N = mp.length;
        women = w;
        menPref = mp;
        womenPref = wp;
        womenPartner = new String[N];
        
        menFree = new Stack<String>();
        men = new manClass[N];
        
        /* Add all free men to stack */
        for(int i=0; i < N; i++)
        {
            menFree.push(m[i]);
            men[i] = new manClass();
            men[i].name = m[i];
            men[i].prefIndex = 0;
        }
        
        calcMatches();
    }
    /** function to calculate all matches **/
    private void calcMatches()
    { 
        /* Loop until stack empty - O(N) */
        while (!menFree.isEmpty())
        {
            /* Pop an available man - O(1) */
            String man = menFree.pop();
            
            /* Find index of man - O(N) */
            int indexMan = menIndexOf(man);
            
            /* Now find prefIndex */
            int prefIndex = men[indexMan].prefIndex;
                        
            /* Find index of woman from her name in menPref - O(N)*/
            int indexWoman = womenIndexOf(menPref[indexMan][prefIndex]);
            if (womenPartner[indexWoman] == null)
            {
                System.out.println(man +" proposing to "+ women[indexWoman]);
                System.out.println(man +" is now partner of "+ women[indexWoman]);
                womenPartner[indexWoman] = man;
            }
            else
            {
                System.out.println(man +" proposing to "+ women[indexWoman]);
                String currentPartner = womenPartner[indexWoman];
                System.out.println(currentPartner +" is current partner of "+ women[indexWoman]);
                /* Find if new partner more attractive - O(N)*/
                if (!morePreference(currentPartner, men[indexMan].name, indexWoman))
                {
                    System.out.println(currentPartner +" is still partner of "+ women[indexWoman]);
                    menFree.push(man);
                }
                else
                {
                    System.out.println(man +" is now partner of "+ women[indexWoman]);
                    womenPartner[indexWoman] = man;
                    /* Set current partner as free - O(1)*/
                    menFree.push(currentPartner);
                }
            }
            /* Increase prefIndex after having proposed */
            men[indexMan].prefIndex++;
            System.out.println(" ");
        }
        
        printCouples();
    }
    /** function to check if women prefers new partner over old assigned partner **/
    private boolean morePreference(String curPartner, String newPartner, int index)
    {
        for (int i = 0; i < N; i++)
        {
            if (womenPref[index][i].equals(newPartner))
                return true;
            if (womenPref[index][i].equals(curPartner))
                return false;
        }
        return false;
    }
    /** get men index **/
    private int menIndexOf(String str)
    {
        for (int i = 0; i < N; i++)
            if (men[i].name.equals(str))
                return i;
        return -1;
    }
    /** get women index **/
    private int womenIndexOf(String str)
    {
        for (int i = 0; i < N; i++)
            if (women[i].equals(str))
                return i;
        return -1;
    }
    /** print couples **/
    public void printCouples()
    {
        System.out.println("Couples are : ");
        for (int i = 0; i < N; i++)
        {
            System.out.println(womenPartner[i] +" "+ women[i]);
        }
    }
    /** main function **/
    public static void main(String[] args) 
    {
        System.out.println("Gale Shapley Marriage Algorithm\n");
        /** list of men **/
        String[] m = {"Tim", "Sebastian", "EliasB", "Leo", "Emil"};
        /** list of women **/
        String[] w = {"Mika", "Ebba", "Wilma", "Amelia", "Molly"};
 
        /** men preference **/
        String[][] mp = {{"Mika", "Ebba", "Amelia", "Molly", "Wilma"}, 
                         {"Ebba", "Amelia", "Mika", "Molly", "Wilma"}, 
                         {"Wilma", "Ebba", "Amelia", "Mika", "Molly"}, 
                         {"Ebba", "Mika", "Amelia", "Molly", "Wilma"},
                         {"Wilma", "Ebba", "Molly", "Amelia", "Mika"}};
        /** women preference **/                      
        String[][] wp = {{"Tim", "EliasB", "Leo", "Sebastian", "Emil"}, 
                         {"EliasB", "Sebastian", "Tim", "Leo", "Emil"}, 
                         {"Emil", "EliasB", "Tim", "Leo", "Sebastian"},
                         {"Tim", "Emil", "Sebastian", "EliasB", "Leo"}, 
                         {"Leo", "Sebastian", "Emil", "EliasB", "Tim"}};
 
        GaleShapley3 gs = new GaleShapley3(m, w, mp, wp);
    }
}