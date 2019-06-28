import java.util.*;


class Model
{
    //Member variables
    int scrollPos;
    ArrayList<Sprite> sprites;
    Mario mario;	
    boolean living;
    Random rand;
    
    //Constructor
    Model()
    {
        //Creates mario, adds to sprite list
        mario = new Mario(this);
        sprites = new ArrayList<Sprite>();
        sprites.add(mario);
        
        //Loads map off execution of program
        Json ob = Json.load("map.json");
        unmarshall(ob);
    }

    //Update every sprite through polymorphism
    public void update() 
    {		
        for (int i = 0; i < sprites.size(); i++) //loops through sprites array
        {
            Sprite s = sprites.get(i);  //gets sprite from list
           
           if(s.coinDead) //if coinDead boolean is true (when coin reaches certain Y value), remove the coin from game
                sprites.remove(i);
            else
                s.update(this); //OTHERWISE, update every sprite
        }
    }
    
    //Add brick method
    void addBrick(int x, int y, int w, int h)
    {
        Sprite b = new Brick(x, y, w, h, this); //Create new Brick
        sprites.add(b); //Add brick to spritesList
    }
    
    //Add Coinblock method
    void addCoinBlock(int x, int y)
    {
        Sprite cb = new CoinBlock(x, y, this); //Create a coinblock
        sprites.add(cb); //add to sprites
    }
    
    //Add Coin method
    void addCoin(int x, int y)
    {   
        rand = new Random(); //random number variable 
        Sprite c = new Coin(x, y, this); //Create a new coin
        c.hvel = rand.nextInt(20+1+20) - 20; //Set the random horizontal velocity for coin when it pops out.
        sprites.add(c); //Add to sprites
    }

    //Clears arraylist of bricks & unmarshalls the json bricks
    void unmarshall(Json ob)
    {
         sprites.clear(); //clears sprites arraylist
         Json json_sprites = ob.get("sprites");
         for(int i = 0; i < json_sprites.size(); i++)
         {
            Json j = json_sprites.get(i);
            String str = j.getString("type");
            if(str.equals("Mario")) //If json_sprites array type is MARIO, unmarshall and add to sprites.
              {
                mario = new Mario(j, this);
                sprites.add(mario);
              }
            else if(str.equals("CoinBlock")) //If json_sprites array type is COINBLOCK, unmarshall and add to sprites.
            {
                CoinBlock cb = new CoinBlock(j, this);
                sprites.add(cb);
            }
            else //If nothing else, it is a brick , unmarshall and add to sprites.
            {
                Brick b = new Brick(j,this);
                sprites.add(b);
            }
         }
    }           

    //Marshalls sprite objects into Json objects into a json sprite list
    Json marshall()
    {
         Json ob = Json.newObject();
         Json json_sprites = Json.newList();
         ob.add("sprites", json_sprites);
         for(int i = 0; i < sprites.size(); i++) 
         {
             Sprite s = sprites.get(i);
             Json j = s.marshall(); //Marshall method for every sprite is called through polymorphism
             json_sprites.add(j); //Add to json sprites list
         }
         return ob;
    }

    //Save method for sprites
     void save(String filename)
     {
         Json ob = marshall();
         ob.save(filename);
         System.out.println("MAP SAVED");
     }
    
    //Load method sprites
    void load(String filename)
    {
        Json ob = Json.load("map.json");
        unmarshall(ob);
        System.out.println("MAP LOADED");
    }

}
