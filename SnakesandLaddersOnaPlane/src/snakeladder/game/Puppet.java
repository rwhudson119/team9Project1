package snakeladder.game;

import ch.aplu.jgamegrid.*;
import java.awt.Point;
import java.util.List;

public class Puppet extends Actor
{
  private GamePane gamePane;
  private NavigationPane navigationPane;
  private int cellIndex = 0;
  private int nbSteps;
  private int turnSteps; // stores total steps taken in turn
  private Connection currentCon = null;
  private int y;
  private int dy;
  private boolean isAuto;
  private String puppetName;

  Puppet(GamePane gp, NavigationPane np, String puppetImage)
  {
    super(puppetImage);
    this.gamePane = gp;
    this.navigationPane = np;
  }

  public boolean isAuto() {
    return isAuto;
  }

  public void setAuto(boolean auto) {
    isAuto = auto;
  }

  public String getPuppetName() {
    return puppetName;
  }

  public void setPuppetName(String puppetName) {
    this.puppetName = puppetName;
  }

  void go(int nbSteps)
  {
    if (cellIndex == 100)  // after game over
    {
      cellIndex = 0;
      setLocation(gamePane.startLocation);
    }
    this.nbSteps = nbSteps;
    this.turnSteps = nbSteps;
    setActEnabled(true);
  }

  void resetToStartingPoint() {
    cellIndex = 0;
    setLocation(gamePane.startLocation);
    setActEnabled(true);
  }

  int getCellIndex() {
    return cellIndex;
  }

  int getTurnSteps() { return turnSteps; }

  private void moveToNextCell()
  {
    int tens = cellIndex / 10;
    int ones = cellIndex - tens * 10;
    if (tens % 2 == 0)     // Cells starting left 01, 21, .. 81
    {
      if (ones == 0 && cellIndex > 0)
        setLocation(new Location(getX(), getY() - 1));
      else
        setLocation(new Location(getX() + 1, getY()));
    }
    else     // Cells starting left 20, 40, .. 100
    {
      if (ones == 0)
        setLocation(new Location(getX(), getY() - 1));
      else
        setLocation(new Location(getX() - 1, getY()));
    }
    cellIndex++;
  }

  private void moveToPrevCell() {
    int tens = cellIndex / 10;
    int ones = cellIndex - tens * 10;
    if(ones == 1) { // move down
      setLocation(new Location(getX(), getY() + 1));
    } else if (tens % 2 == 0)     // Cells starting left 01, 21, .. 81
    {
      if(ones == 0) {
        setLocation(new Location(getX() + 1, getY()));
      } else {
        setLocation(new Location(getX() - 1, getY()));
      }
    }
    else     // Cells starting left 20, 40, .. 100
    {
      if(ones == 0) {
        setLocation(new Location(getX() - 1, getY()));
      } else {
        setLocation(new Location(getX() + 1, getY()));
      }
    }
    cellIndex--;
  }

  public void act()
  {
    if ((cellIndex / 10) % 2 == 0)
    {
      if (isHorzMirror())
        setHorzMirror(false);
    }
    else
    {
      if (!isHorzMirror())
        setHorzMirror(true);
    }

    // Animation: Move on connection
    if (currentCon != null)
    {
      int x = gamePane.x(y, currentCon);
      setPixelLocation(new Point(x, y));
      y += dy;

      // Check end of connection
      if ((dy > 0 && (y - gamePane.toPoint(currentCon.locEnd).y) > 0)
        || (dy < 0 && (y - gamePane.toPoint(currentCon.locEnd).y) < 0))
      {
        gamePane.setSimulationPeriod(100);
        setActEnabled(false);
        setLocation(currentCon.locEnd);
        cellIndex = currentCon.cellEnd;
        setLocationOffset(new Point(0, 0));
        currentCon = null;
        navigationPane.prepareRoll(cellIndex);
      }
      return;
    }

    // Normal movement
    if (nbSteps > 0) {
      moveToNextCell();

      if (cellIndex == 100)  // Game over
      {
        setActEnabled(false);
        navigationPane.prepareRoll(cellIndex);
        return;
      }

      nbSteps--;
    } else if (nbSteps == -1) {
      moveToPrevCell(); //moves puppet back once, yet to be implemented.
      nbSteps = 0; //sets nbSteps to 0 so act() can check rules of square.
    }
    if (nbSteps == 0)
      {
        gamePane.collisionDetect(cellIndex);
        // Check if on connection start and either roll is not minimum or connection is upwards.
        if ((currentCon = gamePane.getConnectionAt(getLocation())) != null
            && (turnSteps != navigationPane.getNumberOfDice() || currentCon.locEnd.y < currentCon.locStart.y))
        {
          gamePane.setSimulationPeriod(50);
          y = gamePane.toPoint(currentCon.locStart).y;
          if (currentCon.locEnd.y > currentCon.locStart.y) {
            dy = gamePane.animationStep;
          	navigationPane.addTraverseUp();
          }
          else {
            dy = -gamePane.animationStep;
            navigationPane.addTraverseDown();
          }
          if (currentCon instanceof Snake)
          {
            navigationPane.showStatus("Digesting...");
            navigationPane.playSound(GGSound.MMM);
          }
          else
          {
            navigationPane.showStatus("Climbing...");
            navigationPane.playSound(GGSound.BOING);
          }
        }
        else
        {
          currentCon = null; // removes connection because it should not be used this turn.
          setActEnabled(false);
          navigationPane.prepareRoll(cellIndex);
        }
      }
  }
}
