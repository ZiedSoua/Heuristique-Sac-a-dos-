/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpheuristique;

/**
 *
 * @author Zied
 */
public class TPHeuristique {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Probleme p= new Probleme();
        p.charg("D:\\insat\\5éme rt\\RT5\\Metaheureustique de l'ing\\Heuristique\\Ls_KP\\Ls_KP\\psad005.txt");
        Solution s= new Solution();
        s=p.constructive();
        System.out.println("Algorithme Recherche glouton:");
        System.out.println("Solution S:");
        System.out.println(s.getvola()+"  "+s.getcout()+"   "+s.getnbelem());
        for(int i=0;i<s.getn();i++)
        {
            System.out.print(s.getelements().elementAt(i));
        }
        System.out.println("");
        System.out.println("Algorithme Recherche Locale:");
        Solution s1=new Solution();
        s1=s1.locale(s,p);
        System.out.println("Solution S1:");
        System.out.println(s1.getvola()+"  "+s1.getcout()+"   "+s1.getnbelem());
        for(int i=0;i<s1.getn();i++)
        {
            System.out.print(s1.getelements().elementAt(i));
        }
        
        s1=s1.tabou(s1, p);
        System.out.println("\nSolution Tabouna:");
        System.out.println(s1.getvola()+"  "+s1.getcout()+"   "+s1.getnbelem());
        for(int i=0;i<s1.getn();i++)
        {
            System.out.print(s1.getelements().elementAt(i));
        }
        
    }
    
}
