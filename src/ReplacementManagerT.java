public class ReplacementManagerT extends Thread{
    public void run(){
        while (true) {
            try {
                Calculator.NRU();
                Thread.sleep(1);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        }
    }
}
