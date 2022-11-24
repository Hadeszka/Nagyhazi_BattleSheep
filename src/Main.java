public class Main {
   public static void main(String[] args) {
      GameVisual gameFrame = new GameVisual("BattleSheep");
      gameFrame.createBoard(40);
      gameFrame.createMenu();
      gameFrame.setUpGame(1000, 700);
   }
}
