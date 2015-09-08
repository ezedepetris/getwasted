package training.careers;

/**
 * clase que representa una fila de la listView;
 *
 */
public class ListRow {
     public String tittle;
     
     public ListRow(String tittle){
    	 this.tittle=tittle;
     }
     
     @Override
    public String toString() {
    	return tittle;
    }
}