import java.util.ArrayList;

public class Firebreak{
    private static ArrayList<Firebreak> breaklist;
    private ArrayList<int[]> region;
    private int minX, maxX, minY, maxY;
    private ArrayList<Boolean> layers;
    private ArrayList<int[]> ids;

    public Firebreak(){
        if(breaklist == null) breaklist = new ArrayList<Firebreak>();
        region = new ArrayList<int[]>();
        ids = new ArrayList<int[]>();
        layers = new ArrayList<Boolean>();
        maxX = -1; minX = -1; minY = -1; maxY = -1;
    }

    public ArrayList<int[]> getIDList(){
        return this.ids;
    }

    public ArrayList<Boolean> getLayers(){
        return this.layers;
    }

    public static ArrayList<Firebreak> getBreakList(){
        return Firebreak.breaklist;
    }

    public void addToRegion(int x, int y){
        int[] pair = new int[2];
        if(x > maxX) maxX = x;
        if(x < minX || minX == -1) minX = x;
        if(y > maxY) maxY = y;
        if(y < minY || minY == -1) minY = y;
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

    public static void removeLatest(){
        breaklist.remove(breaklist.size()-1);
    }

    public boolean inFirebreak(Plant p){
        //System.out.println(region.size());
        if((p.getX() > maxX) || (p.getX() < minX) || (p.getY() > maxY) || (p.getY() < minY)) return false; 
        for(int i = 0; i < region.size(); ++i){
            //System.out.println(region.get(i)[0]);
            if((p.getX() == region.get(i)[0]) && (p.getY() == region.get(i)[1])){
                int[] pair = {p.getSpeciesID(), p.getID()};
                ids.add(pair);
                layers.add(p.getLayer());
                return true;
            }
        }
        return false;
    }
}
