public class Calculatrice {

    /* Écrivez vos fonctions ici */
    public static int somme(String s){
	int sum=-1;
	String t="";
	if (s.charAt(s.length()-1)!='+' && s.charAt(0)!='+'){
	    sum=0;
	    s=s+'+';
	    for (int i=0;i<s.length();i++){
		
		if (s.charAt(i)=='+'){
		    sum=sum+Integer.parseInt(t);
		    t="";
		}
		else {
		    t=t+s.charAt(i);
		}
	    }
	}
	return(sum);
    }

    public static void main(String[] args) {

        /* Écrivez vos tests ici */
	System.out.println(somme("3+8"));
	System.out.println(somme("+3+8"));
	System.out.println(somme("3+8+"));
    }
}
