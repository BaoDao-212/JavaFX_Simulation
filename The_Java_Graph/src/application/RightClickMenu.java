package application;


import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/*
 * Ä�Ã¢y lÃ  class giÃºp táº¡o vÃ  thá»ƒ hiá»‡n cÃ¡c lá»±a chá»�n khi click chuá»™t pháº£i vÃ o giao diá»‡n
 */
public class RightClickMenu {

	ContextMenu menu; // ContextMenu lÃ  cÃ³ thá»ƒ cho phÃ©p liÃªn káº¿t vá»›i nhiá»�u Ä‘á»‘i tÆ°á»£ng
						// vÃ  cho chÃ©p hiá»ƒn thá»‹ menu khi nháº¥p chuá»™t pháº£i vÃ o Ä‘á»‘i
						// tÆ°á»£ng Ä‘Ã³
	NodeFX sourceNode; // Náº¿u Ä‘á»‘i tÆ°á»£ng lÃ  1 node
	Edge sourceEdge; // Náº¿u Ä‘á»‘i tÆ°á»£ng lÃ  1 cáº¡nh
	MenuItem delete, changeId; // cÃ¡c item trong menu

	// constructor khá»Ÿi táº¡o má»™t Ä‘á»‘i tÆ°á»£ng thuá»™c class
	public RightClickMenu() {
		menu = new ContextMenu();
		delete = new MenuItem("Delete");
		changeId = new MenuItem("Change ID");

		Image openIcon = new Image(getClass().getResourceAsStream("/image/delete_img.png"));
		ImageView openView = new ImageView(openIcon);
		delete.setGraphic(openView);

		Image textIcon = new Image(getClass().getResourceAsStream("/image/rename_img.png"));
		ImageView textIconView = new ImageView(textIcon);
		changeId.setGraphic(textIconView);

		menu.getItems().addAll(delete, changeId);
		menu.setOpacity(0.9); // Ä�á»™ má»� 0 < opacity < 1
	}

	// Khi nháº¥p chuá»™t vÃ o 1 node
	public RightClickMenu(NodeFX node) {
		this();
		sourceNode = node;
		delete.setOnAction(e -> {
			DefineGraph.cref.deleteNode(sourceNode);
		});
		changeId.setOnAction(e -> {
			DefineGraph.cref.changeID(node);
		});
	}

	// Khi nháº¥p chuá»™t vÃ o 1 edge
	public RightClickMenu(Edge edge) {
		this();
		sourceEdge = edge;
		delete.setOnAction(e -> {
			DefineGraph.cref.deleteEdge(sourceEdge);
		});
		changeId.setOnAction(e -> {
			DefineGraph.cref.changeWeight(sourceEdge);
		});
	}

	// láº¥y Menu
	public ContextMenu getMenu() {
		return menu;
	}
}
