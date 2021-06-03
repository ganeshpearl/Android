package com.timemoneywaste.flames;

//import android.content.Context;
//import android.widget.Toast;

public class flames_java
{
    public static String main_program(String first_name,String second_name)
    {

        String x="Contact Muthu-dummy initilazation";
        char[] Flames = process_flames(first_name,second_name);

        System.out.println("Your relationship will be like below");

        if(Flames[0]=='F')
        {
            x= "Friend";

        }else  if(Flames[0]=='L')
        {
            x= "Love";
        }else  if(Flames[0]=='A')
        {
            x= "Affection";
        }else  if(Flames[0]=='M')
        {
            x= "Marriage";
        }else  if(Flames[0]=='E')
        {
            x= "Enemy";
        }else  if(Flames[0]=='S')
        {
            x= "Sister";
        }else  if(Flames[0]=='W') // W mean Weird relationship when both name are same
        {
            x= "Kalla Kadhal";
        }
        return x;
    }

    private static char[] process_flames(String firstname,String secondname)
    {

        char[] Flames_answer = new char[]{ 'F','L','A','M','E','S' };

        char[] fName_char = firstname.toCharArray();
        char[] sName_char = secondname.toCharArray();

        int cutoff_loop1=fName_char.length;
        int cutoff_loop2=sName_char.length;


        for(int i=0;i<cutoff_loop1;i++)
        {
            for(int j=0;j<cutoff_loop2;j++)
            {
//                Avoiding case sensitive problem Character.toLowerCase(fName_char[i])

                if  (Character.toLowerCase(fName_char[i]) == Character.toLowerCase(sName_char[j]))
//                if (fName_char[i]==sName_char[j])
                {
                    fName_char=remove_element(fName_char,i);
                    sName_char=remove_element(sName_char,j);
                    i--;
                    j--;
                    cutoff_loop1--;
                    cutoff_loop2--;
                    break;
                }

            }
        }
        int name_count=cutoff_loop1 + cutoff_loop2;
        char[] w = new char[]{ 'W' };

        if (name_count==0)
        {
            return w;  // this w should be char array , because this function returning character array
        }

        int remaining_value=0;
        int Adikra_value=0,flame_count=6;

        for(int z=0;z<flame_count-1;z++)
        {
            if(name_count<remaining_value)
            {
                remaining_value=remaining_value % name_count;
            }

            if (name_count>remaining_value)
            {
                name_count=Math.abs(name_count-remaining_value);
            }


            if (flame_count>name_count)
            {
                Adikra_value=name_count;
                remaining_value=flame_count-Adikra_value;
            }
            else if(flame_count==name_count)
            {
                Adikra_value=name_count;
                remaining_value=Math.abs(name_count-flame_count);
            }
            else
            {
                Adikra_value = name_count%flame_count;
                remaining_value=flame_count-Adikra_value;

                if (Adikra_value==0)
                {
                    Adikra_value = flame_count;
                    remaining_value=flame_count-Adikra_value;
                }

            }


            Flames_answer=remove_element(Flames_answer, Adikra_value-1);

            flame_count--;
            z--;
            name_count=cutoff_loop1 + cutoff_loop2;
        }


//		Flames_answer

            return Flames_answer;



    }

    //removing elemet from array
    public static char[] remove_element(char[] name,int x) {

        char[] temp=new char[name.length-1];

        for(int k=0;k<name.length-1;k++)
        {

            if(k!=x)
            {
                temp[k]=name[k];
            }
            else
            {
                temp[k]=name[k+1];
                x++;
            }


        }

        return temp;

    }


}


