package application;

import javafx.scene.control.Label;
import javafx.scene.shape.Shape;

/*
 * Ä�Ã¢y lÃ  lá»›p cáº¡nh, giÃºp thá»ƒ hiá»‡n má»™t cáº¡nh cÅ©ng nhÆ° cÃ¡c thÃ´ng sá»‘ cá»§a chÃºng
 */
public class Edge {

    public Node source, target; // Ä‘á»‰nh nguá»“n vÃ  Ä‘á»‰nh Ä‘Ã­ch
    public double weight; // trá»�ng sá»‘ cá»§a cáº¡nh
    public Shape line; // hÃ¬nh line trÃªn canvas
    public Label weightLabel; // label hiá»‡n giÃ¡ trá»‹ trá»�ng sá»‘ trÃªn canvas 

    // Láº¥y ra hÃ¬nh áº£nh line, sá»­ dá»¥ng trong quÃ¡ trÃ¬nh xoÃ¡ node -> xoÃ¡ line cÃ³ 1 Ä‘áº§u lÃ  node Ä‘Ã³
  
	// Constructor khá»Ÿi táº¡o má»™t cáº¡nh 
    public Edge(Node source, Node target, double weight, Shape line, Label weiLabel) {
        this.source = source;
        this.target = target;
        this.weight = weight;
        this.line = line;
        this.weightLabel = weiLabel;
    }

}