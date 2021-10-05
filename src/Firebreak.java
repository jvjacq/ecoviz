import java.util.ArrayList;

public class Firebreak {

  private static ArrayList<Firebreak> breaklist;
  private ArrayList<int[]> region;
  public Firebreak(){
    region = new ArrayList<int[]>();
  }

  public void addToRegion(int x, int y, int radius){
    int[] circle  = {x,y,radius};
    region.add(circle);
  }

  public static void addCompleted(Firebreak fb){
    breaklist.add(fb);
  }

  public boolean inFirebreak(Plant p){
    for(int[] i: region){
      if(Math.pow((p.getX() - i[0]),2) + Math.pow((p.getY() - i[1]),2) <= Math.pow(i[2],2)) return true;
    }
    return false;
  }

}
