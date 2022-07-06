package application;

import java.util.LinkedList;

import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.StrokeTransition;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public class BFS extends CanvasController {
	SequentialTransition st = DefineGraph.cref.st;

	BFS(Node source) {
        // tao bien thoi gian theo time  tuy chinh
		int time = DefineGraph.cref.time;
		// set nut dau tien cho vào linkedlist
		source.minDistance = 0;
		source.visited = true;
		LinkedList<Node> q = new LinkedList<Node>();
		q.push(source);
		while (!q.isEmpty()) {
			Node u = q.removeLast();
			// set animation cho nut chua duoc vào
			FillTransition ft = new FillTransition(Duration.millis(time), u.circle);
			if (u.circle.getFill() == Color.BLACK) {
				ft.setToValue(Color.CHOCOLATE);
			}
			st.getChildren().add(ft);
			// add thong tin roi hien thi ben hiddenpane
			String str = "";
			str = str.concat("Popped : Node(" + u.name + ")\n");
			final String str2 = str;
			
			FadeTransition fd = new FadeTransition(Duration.millis(10), textFlow);
			fd.setOnFinished(e -> {
				textFlow.appendText(str2);
			});
			fd.onFinishedProperty();
			st.getChildren().add(fd);
			// hien thi nút duoc duyet trên console
			System.out.println(u.name);
			// duyet các cung lien ke nó
			for (Edge e : u.adjacents) {
				if (e != null) {
					Node v = e.target;
					// xac nhan xem nut nay da duoc di qua hay chua
					if (!v.visited) {
						v.minDistance = u.minDistance + 1;
						v.visited = true;
						q.push(v);// day nó vào linkedlist tren
						v.previous = u;// nut ke truoc cua nut u chinh là v

						// set animation cho cung sang mau xanh
						if (DefineGraph.cref.isUndirected()) {
							StrokeTransition ftEdge = new StrokeTransition(Duration.millis(time), e.line);
							ftEdge.setToValue(Color.FORESTGREEN);
							st.getChildren().add(ftEdge);
						} else if (DefineGraph.cref.isDirected()) {
							FillTransition ftEdge = new FillTransition(Duration.millis(time), e.line);
							ftEdge.setToValue(Color.FORESTGREEN);
							st.getChildren().add(ftEdge);
						}
						// set animation cho node  cho duoc them
						FillTransition ft1 = new FillTransition(Duration.millis(time), v.circle);
						ft1.setToValue(Color.FORESTGREEN);
						ft1.setOnFinished(ev -> {
							v.circle.distance.setText("Dist. : " + v.minDistance);// hien thi chieu sau cua nut do
						});
						ft1.onFinishedProperty();
						st.getChildren().add(ft1);
						// hien thi cac nut cung chieu sau dang duoc duyet toi
						str = "\t";
						str = str+"Pushing : Node(" + v.name + ")\n";
						// thêm các nút màu vào textflow ?? hi?n th? trên hiddenpane
						final String str1 = str;
						FadeTransition fd2 = new FadeTransition(Duration.millis(10), textFlow);
						fd2.setOnFinished(ev -> {
							textFlow.appendText(str1);
						});
						fd2.onFinishedProperty();
						st.getChildren().add(fd2);
						// </editor-fold>
					}
				}
			}
			// set màu tím xanh cho cho các nút da duyet qua
			FillTransition ft2 = new FillTransition(Duration.millis(time), u.circle);
			ft2.setToValue(Color.BLUEVIOLET);
			st.getChildren().add(ft2);
		}

		// set animation de chuyen tu mau tim xanh ve lai mau den Khi thuc hien xong thuat toan
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
			FillTransition ft1 = new FillTransition(Duration.millis(time), source.circle);
			ft1.setToValue(Color.RED);
			ft1.play();
			// sau khi ket thuc thuat toan se  bat che do play cua nut playpause 
			Image image = new Image(getClass().getResourceAsStream("/image/hiPlay.png"));
			DefineGraph.cref.getPlayPauseImage().setImage(image);
			DefineGraph.cref.paused = true;
			DefineGraph.cref.playing = false;
			textFlow.appendText("---Finished--\n");
		});
		st.onFinishedProperty();
		st.play();
		DefineGraph.cref.playing = true;
		DefineGraph.cref.paused = false;
	}

}