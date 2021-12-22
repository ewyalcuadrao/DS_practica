package core;

public class Id {
  private int id;
  private static Id instance = null;

  private Id()
  {
    this.id = -1;
  }
  public static Id getInstance(){
    if (instance == null) {
      instance = new Id();
    }
    return instance;
  }
  public void setId( int id){
    this.id = id;
  }
  public int getId()
  {
    this.id +=1;
    return this.id;
  }


}
