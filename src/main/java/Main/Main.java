package Main;
import FilmSendBot.*;
import TvSeriesSendBot.*;
import UploadBot.*;
import GroupManage.*;
import Database.*;


public class Main {
    public static void main(String[] args) {
       FilmThread ft = new FilmThread();
       GroupManagerThread gt = new GroupManagerThread();
       TvSeriesThread tt = new TvSeriesThread();
       UploadThread ut = new UploadThread();
       DbThread dt = new DbThread();
       dt.start();
       dt.setPriority(10);
       ft.start();
       ft.setPriority(10);
       gt.start();
       gt.setPriority(6);
       tt.start();
       tt.setPriority(9);
       ut.start();
       ut.setPriority(8);
    }
}
