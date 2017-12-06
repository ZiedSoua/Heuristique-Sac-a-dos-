/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpheuristique;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Zied
 */
public class Probleme {
    private int c;
    private int n ;
    private Vector v ;
    private Vector p ;
    
    public Probleme()
    {
        v=new Vector();
        p=new Vector();
    }
    public Probleme(int capacite, int nbr, Vector vol, Vector poid )
    {
        this.c=capacite;
        this.n=nbr;
        this.p=poid;
        this.v=vol ;
    }
    public int getc()
    {
        return this.c ;
    }
    public int getn()
    {
        return this.n ;
    }
    public Vector getv()
    {
        return this.v ;
    }
    public Vector getp()
    {
        return this.p ;
    }
    public void setc(int capacite)
    {
        this.c=capacite;
    }
    public void setn(int nbr)
    {
        this.n=nbr;
    }
    public void setv(Vector v)
    {
        this.v=v;
    }
    public void setp(Vector p)
    {
        this.p=p;
    }
    public void addelemp(int a)
    {
        this.p.addElement(a);
    }
    public void addelemv(int b)
    {
        this.v.addElement(b);
    }
    
    public void charg( String filename)
    {
        try {
            FileReader f= new FileReader(new File(filename));
            BufferedReader br = new BufferedReader(f);
            this.setc(Integer.parseInt(br.readLine()));
            this.setn(Integer.parseInt(br.readLine()));
            //System.out.println(p.getc()+"  "+p.getn());
            String line ;
            while ((line=br.readLine())!= null)
            {
                String[] ch = line.split("	");
                int a = Integer.parseInt(ch[0]);
                int b = Integer.parseInt(ch[1]);
                this.addelemp(b);
                this.addelemv(a);
                //System.out.println(a+"////"+b);
            }
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
            System.out.println("fichier introuvable !!!");
        } catch (IOException ex) {
            Logger.getLogger(Probleme.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Solution constructive()
    {
        Solution s= new Solution();
        s.setc(this.getc());
        s.setn(this.getn());
        s.setcout(0);
        s.setnbrelem(0);
        s.setvolact(0);
        Vector rap = new Vector();
        int indices[]= new int[s.getn()] ;
        //calcul rapport p/v, initialisation d'indice et elements  
        for(int i=0;i<s.getn();i++)
        {
            float a= (int )this.getp().elementAt(i);
            float b =(int ) this.getv().elementAt(i);
            float r = a/b;
            rap.addElement(r);
            indices[i]=i;
            s.getelements().add(0);
        }
        float tmp ;
        int tmpi;
        //trie décroissant et conservation d'indice
        for (int i = 0; i<s.getn(); i++)
        {
            for (int j= i + 1; j<s.getn(); j++) 
            {
                if ((float)rap.elementAt(i)<(float)rap.elementAt(j)) 
                {
                    tmp = (float)rap.elementAt(i);
                    rap.setElementAt(rap.elementAt(j),i);
                    rap.setElementAt(tmp,j);
                    tmpi = indices[i];
                    indices[i] = indices[j];
                    indices[j] = tmpi;
                }
            }
        }
        int i=0;
        while((s.getc()>s.getvola())&&(i<s.getn()))
        {
            if((s.getvola()+(int)this.getv().elementAt(indices[i]))>s.getc())
            {
                break ;//i++;
            }
            else
            {
                s.setcout(s.getcout()+(int)this.getp().elementAt(indices[i]));
                s.setvolact(s.getvola()+(int)this.getv().elementAt(indices[i]));
                s.setnbrelem(s.getnbelem()+1);
                s.setelement(indices[i],1);
                //System.out.println("ajout "+indices[i]+" cout:"+s.getcout()+" volume:"+s.getvola()+" nbelem:"+s.getnbelem()+" "+s.getelements().elementAt(indices[i]));
                i++;
            }
        }
        
        return s;
    }
    
}
