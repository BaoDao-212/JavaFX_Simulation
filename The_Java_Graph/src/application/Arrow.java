package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

/*
 * Ä�Ã¢y lÃ  lá»›p giÃºp thá»ƒ hiá»‡n Ä‘Æ°á»£c hÆ°á»›ng cá»§a cáº¡nh, lá»›p táº¡o ra mÅ©i tÃªn.
 * ThÃ´ng tin vá»� lá»›p Path: extends tá»« Shape, Path Ä‘áº¡i diá»‡n cho má»™t hÃ¬nh dáº¡ng Ä‘Æ¡n giáº£n vÃ  cung cáº¥p cÃ¡c
 * phÆ°Æ¡ng tiá»‡n cáº§n thiáº¿t Ä‘á»ƒ xÃ¢y dá»±ng cÆ¡ báº£n vÃ  quáº£n lÃ½ 1 Ä‘Æ°á»�ng hÃ¬nh há»�c
 */
public class Arrow extends Path{
    private static final double defaultArrowHeadSize = 7; // Máº·c Ä‘á»‹nh Ä‘á»ƒ cá»¡ mÅ©i tÃªn lÃ  7
    public Arrow(double startX, double startY, double endX, double endY, double arrowHeadSize){
        // Khá»Ÿi táº¡o má»™t Ä‘á»‘i tÆ°á»£ng Path
    	super();
        
        // Thiáº¿t láº­p Ä‘Æ°á»�ng viá»�n cÃ³ cÃ¹ng mÃ u vá»›i ná»™i dung bÃªn trong
        strokeProperty().bind(fillProperty());
        setFill(Color.BLACK); // MÃ u cá»§a Ä‘Æ°á»�ng lÃ  mÃ u Ä‘en -> viá»�n cÅ©ng lÃ  mÃ u Ä‘en
        
        // Táº¡o má»™t Ä‘Æ°á»�ng báº¯t Ä‘áº§u tá»« Ä‘iá»ƒm start -> End: giÃºp thÃªm cáº¡nh cÃ³ hÆ°á»›ng
        getElements().add(new MoveTo(startX, startY));
        getElements().add(new LineTo(endX, endY));
        
        // Sá»­ dá»¥ng toÃ¡n Ä‘á»ƒ váº½ mÅ©i tÃªn, xÃ¡c Ä‘á»‹nh gÃ³c angle = 1/2 gÃ³c táº¡o bá»Ÿi 2 cáº¡nh mÅ©i tÃªn, Ä‘á»ƒ táº¡o mÅ©i tÃªn ta táº¡o thÃªm 2 Ä‘iá»ƒm + Ä‘iá»ƒm End
        double angle = Math.atan2((endY - startY), (endX - startX)) - Math.PI / 2.0;
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        //point1
        double x1 = (- 1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * arrowHeadSize + endX;
        double y1 = (- 1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * arrowHeadSize + endY;
        //point2
        double x2 = (1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * arrowHeadSize + endX;
        double y2 = (1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * arrowHeadSize + endY;
        
        // tá»« End -> point1 -> point2 -> vá»� End -> táº¡o hÃ¬nh tam giÃ¡c giá»‘ng mÅ©i tÃªn
        getElements().add(new LineTo(x1, y1));
        getElements().add(new LineTo(x2, y2));
        getElements().add(new LineTo(endX, endY));
    }
    
    // Constructor khá»Ÿi táº¡o arrow size máº·c Ä‘á»‹nh
    public Arrow(double startX, double startY, double endX, double endY){
        this(startX, startY, endX, endY, defaultArrowHeadSize);
    }
}