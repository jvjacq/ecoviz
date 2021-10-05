import java.util.ArrayList;

public class Firebreak{
    private static ArrayList<Firebreak> breaklist;
    private ArrayList<int[]> region;
    private int minX, maxX, minY, maxY;
    private int[][] ids;

    public Firebreak(){
        if(breaklist == null) breaklist = new ArrayList<Firebreak>(1);
        region = new ArrayList<int[]>(1);
        maxX = -1; minX = -1; minY = -1; maxY = -1;
    }

    public void addToRegion(int x, int y){
        int[] pair = new int[2];
        if(x > maxX) maxX = x;
        if(x < minX) minX = x;
        if(y > maxY) maxY = y;
        if(y < minY) minY = y;
        pair[0] = x;
        pair[1] = y;
        if(!region.contains(pair)){
            region.add(pair);
            //System.out.println(region.get(region.size() -1));
        }
    }

    public static void addCompleted(Firebreak fb){
        breaklist.add(fb);
    }

    public boolean inFirebreak(Plant p){
        //System.out.println(region.size());
        if((p.getX() > maxX) || (p.getX() < minX) || (p.getY() > maxY) || (p.getY() < minY)) return false; 
        for(int i = 0; i < region.size(); ++i){
            //System.out.println(region.get(i)[0]);
            if((p.getX() == region.get(i)[0]) && (p.getY() == region.get(i)[1])){
                return true;
            }
        }
        return false;
    }
}
