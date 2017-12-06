/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpheuristique;

import java.util.Vector;

/**
 *
 * @author Zied
 */
public class Solution {
    private int n;// Le nombre des objets
    private int c;//La capacité du sac
    private int objval;//La valeur de la fonction objctif (profit total des objets selectionnes)
    private int volact;//Le volume total des objets selectionnés
    private Vector elem;//Un vecteur précisant les objets sélectionnés (0 ou 1)
    private int nbelem;
    
    public Solution()
    {
        elem= new Vector();
    }
    public Solution(int nbr,int capacite, int profit,int vol,int nbre,Vector elements)
    {
        this.c=capacite;
        this.n=nbr;
        this.nbelem=nbre;
        this.objval=profit;
        this.volact=vol;
        this.elem=elements;
    }
    public Solution(Solution s)
    {
        this.c=s.getc();
        this.n=s.getn();
        this.nbelem=s.getnbelem();
        this.objval=s.getcout();
        this.volact=s.getvola();
        this.elem=new Vector(s.getelements());
    }
    public int getc()
    {
        return this.c ;
    }
    public int getn()
    {
        return this.n ;
    }
    public int getnbelem()
    {
        return this.nbelem ;
    }
    public int getcout()
    {
        return this.objval ;
    }
    public int getvola()
    {
        return this.volact ;
    }
    public Vector getelements()
    {
        return this.elem ;
    }
    public void setc(int c)
    {
        this.c=c;
    }
    public void setn(int n)
    {
        this.n=n;
    }
    public void setnbrelem(int nbrelem)
    {
        this.nbelem=nbrelem;
    }
    public void setcout(int profit)
    {
        this.objval=profit;
    }
    public void setvolact(int vol)
    {
        this.volact=vol;
    }
    public void setelem(Vector elements)
    {
        this.elem=new Vector(elements);
        //this.elem=elements;
    }
    public void setelement(int pos,int a)
    {
        this.elem.setElementAt(a,pos);
    }
    
    public Solution locale(Solution s, Probleme p)
    {
        Solution tmp=new Solution();
        tmp.setc(s.getc());
        tmp.setn(s.n);
        tmp.setelem(s.getelements());
        tmp.setvolact(s.getvola());
        tmp.setcout(s.getcout());
        tmp.setnbrelem(s.getnbelem());
        int newvol=s.getvola();
        int newp=s.getcout();
        boolean ameliore=true;
        while (ameliore)
        {
            ameliore = false ;
            for(int i=0;i<tmp.getn();i++)
            {
                Vector v = new Vector(tmp.getelements());
                //v = tmp.getelements();
                if((int)tmp.getelements().elementAt(i)==1)
                {
                    //System.out.println("enlevement bit "+i);
                    v.setElementAt(0,i);
                    //for(int k=0;k<v.size();k++)System.out.print(v.elementAt(k));
                    newvol=newvol-(int)p.getv().elementAt(i);
                    newp=newp-(int)p.getp().elementAt(i);
                    for(int j=0;j<v.size();j++)
                    {
                        if((j!=i)&&((int)v.elementAt(j)==0))
                        {
                            if((newvol+(int)p.getv().elementAt(j))<=s.getc())
                            {
                                v.setElementAt(1,j);
                                newvol=newvol+(int)p.getv().elementAt(j);
                                newp=newp+(int)p.getp().elementAt(j);
                                //System.out.println("augmenter par obj "+j+"   vol et cout:"+newvol+"   "+newp);
                            }
                        }
                    }
                    if(tmp.getcout()<newp)
                    {
                        ameliore = true;
                        tmp.setcout(newp);
                        tmp.setvolact(newvol);
                        tmp.setelem(v);
                        int nb=0;
                        for(int k=0;k<tmp.getelements().size();k++)
                        {
                            if((int)tmp.getelements().elementAt(k)==1)
                                nb++;
                        }
                        tmp.setnbrelem(nb);
                        //System.out.println("marche "+tmp.getvola()+"     "+tmp.getcout());
                    }
                }
            }
        }
        return tmp;
    }
    
public Solution tabou(Solution s,Probleme p)
{
    Solution ms=new Solution(); 
    ms.setc(s.getc());
    ms.setn(s.getn());
    ms.setcout(s.getcout());
    ms.setelem(s.getelements());
    ms.setvolact(s.getvola());
    ms.setnbrelem(s.getnbelem());
    Solution s1=new Solution();
    s1.setc(s.getc());
    s1.setn(s.getn());
    s1.setcout(s.getcout());
    s1.setelem(s.getelements());
    s1.setvolact(s.getvola());
    s1.setnbrelem(s.getnbelem());
    //for (int i=0;i<s1.getelements().size();i++) System.out.print(s1.getelements().elementAt(i)+"    ");
    Vector tabou = new Vector();
    Vector iter = new Vector();
    boolean ameliore = true ;
    while(ameliore)
    { 
        ameliore = false ;
        int i=0;
        while(i<s1.getelements().size())
        {
            Vector c = new Vector(s1.getelements());
            //for (int k=0;k<c.size();k++) System.out.print(s1.getelements().elementAt(k)+"    ");
            if((int)s1.getelements().elementAt(i)==0)
            {
                i++;
            }
            else
            {
                c.setElementAt(0,i);
                //System.out.println(" c fait normalement"+c.elementAt(i));
                s1.setcout(s1.getcout()-(int)p.getp().elementAt(i));
                s1.setvolact(s1.getvola()-(int)p.getv().elementAt(i));
                Vector voisin= new Vector();
                for(int j=0;j<c.size();j++)
                {
                    if((i!=j)&&((int)c.elementAt(j)==0)&&((s1.getvola()+(int)p.getv().elementAt(j))<=s1.getc()))
                    {
                        Solution v = new Solution();
                        v.setelem(c);
                        v.setelement(j,1);
                        //System.out.println("voisin"+j);
                        //for (int k=0;k<v.getelements().size();k++) System.out.print(v.getelements().elementAt(k));
                        //System.out.println();
                        v.setcout(s1.getcout()+(int)p.getp().elementAt(j));
                        v.setvolact(s1.getvola()+(int)p.getv().elementAt(j));
                        v.setc(s1.getc());
                        v.setn(s1.getn());
                        v.setnbrelem(s1.getnbelem());
                        voisin.add(v);
                    }
                }
                tabou.add(s1);
                iter.add(7);
                s1=meilleurvoisin(voisin,tabou,iter);
                //System.out.println("meilleir voisin retournéééééé:");
                //for (int k=0;k<s1.getelements().size();k++) System.out.print(s1.getelements().elementAt(k));
                if(s1.getcout()>ms.getcout())
                {
                        //System.out.println("amélioree truuuuuue:");
                        ameliore=true;
                        ms.setcout(s1.getcout());
                        ms.setelem(s1.getelements());
                        ms.setvolact(s1.getvola());
                }
                i++;
            }
            
        }
    }
    return ms;    
}
public Solution meilleurvoisin(Vector voisin,Vector tabou,Vector iter)
{
    Solution s=new Solution();
    if(voisin.isEmpty())
    {
        //System.out.println("voisin vide");
        s.setcout(0);
        s.setvolact(0);
        return s ;
    }
    else
    {
        //System.out.println("voisin nonnnnn vide");
    for(int i=0;i<voisin.size();i++)
    {
        for(int j=0;j<tabou.size();j++)
        {
            Solution s1=(Solution)voisin.elementAt(i);
            Solution s2=(Solution)tabou.elementAt(j);
            if(s1.getelements().equals(s2.getelements()))
            {
                //System.out.println("voisin tabou recontré");
                voisin.remove(i);
            }
        }
    }
    s=(Solution)voisin.elementAt(0);
    for(int i=1;i<voisin.size();i++)
    {
        Solution s1=new Solution((Solution)voisin.elementAt(i));
        if(s.getcout()<s1.getcout())
        {
            s.setcout(s1.getcout());
            s.setelem(s1.getelements());
            s.setvolact(s1.getvola());
        }
    }
    //System.out.println("meilleir voisin:");
    //for (int k=0;k<s.getelements().size();k++) System.out.print(s.getelements().elementAt(k));
    for(int i=0;i<iter.size();i++)
    {
        iter.setElementAt((int)iter.elementAt(i)-1, i);
        if((int)iter.elementAt(i)==0)
        {
            voisin.remove(i);
            iter.remove(i);
        }
    }
    }
    return s;
}
    
}
