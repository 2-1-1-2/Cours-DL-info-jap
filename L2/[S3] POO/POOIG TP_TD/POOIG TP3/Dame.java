public class Dame extends Piece{
    //2.
    Dame(boolean couleur){
        super(couleur, "Dame");
    }
    
    //6.2
    boolean estValide(Deplacement d, Plateau p){
        if(super.estValide(d, p)){
            for(int i=1; i<d.dist(); i++){
                if(d.typeDeplacement()=='v'){
                    if(d.getX0()<d.getX1()){
                        if(!(p.getCase(d.getX0()+i, d.getY0()).estVide())) return false;
                    }
                    else{
                        if(!(p.getCase(d.getX0()-i, d.getY0()).estVide())) return false;
                    }
                }
                if(d.typeDeplacement()=='h'){
                    if(d.getY0()<d.getY1()){
                        if(!(p.getCase(d.getX0(),d.getY0()+i).estVide())) return false;
                    }
                    else{
                        if(!(p.getCase(d.getX0(),d.getY0()-i).estVide())) return false;
                    }
                }
                if(d.typeDeplacement()=='d'){
                    if(d.getX0()<d.getX1()){
                        if(d.getY0()<d.getY1()){
                            if(!(p.getCase(d.getX0()+i, d.getY0()+i).estVide())) return false;
                        }
                        else{
                            if(!(p.getCase(d.getX0()+i, d.getY0()-i).estVide())) return false;
                        }
                    }
                    else{
                        if(d.getY0()<d.getY1()){
                            if(!(p.getCase(d.getX0()-i, d.getY0()+i).estVide())) return false;
                        }
                        else{
                            if(!(p.getCase(d.getX0()-i, d.getY0()-i).estVide())) return false;
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}