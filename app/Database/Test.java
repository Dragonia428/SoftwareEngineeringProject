
import java.util.ArrayList;


class Test{

    public static void main( String[] cmd_args ) {
        
        ArrayList<Integer> list = new ArrayList<Integer>();
        for( int i = 1; i <= 4; i++ )
            list.add(i);

        Connect con = new Connect();
        DBManage dbm = new DBManage();
        dbm.authorizeAccounts( list );
        
    }
    
}