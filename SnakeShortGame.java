 
package snakeshortgame;

/***********************************************************************************
 *  SHORT VERSION BY JIM , TO RUN, PUSH NEW GAME    *
 */


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.applet.*; 
import java.util.ArrayList;
import static javax.swing.Action.MNEMONIC_KEY;
  //******jims short version of snake game
////////////////////////////////////////////////////////////////
/***************************************************************
* Class SnakeGame
*   Encompasses the logic for building the gui of the game
* and handling menu events and options input from the user.
*
*
***************************************************************/
public class SnakeShortGame extends JFrame {
    
    
  //////////////////////// VARIABLE DECLARATIONS //////////////////////// 
    
  //DEVELOPER SETTINGS
  public static final int WIDTH = 950;
  public static final int HEIGHT = 800;
  public static final int DIFF_MIN = 0; //
  public static final int DIFF_MAX = 10;  // difficulty slider settings
  public static final int DIFF_INIT= 5; //
    
  public static final String TITLE = "Snake";
  public static final String PLAY_BUTTON = "start2.gif";
  public static final String HELP_BUTTON = "help2.gif";  
  
      
  //forward component declares
  JMenuBar menuBar;
  JMenu gameMenu, helpMenu;
  JMenuItem newGame, exitGame, rules, about;
  JToolBar toolBar;
  JButton newGameButton, helpButton;
  JCheckBox soundOption;
  JSlider difficultySlider;
  JPanel fieldMount, optionsPanel;
  GamePanelField gamePanel;
        
  ////////////////////////////// METHODS ////////////////////////////////
    
  /***************************************************************
  * SnakeGame()
  *   the constructor for the main window. 
  *   builds the gui.
  */  
  public SnakeShortGame() {
    //setup window
    super(TITLE);
      
    setSize(WIDTH, HEIGHT);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
      
    //menu creation 
    makeMenu();
            
    //make sound option
    makeOptions();
      
    //make game field
    makeGameField();
          
  }
    
  /***************************************************************
  * makeMenu()
  *   Encapsulate the menu creation logic so SnakeGame() 
  *   is more readable.
  */  
  private void makeMenu() {
    menuBar = new JMenuBar();
      
    gameMenu = new JMenu("Game");//creating Game menu
    gameMenu.setMnemonic(KeyEvent.VK_G);
    menuBar.add(gameMenu);
      
    newGame  = new JMenuItem(new menuAction("New Game", null, KeyEvent.VK_N));
    exitGame = new JMenuItem(new menuAction("Exit", null, KeyEvent.VK_X));
    gameMenu.add(newGame);
    gameMenu.add(exitGame);
    setJMenuBar(menuBar);//set to frame 
  
  }
       
  /***************************************************************
  * makeToolbar()
  *   Encapsulate the toolbar creation logic so SnakeGame() 
  *   is more readable.
  */  
  private void makeToolbar() {
    toolBar = new JToolBar();
    //add buttons
    newGameButton = new JButton(new menuAction("Play a new Game", new ImageIcon(PLAY_BUTTON), 0));
    toolBar.add(newGameButton);
      
    helpButton =  new JButton(new menuAction("Help", new ImageIcon(HELP_BUTTON), 0));
    toolBar.add(helpButton);
      
    add(toolBar, BorderLayout.PAGE_START);
  }
      
  /***************************************************************
  * makeOptions()
  *   Encapsulate the menu creation logic so SnakeGame() 
  *   is more readable.
  */  
  private void makeOptions() {
     //setup optionsPanel
     optionsPanel = new JPanel(new FlowLayout());
    
     //add sound tick option to control panel
     soundOption = new JCheckBox("Sound:");
     soundOption.setHorizontalAlignment(SwingConstants.CENTER);
     soundOption.setHorizontalTextPosition(SwingConstants.LEFT);
     soundOption.setSelected(true);
     soundOption.setMnemonic(KeyEvent.VK_S);
     soundOption.addItemListener(new optionListener());
                
     //add the whole thing to main frame
    add(optionsPanel, BorderLayout.SOUTH);
       
   }
    
    
  /***************************************************************
  * makeGameField()
  *   Encapsulate the play field creation logic so SnakeGame() 
  *   is more readable.
  */  
  private void makeGameField() {
    gamePanel =  new GamePanelField();
    gamePanel.setPreferredSize(gamePanel.getPreferredSize());
    //gamePanel.setPreferredSize(200,600);
    gamePanel.setFocusable(true);
    gamePanel.requestFocusInWindow();
    gamePanel.addMouseListener(gamePanel);
    gamePanel.addKeyListener(gamePanel);
      
    fieldMount =  new JPanel();
    fieldMount.setLayout(new GridBagLayout());
   
    //null GBCs will center item inside this panel
    fieldMount.add(gamePanel, new GridBagConstraints());
    add(fieldMount);
    
  }
    
  /***************************************************************
  * main()
  *   initializes the GUI and makes it visible
  */
  public static void main(String[] args) {
    //initialize ui
    SnakeShortGame game = new SnakeShortGame();   
    //draw the completed ui
    game.setVisible(true);
    
  }
  
  ////////////////////////// INNER CLASSES //////////////////////////////
    
  /***************************************************************
  * inner Class menuListener
  *   Handles actionEvents generated by menu interaction from user
  *
  ***************************************************************/
  private class menuAction extends AbstractAction {
    
     //constructor 
     public menuAction(String text, ImageIcon icon, int mnemonic) {
       
        super(text, icon);
            
        putValue(MNEMONIC_KEY, mnemonic);  
     }
       
     //actionPerformed to handle events
     //overrid
     public void actionPerformed(ActionEvent e) {
       
       String command =  e.getActionCommand();
         
       //handle menu choices
       if(command.equals("New Game") || command.equals("Play a new Game")) {
         newGame(); 
       }  
       else if(command.equals("Exit")) {
           System.exit(0); 
       }
      
     }
       
     //******************************************************
     // helper methods to improve readability of if/else tree
       
     //start up a new game
     private void newGame() {
       gamePanel.requestFocusInWindow();
       gamePanel.cleanup();
       gamePanel.startGame(soundOption.isSelected());             
     }
       
     //create a rules dialog 
     private void showRules() {
       //JOptionPane.showMessageDialog(new JFrame(),
     }
     //create an about dialog
     private void showAbout() {
           
     }
       
  }//endInnerClass menuListener
    
  /***************************************************************
  * inner Class optionListener
  *   Handles actionEvents generated by option interaction
  *
  *   exists primarily as a skeleton for future options.
  ***************************************************************/
  private class optionListener implements ItemListener, ChangeListener {
    public void itemStateChanged(ItemEvent e) {
    }
      
    public void stateChanged(ChangeEvent e) {
    }
  }//endInnerClass optionListener
    
    
}//endclass SnakeGame
   
////////////////////////////////////////////////////////////////
/***************************************************************
* Class GameField
*   A subclass of JPanel that handles drawing the game graphics
*   as well as the game sound and logic. 
***************************************************************/
class GamePanelField extends JPanel implements KeyListener, MouseListener, Runnable {
  
   // DEVELOPER SETTINGS//this is the grid in middle
   public static final int WIDTH = 650;
   public static final int HEIGHT = 425;
   public static final int SNAKE_SIZE = 15; 
   public static final int NUM_APPLES = 10;
   public static final int SNAKE_START_LENGTH = 4; //1 head , 3 in body
   public static final int APPLE_SPAWN_TIME = 4000;
     
   public static final String APPLE = "apple.gif";
   public static final String SNAKE_HEAD = "snakehead1.gif";
   public static final String SNAKE_BODY = "snakebody.gif";
   public static final String BG_MUSIC = "File:trippygaia1.mid";
  
   //member declarations
   Image apple, snakeHead, snakeBody; 
   AudioClip bgMusicClip;   
   ArrayList<DisplayableFieldElement> appleList;
   Snake snake;
   boolean gameActive;
   Point splashStart;
   Thread appleSpawner = new Thread();
   int difficulty = 5;
  
     
  /***************************************************************
  * GameField()
  *   constructor. load image and sound assets, display start screen.
  */
   public GamePanelField() {
     //customize game field
     setBorder(BorderFactory.createLineBorder(Color.blue));
       
     //load data for the graphics
     apple = (Image) Toolkit.getDefaultToolkit().getImage("apple.gif");  
     snakeHead = (Image) Toolkit.getDefaultToolkit().getImage("snakehead1.gif"); 
     snakeBody = (Image) Toolkit.getDefaultToolkit().getImage(SNAKE_BODY);    
  
          
     //init other objects just to show game field
  splashStart = new Point(16*SNAKE_SIZE, 22*SNAKE_SIZE); //snake on opening screen
  
     PutSnakeOnScreen(); //show the first snake
       
     gameActive = false;
   }
     
  //accessors 
  public int getDifficulty() { return difficulty;}
  //mutators
  public void setDifficulty(int d) { difficulty = d; } 
    
    
  ////////////////////////////// METHODS ////////////////////////////////  
  /***************************************************************
  * startGame()
  *   //START GAME STARTS THE MOVEMENT TO RIGHT ON SCREEN BY SNAKE 
  */
   public void startGame(boolean musicOpt) {
    
   
    //this gets a pointer to snake code and gives it a lot of stuff needed
  snake = new Snake(snakeHead, snakeBody, snakeStartPt(), SNAKE_START_LENGTH, SNAKE_SIZE, difficulty, this);
      repaint();
               
      //start snake thread
      snake.startSnakeMoving();
    }
     
   /***************************************************************
  * endGame()
  *   terminate game logic threads. 
  */
   public void endGame() {
     cleanup();
     repaint();
    
   }
  
  /***************************************************************
  * run()
  *   adds apples to appleSprites at a regular interval
  */
  public void run() {
    //apples stuff
    
  }
     
  /***************************************************************
  * paintComponent()
  *   paints the field
  */
   public void paintComponent(Graphics g) {
     super.paintComponent(g);
     Dimension dim = getSize();        //get size of panel
          
   g.setColor(Color.YELLOW); //set color for background, g was set above
   g.fillRect(0, 0, dim.width, dim.height); //put green over the frame
     System.out.println("PAINT COMPONENT AND NOW DRAW GRID***" );  
     // create the game grid
     drawGrid(g);
                  
     //use draw of the snake class to draw the current snake on current grid
     snake.draw(g);
        
   }
        
  ////////////////////////// UTILITY METHODS ////////////////////////////     
  /***************************************************************
  * drawGrid()
  *   paints the gridlines on the field.
  */
   private void drawGrid(Graphics g) {
       //15x15 pixels
     g.setColor(Color.BLACK);
     for(int x =0 ; x<WIDTH; x++)//paint vertical lines
       if(x%15 == 0)                   // x |  |  |
         g.drawLine(x, 0, x, HEIGHT); //drawline at 15,30,45...
     for(int y =0 ;  y<HEIGHT; y++)//paint horizontal lines
       if(y%15 == 0)
         g.drawLine( 0, y, WIDTH, y ); // y - - - moving down
   }
  
   private void PutSnakeOnScreen() {
      //place a snake in the middle of screen
       //field is passed in on far right
  snake = new Snake(snakeHead, snakeBody, splashStart, SNAKE_START_LENGTH, SNAKE_SIZE, difficulty, this);
  
  }
    
   // choose a start point for snake once user selects new game
  private Point snakeStartPt() {
      Point thepoint = new Point(90,200); //starting point for snake for new game
      return thepoint;
  }   
    
      
  public void stopSound(){
   // bgMusicClip.stop();
  }
    
  /***************************************************************
  * cleanup()
  *   shut down the threads and reset data
  */
  public void cleanup() {
     if(gameActive = true)
       appleSpawner.interrupt();
     gameActive = false;
       
     snake.stopSnake();
     stopSound();
   }
  /***************************************************************
  * getPrefferedSize()
  *   get the field size, used by parent container
  */
  public Dimension getPreferredSize() {
    return new Dimension(WIDTH, HEIGHT);
  }
  //////////////////////    LISTENERS    ////////////////////////
    
  //mouseListener methods
  public void mouseEntered(MouseEvent e) {
     requestFocusInWindow();}
   public void mouseClicked(MouseEvent e) {
     requestFocusInWindow();}
  public void mouseExited(MouseEvent e) { }
  public void mousePressed(MouseEvent e) { }
  public void mouseReleased(MouseEvent e) { }
  
  // a key on keyboard has been pressed,
  // set direction of snake based on arrows on keyboard
  //then call method based on what was hit
  public void keyPressed(KeyEvent e) {
     //set snake head direction property (up,down.etc)
     //direction is in the arraylist entry along with x,y and gif 
     if(e.getKeyCode()==(KeyEvent.VK_UP))
        snake.setSnakeDirection(SnakeElement.UP);
     else if(e.getKeyCode()==(KeyEvent.VK_DOWN))
        snake.setSnakeDirection(SnakeElement.DOWN);
     else if(e.getKeyCode()==(KeyEvent.VK_RIGHT))
       snake.setSnakeDirection(SnakeElement.RIGHT);   
     else if(e.getKeyCode()==(KeyEvent.VK_LEFT))
        snake.setSnakeDirection(SnakeElement.LEFT);
     else if(e.getKeyCode()==(KeyEvent.VK_Q))
        endGame();
  } 
  public void keyReleased(KeyEvent e) { }
  public void keyTyped(KeyEvent e) { }
    
}//endlclass GameField
   
////////////////////////////////////////////////////////////////
/***************************************************************
* Class Snake
*   encapsulates the details of a snake entity. 
* 
***************************************************************/
class Snake implements Runnable{
    private ArrayList<SnakeElement> snakeList;
    private boolean mobile;
      
    //game variables
    static final int BASE_MOVE_DELAY = 500; //1 second base delay
    private double maxSpeed = 0.27;
    private double speed = 0.038;
    private double speedFactor = 0.08;
    private int growth = 16;
    private int difficulty = 5;
             
    //these are filled in on call to snake from gamefield
    private GamePanelField gfield;
    private int snakeSize;
    private Image headsnakegif;
    private Image bodysnakegif;
    private Thread snakeMover;
      
    //constructors
 public Snake() {}
      
 public Snake(Image h, Image b, Point startpt, int length, int sSize, int diff, GamePanelField f) {
       snakeList = new ArrayList<SnakeElement>();
       headsnakegif = h;  //bring in the images for snake
       bodysnakegif = b;
       difficulty = diff;
       snakeSize = sSize;
       gfield = f;
         
       //scale speeds to difficulty
       speed = (speed+(.02 *diff));
       maxSpeed = (maxSpeed+(.02 * diff));
         
       //here we put the head of snake in arraylist at (0)
       //snake element is at far bottom of code in elements
       //image   point    direction    is an entry in one element        
                                  //gif head   point(90,200) RIGHT
                 //the constructor on this sets direction to right
       snakeList.add(new SnakeElement(headsnakegif, startpt));
       //here we put the body parts of snake in array list
       for(int i=0; i<length-1; i++) {
                                            //this was 15
           //each cell is 15 pixels and 3 positions 
                       
                             //75,200  RIGHT :body points and direction
                             //60,200  RIGHT
                             //45,200  RIGHT
           //startpt is an object of point,it has an x and y
           //here we figure a new Point object based on the startpt we
           //gave the snake, startpt is of type Point and we
           //can get in double precision the x and y set of this point
           //with the methods getX,getY
                         // x                          //y
          startpt = new Point((int)startpt.getX()-15, (int)startpt.getY());
          //add snake body part to entries in array list
          snakeList.add(new SnakeElement(bodysnakegif, startpt));
       }    
         
       //allow the snake to move
       mobile = true;      
    }
     //method is called based on what direction arrow is hit by user 
    //direction is part of each element in the snake arraylist,setting head here
    public void setSnakeDirection(int d) {//does not allow snake to double back
       if((snakeList.get(0).getDirection() != d+2) && (snakeList.get(0).getDirection() != d-2))
          snakeList.get(0).setDirection(d);
    }
    
     
    /////////////////////////////// CORE METHODS ////////////////////////////////
    /***************************************************************
    * snake.run()
    *   The method exectued by Thread snakeMover. the core logic of
    *   the snake behavior is contained here
    */
    //game is running now snake moves into screen and keeps moving -mobile
    //this is the key loop that keeps calling movesnake to adjust by 1
    //and then draw the new adjustments
    //override
    public void run() {
       try {      
          while(mobile) {
            //move the snake in the head direction
            moveSnakeCoOrdinates_In_List(); //this will adjust the positions in
                          //the array list by copying entries in the table to next entry
            gfield.repaint();  //calls draw component again above?
  
                                   
            Thread.sleep((int)(BASE_MOVE_DELAY * (1-speed)));            
          }
       } catch(InterruptedException e) { 
          System.out.println("snake mover: " + e.getMessage());
          return; 
       }
    }    
      
    /***************************************************************
    * snake.moveSnake()
    *   game is running, here we adjust the body of snake to go in its
    * //current direction
    */
    public void moveSnakeCoOrdinates_In_List() {
         
         System.out.println("MOVE SNAKE co-ordinates in ARRAY LIST " );
       //loop starts at last position in table and counts down
       for(int i = (snakeList.size()-1); i > 0; i--) {
                               //before               after
                        //  0     90,200  head  R         xx    
                        //  1     75,200  body  R        90,200 R
                        //  2     60,200  body  R        75,200 R
                        //  3     45,200  body  R        60,200 R
           //shift array element down so an element has the elements-1 value
           
           //get "point" entry of previous element
         Point ptentry =(snakeList.get(i-1).getPosition());
         
         //now set current element to that point
         snakeList.get(i).setPosition(ptentry);
       }
      //move the head to a new point in direction of travel head 
          adjustSnakeHead();
    }
    /***************************************************************
    * snake.adjustSnakeHead()
    *   Snakehead is element(0) and we want to adjust the x,y of snakehead
    *  based on the direction (up,down,right,left) in the entry
    *  The direction may have been changed by user with arrow keys 
    *  which changes the direction part of the entry*/   
    private void adjustSnakeHead() {
        
        //recall head is at position 0 in arraylist
       int direction = snakeList.get(0).getDirection(); //the head has a 
                                        //direction code integer
       Point p = snakeList.get(0).getPosition(); //get the point(x,y) of head
         
       //determine which way to increment snake head and set new head position
       //at (0
     if(direction == SnakeElement.UP)
         //set new points for head in the arraylist based on movement-UP,etc
                                    //y is smaller, move up screen
       snakeList.get(0).setPosition(new Point((int)p.getX(), (int)p.getY()-snakeSize));
    else if(direction == SnakeElement.DOWN)
          snakeList.get(0).setPosition(new Point((int)p.getX(), (int)p.getY()+snakeSize));          
    else if(direction == SnakeElement.RIGHT)
                       //set/increase x by units in snake
     snakeList.get(0).setPosition(new Point((int)p.getX()+snakeSize, (int)p.getY()));
    else if(direction == SnakeElement.LEFT)
                                                         //decrease y 
          snakeList.get(0).setPosition(new Point((int)p.getX()-snakeSize, (int)p.getY()));          
    }
   
      
    /***************************************************************
    * snake.draw()
    *   draw the snake using the passed in Graphics object
    */
    public void draw(Graphics g) {
        
        System.out.println("IN SNAKE.DRAW GO THROUGH LIST AND DRAW LIST ELEMENTS " );
        //go through the arraylist, one piece at a time and draw it
       for(SnakeElement snakepieceelement : snakeList) {
            System.out.println("IN SNAKE DRAW LOOP" );
           //call draw below to print this gif at some position x,y
          snakepieceelement.draw(g);
       }
    }
      
    /***************************************************************
    * snake.startSnakeMoving()
    *   this gets called when user hits start game 
    *   and snake will start moving left to right based on how
    * we built the snake in the snake array list.
    */
    public void startSnakeMoving() {
       snakeMover = new Thread(this);
       snakeMover.start();   
    }
      
    /***************************************************************
    * stopSnake()
    *   cause the thread moving the snake to return.
    */
    public void stopSnake() {
       mobile = false;
    }
       
    ////////////////////////////// UTILITY METHODS /////////////////////////////// 
        
   }
      
//endclass Snake
    
////////////////////////////////////////////////////////////////
/***************************************************************
* Class DisplayableFieldElement
*   this is the format of an entry in arraylist that will ultimately
*   be drawn on the field
* 
***************************************************************/
//could be snake or apple we are showing
class DisplayableFieldElement {
    private Point positionpoint; //has x y
    private Image someimage;
      
    //contructors
    public DisplayableFieldElement() {
       positionpoint = new Point(0,0);
       someimage = null;
    }    
    //constructor 2
    public DisplayableFieldElement(Image inimage, Point inp) {
       positionpoint = inp;
       someimage = inimage;
    }
    //constructor 3 called to print snake or apple?(?)
    public DisplayableFieldElement( DisplayableFieldElement d) {
       positionpoint = new Point(d.getPosition());
       someimage = d.getSnake();
    }    
      
    //accessors to private variables
    public Point getPosition() { return positionpoint;}
    public Image getSnake() { return someimage;}
    
    //mutators to set the private variables
    public void setPosition(Point p) { positionpoint = p;}
    public void setSnake(Image i) { someimage = i;}
      
    //override draw method
    public void draw(Graphics g) {
        
        System.out.println("IN DISPLAY FIELD ELEMENT DRAW*****" );
            //image!!               // position object has x and y
            //this is just one gif on screen
       g.drawImage(someimage, (int)positionpoint.getX(), (int)positionpoint.getY(), null); 
    }
  
}//endclass DisplayableFieldElement
  
////////////////////////////////////////////////////////////////
/***************************************************************
* Class SnakeElement
*   a subclass of DisplayableFieldElement that helps the snake 
*   elements move on the field.
***************************************************************/
class SnakeElement extends DisplayableFieldElement {
    //each element one creates of this  has an
    //image, point(x,y), and directon integer
   
   //directional constants
   static final int UP = 1;
   static final int DOWN = 3;
   static final int LEFT = 2;
   static final int RIGHT = 4;
     
   private int directionint; //holds the direction for this element
          
   //constructors
   public SnakeElement() {
      super();
      directionint = RIGHT;
   }
   public SnakeElement(Image img, Point p) {
      super(img, p);
      directionint = RIGHT;  //on startup this is the constructor called
   }
   public SnakeElement(Image img, Point p, int d ) {
      super(img, p);
      directionint = d;
   }
   public SnakeElement( SnakeElement s) {//copy constructor
      super(s);
      directionint = s.getDirection();
   }
     
   //accessor/mutator
   public int  getDirection() { return directionint; }
   public void setDirection(int d) { directionint = d; }
      
}//endclass SnakeElement