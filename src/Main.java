public class Main {
   public static void main(String[] args) {
      GameFrame gameFrame = new GameFrame("BattleSheep");
      gameFrame.createBoard(40);
      gameFrame.createMenu();
      gameFrame.setUpGame(1000, 700);
   }
}
