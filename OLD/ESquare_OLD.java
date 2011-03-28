import java.awt.Color;

public class ESquare {

  public Color color;
  public Boolean owned;

  public ESquare (Color[] colorsAvailable){
    int n  = (int) Math.floor((Math.random()*colorsAvailable.length));
    this.color = colorsAvailable[n];
    this.owned = false;
  }
  public Boolean check(Color compare){
    if (compare == this.color){
      this.owned = true;
      return true;
    }
    return false;
  }
  public void shift(Color thatColor){
    if (owned){
      this.color = thatColor;
    }
  }
}
