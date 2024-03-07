package Database;

public class DbThread extends Thread{
    public void run(){
        DbConnection dc = new DbConnection();
        DbDelete dd = new DbDelete();
        DbSelect ds = new DbSelect();
        DBUpdate du = new DBUpdate();
    }
}
