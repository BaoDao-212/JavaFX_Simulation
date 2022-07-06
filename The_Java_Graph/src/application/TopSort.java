package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.StrokeTransition;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public class TopSort extends CanvasController {
	StackPane stackRoot = DefineGraph.cref.getStackRoot();
	AnchorPane anchorRoot = DefineGraph.cref.getAnchorRoot();
	List<Shape> edges = DefineGraph.cref.edges;
	List<NodeFX> circles = DefineGraph.cref.circles;
	SequentialTransition st = DefineGraph.cref.st;
	int times = DefineGraph.cref.time;
	String reverse = "";
	List<String> topSort = new ArrayList<>();
	boolean cycleFound = false;

	public TopSort() {
		// sap xep  Tô-pô dua tren tim kiem theo chieu sau
		// co 3 trang thai khi nut va cung: chua duoc duyet(den), dang duoc duyet(xanh), da duyet xong(tim xanh) 
		cycleFound = false;
		// kiem tra no do thi co chu trinh khong
		for (NodeFX n : circles) {
			if (n.node.DAGColor == 0) {
				cycleExists(n.node, 0);
			}
		}
		if (cycleFound == false) {// neu khong co chu trinh
			// duyet tat ca cac nut và kiem tra xem nó da duocj di qua hay chua
			// neu chua thi thuc hien thuat toan topo
			for (NodeFX source : circles) {
				if (source.node.visited == false) {
					topsortRecursion(source.node, 0, times);
				}
			}
			// thuc hien xong thuat toan thi dao nguoc vi tri của cua roi in ra thu tu sap
			// xep topo
			System.out.println("Hello World " + topSort);
			Collections.reverse(topSort);
			for (String s : topSort) {
				reverse += " -> " + s;
			}
			reverse = reverse.replaceFirst(" -> ", "");// xoa ki tu -> o vi tri dau tien thanh null
			System.out.println(reverse);

			// sau khi thuat toan thuc hien thi set lai mau cho cac nut va cac cung thanh
			// mau den
			st.setOnFinished(ev -> {
				for (NodeFX n : DefineGraph.cref.circles) {
					FillTransition ft1 = new FillTransition(Duration.millis(time), n);
					ft1.setToValue(Color.BLACK);
					ft1.play();
				}
				if (DefineGraph.cref.directed) {
					for (Shape n : DefineGraph.cref.edges) {
						n.setFill(Color.BLACK);
					}
				} else if (DefineGraph.cref.undirected) {
					for (Shape n : DefineGraph.cref.edges) {
						n.setStroke(Color.BLACK);
					}
				}
				// set lai nut playpause sang che do play de san sang chay lai thuat toan
				Image image = new Image(getClass().getResourceAsStream("/image/hiPlay.png"));
				DefineGraph.cref.getPlayPauseImage().setImage(image);
				DefineGraph.cref.paused = true;
				DefineGraph.cref.playing = false;
				textFlow.appendText("---Finished--\n\n");
				textFlow.appendText("Top Sort: " + reverse + "\n");
			});
			st.onFinishedProperty();
			st.play();
			DefineGraph.cref.playing = true;
			DefineGraph.cref.paused = false;

		} else {
			// khi xuat hien chu trinh
			System.out.println("Cycle");
			BoxBlur blur = new BoxBlur(3, 3, 3);// tạo mot boxblur de lam mo
			// set 1 layout hien thi
			JFXDialogLayout dialogLayout = new JFXDialogLayout();
			dialogLayout.setStyle("-fx-background-color:#dfe6e9");
			JFXDialog dialog = new JFXDialog(stackRoot, dialogLayout, JFXDialog.DialogTransition.TOP);
			// set 1 nut de thuc hien nhan thoat khoi che do lam mo anchorRoot
			JFXButton button = new JFXButton("OK");
			button.setPrefSize(50, 30);
			button.setStyle("-fx-background-radius:20");
			dialogLayout.setActions(button);
			// set thong tin hien thi tren man hinh thong bao co chu trinh
			Label message = new Label("     Cycle Detected!\n" + "Cannot run TopSort on a  Directed Cyclic Graph!");
			message.setId("message");
			dialogLayout.setBody(message);
			// khi nut duoc bam thuc hien
			// dialog se dong lai va set mac dinh cac hieu ung cua anchorRoot la rong
			button.setOnAction(e -> {
				dialog.close();
				anchorRoot.setEffect(null);
				stackRoot.toBack();
				anchorRoot.setEffect(null);
			});

			// cho hien thi dialog ra tren cung cua man hinh
			// khi nay se thuc hien hieu ung lam mo anchorRoot
			stackRoot.toFront();
			dialog.toFront();
			dialog.show();
			anchorRoot.setEffect(blur);
		}
	}

// kiem tra co chu trinh hay khong va tinh bac cua cac nut
	void cycleExists(Node source, int level) {
		source.DAGColor = 1;

		for (Edge e : source.adjacents) {
			if (e != null) {
				Node v = e.target;
				// khi DAGcolor=1 thi co chu trinh
				if (v.DAGColor == 1) {
					cycleFound = true;
				} else if (v.DAGColor == 0) {
					v.previous = source;
					cycleExists(v, level + 1);
				}
			}
		}
		
		//source.DAGColor = 2;
	}

	public void topsortRecursion(Node source, int level, int times) {
		// set animation chuyen cac nut sang mau xanh
		FillTransition ft = new FillTransition(Duration.millis(times), source.circle);
		if (source.circle.getFill() == Color.BLACK) {
			ft.setToValue(Color.FORESTGREEN);
		}
		st.getChildren().add(ft);
		// in ra theo so bac
		String str = "";
		for (int i = 0; i < level; i++) {
			str = str.concat("\t");
		}
		str = str.concat("Recursion(" + source.name + ") Enter\n");
		final String str2 = str;
		FadeTransition fd = new FadeTransition(Duration.millis(10), textFlow);
		fd.setOnFinished(e -> {
			textFlow.appendText(str2);
		});
		fd.onFinishedProperty();
		st.getChildren().add(fd);

		source.visited = true;
		// duyet danh sach cac cung ke voi no
		for (Edge e : source.adjacents) {
			if (e != null) {
				Node v = e.target;
				// khi no chua duoc kiem tra
				// d[v]= d[source]+1;
				if (!v.visited) {
					v.minDistance = source.minDistance + 1;
					v.previous = source;
					// thay doi mau cua cung sang mau xanh
					if (isUndirected()) {
						StrokeTransition ftEdge = new StrokeTransition(Duration.millis(10), e.line);
						ftEdge.setToValue(Color.FORESTGREEN);
						st.getChildren().add(ftEdge);
					} else if (isDirected()) {
						FillTransition ftEdge = new FillTransition(Duration.millis(10), e.line);
						ftEdge.setToValue(Color.FORESTGREEN);
						st.getChildren().add(ftEdge);
					}
					// goi de quy thuc hien tang bac cua nut v len 1 bac
					topsortRecursion(v, level + 1, times);
					// <editor-fold defaultstate="collapsed" desc="Animation Control">
					// <editor-fold defaultstate="collapsed" desc="Change Edge colors">

					// sau khi thuc hien xong 1 nhanh(toi nut la) thi set mau cho cac cung va cac canh thanh mau
					// tim xanh (blueviolet)
					if (isUndirected()) {
						StrokeTransition ftEdge = new StrokeTransition(Duration.millis(times), e.line);
						ftEdge.setToValue(Color.BLUEVIOLET);
						st.getChildren().add(ftEdge);
					} else if (isDirected()) {
						FillTransition ftEdge = new FillTransition(Duration.millis(times), e.line);
						ftEdge.setToValue(Color.BLUEVIOLET);
						st.getChildren().add(ftEdge);
					}

					FillTransition ft1 = new FillTransition(Duration.millis(times), v.circle);
					ft1.setToValue(Color.BLUEVIOLET);
					ft1.onFinishedProperty();
					st.getChildren().add(ft1);

				}
			}
		}
		str = "";
		for (int i = 0; i < level; i++) {
			str = str.concat("\t");
		}
		// them nut nay vao list cac nut, sau do thoat khoi nut source khi da duyet het cac nut la
		topSort.add(source.name);
		// in ra ket qua ben hiddenpane
		// <editor-fold defaultstate="collapsed" desc="Recursion exit text">
		str = str.concat("Recursion(" + source.name + ") Exit\n");
		final String str1 = str;
		fd = new FadeTransition(Duration.millis(10), textFlow);
		fd.setOnFinished(e -> {
			textFlow.appendText(str1);
		});
		fd.onFinishedProperty();
		st.getChildren().add(fd);
		// </editor-fold>
	}
}